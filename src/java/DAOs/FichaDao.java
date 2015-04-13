/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Ficha;
import Entidades.TipoFicha;
import Utilidades.DAO;
import Utilidades.Mensajes;
import VO.DatoDinamicoVO;
import VO.RespuestaVO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class FichaDao extends DAO {

    private final static short GUARDAR = 1;
    private final static short MODIFICAR = 2;

    public List<TipoFicha> listarTipoFicha() {
        List<TipoFicha> tipoFichas = new ArrayList<TipoFicha>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(TipoFicha.class);
            tipoFichas = criteria.list();
        } catch (Exception e) {
            tipoFichas = null;
        } finally {
            close();
            return tipoFichas;
        }
    }

    public RespuestaVO crear(int idContrato, short idTipoFicha, String concepto, String observacion, Double valorPrestamo, Calendar fechaDelPago,
            String texto, Calendar fechaInicio, Calendar fechaFin, short tipoPrestamo, short llamadoAtencion, short buenComportamiento) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            switch (idTipoFicha) {
                case 2:
                    RespuestaVO respuestaVOGurdar = this.guardarFichaPrestamo(idContrato, idTipoFicha, concepto, observacion, valorPrestamo, fechaDelPago, texto, tipoPrestamo, GUARDAR, 0);
                    if (respuestaVOGurdar.getIdRespuesta() == 1) {
                        respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGurdar.getMensajeRespuesta());
                    }
                    break;
                case 3:
                    RespuestaVO respuestaVOGurdar1 = this.guardarFichaPermiso(idContrato, idTipoFicha, concepto, observacion, texto, fechaInicio, fechaFin, GUARDAR, 0);
                    if (respuestaVOGurdar1.getIdRespuesta() == 1) {
                        respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGurdar1.getMensajeRespuesta());
                    }
                    break;
                case 4:
                    RespuestaVO respuestaVOGurdar2 = this.guardarIncapacidades(idContrato, idTipoFicha, concepto, observacion, texto, fechaInicio, fechaFin, GUARDAR, 0);
                    if (respuestaVOGurdar2.getIdRespuesta() == 1) {
                        respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGurdar2.getMensajeRespuesta());
                    }
                    break;
                case 5:
                    RespuestaVO respuestaVOGurdar3 = this.guardarObservacionMemo(idContrato, idTipoFicha, concepto, observacion, texto, llamadoAtencion, buenComportamiento, GUARDAR, 0);
                    if (respuestaVOGurdar3.getIdRespuesta() == 1) {
                        respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGurdar3.getMensajeRespuesta());
                    }
                    break;
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private RespuestaVO guardarFichaPrestamo(int idContrato, short idTipoFicha, String concepto, String observacion, Double valorPrestamo,
            Calendar fechaDelPago, String texto, short tipoPrestamo, short tipoMetodo, int idFicha) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            Ficha ficha = new Ficha();
            switch (tipoMetodo) {
                case GUARDAR:
                    ficha = new Ficha(idContrato, idTipoFicha, concepto, observacion, valorPrestamo, fechaDelPago.getTime(), texto, tipoPrestamo);
                    break;
                case MODIFICAR:
                    Criteria criteria = getSession().createCriteria(Ficha.class);
                    ficha = (Ficha) criteria.add(Restrictions.eq("id", idFicha)).uniqueResult();
                    ficha.setConcepto(concepto);
                    ficha.setObservacion(observacion);
                    ficha.setValorPrestamo(valorPrestamo);
                    ficha.setFechaDelPago(fechaDelPago.getTime());
                    ficha.setTexto(texto);
                    ficha.setTipoPrestamo(tipoPrestamo);
                    break;
            }

            ficha.setEstado((short) 0);
            ficha.setBuenComportamiento((short) 0);
            ficha.setLlamadoAtencion((short) 0);

            switch (tipoMetodo) {
                case GUARDAR:
                    getSession().save(ficha);
                    break;
                case MODIFICAR:
                    getSession().update(ficha);
                    break;
            }
            commit();
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO guardarFichaPermiso(int idContrato, short idTipoFicha, String concepto, String observacion,
            String texto, Calendar fechaInicio, Calendar fechaFin, short tipoMetodo, int idFicha) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            Ficha ficha = new Ficha();
            switch (tipoMetodo) {
                case GUARDAR:
                    ficha = new Ficha(idContrato, idTipoFicha, concepto, observacion, texto, fechaInicio.getTime(), fechaFin.getTime());
                    break;
                case MODIFICAR:
                    Criteria criteria = getSession().createCriteria(Ficha.class);
                    ficha = (Ficha) criteria.add(Restrictions.eq("id", idFicha)).uniqueResult();
                    ficha.setConcepto(concepto);
                    ficha.setObservacion(observacion);
                    ficha.setTexto(texto);
                    ficha.setFechaInicio(fechaInicio.getTime());
                    ficha.setFechaFin(fechaFin.getTime());
                    break;
            }


            ficha.setEstado((short) 0);
            ficha.setBuenComportamiento((short) 0);
            ficha.setLlamadoAtencion((short) 0);
            ficha.setTipoPrestamo((short) 0);
            switch (tipoMetodo) {
                case GUARDAR:
                    getSession().save(ficha);
                    break;
                case MODIFICAR:
                    //ficha.setId(idFicha);
                    getSession().update(ficha);
                    break;
            }

            commit();
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO guardarIncapacidades(int idContrato, short idTipoFicha, String concepto, String observacion,
            String texto, Calendar fechaInicio, Calendar fechaFin, short idTipoMetodo, int idFicha) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            Ficha ficha = new Ficha();
            switch (idTipoMetodo) {
                case GUARDAR:
                    ficha = new Ficha(idContrato, idTipoFicha, concepto, observacion, texto, fechaInicio.getTime(), fechaFin.getTime());
                    break;
                case MODIFICAR:
                    Criteria criteria = getSession().createCriteria(Ficha.class);
                    ficha = (Ficha) criteria.add(Restrictions.eq("id", idFicha)).uniqueResult();
                    ficha.setConcepto(concepto);
                    ficha.setObservacion(observacion);
                    ficha.setTexto(texto);
                    ficha.setFechaInicio(fechaInicio.getTime());
                    ficha.setFechaFin(fechaFin.getTime());
                    break;
            }
            ficha.setEstado((short) 0);
            ficha.setBuenComportamiento((short) 0);
            ficha.setLlamadoAtencion((short) 0);
            ficha.setTipoPrestamo((short) 0);
            switch (idTipoMetodo) {
                case GUARDAR:
                    getSession().save(ficha);
                    break;
                case MODIFICAR:
                    getSession().update(ficha);
                    break;
            }

            commit();
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO guardarObservacionMemo(int idContrato, short idTipoFicha, String concepto, String observacion,
            String texto, short llamadoAtencion, short buenComportamiento, short tipoMetodo, int idFicha) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            Ficha ficha = new Ficha();
            switch (tipoMetodo) {
                case GUARDAR:
                    ficha = new Ficha(idContrato, idTipoFicha, concepto, observacion, texto, llamadoAtencion, buenComportamiento);
                    break;
                case MODIFICAR:
                    Criteria criteria = getSession().createCriteria(Ficha.class);
                    ficha = (Ficha) criteria.add(Restrictions.eq("id", idFicha)).uniqueResult();
                    ficha.setConcepto(concepto);
                    ficha.setObservacion(observacion);
                    ficha.setTexto(texto);
                    ficha.setLlamadoAtencion(llamadoAtencion);
                    ficha.setBuenComportamiento(buenComportamiento);
                    break;
            }
            ficha.setEstado((short) 0);
            ficha.setTipoPrestamo((short) 0);
            switch (tipoMetodo) {
                case GUARDAR:
                    getSession().save(ficha);
                    break;

                case MODIFICAR:
                    getSession().update(ficha);
                    break;
            }
            commit();
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    public RespuestaVO modificar(int idFicha, short idTipoFicha, String concepto, String observacion, Double valorPrestamo,
            Calendar fechaDelPago, String texto, Calendar fechaInicio, Calendar fechaFin, short tipoPrestamo, short llamadoAtencion,
            short buenComportamiento) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Ficha.class);
            Ficha ficha = (Ficha) criteria.add(Restrictions.eq("id", idFicha)).uniqueResult();
            switch (idTipoFicha) {
                case 2:
                    RespuestaVO respuestaVOGurdar = this.guardarFichaPrestamo(ficha.getIdContrato(), idTipoFicha, concepto, observacion, valorPrestamo, fechaDelPago, texto, tipoPrestamo, MODIFICAR, idFicha);
                    if (respuestaVOGurdar.getIdRespuesta() == 1) {
                        respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGurdar.getMensajeRespuesta());
                    }
                    break;
                case 3:
                    RespuestaVO respuestaVOGurdar1 = this.guardarFichaPermiso(ficha.getIdContrato(), idTipoFicha, concepto, observacion, texto, fechaInicio, fechaFin, MODIFICAR, idFicha);
                    if (respuestaVOGurdar1.getIdRespuesta() == 1) {
                        respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGurdar1.getMensajeRespuesta());
                    }
                    break;
                case 4:
                    RespuestaVO respuestaVOGurdar2 = this.guardarIncapacidades(ficha.getIdContrato(), idTipoFicha, concepto, observacion, texto, fechaInicio, fechaFin, MODIFICAR, idFicha);
                    if (respuestaVOGurdar2.getIdRespuesta() == 1) {
                        respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGurdar2.getMensajeRespuesta());
                    }
                    break;
                case 5:
                    RespuestaVO respuestaVOGurdar3 = this.guardarObservacionMemo(ficha.getIdContrato(), idTipoFicha, concepto, observacion, texto, llamadoAtencion, buenComportamiento, MODIFICAR, idFicha);
                    if (respuestaVOGurdar3.getIdRespuesta() == 1) {
                        respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGurdar3.getMensajeRespuesta());
                    }
                    break;
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO eliminar(int idFicha) {
        RespuestaVO respuestaVO = new RespuestaVO();
        boolean eliminar = true;
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Ficha.class);
            criteria.add(Restrictions.eq("id", idFicha));
            Ficha ficha = (Ficha) criteria.uniqueResult();
            if (ficha.getIdTipoFicha() == 2) {
                if (ficha.getEstado() == 0) {
                    eliminar = false;
                    respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.FICHA_TIENE_ESTADO_PENDIENTE);
                }
            } else {
                eliminar = true;
            }
            if (eliminar) {
                getSession().delete(ficha);
                commit();
                respuestaVO = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public List<Ficha> listarFichasPorContrato(int idContrato) {
        List<Ficha> fichas = new ArrayList<Ficha>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Ficha.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            fichas = criteria.list();
            fichas = this.crearFichas(fichas);
        } catch (Exception e) {
            fichas = null;
        } finally {
            close();
            return fichas;
        }
    }

    private List<Ficha> crearFichas(List<Ficha> fichas) {
        List<Ficha> newFichas = new ArrayList<Ficha>();
        for (Ficha ficha : fichas) {
            Ficha newFicha = new Ficha();
            newFicha = ficha;

            DatoDinamicoVO estadoVO = new DatoDinamicoVO(ficha.getEstado());

            switch (ficha.getEstado()) {
                case 0:
                    estadoVO.setNombre("No devuelto");
                    break;
                case 1:
                    estadoVO.setNombre("devuelto");
                    break;
            }

            DatoDinamicoVO tipoFichaVO = new DatoDinamicoVO(ficha.getIdTipoFicha());
            switch (ficha.getIdTipoFicha()) {
                case 2:
                    tipoFichaVO.setNombre("Préstamo");
                    break;
                case 3:
                    tipoFichaVO.setNombre("Permisos");
                    break;
                case 4:
                    tipoFichaVO.setNombre("Incapacidades");
                    break;
                case 5:
                    tipoFichaVO.setNombre("Observaciones-Memos");
                    break;
            }

            newFicha.setTipoFichaVO(tipoFichaVO);
            newFicha.setEstadoVO(estadoVO);
            newFichas.add(newFicha);
        }
        return newFichas;
    }
}
