/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;
//Work with subversion
import Entidades.CajaMenor;
import Entidades.CentroDeCosto;
import Entidades.Movimientos;
import Entidades.NotificacionCajaMenor;
import Entidades.Persona;
import Entidades.Rol;
import Entidades.RolPermisos;
import Entidades.TipoMovimiento;
import Entidades.UsuarioSistemaRol;
import Entidades.UsuarioSys;
import Utilidades.AdException;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.CajaMenorVO;
import VO.MovimientoVO;
import VO.NotificacionCajaMenorsVO;
import VO.RespuestaVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class CajaMenorDao extends DAO {

    public CajaMenorDao() {
    }
    RespuestaVO respuesta;
    Utilidades utilidades = new Utilidades();
    Entidades.CajaMenor cajaMenor;
    int idCajaMenor = 0;

    public int[] getCentroCostoYPersonaEmpresa() {

        try {
            int[] ids = new int[2];
            begin();
            Criteria criteria = getSession().createCriteria(Persona.class);
            criteria.add(Restrictions.eq("esEmpresa", (short) 1));
            Persona persona = (Persona) criteria.uniqueResult();
            ids[0] = persona.getId();

            criteria = getSession().createCriteria(CentroDeCosto.class);
            criteria.add(Restrictions.eq("esEmpresa", (short) 1));
            CentroDeCosto centroDeCosto = (CentroDeCosto) criteria.uniqueResult();
            ids[1] = centroDeCosto.getId();

            close();
            return ids;
        } catch (Exception e) {
            return null;
        }

    }

    public VO.RespuestaVO create(String nombre, float montoInicio, double valorMinimoNotificacion, double montoMinimoReintegro, int idResponsable, int[] idsUsuatiosToNotificar, int idCentroCosto, int idPersona, short esCuenta) throws AdException {
        respuesta = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
        try {
            int[] ids = this.getCentroCostoYPersonaEmpresa();
            if (ids != null) {
                idPersona = ids[0];
            }
            //idCentroCosto = ids[1];
            begin();

            if (utilidades.buscarPropiedad(Entidades.CajaMenor.class, "nombre", nombre, Utilidades.STRING)) {
                cajaMenor = new CajaMenor();
                cajaMenor.setFechaCreacion(new Date());
                cajaMenor.setMontoInicio(montoInicio);
                cajaMenor.setMontoMinimoReintegro(montoMinimoReintegro);
                cajaMenor.setNombre(nombre);
                cajaMenor.setValor(montoInicio);
                cajaMenor.setValorMinimoNotificacion(valorMinimoNotificacion);
                if (esCuenta == 1) {
                    cajaMenor.setEsCuenta((short) 1);
                } else {
                    cajaMenor.setEsCuenta((short) 0);
                }

                getSession().save(cajaMenor);

                //AQUI
                Serializable serializable = getSession().getIdentifier(cajaMenor);
                int id = Integer.parseInt(serializable.toString());
                if (setResponsable(id, idResponsable)) {
                    if (setCorreosToNotificar(idsUsuatiosToNotificar, id)) {
                        Date fechaMovimiento = new Date();
                        fechaMovimiento.setHours(0);
                        fechaMovimiento.setMinutes(0);
                        fechaMovimiento.setSeconds(0);
                        Criteria criteria = getSession().createCriteria(UsuarioSys.class);
                        criteria.add(Restrictions.eq("id", idResponsable));
                        UsuarioSys usuarioSys = (UsuarioSys) criteria.uniqueResult();
                        criteria = getSession().createCriteria(Persona.class);
                        criteria.add(Restrictions.eq("cedula", usuarioSys.getCedula()));
                        Persona persona = (Persona) criteria.uniqueResult();
                        Movimientos movimientos = new Movimientos(0, new TipoMovimiento((short) 1, ""), new Persona(idPersona), new CajaMenor(id), new CentroDeCosto(idCentroCosto), null, montoInicio, fechaMovimiento, idResponsable, "Apertura Caja Menor", (short) 1, null);

                        getSession().save(movimientos);
                        commit();
                        respuesta = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                    }
                }
            } else {
                respuesta = new RespuestaVO(Mensajes.NOMBRE_CAJA_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.NOMBRE_CAJA_ESTA_ASIGNADO_MSG);
            }
        } catch (Exception e) {
            rollback();
            throw new AdException("  No se creo la caja menor " + e);
        } finally {
            close();
            return respuesta;
        }
    }

    //public RespuestaVO  actualizar(int id,int  idUsuarioSys, String nombre, Date fechaCreacion, double montoInicio, double valorMinimoNotificacion, double montoMinimoReintegro) throws AdException
    public VO.RespuestaVO actualizar(int id, String nombre, double montoInicio, double valorMinimoNotificacion, double montoMinimoReintegro, int idResponsable, int[] idsUsuatiosToNotificar/*,int idCentroCosto,int idPersona*/) throws AdException {

        String msgAlterno = "";
        respuesta = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
        cajaMenor = new CajaMenor();
        begin();
        cajaMenor = (CajaMenor) getSession().get(CajaMenor.class, id);

        try {
            if ((utilidades.buscarPropiedad(CajaMenor.class, "nombre", nombre, Utilidades.STRING)) || cajaMenor.getNombre().equals(nombre)) {
                Criteria criteria = getSession().createCriteria(Movimientos.class);
                criteria.add(Restrictions.eq("cajaMenor", cajaMenor));
                int numeroMovimientoEnCaja = criteria.list().size();
                if (numeroMovimientoEnCaja == 1) {
                    cajaMenor.setNombre(nombre);
                    cajaMenor.setMontoInicio(montoInicio);
                    cajaMenor.setFechaCreacion(new Date());
                    cajaMenor.setValor(montoInicio);
                } else {
                    if (cajaMenor.getValor() <= valorMinimoNotificacion) {
                        // Mandar Correo de Notificacion
                    }
                    String strNombre = "";
                    String strMontoInicio = "";
                    String conjuncionY = "";

                    boolean bandera = false;
                    if (cajaMenor.getMontoInicio() != montoInicio) {
                        strMontoInicio = "el monto inicio";
                        bandera = true;

                    }
                    if (!cajaMenor.getNombre().equals(nombre)) {
                        strNombre = "el nombre";
                        if (bandera) {
                            conjuncionY = "y";
                        }
                    }

                    if (!strNombre.equals("") || !strMontoInicio.equals("")) {
                        msgAlterno = "No actualizo " + strNombre + " " + conjuncionY + " " + strMontoInicio + ": debido a que la caja menor está asociada a un movimiento";
                    }
                }

                cajaMenor.setMontoMinimoReintegro(montoMinimoReintegro);
                cajaMenor.setValorMinimoNotificacion(valorMinimoNotificacion);

                getSession().update(cajaMenor);

                Criteria criteria1 = getSession().createCriteria(NotificacionCajaMenor.class);
                criteria1.add(Restrictions.eq("cajaMenor", new CajaMenor(id)));
                criteria1.add(Restrictions.eq("esResponsable", (short) 1));
                NotificacionCajaMenor notificacionCajaMenor = (NotificacionCajaMenor) criteria1.uniqueResult();

                boolean respuestaSetResponsable = true;
                if (notificacionCajaMenor.getUsuarioSys().getId() != idResponsable) {
                    getSession().delete(notificacionCajaMenor);
                    respuestaSetResponsable = setResponsable(id, idResponsable);
                }
                if (respuestaSetResponsable) {
                    notificacionCajaMenor = new NotificacionCajaMenor();
                    criteria1 = getSession().createCriteria(NotificacionCajaMenor.class);
                    criteria1.add(Restrictions.eq("cajaMenor", new CajaMenor(id)));
                    criteria1.add(Restrictions.eq("esResponsable", (short) 0));
                    List<NotificacionCajaMenor> notiCm = criteria1.list();

                    for (NotificacionCajaMenor notificacionCajaMenor1 : notiCm) {
                        getSession().delete(notificacionCajaMenor1);
                    }

                    if (setCorreosToNotificar(idsUsuatiosToNotificar, id)) {
                        if (numeroMovimientoEnCaja == 1) {
                            criteria1 = getSession().createCriteria(Movimientos.class);
                            criteria1.add(Restrictions.eq("cajaMenor", cajaMenor));
                            Movimientos m = (Movimientos) criteria1.uniqueResult();
                            m.setCajaMenor(new CajaMenor(id));
                            //m.setCentroDeCosto(new CentroDeCosto(idCentroCosto));
                            m.setConcepto(Mensajes.MOVIMIENTO_DE_APERTURA);
                            m.setFechaMovimiento(new Date());
                            m.setIdUsuario(idResponsable);
                            m.setTipoMovimiento(new TipoMovimiento((short) 1, ""));
                            m.setValor(montoInicio);
//                            m.setPersona(new Persona(idPersona));
                            getSession().update(m);
                            commit();
                            respuesta = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG + " - " + msgAlterno);
                        } else {
                            //Movimientos m = new Movimientos();
                            //m = (Movimientos) utilidades.getObjetoEspecifico(Movimientos.class,"cajaMenor",cajaMenor,Utilidades.OBJECT);
//                            m.setCentroDeCosto(new CentroDeCosto(idCentroCosto));
//                            m.setPersona(new Persona(idPersona));
                            //getSession().update(m);
                            commit();
                            respuesta = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG + " - " + msgAlterno);
                        }
                    }

                }

            } else {
                respuesta = new RespuestaVO(Mensajes.NOMBRE_CAJA_ESTA_ASIGNADO, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.NOMBRE_CAJA_ESTA_ASIGNADO_MSG);
            }
        } catch (Exception e) {
            rollback();
            throw new AdException(" No se modifico la caja Menor " + e);
        } finally {
            close();
            return respuesta;
        }
    }

    public VO.RespuestaVO eliminar(int id) throws AdException {
        respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        MovimientosDao movimientosDao = new MovimientosDao();

        try {
            begin();
            cajaMenor = new CajaMenor();
            cajaMenor = (CajaMenor) getSession().get(CajaMenor.class, id);

            if (!utilidades.buscarPropiedad(CajaMenor.class, "id", id, Utilidades.OBJECT)) {
                close();
                List<MovimientoVO> listMovimientos = movimientosDao.listarMovimientosCajaMenor(id, null, null, (short) 1);
                begin();
                if (listMovimientos.size() == 1 && listMovimientos.get(0).getIsApertura() == (short) 1) {
                    getSession().delete(getSession().get(CajaMenor.class, id));
                    commit();
                    respuesta = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
                } else {
                    respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + "\n" + Mensajes.NO_PUEDE_ELIMINAR_CM_TIENE_VARIOS_MOVI);
                }
            } else {
                respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.CAJA_MENOR_NO_EXISTE);
            }
        } catch (Exception e) {
            rollback();
            respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + "\n Detalle: " + e);
            throw new AdException(" No se elimino el caja menor " + e);
        } finally {
            close();
            return respuesta;
        }
    }

    public List listar(int idUsuario, short tipoLista) throws AdException {
        //respuesta = new RespuestaVO(Mensajes.NO_OBTUVO_DATOS, Mensajes.NO_OBTUVO_DATOS_MSG);
        List<CajaMenor> cajaMenores = new ArrayList<CajaMenor>();
        List<CajaMenorVO> cajaMenorVOs = new ArrayList<CajaMenorVO>();
        try {
            if (getSession().isOpen()) {
                close();
            }

            begin();
            Criteria criteria = getSession().createCriteria(UsuarioSys.class);
            criteria.add(Restrictions.eq("id", idUsuario));
            UsuarioSys usuarioSys = new UsuarioSys();
            usuarioSys = (UsuarioSys) criteria.uniqueResult();
            short esAdmin = usuarioSys.getIsAdmin();

            if (esAdmin != 1) {
                List<NotificacionCajaMenor> notificacionCajaMenors = new ArrayList<NotificacionCajaMenor>();
                criteria = getSession().createCriteria(NotificacionCajaMenor.class);
                criteria.add(Restrictions.eq("usuarioSys", new UsuarioSys(idUsuario)));
                criteria.add(Restrictions.eq("esResponsable", (short) 1));
                notificacionCajaMenors = criteria.list();

                for (NotificacionCajaMenor notificacionCajaMenor : notificacionCajaMenors) {

                    if (tipoLista == 1) {
                        if (notificacionCajaMenor.getCajaMenor().getEsCuenta() == (short) 1) {
                            cajaMenores.add(notificacionCajaMenor.getCajaMenor());
                        }

                    } else {
                        if (notificacionCajaMenor.getCajaMenor().getEsCuenta() == (short) 0) {
                            cajaMenores.add(notificacionCajaMenor.getCajaMenor());
                        }
                    }
                }
            } else {
                cajaMenores = this.listarCajasCuentasAdmin(tipoLista);
            }
            commit();
            cajaMenorVOs = this.convertCajaMenoresToCajasMenoresVO(cajaMenores);


        } catch (HibernateException e) {
            cajaMenores = null;
            cajaMenorVOs = null;
            rollback();
            throw new AdException(" No se obtuvieron la lista de cajas menores " + e);
        } finally {
            //close();
            return cajaMenorVOs;
        }

    }

    private List<CajaMenorVO> convertCajaMenoresToCajasMenoresVO(List<CajaMenor> cajaMenors) {
        List<CajaMenorVO> cajaMenorVOs = new ArrayList<CajaMenorVO>();
        for (CajaMenor cajaMenorx : cajaMenors) {
            cajaMenorVOs.add(convertCajaMenorToCajaMenorVO(cajaMenorx));
        }
        return cajaMenorVOs;

    }

    private CajaMenorVO convertCajaMenorToCajaMenorVO(CajaMenor cajaMenor) {
        return new CajaMenorVO(cajaMenor.getId(), cajaMenor.getNombre(), cajaMenor.getValor(), cajaMenor.getFechaCreacion(), cajaMenor.getMontoInicio(), cajaMenor.getValorMinimoNotificacion(), cajaMenor.getMontoMinimoReintegro(), convertNotificacionCajaMenorsToNotificacionCajaMenorsVO(cajaMenor.getNotificacionCajaMenors()), cajaMenor.getEsCuenta());
    }

    private List<NotificacionCajaMenorsVO> convertNotificacionCajaMenorsToNotificacionCajaMenorsVO(Set setNotificacionCajaMenors) {
        List<NotificacionCajaMenorsVO> notificacionCajaMenorsVOs = new ArrayList<NotificacionCajaMenorsVO>();
        for (Object setNotificacionCajaMenor : setNotificacionCajaMenors) {
            NotificacionCajaMenor notificacionCajaMenor = new NotificacionCajaMenor();
            notificacionCajaMenor = (NotificacionCajaMenor) setNotificacionCajaMenor;
            NotificacionCajaMenorsVO notificacionCajaMenorsVO = new NotificacionCajaMenorsVO();
            notificacionCajaMenorsVO = this.convertNotificacionCajaMenorToNotificacionCajaMenorVO(notificacionCajaMenor);
            notificacionCajaMenorsVOs.add(notificacionCajaMenorsVO);
        }
        return notificacionCajaMenorsVOs;
    }

    private NotificacionCajaMenorsVO convertNotificacionCajaMenorToNotificacionCajaMenorVO(NotificacionCajaMenor notificacionCajaMenor) {
        UsuarioSistemaDao usuarioSistemaDao = new UsuarioSistemaDao();
        return new NotificacionCajaMenorsVO(notificacionCajaMenor.getId(), notificacionCajaMenor.getEsResponsable(), usuarioSistemaDao.convertUsuarioSistemaToUsuarioSistemaVO(notificacionCajaMenor.getUsuarioSys()));
    }

    private boolean setResponsable(int idCajaMenor, int idResponsable) {
        boolean res = false;
        try {
            getSession().save(new NotificacionCajaMenor(0, new UsuarioSys(idResponsable), new CajaMenor(idCajaMenor), (short) 1));
            res = true;
        } catch (Exception e) {
            res = false;
        } finally {
            return res;
        }
    }

    private boolean setCorreosToNotificar(int idsUsuatiosToNotificar[], int IdCajaMenor) {
        boolean resp = false;
        NotificacionCajaMenor notificacionCajaMenor;
        try {
            for (int idUsuatiosToNotificar : idsUsuatiosToNotificar) {
                notificacionCajaMenor = new NotificacionCajaMenor();
                notificacionCajaMenor.setUsuarioSys(new UsuarioSys(idUsuatiosToNotificar));
                notificacionCajaMenor.setEsResponsable((short) 0);
                notificacionCajaMenor.setCajaMenor(new CajaMenor(IdCajaMenor));
                getSession().save(notificacionCajaMenor);
            }

            resp = true;

        } catch (Exception e) {
            resp = false;
        } finally {
            return resp;
        }
    }

    private List<NotificacionCajaMenor> ordenarNotiCajasMsByEsCuenta(List<NotificacionCajaMenor> notificacionCajaMenors) {
        List<NotificacionCajaMenor> lista1 = new ArrayList<NotificacionCajaMenor>();
        List<NotificacionCajaMenor> lista2 = new ArrayList<NotificacionCajaMenor>();
        List<NotificacionCajaMenor> listaRetorno = new ArrayList<NotificacionCajaMenor>();
        for (NotificacionCajaMenor notificacionCajaMenor : notificacionCajaMenors) {
            if (notificacionCajaMenor.getCajaMenor().getEsCuenta() == (short) 1) {
                lista2.add(notificacionCajaMenor);
            } else {
                lista1.add(notificacionCajaMenor);
            }
        }
        for (NotificacionCajaMenor notificacionCajaMenor : lista1) {
            listaRetorno.add(notificacionCajaMenor);
        }
        for (NotificacionCajaMenor notificacionCajaMenor : lista2) {
            listaRetorno.add(notificacionCajaMenor);
        }
        return listaRetorno;
    }

    private List<CajaMenorVO> ordenarCajasMsByEsCuenta(List<CajaMenorVO> cajaMenorVOs) {
        List<CajaMenorVO> lista1 = new ArrayList<CajaMenorVO>();
        List<CajaMenorVO> lista2 = new ArrayList<CajaMenorVO>();
        List<CajaMenorVO> listaRetorno = new ArrayList<CajaMenorVO>();
        for (CajaMenorVO cajaMenorVO : cajaMenorVOs) {
            if (cajaMenorVO.getEsCuenta() == (short) 1) {
                lista2.add(cajaMenorVO);
            } else {
                lista1.add(cajaMenorVO);
            }
        }
        for (CajaMenorVO cajaMenorVO1 : lista1) {
            listaRetorno.add(cajaMenorVO1);
        }
        for (CajaMenorVO cajaMenorVO2 : lista2) {
            listaRetorno.add(cajaMenorVO2);
        }
        return listaRetorno;
    }
    private static final short TODOS_LOS_DESTINOS = 0;
    private static final short CAJAS_MENORES = 1;
    private static final short CUENTAS_BANCO = 2;

    public List cajasUsuarioEspecifico(int idUsuario, short tipoDestinoMovimiento) {
        List<CajaMenorVO> cajaMenorVOs = null;
        try {
            begin();
            UsuarioSys usuarioSys = new UsuarioSys();
            usuarioSys = (UsuarioSys) utilidades.getObjetoEspecifico(UsuarioSys.class, "id", idUsuario, Utilidades.INT);

            if (usuarioSys.getIsAdmin() != 1) {
                Criteria criteria = getSession().createCriteria(NotificacionCajaMenor.class);
                criteria.add(Restrictions.eq("usuarioSys", new UsuarioSys(idUsuario)));
                criteria.add(Restrictions.eq("esResponsable", (short) 1));
                List<NotificacionCajaMenor> notificacionCajaMenores = criteria.list();
                notificacionCajaMenores = this.ordenarNotiCajasMsByEsCuenta(notificacionCajaMenores);
                close();
                cajaMenorVOs = new ArrayList<CajaMenorVO>();
                for (NotificacionCajaMenor notificacionCajaMenor : notificacionCajaMenores) {

                    if (tipoDestinoMovimiento == TODOS_LOS_DESTINOS) {
                        CajaMenorVO cmvo = new CajaMenorVO(notificacionCajaMenor.getCajaMenor().getId(), notificacionCajaMenor.getCajaMenor().getNombre(), notificacionCajaMenor.getCajaMenor().getValor(), notificacionCajaMenor.getCajaMenor().getEsCuenta());
                        cajaMenorVOs.add(cmvo);
                    } else {
                        short esCuenta = 0;

                        if (tipoDestinoMovimiento == CAJAS_MENORES) {
                            esCuenta = 0;
                        }
                        if (tipoDestinoMovimiento == CUENTAS_BANCO) {
                            esCuenta = 1;
                        }

                        if (notificacionCajaMenor.getCajaMenor().getEsCuenta() == esCuenta) {
                            CajaMenorVO cmvo = new CajaMenorVO(notificacionCajaMenor.getCajaMenor().getId(), notificacionCajaMenor.getCajaMenor().getNombre(), notificacionCajaMenor.getCajaMenor().getValor(), notificacionCajaMenor.getCajaMenor().getEsCuenta());
                            cajaMenorVOs.add(cmvo);
                        }
                    }

                }
            } else {
                Criteria criteria = getSession().createCriteria(CajaMenor.class);
                switch (tipoDestinoMovimiento) {
                    case TODOS_LOS_DESTINOS:

                        break;
                    case CAJAS_MENORES:
                        criteria.add(Restrictions.eq("esCuenta", (short) 0));
                        break;
                    case CUENTAS_BANCO:
                        criteria.add(Restrictions.eq("esCuenta", (short) 1));
                        break;
                }

                List<CajaMenor> cajaMenors = new ArrayList<CajaMenor>();
                cajaMenors = criteria.list();
                cajaMenorVOs = this.convertCajaMenoresToCajasMenoresVO(cajaMenors);
                cajaMenorVOs = this.ordenarCajasMsByEsCuenta(cajaMenorVOs);
            }

        } catch (Exception e) {
            cajaMenorVOs = null;
        } finally {
            close();
            return cajaMenorVOs;
        }
    }

    private List<CajaMenor> listarCajasCuentasAdmin(short tipoLista) {
        List<CajaMenor> cajaMenors = new ArrayList<CajaMenor>();
        try {
            Criteria criteria = getSession().createCriteria(CajaMenor.class);
            switch (tipoLista) {
                case 0:
                    criteria.add(Restrictions.eq("esCuenta", (short) 0));
                    break;
                case 1:
                    criteria.add(Restrictions.eq("esCuenta", (short) 1));
                    break;
            }
            cajaMenors = criteria.list();
        } catch (Exception e) {
            cajaMenors = null;
        } finally {
            return cajaMenors;
        }
    }

    public List<CajaMenorVO> listarTodasCajas(short tipoLista) {
        List<CajaMenor> cajaMenors = new ArrayList<CajaMenor>();
        List<CajaMenorVO> cajaMenorVOs = new ArrayList<CajaMenorVO>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CajaMenor.class);
            switch (tipoLista) {
                case 0:
                    criteria.add(Restrictions.eq("esCuenta", (short) 0));
                    break;
                case 1:
                    criteria.add(Restrictions.eq("esCuenta", (short) 1));
                    break;
            }
            cajaMenors = criteria.list();
            cajaMenorVOs = this.convertCajaMenoresToCajasMenoresVO(cajaMenors);
        } catch (Exception e) {
            cajaMenorVOs = null;
        } finally {
            close();
            return cajaMenorVOs;
        }
    }
}
