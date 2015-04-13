/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.CajaMenor;
import Entidades.CentroDeCosto;
import Entidades.Movimientos;
import Entidades.NotificacionCajaMenor;
import Entidades.Persona;
import Entidades.TipoMovimiento;
import Entidades.UsuarioSys;
import Utilidades.AdException;
import Utilidades.DAO;
import Utilidades.EtiquetasHtml;
import Utilidades.Mail;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.CajaMenorVO;
import VO.CentroCostoVO;
import VO.IngresoEgresoVO;
import VO.MovimientoVO;
import VO.PersonaVO;
import VO.RespuestaVO;
import VO.ReporteMovimientosVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class MovimientosDao extends DAO {

    Movimientos movimiento;
    Utilidades utilidades = new Utilidades();
    RespuestaVO respuestaVO;
    public static final short INGRESO = 1;
    public static final short EGRESO = 2;
    private java.lang.String nombreCajaMenor = "sin caja menor";
    public static boolean BUSQUEDA_SERVICIO = true;

    private boolean compararFechas(Date fechaCreacion, Date fechaApertura) {
        boolean retorno = false;
        if (fechaCreacion.getYear() < fechaApertura.getYear()) {
            retorno = true;
        } else {
            if (fechaCreacion.getYear() == fechaApertura.getYear()) {
                if (fechaCreacion.getMonth() < fechaApertura.getMonth()) {
                    retorno = true;
                } else {
                    if (fechaCreacion.getMonth() == fechaApertura.getMonth()) {
                        if (fechaCreacion.getDate() < fechaApertura.getDate()) {
                            retorno = true;
                        }
                    }
                }
            }
        }
        return retorno;

    }

    public RespuestaVO create(short idTipoMovimiento, int idPersona, int idCajaMenor, int idCentroDeCosto, double valor, Date fechaMovimiento, java.lang.String concepto) throws AdException {
        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
        int idResponsableCM = 0;
        fechaMovimiento.setHours(0);
        fechaMovimiento.setMinutes(0);
        fechaMovimiento.setSeconds(1);
        try {
            // Movimientos
            /*if (idTipoMovimiento == 1) {
            CajaMenorDao cajaMenorDao = new CajaMenorDao();
            int[] ids = cajaMenorDao.getCentroCostoYPersonaEmpresa();
            idPersona = ids[0];
            }*/

            begin();
            CajaMenor cm = (CajaMenor) getSession().get(CajaMenor.class, idCajaMenor);

            /**********************************************************************************************/
            if (cm != null) {
                Criteria criteria = getSession().createCriteria(NotificacionCajaMenor.class);
                criteria.add(Restrictions.eq("cajaMenor", cm));
                criteria.add(Restrictions.eq("esResponsable", (short) 1));
                NotificacionCajaMenor notificacionCajaMenor = new NotificacionCajaMenor();
                notificacionCajaMenor = (NotificacionCajaMenor) criteria.uniqueResult();
                if (notificacionCajaMenor != null) {
                    idResponsableCM = notificacionCajaMenor.getUsuarioSys().getId();
                }

                Criteria c = getSession().createCriteria(Movimientos.class);
                c.add(Restrictions.eq("cajaMenor", cm));
                c.add(Restrictions.eq("isApertura", (short) 1));
                Movimientos m = (Movimientos) c.uniqueResult();

                boolean fechaMenor = false;

                if (fechaMovimiento.getYear() < m.getFechaMovimiento().getYear()) {
                    fechaMenor = true;
                } else {
                }
                //if(fechaMovimiento.compareTo(m.getCajaMenor().getFechaCreacion()) < 0)
                if (this.compararFechas(fechaMovimiento, m.getCajaMenor().getFechaCreacion())) {
                    return respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + " \n La fecha del movimiento debe ser superior a la fecha de creaciÃ³n de la caja menor");
                }
            }
            /**********************************************************************************************/
            if (idTipoMovimiento == (short) 1 || idCajaMenor == -1 || valor <= cm.getValor()) {
                if (!utilidades.buscarPropiedad(CajaMenor.class, "id", idCajaMenor, Utilidades.INT) || idCajaMenor == -1) {
                    if (!utilidades.buscarPropiedad(Persona.class, "id", idPersona, Utilidades.INT)) {
                        if (!utilidades.buscarPropiedad(CentroDeCosto.class, "id", idCentroDeCosto, Utilidades.INT)) {
                            if (idCajaMenor == -1 || !utilidades.buscarPropiedad(UsuarioSys.class, "id", idResponsableCM, Utilidades.INT)) {
                                movimiento = new Movimientos();
                                /*********************************/
                                if (idCajaMenor == -1) {
                                    movimiento.setCajaMenor(null);
                                } else {
                                    movimiento.setCajaMenor(new CajaMenor(idCajaMenor));
                                }
                                /*********************************/
                                movimiento.setCentroDeCosto(new CentroDeCosto(idCentroDeCosto));
                                movimiento.setConcepto(concepto);
                                movimiento.setFechaMovimiento(fechaMovimiento);
                                movimiento.setIdUsuario(idResponsableCM);
                                movimiento.setPersona(new Persona(idPersona, ""));
                                movimiento.setTipoMovimiento(new TipoMovimiento(idTipoMovimiento, ""));
                                movimiento.setValor(valor);
                                Serializable serializable = getSession().save(movimiento);
                                int idMovimiento = Integer.parseInt(serializable.toString());


                                if (idCajaMenor != -1) {
                                    double saldo = this.fijarSaldoCaja(cm);
                                    if (saldo != -1) {
                                        cm.setValor(saldo);
                                        getSession().update(cm);
                                        commit();
                                    } else {
                                        return respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + "\n No se asigno el saldo a la caja menor");
                                    }
                                } else {
                                    commit();
                                }




                                java.lang.String mensajeValorNotificacion = "";
                                if (idCajaMenor != -1) {
                                    begin();
                                    cm = new CajaMenor();
                                    cm = (CajaMenor) utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenor, Utilidades.INT);
                                    nombreCajaMenor = cm.getNombre();
                                    close();
                                    if (cm.getValor() <= cm.getValorMinimoNotificacion()) {
                                        mensajeValorNotificacion = EtiquetasHtml.NOTA + Mensajes.CAJA_SUPERADO_MONTO_NOTIFICACION;

                                    }
                                }

                                java.lang.String nombreMovimiento = "";
                                if (idTipoMovimiento == INGRESO) {
                                    nombreMovimiento = "Ingreso";
                                } else {
                                    nombreMovimiento = "Egreso";
                                }

                                java.lang.String msg = Mail.ID_MOVIMIENTO + idMovimiento + EtiquetasHtml.BR;
                                msg += Mail.CONCEPTO_MOVIMIENTO + concepto + EtiquetasHtml.BR;
                                msg += Mail.TIPO_MOVIMIENTO + nombreMovimiento + EtiquetasHtml.BR;
                                msg += Mail.CAJA_MENOR + nombreCajaMenor + EtiquetasHtml.BR;
                                msg += Mail.VALOR_MOVIMIENTO + valor + EtiquetasHtml.BR;
                                msg += mensajeValorNotificacion;
                                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG + this.enviarNotificacion(idCajaMenor, msg, Mail.ASUNTO_MOVIMIENTO));

                            } else {
                                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + ": " + Mensajes.RESPONSABLE_NO_EXISTE_MSG);
                            }
                        } else {
                            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + ": " + Mensajes.CENTRO_COSTO_NO_EXISTE_MSG);
                        }
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + ": " + Mensajes.PERSONA_NO_EXISTE_MSG);
                    }
                } else {
                    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + ": " + Mensajes.CAJA_MENOR_NO_EXISTE_MSG);
                }
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_REALIZO_MOVIMIENTO, Mensajes.NO_REALIZO_MOVIMIENTO_MSG);
            }
        } catch (Exception e) {
            rollback();
            throw new AdException(" No se creo el Movimiento " + e);
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO actualizar(int idMovimiento, int idAdmin, short idTipoMovimiento, int idPersona, int idCajaMenor, int idCentroDeCosto, double valor, Calendar fechaMovimiento, String concepto) {
        RespuestaVO resVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
        utilidades = new Utilidades();
        UsuarioSys usys;
        Movimientos movimientos;
        CajaMenor cm = null;
        int idResponsable = -1;
        String nombreTipoMovimiento = "";
        TipoMovimiento tpm = null;
        double valorDeCajaMenor = 0;
        String nombreCajaMenor = "";
        int idCajaMenorAnterior;

        /*if (idTipoMovimiento == (short) 1) {
        CajaMenorDao cajaMenorDao = new CajaMenorDao();
        int[] ids = cajaMenorDao.getCentroCostoYPersonaEmpresa();
        idPersona = ids[0];
        idCentroDeCosto = ids[1];
        }*/
        try {
            begin();
            usys = new UsuarioSys();
            usys = (UsuarioSys) utilidades.getObjetoEspecifico(UsuarioSys.class, "id", idAdmin, Utilidades.INT);
            if (usys.getIsAdmin() == 1) {
                RespuestaVO rvo = new RespuestaVO();
                rvo = this.comprobarExistenciaDeDatos(idTipoMovimiento, idPersona, idCajaMenor, idCentroDeCosto, idMovimiento);
                if (rvo.getIdRespuesta() == Mensajes.DATO_EXISTE) {
                    movimientos = new Movimientos();
                    movimientos = (Movimientos) utilidades.getObjetoEspecifico(Movimientos.class, "id", idMovimiento, Utilidades.INT);

                    if (movimientos.getIsApertura() == 1) {
                        resVO = this.modificarMovimientoApertura(movimientos, idPersona, idCentroDeCosto, valor, fechaMovimiento.getTime());
                        return resVO;
                    } else {
                        if (idCajaMenor != -1) {
                            cm = new CajaMenor();
                            cm = (CajaMenor) utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenor, Utilidades.INT);
                            valorDeCajaMenor = cm.getValor();
                            nombreCajaMenor = cm.getNombre();
                            idResponsable = this.getResponsableCajaMenor(cm);
                        }
                        if (valor <= valorDeCajaMenor || idTipoMovimiento == (short) 1 || idCajaMenor == -1) {
                            tpm = new TipoMovimiento();
                            tpm = (TipoMovimiento) utilidades.getObjetoEspecifico(TipoMovimiento.class, "id", idTipoMovimiento, Utilidades.SHORT);
                            nombreTipoMovimiento = tpm.getNombre();

                            /***************************************************************/
                            idCajaMenorAnterior = movimientos.getCajaMenor().getId();

                            if (idCajaMenor != -1) {
                                movimientos.setCajaMenor(new CajaMenor(idCajaMenor));
                            } else {
                                movimientos.setCajaMenor(null);
                            }

                            movimientos.setCentroDeCosto(new CentroDeCosto(idCentroDeCosto));
                            movimientos.setConcepto(concepto);
                            movimientos.setFechaMovimiento(fechaMovimiento.getTime());
                            movimientos.setIdUsuario(idResponsable);
                            movimientos.setPersona(new Persona(idPersona));
                            movimientos.setTipoMovimiento(new TipoMovimiento(idTipoMovimiento, ""));
                            movimientos.setValor(valor);
                            getSession().update(movimientos);

                            if (idCajaMenor != idCajaMenorAnterior) {
                                if (idCajaMenor != -1) {
                                    if (this.fijarSaldoCaja((CajaMenor) utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenor, Utilidades.INT)) == -1) {
                                        return resVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + "\n Ha ocurrido una excepción en el momento de fijar el saldo a la caja menor");
                                    }
                                }

                                if (idCajaMenorAnterior != -1) {
                                    if (this.fijarSaldoCaja((CajaMenor) utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenorAnterior, Utilidades.INT)) == -1) {
                                        return resVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + "\n Ha ocurrido una excepción en el momento de fijar el saldo a la caja menor");
                                    }
                                }
                            } else {
                                if (idCajaMenor != -1) {
                                    if (this.fijarSaldoCaja((CajaMenor) utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenor, Utilidades.INT)) == -1) {
                                        return resVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + "\n Ha ocurrido una excepción en el momento de fijar el saldo a la caja menor");
                                    }
                                }
                            }
                            commit();
                            close();

                            String mensajeValorNotificacion = "";
                            if (idCajaMenor != -1) {
                                begin();
                                cm = new CajaMenor();
                                cm = (CajaMenor) utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenor, Utilidades.INT);
                                close();
                                if (cm.getValor() <= cm.getValorMinimoNotificacion()) {
                                    mensajeValorNotificacion = EtiquetasHtml.NOTA + Mensajes.CAJA_SUPERADO_MONTO_NOTIFICACION;
                                }
                            }
                            resVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
                            String msg = Mail.ID_MOVIMIENTO + idMovimiento + EtiquetasHtml.BR;
                            msg += Mail.CONCEPTO_MOVIMIENTO + concepto + EtiquetasHtml.BR;
                            msg += Mail.TIPO_MOVIMIENTO + nombreTipoMovimiento + EtiquetasHtml.BR;
                            msg += Mail.CAJA_MENOR + nombreCajaMenor + EtiquetasHtml.BR;
                            msg += Mail.VALOR_MOVIMIENTO + valor + EtiquetasHtml.BR;
                            msg += mensajeValorNotificacion;
                            resVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG + this.enviarNotificacion(idCajaMenor, msg, Mail.ASUNTO_MODIFICACION_MOVIMIENTO));

                        } else {
                            close();
                            resVO = new RespuestaVO(Mensajes.NO_REALIZO_MOVIMIENTO, Mensajes.NO_REALIZO_MOVIMIENTO_MSG);
                        }
                    }
                } else {
                    close();
                    //return resVO;
                }
            } else {
                resVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.ACCION_PARA_ADMIN_SISTEMA);
            }

        } catch (Exception e) {
            rollback();
            throw new AdException(" No se creo el Movimiento " + e);
        } finally {
            close();
            System.out.print(resVO.getMensajeRespuesta());
            return resVO;
        }
    }

    public List<MovimientoVO> listarMovimientosCajaMenor(int id, Date fechaInicio, Date fechaFin, short tipoLista) {
        List<MovimientoVO> movimientoVOs = null;
        CajaMenor cajaMenor = new CajaMenor();
        List<Movimientos> movimientoses = null;
        boolean callGetSaldosParcial = false;
        Criteria criteria;
        CentroDeCosto centroDeCosto = new CentroDeCosto();
        Persona persona = new Persona();
        List<MovimientoVO> movimientoVOs1 = new ArrayList<MovimientoVO>();
        try {
            begin();
            switch (tipoLista) {
                case 1://1 PARA LISTAR MOVIMIENTOS POR ID DE CAJA MENOR
                    criteria = getSession().createCriteria(CajaMenor.class);
                    criteria.add(Restrictions.eq("id", id));
                    cajaMenor = (CajaMenor) criteria.uniqueResult();
                    break;
                case 2://2 PARA LISTAR MOVIMIENTOS POR ID DE CENTRO DE COSTO
                    criteria = getSession().createCriteria(CentroDeCosto.class);
                    criteria.add(Restrictions.eq("id", id));
                    centroDeCosto = (CentroDeCosto) criteria.uniqueResult();
                    break;

                case 3://2 PARA LISTAR MOVIMIENTOS POR ID DE PERSONA
                    criteria = getSession().createCriteria(Persona.class);
                    criteria.add(Restrictions.eq("id", id));
                    persona = (Persona) criteria.uniqueResult();
                    break;
            }


            if (cajaMenor != null || centroDeCosto != null || persona != null/*|| idCajaMenor == -1*/) {
                /*if(idCajaMenor == -1)
                {
                cajaMenor = new CajaMenor();
                cajaMenor = null;
                criteria = null;
                criteria = getSession().createCriteria(Movimientos.class);
                criteria.add(Restrictions.isNull("cajaMenor"));

                }
                else
                {*/
                criteria = null;
                criteria = getSession().createCriteria(Movimientos.class);
                switch (tipoLista) {
                    case 1://1 PARA LISTAR MOVIMIENTOS POR ID DE CAJA MENOR
                        criteria.add(Restrictions.eq("cajaMenor", cajaMenor));
                        break;
                    case 2://2 PARA LISTAR MOVIMIENTOS POR ID DE CENTRO DE COSTO
                        criteria.add(Restrictions.eq("centroDeCosto", centroDeCosto));
                        break;
                    case 3://2 PARA LISTAR MOVIMIENTOS POR ID DE CENTRO DE COSTO
                        criteria.add(Restrictions.eq("persona", persona));
                        break;
                }

                //}

                if (fechaInicio == null || fechaFin == null) {
                    if (BUSCAR_POR_PARAMETRO == false) {
                        if (fechaInicio == null) {
                            fechaInicio = new Date();
                            fechaInicio.setDate(0);
                        }
                        if (fechaFin == null) {
                            fechaFin = new Date();
                            fechaFin.setDate(31);

                        }
                    }
                }
                if (fechaFin != null && fechaInicio != null) {
                    fechaInicio.setHours(0);
                    fechaInicio.setMinutes(0);
                    fechaInicio.setSeconds(0);
                    long t = fechaInicio.getTime();
                    java.sql.Date dt = new java.sql.Date(t);

                    fechaFin.setHours(23);
                    fechaFin.setMinutes(59);
                    fechaFin.setSeconds(59);
                    long t2 = fechaFin.getTime();
                    java.sql.Date dt2 = new java.sql.Date(t2);

                    criteria.add(Restrictions.between("fechaMovimiento", dt, dt2));
                    callGetSaldosParcial = true;
                }

                criteria.addOrder(Order.asc("fechaMovimiento"));
                criteria.addOrder(Order.asc("id"));
                movimientoses = criteria.list();

                movimientoVOs1 = this.solucion(movimientoses, id);
                MovimientoVO movimientoVO;
                double saldo = 0;
                movimientoVOs = new ArrayList();
                double[] saldos = new double[movimientoses.size()];

                if (callGetSaldosParcial) {
                    int[] idsMovimientos = new int[movimientoses.size()];
                    int index = 0;
                    for (Movimientos movimientos : movimientoses) {
                        idsMovimientos[index] = movimientos.getId();
                        index++;
                    }
                    saldos = this.getSaldoParcial(cajaMenor, idsMovimientos);

                }
                int index = 0;
                for (Movimientos movimientos : movimientoses) {
                    double valor = 0;
                    valor = movimientos.getValor();
                    if (movimientos.getIsApertura() == (short) 1) {
                        saldo = valor;

                    } else {
                        if (movimientos.getTipoMovimiento().getId() == INGRESO) {
                            saldo = saldo + valor;
                        } else {
                            saldo = saldo - valor;
                            valor = valor * -1;
                        }
                    }
                    movimientoVO = new MovimientoVO();
                    if (callGetSaldosParcial) {
                        saldo = saldos[index];
                        index++;
                    }

                    movimientoVO = new MovimientoVO(movimientos.getId(), movimientos.getConcepto(), movimientos.getCentroDeCosto().getNombre(), movimientos.getPersona().getNombreRs(), valor, saldo, movimientos.getFechaMovimiento(), movimientos.getIsApertura(), movimientos.getTipoMovimiento().getId(), movimientos.getCentroDeCosto().getId(), movimientos.getPersona().getId(), new CajaMenorVO(movimientos.getCajaMenor().getId(), movimientos.getCajaMenor().getNombre()));
                    movimientoVOs.add(movimientoVO);
                }
            }
        } catch (Exception e) {
            System.out.println("");
        } finally {
            close();

            //return movimientoVOs;
            if (BUSQUEDA_SERVICIO) {
                return movimientoVOs1;
            } else {
                return movimientoVOs;
            }
        }
    }

    public RespuestaVO eliminar(int idMovimiento, int idAdmin) {
        RespuestaVO respuestaVOEliminar = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        UsuarioSys usuarioSys = new UsuarioSys();
        Movimientos movimientos = new Movimientos();
        try {
            begin();
            usuarioSys = (UsuarioSys) utilidades.getObjetoEspecifico(UsuarioSys.class, "id", idAdmin, Utilidades.INT);
            if (usuarioSys.getIsAdmin() == (short) 0) {
                return respuestaVOEliminar = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.ACCION_PARA_ADMIN_SISTEMA);
            } else {
                if (utilidades.buscarPropiedad(UsuarioSys.class, "id", idAdmin, Utilidades.INT)) {
                    return respuestaVOEliminar = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.USUARIO_LOGEADO_NO_EXISTE_MSG);
                } else {
                    if (utilidades.buscarPropiedad(Movimientos.class, "id", idMovimiento, Utilidades.INT)) {
                        return respuestaVOEliminar = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.MOVIMIENTO_NO_EXISTE);
                    } else {
                        movimientos = (Movimientos) utilidades.getObjetoEspecifico(Movimientos.class, "id", idMovimiento, Utilidades.INT);
                        if (movimientos.getIsApertura() == (short) 1) {
                            return respuestaVOEliminar = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.NO_PUEDE_ELIMINAR_MOVIMIENTO_APERTURA_MSG);
                        } else {
                            getSession().delete(utilidades.getObjetoEspecifico(Movimientos.class, "id", idMovimiento, Utilidades.INT));
                            this.fijarSaldoCaja(movimientos.getCajaMenor());
                            commit();
                            return respuestaVOEliminar = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
                        }
                    }
                }
            }
        } catch (Exception e) {
            respuestaVOEliminar = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + "\n" + e);
        } finally {
            return respuestaVOEliminar;
        }
    }

    private double fijarSaldoCaja(CajaMenor cajaMenor) {
        double valor = 0;
        double saldo = -1;
        try {
            Criteria criteria = getSession().createCriteria(Movimientos.class);
            criteria.add(Restrictions.eq("cajaMenor", cajaMenor));
            List<Movimientos> movimientoses = criteria.list();
            for (Movimientos movimientos : movimientoses) {
                valor = 0;
                valor = movimientos.getValor();
                if (movimientos.getIsApertura() == 1) {
                    saldo = valor;
                } else {
                    if (movimientos.getTipoMovimiento().getId() == INGRESO) {
                        saldo = saldo + valor;
                    } else {
                        saldo = saldo - valor;
                    }
                }
            }
            if (saldo != -1) {
                cajaMenor.setValor(saldo);
            }

        } catch (Exception e) {
            saldo = -1;
        } finally {
            return saldo;
        }

    }

    private double[] getSaldoParcial(CajaMenor cajaMenor, int[] idsMovimientos) {
        double saldos[] = new double[idsMovimientos.length];
        double valor = 0;
        double saldo = 0;

        try {

            Criteria criteria = getSession().createCriteria(Movimientos.class);
            criteria.add(Restrictions.eq("cajaMenor", cajaMenor));
            List<Movimientos> movimientoses = criteria.list();
            int index = 0;
            for (Movimientos movimientos : movimientoses) {
                valor = 0;
                valor = movimientos.getValor();
                if (movimientos.getIsApertura() == 1) {
                    saldo = valor;
                } else {
                    if (movimientos.getTipoMovimiento().getId() == INGRESO) {
                        saldo = saldo + valor;
                    } else {
                        saldo = saldo - valor;
                    }
                }
                for (int i : idsMovimientos) {
                    if (movimientos.getId() == i) {
                        saldos[index] = saldo;
                        index++;
                    }
                }

            }

        } catch (Exception e) {
        } finally {
            return saldos;
        }
    }

    private RespuestaVO comprobarExistenciaDeDatos(short idTipoMovimiento, int idPersona, int idCajaMenor, int idCentroDeCosto, int idMovimiento) {
        utilidades = new Utilidades();
        try {
            if (utilidades.getObjetoEspecifico(TipoMovimiento.class, "id", idTipoMovimiento, Utilidades.SHORT) != null) {
                if (utilidades.getObjetoEspecifico(Persona.class, "id", idPersona, Utilidades.INT) != null) {
                    if (utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenor, Utilidades.INT) != null || idCajaMenor == -1) {
                        if (utilidades.getObjetoEspecifico(CentroDeCosto.class, "id", idCentroDeCosto, Utilidades.INT) != null) {
                            if (utilidades.getObjetoEspecifico(Movimientos.class, "id", idMovimiento, Utilidades.INT) != null) {
                                return new RespuestaVO(Mensajes.DATO_EXISTE, "");
                            } else {
                                return new RespuestaVO(Mensajes.DATO_NO_EXISTE, Mensajes.MOVIMIENTO_NO_EXISTE);
                            }
                        } else {
                            return new RespuestaVO(Mensajes.DATO_NO_EXISTE, Mensajes.CENTRO_COSTO_NO_EXISTE_MSG);
                        }
                    } else {
                        return new RespuestaVO(Mensajes.DATO_NO_EXISTE, Mensajes.CAJA_MENOR_NO_EXISTE);
                    }
                } else {
                    return new RespuestaVO(Mensajes.DATO_NO_EXISTE, Mensajes.PERSONA_NO_EXISTE);
                }
            } else {
                return new RespuestaVO(Mensajes.DATO_NO_EXISTE, Mensajes.TIPO_MOVIMIENTO_NO_EXISTE);
            }
        } catch (Exception e) {
            return new RespuestaVO(Mensajes.DATO_NO_EXISTE, "Error al tratar de comprobar la existencia de datos.");
        }

    }

    private String enviarNotificacion(int idCajaMenor, String mensaje, String asunto) {
        String from;
        Criteria cri;
        utilidades = new Utilidades();

        try {
            begin();
            UsuarioSys us = (UsuarioSys) utilidades.getObjetoEspecifico(UsuarioSys.class, "isAdmin", (short) 1, Utilidades.SHORT);
            from = us.getCorreoElectronico();

            CajaMenor cm = (CajaMenor) utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenor, Utilidades.INT);
            cri = getSession().createCriteria(NotificacionCajaMenor.class).add(Restrictions.eq("cajaMenor", cm));
            List<NotificacionCajaMenor> noticm = cri.list();

            String[] correosToNotificar = new String[noticm.size()];
            int index = 0;

            for (NotificacionCajaMenor notificacionCajaMenor : noticm) {
                correosToNotificar[index] = notificacionCajaMenor.getUsuarioSys().getCorreoElectronico();
                index++;
            }

            Mail mail = new Mail();
            close();
            return mail.sendMail(us.getCorreoElectronico(), correosToNotificar, asunto, mensaje);

        } catch (Exception e) {
            return " - No se envió el correo  de notificación ";
        }
    }

    private int getResponsableCajaMenor(CajaMenor cm) {
        int idResponsable = 0;
        try {
            Criteria criteria = getSession().createCriteria(NotificacionCajaMenor.class);
            criteria.add(Restrictions.eq("cajaMenor", cm));
            criteria.add(Restrictions.eq("esResponsable", (short) 1));

            NotificacionCajaMenor notificacionCajaMenor = new NotificacionCajaMenor();
            notificacionCajaMenor = (NotificacionCajaMenor) criteria.uniqueResult();
            idResponsable = notificacionCajaMenor.getUsuarioSys().getId();
        } catch (Exception e) {
            idResponsable = -1;
        } finally {
            return idResponsable;
        }
    }

    private RespuestaVO modificarMovimientoApertura(Movimientos movimientos, int idPersona, int idCentroDeCosto, double valor, Date fechaMovimiento) {
        RespuestaVO rvo = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
        try {
            Criteria criteria = getSession().createCriteria(Movimientos.class);
            criteria.add(Restrictions.eq("cajaMenor", movimientos.getCajaMenor()));
            List<Movimientos> listMovimientos = criteria.list();
            if (listMovimientos.size() == 1) {
                if (listMovimientos.get(0).getIsApertura() == 1) {
                    CajaMenor cm = movimientos.getCajaMenor();
                    cm.setValor(valor);
                    getSession().update(cm);

                    movimientos.setCentroDeCosto(new CentroDeCosto(idCentroDeCosto));
                    movimientos.setFechaMovimiento(fechaMovimiento);
                    movimientos.setPersona(new Persona(idPersona));
                    movimientos.setValor(valor);

                    getSession().update(movimientos);
                    commit();
                    close();
                    return new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
                } else {
                    return new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + "\n Movimiento seleccionado no es una apertura de caja menor");
                }
            } else {
                return new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + "\n No se puede modificar movimientos de apertura, cuando la caja menor tiene más de un movimiento.");
            }
        } catch (Exception e) {
            return rvo;
        }
    }
    private static boolean BUSCAR_POR_PARAMETRO = false;

    public List<ReporteMovimientosVO> busquedaPorCajasMenores(int[] idsCajasMenores, Date fechaInicio, Date fechaFin) {
        BUSCAR_POR_PARAMETRO = true;
        BUSQUEDA_SERVICIO = false;
        List<ReporteMovimientosVO> reMovimientosVOs = new ArrayList<ReporteMovimientosVO>();
        double totalIngresos = 0;
        double totalEgresos = 0;
        try {

            for (int idCajaMenor : idsCajasMenores) {
                CajaMenor cajaMenor = new CajaMenor();
                begin();
                cajaMenor = (CajaMenor) utilidades.getObjetoEspecifico(CajaMenor.class, "id", idCajaMenor, Utilidades.INT);
                close();

                List<MovimientoVO> movimientoVOs = new ArrayList<MovimientoVO>();
                movimientoVOs = this.listarMovimientosCajaMenor(idCajaMenor, fechaInicio, fechaFin, (short) 1);
                IngresoEgresoVO ingresoEgresoVO = new IngresoEgresoVO();
                ingresoEgresoVO = this.getValoresIngresoEgreso(movimientoVOs);
                totalIngresos = totalIngresos + ingresoEgresoVO.getValorIngreso();
                totalEgresos = totalEgresos + ingresoEgresoVO.getValorEgreso();
                reMovimientosVOs.add(new ReporteMovimientosVO(movimientoVOs, ingresoEgresoVO, new CajaMenorVO(cajaMenor.getId(), cajaMenor.getNombre(), this.totalizarSaldoCajaMenor(movimientoVOs))));
            }
            reMovimientosVOs.add(new ReporteMovimientosVO(null, new IngresoEgresoVO(totalIngresos, totalEgresos), new CajaMenorVO(-1, "Total")));
        } catch (Exception e) {
            BUSQUEDA_SERVICIO = true;
            BUSCAR_POR_PARAMETRO = false;
            reMovimientosVOs = null;
            System.out.println("");
        } finally {
            BUSCAR_POR_PARAMETRO = false;
            BUSQUEDA_SERVICIO = true;
            return reMovimientosVOs;
        }
    }

    public List<ReporteMovimientosVO> busquedaPorCentroCosto(int[] idsCentrosCostos, Date fechaInicio, Date fechaFin) {
        List<ReporteMovimientosVO> reMovimientosVOs = new ArrayList<ReporteMovimientosVO>();
        double totalIngresos = 0;
        double totalEgresos = 0;
        BUSCAR_POR_PARAMETRO = true;
        BUSQUEDA_SERVICIO = false;
        try {
            for (int idCentroCosto : idsCentrosCostos) {
                CentroDeCosto centroDeCosto = new CentroDeCosto();
                begin();
                centroDeCosto = (CentroDeCosto) utilidades.getObjetoEspecifico(CentroDeCosto.class, "id", idCentroCosto, Utilidades.INT);
                close();

                List<MovimientoVO> movimientoVOs = new ArrayList<MovimientoVO>();
                movimientoVOs = this.listarMovimientosCajaMenor(idCentroCosto, fechaInicio, fechaFin, (short) 2);
                IngresoEgresoVO ingresoEgresoVO = new IngresoEgresoVO();
                ingresoEgresoVO = this.getValoresIngresoEgreso(movimientoVOs);
                totalIngresos = totalIngresos + ingresoEgresoVO.getValorIngreso();
                totalEgresos = totalEgresos + ingresoEgresoVO.getValorEgreso();
                reMovimientosVOs.add(new ReporteMovimientosVO(movimientoVOs, ingresoEgresoVO, new CentroCostoVO(centroDeCosto.getId(), centroDeCosto.getNombre(), centroDeCosto.getCiudad())));
            }
            reMovimientosVOs.add(new ReporteMovimientosVO(null, new IngresoEgresoVO(totalIngresos, totalEgresos), new CentroCostoVO(-1, "Total")));

        } catch (Exception e) {
            reMovimientosVOs = null;
            System.out.println("");
            BUSCAR_POR_PARAMETRO = false;
            BUSQUEDA_SERVICIO = true;
        } finally {
            BUSCAR_POR_PARAMETRO = false;
            BUSQUEDA_SERVICIO = true;
            return reMovimientosVOs;
        }
    }

    public List<ReporteMovimientosVO> busquedaPorPersonas(int[] idsPersonas, Date fechaInicio, Date fechaFin) {
        List<ReporteMovimientosVO> reMovimientosVOs = new ArrayList<ReporteMovimientosVO>();
        double totalIngresos = 0;
        double totalEgresos = 0;
        BUSCAR_POR_PARAMETRO = true;
        BUSQUEDA_SERVICIO = false;
        try {
            for (int idPersona : idsPersonas) {
                Persona persona = new Persona();
                begin();
                persona = (Persona) utilidades.getObjetoEspecifico(Persona.class, "id", idPersona, Utilidades.INT);
                close();

                List<MovimientoVO> movimientoVOs = new ArrayList<MovimientoVO>();
                movimientoVOs = this.listarMovimientosCajaMenor(idPersona, fechaInicio, fechaFin, (short) 3);
                IngresoEgresoVO ingresoEgresoVO = new IngresoEgresoVO();
                ingresoEgresoVO = this.getValoresIngresoEgreso(movimientoVOs);
                totalIngresos = totalIngresos + ingresoEgresoVO.getValorIngreso();
                totalEgresos = totalEgresos + ingresoEgresoVO.getValorEgreso();
                reMovimientosVOs.add(new ReporteMovimientosVO(movimientoVOs, ingresoEgresoVO, new Persona(persona.getId(), persona.getNombreRs(), persona.getEsEmpresa())));
            }
            reMovimientosVOs.add(new ReporteMovimientosVO(null, new IngresoEgresoVO(totalIngresos, totalEgresos), new Persona(-1, "Total")));
        } catch (Exception e) {
            reMovimientosVOs = null;
            System.out.println("");
            BUSCAR_POR_PARAMETRO = false;
            BUSQUEDA_SERVICIO = true;
        } finally {
            BUSCAR_POR_PARAMETRO = false;
            BUSQUEDA_SERVICIO = true;
            return reMovimientosVOs;
        }
    }

    private IngresoEgresoVO getValoresIngresoEgreso(List<MovimientoVO> movimientoVOs) {
        double ingreso = 0;
        double egreso = 0;

        for (MovimientoVO movimientoVO : movimientoVOs) {
            //if (movimientoVO.getIsApertura() != 1) {
            switch (movimientoVO.getTipoMovimiento()) {
                case 1:
                    ingreso = ingreso + movimientoVO.getValor();
                    break;

                case 2:
                    egreso = egreso + (movimientoVO.getValor() * -1);
                    break;
            }
            // }
        }

        return new IngresoEgresoVO(ingreso, egreso);
    }

    private double totalizarSaldoCajaMenor(List<MovimientoVO> movimientoVOs) {
        double totalCajaMenor = 0;
        for (MovimientoVO movimientoVO : movimientoVOs) {
            totalCajaMenor = totalCajaMenor + movimientoVO.getValor();
        }

        return totalCajaMenor;
    }

    private List<MovimientoVO> solucion(List<Movimientos> movimientoses, int idCajaMenor) {
        double valorApertura;
        Movimientos movimientos = new Movimientos();
        double valor;
        double saldo;
        Vector valorSaldo = new Vector();
        double valSal[] = new double[2];
        MovimientoVO movimientoVO;
        List<MovimientoVO> movimientoVOs = new ArrayList<MovimientoVO>();
        try {
            Criteria criteria = getSession().createCriteria(Movimientos.class);
            criteria.add(Restrictions.eq("cajaMenor", new CajaMenor(idCajaMenor)));
            criteria.add(Restrictions.eq("isApertura", (short) 1));
            movimientos = (Movimientos) criteria.uniqueResult();
            valor = movimientos.getValor();
            saldo = valor;
            valSal = new double[2];
            valSal[0] = valor;
            valSal[1] = saldo;
            valorSaldo.add(valSal);
            for (Movimientos movimientos1 : movimientoses) {
                if (movimientos1.getIsApertura() != 1) {
                    if (movimientos1.getTipoMovimiento().getId() == (short) 1) {
                        saldo = saldo + movimientos1.getValor();
                        valor = movimientos1.getValor();
                    } else {
                        saldo = saldo - movimientos1.getValor();
                        valor = movimientos1.getValor() * -1;
                    }
                    valSal = new double[2];
                    valSal[0] = valor;
                    valSal[1] = saldo;
                    valorSaldo.add(valSal);
                }

                criteria = getSession().createCriteria(UsuarioSys.class);
                criteria.add(Restrictions.eq("id", movimientos1.getIdUsuario()));
                UsuarioSys usuarioSys = (UsuarioSys) criteria.uniqueResult();

                PersonaVO personaVO = new PersonaVO(usuarioSys.getId(), usuarioSys.getNombre() + " " + usuarioSys.getApellido(), new BigDecimal(0), usuarioSys.getCedula());

                movimientoVO = new MovimientoVO(movimientos1.getId(), movimientos1.getConcepto(), movimientos1.getCentroDeCosto().getNombre(), movimientos1.getPersona().getNombreRs(), valor, saldo, movimientos1.getFechaMovimiento(), movimientos1.getIsApertura(), movimientos1.getTipoMovimiento().getId(), movimientos1.getCentroDeCosto().getId(), movimientos1.getPersona().getId(), new CajaMenorVO(movimientos1.getCajaMenor().getId(), movimientos1.getCajaMenor().getNombre()));
                movimientoVO.setPersonaResponsable(personaVO);
                movimientoVOs.add(movimientoVO);

            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            System.out.println();
            return movimientoVOs;
        }
    }
}
