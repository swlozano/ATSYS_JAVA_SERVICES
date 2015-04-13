/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Adendo;
import Entidades.Contrato;
import Entidades.Country;
import Entidades.FechasPago;
import Entidades.Ficha;
import Entidades.OficioDesempenar;
import Entidades.RecursoHumano;
import Entidades.TerminacionContrato;
import Entidades.TipoPago;
import Entidades.TiposContrato;
import Entidades.ValorFormaPago;
import Utilidades.DAO;
import Utilidades.Fechas;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.FechaPagoVO;
import VO.RespuestaVO;
import VO.ValFormPagoFchasPagVO;
import VO.ValorFormaFechasPagoVO;
import VO.ValorFormaPagoFxVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class ContratoDao extends DAO {

    Utilidades utilidades = new Utilidades();

    public List<TiposContrato> listarTipoContrato() {
        List<TiposContrato> tiposContratos = new ArrayList<TiposContrato>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(TiposContrato.class);
            tiposContratos = criteria.list();
            List<TiposContrato> tiposContratos1 = new ArrayList<TiposContrato>();
            for (TiposContrato tiposContrato : tiposContratos) {
                TiposContrato tiposContrato1 = new TiposContrato(tiposContrato.getId(), tiposContrato.getTipoContrato());
                tiposContrato1.setContratos(null);
                tiposContratos1.add(tiposContrato1);
            }
            tiposContratos = tiposContratos1;

        } catch (Exception e) {
            tiposContratos = null;
        } finally {
            close();
            return tiposContratos;
        }
    }

    public RespuestaVO crear(int idOficioDesempenar, short idTiposContrato, String idCountryByIdNacionLabores, int idRecursoHumano, String idCountryByIdNacionContrato, Date fechaInicio, Date fechaFin, double tiempo, Object[] valores_forma_pago) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
        try {
            begin();
            RespuestaVO respuestaVOExisenDatos = this.comprobarExistenciaDatos(idOficioDesempenar, idTiposContrato, idCountryByIdNacionLabores, idRecursoHumano, idCountryByIdNacionContrato);
            if (respuestaVOExisenDatos.getIdRespuesta() == 1) {
                OficioDesempenar oficioDesempenar = new OficioDesempenar(idOficioDesempenar);
                TiposContrato tiposContrato = new TiposContrato(idTiposContrato, "");
                Country countryNLabores = new Country(idCountryByIdNacionLabores);
                RecursoHumano recursoHumano = new RecursoHumano(idRecursoHumano);
                Country countryNcontrato = new Country(idCountryByIdNacionContrato);

                Contrato contrato = new Contrato(oficioDesempenar, tiposContrato, countryNLabores, recursoHumano, countryNcontrato, fechaInicio, fechaFin, (short) 0, fechaFin, tiempo);
                Serializable serializable = getSession().save(contrato);
                int idContrato = Integer.parseInt(serializable.toString());

                List<ValorFormaPago> valorFormaPagos = new ArrayList<ValorFormaPago>();
                ValorFormaPagoDao valorFormaPagoDao = new ValorFormaPagoDao();
                valorFormaPagos = valorFormaPagoDao.ObjectsToValoresFormaPago(valores_forma_pago);
                RespuestaVO respuestaVOValFormaPago = valorFormaPagoDao.guardarValoresFormaPago(valorFormaPagos, idContrato, idTiposContrato);
                if (respuestaVOValFormaPago.getIdRespuesta() == 1) {
                    if (idTiposContrato == 2 && ValorFormaPagoDao.TIENE_IBC) {
                        if (ValorFormaPagoDao.CONTADOR_IBCS == 1) {
                            commit();
                            respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                        } else {
                            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.SELECCIONE_SOLO_UN_IBC);
                        }

                    } else {
                        if (idTiposContrato != 2) {
                            commit();
                            respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                        } else {
                            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.SELECCIONE_IBC);
                        }
                    }

                } else {
                    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOValFormaPago.getMensajeRespuesta());
                }
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOExisenDatos.getMensajeRespuesta());
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private RespuestaVO comprobarExistenciaDatos(int idOficioDesempenar, short idTiposContrato, String idCountryByIdNacionLabores, int idRecursoHumano, String idCountryByIdNacionContrato) {
        RespuestaVO respuestaVO = new RespuestaVO((short) -1, "");
        try {
            if (utilidades.buscarPropiedad(OficioDesempenar.class, "id", idOficioDesempenar, Utilidades.INT)) {
                respuestaVO = new RespuestaVO((short) -1, Mensajes.OFICIO_NO_EXISTE_MSG);
            } else {
                if (utilidades.buscarPropiedad(TiposContrato.class, "id", idTiposContrato, Utilidades.SHORT)) {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.TIPO_CONTRATO_NO_EXISTE_MSG);
                } else {
                    if (utilidades.buscarPropiedad(Country.class, "id", idCountryByIdNacionLabores, Utilidades.STRING)) {
                        respuestaVO = new RespuestaVO((short) -1, Mensajes.PAIS_LABORES_NO_EXISTE_MSG);
                    } else {
                        if (utilidades.buscarPropiedad(Country.class, "id", idCountryByIdNacionContrato, Utilidades.STRING)) {
                            respuestaVO = new RespuestaVO((short) -1, Mensajes.PAIS_CONTRATO_NO_EXISTE_MSG);
                        } else {
                            if (utilidades.buscarPropiedad(RecursoHumano.class, "id", idRecursoHumano, Utilidades.INT)) {
                                respuestaVO = new RespuestaVO((short) -1, Mensajes.RH_NO_EXISTE_MSG);
                            } else {
                                respuestaVO = new RespuestaVO((short) 1, "");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }

    }

    /*public RespuestaVO crearAdendo(int idContrato, Date fechaFin, ArrayList valsFormaPagoFechasPago) {

    RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_ADENDO_MSG);

    boolean hacerCommint = true;
    try {
    List<ValFormPagoFchasPagVO> valFormPagoFchasPagVOs = new ArrayList<ValFormPagoFchasPagVO>();
    valFormPagoFchasPagVOs = this.arrayToValFormaPagoFechasPagoVO(valsFormaPagoFechasPago);
    begin();
    Criteria criteria = getSession().createCriteria(Contrato.class);
    criteria.add(Restrictions.eq("id", idContrato));
    Contrato contrato = (Contrato) criteria.uniqueResult();

    if (contrato.getFechaFin().compareTo(fechaFin) > 0) {
    ValorFormaPagoDao valorFormaPagoDao = new ValorFormaPagoDao();

    for (ValFormPagoFchasPagVO valFormPagoFchasPagVO : valFormPagoFchasPagVOs) {
    RespuestaVO respuestaVOSaveFechasPago = valorFormaPagoDao.saveFechasPago(valFormPagoFchasPagVO.getDates(), valFormPagoFchasPagVO.getIdValorFormPago(), contrato.getTiposContrato().getId());
    if (respuestaVOSaveFechasPago.getIdRespuesta() == -1) {
    hacerCommint = false;
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_ADENDO_MSG + Mensajes.SALTO_LINEA + respuestaVOSaveFechasPago.getMensajeRespuesta());
    break;
    }
    }
    if (hacerCommint) {
    Adendo adendo = new Adendo();
    adendo.setContrato(new Contrato(idContrato));
    adendo.setFechaFin(fechaFin);
    getSession().save(adendo);
    commit();
    respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_ADENDO_MSG);
    }

    } else {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_ADENDO_MSG + Mensajes.FECHA_FIN_ADENDO_DEBE_SER_MAYOR_MSG);
    }

    } catch (Exception e) {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_ADENDO_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
    } finally {
    close();
    return respuestaVO;
    }
    }*/
    private List<ValFormPagoFchasPagVO> arrayToValFormaPagoFechasPagoVO(ArrayList valsFormaPagoFechasPago) {
        List<ValFormPagoFchasPagVO> formPagoFchasPagVOs = new ArrayList<ValFormPagoFchasPagVO>();
        for (Object object : valsFormaPagoFechasPago) {
            Object[] datos = (Object[]) object;
            Set<Date> dates = new HashSet<Date>();
            dates = this.arregloToSetFechasPago((Object[]) datos[1]);
            ValFormPagoFchasPagVO vo = new ValFormPagoFchasPagVO(Integer.parseInt(datos[0].toString()), dates);
            formPagoFchasPagVOs.add(vo);
        }
        return formPagoFchasPagVOs;
    }

    private Set<Date> arregloToSetFechasPago(Object[] fechasPago) {

        Set<Date> dates = new HashSet<Date>();
        for (Object date : fechasPago) {
            Date date1 = (Date) date;
            dates.add(date1);
        }
        return dates;

    }

    public RespuestaVO terminarContrato(int idContrato, short solicitante, String motivo, short cartaRenuncia, short cartaAceptacion, short cartaTerminacion, Calendar fechaPropuestaRetiro, Calendar fechaRetiro, short respuesta) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
        String mensajeAlterno = "";
        try {
            begin();
            List<TerminacionContrato> terminacionContratos = new ArrayList<TerminacionContrato>();
            Criteria criteria = getSession().createCriteria(TerminacionContrato.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            criteria.add(Restrictions.eq("solicitante", (short) 3));
            terminacionContratos = criteria.list();

            if (terminacionContratos.size() > 0) {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.NO_PUEDE_TERMINAR_CONTRATO);
            } else {
                RespuestaVO respuestaVOActivarFechas = new RespuestaVO();
                TerminacionContrato terminacionContrato = new TerminacionContrato(idContrato, solicitante, motivo, cartaRenuncia, cartaAceptacion, cartaTerminacion, fechaPropuestaRetiro.getTime(), fechaRetiro.getTime(), respuesta);
                if (terminacionContrato.getRespuesta() == 1) {
                    Contrato contrato = (Contrato) utilidades.getObjetoEspecifico(Contrato.class, "id", terminacionContrato.getIdContrato(), Utilidades.INT);
                    contrato.setEstado((short) 1);
                    contrato.setFechaRealTerminacion(fechaRetiro.getTime());
                    getSession().update(contrato);
                    mensajeAlterno = Mensajes.CONTRATO_TERMINADO;
                    respuestaVOActivarFechas = desActivarFechasPago(contrato, fechaRetiro);
                } else {
                    Contrato contrato = (Contrato) utilidades.getObjetoEspecifico(Contrato.class, "id", terminacionContrato.getIdContrato(), Utilidades.INT);
                    contrato.setEstado((short) 0);
                    contrato.setFechaRealTerminacion(contrato.getFechaFin());
                    getSession().update(contrato);
                    mensajeAlterno = Mensajes.NO_ACEPTO_TERMINAR_CONTRATO;
                    respuestaVOActivarFechas = activarFechasPago(contrato);
                }

                if (respuestaVOActivarFechas.getIdRespuesta() == 1) {
                    getSession().save(terminacionContrato);
                    commit();
                    respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG + mensajeAlterno);
                } else {

                    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOActivarFechas.getMensajeRespuesta());
                }
            }


        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private RespuestaVO desActivarFechasPago(Contrato contrato, Calendar fechaRealTerminacion) {

        Fechas fechas = new Fechas();
        int year = fechaRealTerminacion.get(Calendar.YEAR);
        int mes = fechaRealTerminacion.get(Calendar.MONTH);
        int dia = fechaRealTerminacion.get(Calendar.DAY_OF_MONTH);

        Calendar frt = Calendar.getInstance();
        frt.set(Calendar.YEAR, year);
        frt.set(Calendar.MONTH, mes);
        frt.set(Calendar.DAY_OF_MONTH, fechas.getNumeroDiasMes(mes + 1, year));
        frt.set(Calendar.HOUR, 0);
        frt.set(Calendar.MINUTE, 59);
        frt.set(Calendar.SECOND, 59);
        frt.set(Calendar.MILLISECOND, 59);
        frt.set(Calendar.AM_PM, Calendar.PM);

        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            Set<ValorFormaPago> valorFormaPagos = new HashSet<ValorFormaPago>();
            valorFormaPagos = contrato.getValorFormaPagos();
            for (ValorFormaPago valorFormaPago : valorFormaPagos) {
                Set<FechasPago> fechasPagos = new HashSet<FechasPago>();
                fechasPagos = valorFormaPago.getFechasPagos();
                for (FechasPago fechasPago : fechasPagos) {

                    Calendar fechaFinPago = Calendar.getInstance();
                    fechaFinPago.setTime(fechasPago.getFinPeriodoPago());
                    fechaFinPago.set(Calendar.HOUR, 1);
                    fechaFinPago.set(Calendar.MINUTE, 0);
                    fechaFinPago.set(Calendar.SECOND, 0);
                    fechaFinPago.set(Calendar.MILLISECOND, 0);

                    Calendar fechaIniPago = Calendar.getInstance();
                    fechaIniPago.setTime(fechasPago.getIniPeriodoPago());
                    fechaIniPago.set(Calendar.HOUR, 1);
                    fechaIniPago.set(Calendar.MINUTE, 0);
                    fechaIniPago.set(Calendar.SECOND, 0);
                    fechaIniPago.set(Calendar.MILLISECOND, 0);
                    System.out.println("***************************************");
                    System.out.println("FECHA REAL " + frt.getTime());
                    System.out.println("FECHA INICIO " + fechaIniPago.getTime());
                    System.out.println("FECHA FIN " + fechaFinPago.getTime());

                    if (fechaIniPago.compareTo(frt) > 0) {
                        fechasPago.setActiva((short) 0);
                        getSession().update(fechasPago);
                    }
                }
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO activarFechasPago(Contrato contrato) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            Set<ValorFormaPago> valorFormaPagos = new HashSet<ValorFormaPago>();
            valorFormaPagos = contrato.getValorFormaPagos();
            for (ValorFormaPago valorFormaPago : valorFormaPagos) {
                Set<FechasPago> fechasPagos = new HashSet<FechasPago>();
                fechasPagos = valorFormaPago.getFechasPagos();
                for (FechasPago fechasPago : fechasPagos) {
                    fechasPago.setActiva((short)1);
                    getSession().update(fechasPago);
                }
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    public RespuestaVO modificarTerminarContrato(int idTerminacion, short solicitante, String motivo, short cartaRenuncia, short cartaAceptacion, short cartaTerminacion, Calendar fechaPropuestaRetiro, Calendar fechaRetiro, short respuesta) {
        RespuestaVO respuestaVO = new RespuestaVO();
        String mensajeAlterno = "";
        try {
            begin();
            TerminacionContrato terminacionContrato = (TerminacionContrato) utilidades.getObjetoEspecifico(TerminacionContrato.class, "id", idTerminacion, Utilidades.INT);
            int idContrato = terminacionContrato.getId();

            List<TerminacionContrato> terminacionContratos = new ArrayList<TerminacionContrato>();
            Criteria criteria = getSession().createCriteria(TerminacionContrato.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            criteria.add(Restrictions.eq("solicitante", (short) 3));
            terminacionContratos = criteria.list();

            if (terminacionContratos.size() > 0) {
                respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.NO_PUEDE_TERMINAR_CONTRATO);
            } else {
                terminacionContrato.setCartaAceptacion(cartaAceptacion);
                terminacionContrato.setCartaRenuncia(cartaRenuncia);
                terminacionContrato.setCartaTerminacion(cartaTerminacion);
                terminacionContrato.setFechaPropuestaRetiro(fechaPropuestaRetiro.getTime());
                terminacionContrato.setFechaRetiro(fechaRetiro.getTime());
                terminacionContrato.setMotivo(motivo);
                terminacionContrato.setRespuesta(respuesta);
                terminacionContrato.setSolicitante(solicitante);
                Contrato contrato = (Contrato) utilidades.getObjetoEspecifico(Contrato.class, "id", terminacionContrato.getIdContrato(), Utilidades.INT);
                if (respuesta == 1) {
                    contrato.setEstado((short) 1);
                    contrato.setFechaRealTerminacion(fechaRetiro.getTime());
                    getSession().update(contrato);
                    mensajeAlterno = Mensajes.CONTRATO_TERMINADO;
                } else {
                    contrato.setEstado((short) 1);
                    contrato.setFechaRealTerminacion(contrato.getFechaFin());
                    getSession().update(contrato);
                    mensajeAlterno = Mensajes.NO_ACEPTO_TERMINAR_CONTRATO;
                }
                getSession().update(terminacionContrato);
                commit();
                respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG + mensajeAlterno);
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO eliminarTerminarContrato(int idTerminacion) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            TerminacionContrato terminacionContrato = (TerminacionContrato) utilidades.getObjetoEspecifico(TerminacionContrato.class, "id", idTerminacion, Utilidades.INT);
            int idContrato = terminacionContrato.getId();

            List<TerminacionContrato> terminacionContratos = new ArrayList<TerminacionContrato>();
            Criteria criteria = getSession().createCriteria(TerminacionContrato.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            criteria.add(Restrictions.eq("solicitante", 3));
            terminacionContratos = criteria.list();
            if (terminacionContratos.size() > 0) {
                respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.NO_PUEDE_TERMINAR_CONTRATO);
            } else {
                getSession().delete(terminacionContrato);
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
    private static final short VIGENTE_TP = 1;
    private static final short TERMINADO_TP = 2;
    private static final short AMBOS_TP = 3;

    public List<Contrato> listarContratos(short tipoTolistar) {
        List<Contrato> contratos = new ArrayList<Contrato>();


        try {
            begin();
            Criteria criteria = getSession().createCriteria(Contrato.class);
            switch (tipoTolistar) {
                case VIGENTE_TP:
                    criteria.add(Restrictions.eq("estado", (short) 0));
                    break;
                case TERMINADO_TP:
                    criteria.add(Restrictions.eq("estado", (short) 1));
                    break;
            }
            contratos = criteria.list();
            contratos = normalizarContratos(contratos);
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return contratos;
        }

    }

    private List<Contrato> normalizarContratos(List<Contrato> contratos) {
        List<Contrato> contratos1 = new ArrayList<Contrato>();
        for (Contrato contrato : contratos) {
            Contrato contrato1 = new Contrato();
            contrato1 = normalizarContrato(contrato);
            contratos1.add(contrato1);
        }
        return contratos1;

    }

    private Contrato normalizarContrato(Contrato contrato) {

        TiposContrato tiposContrato = new TiposContrato(contrato.getTiposContrato().getId(), contrato.getTiposContrato().getTipoContrato());
        Country countryByIdNacionLabores = new Country(contrato.getCountryByIdNacionLabores().getCode(), contrato.getCountryByIdNacionLabores().getName());
        RecursoHumano recursoHumano = new RecursoHumano(contrato.getRecursoHumano().getId(), contrato.getRecursoHumano().getNombre() + " " + contrato.getRecursoHumano().getApellido(), contrato.getRecursoHumano().getCedula());
        Country countryByIdNacionContrato = new Country(contrato.getCountryByIdNacionLabores().getCode(), contrato.getCountryByIdNacionLabores().getName());
        OficioDesempenar oficioDesempenar = new OficioDesempenar(contrato.getOficioDesempenar().getId(), contrato.getOficioDesempenar().getNombre(), contrato.getOficioDesempenar().getObjeto());
        Contrato contrato1 = new Contrato(contrato.getId(), contrato.getOficioDesempenar(), tiposContrato, countryByIdNacionLabores, recursoHumano, countryByIdNacionContrato, contrato.getFechaInicio(), contrato.getFechaFin(), contrato.getEstado(), contrato.getFechaRealTerminacion(), contrato.getTiempo(), contrato.getPagosCancelados());
        return contrato1;
    }

    public RespuestaVO consultarCausales(int idContrato) {
        Ficha ficha = new Ficha();
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Ficha.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            criteria.add(Restrictions.eq("idTipoFicha", (short) 5));
            criteria.add(Restrictions.eq("llamadoAtencion", (short) 1));
            ficha = new Ficha();
            if (ficha == null) {
                respuestaVO = new RespuestaVO(Mensajes.RH_NO_TIENE_CAUSAL, Mensajes.RH_NO_TIENE_CAUSAL_MSG + Mensajes.SALTO_LINEA + Mensajes.RH_NO_TIENE_CAUSAL_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.RH_TIENE_CAUSAL, "");
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.HA_OCURRIDO_EXEP, Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }
    //tiopo a listar
    private static final short VIGENTE = 1;
    private static final short TERMINADO = 2;
    private static final short AMBOS = 3;
    //Tipos de contrato
    private static final short PRESTACION_SERVICIO = 1;
    private static final short PRESTACION_NOMINA = 2;
    //3 PARA AMBOS O CUALQUIER NUMERO
    //Tipos de cancelacion
    private static final short PAGADO = 1;
    private static final short NO_PAGADO = 2;
    //3 PARA AMBOS O CUALQUIER NUMERO

    public List<Contrato> listarContratoPorRh(int idRh, short tipoAListar, short tipoContrato, short tipoCancelacion) {
        List<Contrato> contratos = new ArrayList<Contrato>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("recursoHumano", new RecursoHumano(idRh)));

            switch (tipoContrato) {
                case PRESTACION_SERVICIO:
                    criteria.add(Restrictions.eq("tiposContrato", new TiposContrato((short) 1)));
                    break;
                case PRESTACION_NOMINA:
                    criteria.add(Restrictions.eq("tiposContrato", new TiposContrato((short) 2)));
                    break;
            }
            switch (tipoCancelacion) {
                case PAGADO:
                    criteria.add(Restrictions.eq("pagosCancelados", (short) 1));
                    break;
                case NO_PAGADO:
                    criteria.add(Restrictions.eq("pagosCancelados", (short) 0));
                    break;
            }
            switch (tipoAListar) {
                case VIGENTE_TP:
                    criteria.add(Restrictions.eq("estado", (short) 0));
                    contratos = criteria.list();
                    break;
                case TERMINADO_TP:
                    criteria.add(Restrictions.eq("estado", (short) 1));
                    contratos = criteria.list();
                    break;
                case AMBOS_TP:
                    contratos = criteria.list();
                    break;
            }
            contratos = normalizarContratos(contratos);
        } catch (Exception e) {
            contratos = null;
        } finally {
            close();
            return contratos;
        }
    }
    public final static short RESPUESTA = 1;
    public final static short HISTORIAL = 2;

    public List<TerminacionContrato> listarHitorialTerminacionesContrato(int idContrato) {
        List<TerminacionContrato> terminacionContratos = new ArrayList<TerminacionContrato>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(TerminacionContrato.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            terminacionContratos = criteria.list();
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return terminacionContratos;
        }
    }

    public TerminacionContrato listarTerminacionPorRespuesta(int idContrato, short tipoToListar) {
        TerminacionContrato terminacionContrato = new TerminacionContrato();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(TerminacionContrato.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            switch (tipoToListar) {
                case RESPUESTA:
                    criteria.add(Restrictions.eq("respuesta", (short) 1));
                    terminacionContrato = (TerminacionContrato) criteria.uniqueResult();
                    break;
                case HISTORIAL:
                    break;
            }

        } catch (Exception e) {
            terminacionContrato = null;
        } finally {
            close();
            return terminacionContrato;
        }
    }

    public RespuestaVO crearAdendo(int idContrato, Date fechaInicio, Date fechaFin, ValorFormaPagoFxVO[] valorFormaPagoFxVOs) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_CREO_ADENDO);
        try {
            begin();
            Contrato contrato = (Contrato) utilidades.getObjetoEspecifico(Contrato.class, "id", idContrato, Utilidades.INT);
            short idTipoContrato = contrato.getTiposContrato().getId();
            Adendo adendo = new Adendo(idContrato, fechaInicio, fechaFin);
            getSession().save(adendo);
            for (ValorFormaPagoFxVO valorFormaPagoFxVO : valorFormaPagoFxVOs) {
                List<FechaPagoVO> fechaPagoVOs = valorFormaPagoFxVO.getFechaPagoVOs();
                for (FechaPagoVO fechaPagoVO : fechaPagoVOs) {
                    FechasPago fechasPago = new FechasPago(new ValorFormaPago(valorFormaPagoFxVO.getIdValorFormaPago()), fechaPagoVO.getFechaPago(), fechaPagoVO.getFechaIni(), fechaPagoVO.getFechaFin(), idTipoContrato, (short) 0);
                    getSession().save(fechasPago);
                }
            }
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.SE_CREO_ADENDO);
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_CREO_ADENDO + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public List<ValorFormaPago> listarValoresFormaPagoByContrato(int idContrato) {
        List<ValorFormaPago> newValorFormaPagos = new ArrayList<ValorFormaPago>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(ValorFormaPago.class);
            criteria.add(Restrictions.eq("contrato", new Contrato(idContrato)));
            List<ValorFormaPago> valorFormaPagos = new ArrayList<ValorFormaPago>();
            valorFormaPagos = criteria.list();
            for (ValorFormaPago valorFormaPago : valorFormaPagos) {
                ValorFormaPago valorFormaPago1 = new ValorFormaPago();

                valorFormaPago1.setConcepto(valorFormaPago.getConcepto());
                valorFormaPago1.setEsFavorito(valorFormaPago.getEsFavorito());
                valorFormaPago1.setId(valorFormaPago.getId());
                valorFormaPago1.setObservacion(valorFormaPago.getObservacion());
                valorFormaPago1.setPeriodoPago(valorFormaPago.getPeriodoPago());
                valorFormaPago1.setTipoPago(new TipoPago(valorFormaPago.getTipoPago().getId(), valorFormaPago.getTipoPago().getNombre()));
                valorFormaPago1.setValorPago(valorFormaPago.getValorPago());
                valorFormaPago1.setEsIbc(valorFormaPago.getEsIbc());
                newValorFormaPagos.add(valorFormaPago1);
            }
        } catch (Exception e) {
            newValorFormaPagos = null;
        } finally {
            close();
            return newValorFormaPagos;
        }
    }

    public List<Adendo> listAdendos(int idContrato) {
        List<Adendo> adendos = new ArrayList<Adendo>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Adendo.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            adendos = criteria.list();
        } catch (Exception e) {
            adendos = null;
        } finally {
            close();
            return adendos;
        }
    }

    protected Contrato getContratoById(int idContrato) {
        Contrato contrato = null;
        try {
            Criteria criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("id", idContrato));
            contrato = new Contrato();
            contrato = (Contrato) criteria.uniqueResult();
        } catch (Exception e) {
            System.out.println();
        } finally {
            return contrato;
        }
    }

    public RespuestaVO generarNuevasFechasPago(int idContrato) {
        RespuestaVO respuestaVO = new RespuestaVO();
        List<ValorFormaFechasPagoVO> valorFormaFechasPagoVOs = new ArrayList<ValorFormaFechasPagoVO>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("id", idContrato));
            criteria.add(Restrictions.eq("estado", (short) 0));
            criteria.add(Restrictions.isNull("fechaFin"));
            Contrato contrato = (Contrato) criteria.uniqueResult();
            boolean hacerCommit = true;
            if (contrato != null) {
                Set<ValorFormaPago> valorFormaPagos = new HashSet<ValorFormaPago>();
                valorFormaPagos = contrato.getValorFormaPagos();
                Fechas fechas = new Fechas();
                //ValorFormaPagoDao valorFormaPagoDao = new ValorFormaPagoDao();
                for (ValorFormaPago valorFormaPago : valorFormaPagos) {

                    FechasPago fechasPago = getLastFechaValorFormaPago(valorFormaPago);
                    int periodo = valorFormaPago.getPeriodoPago();
                    Calendar fIniPago = Calendar.getInstance();
                    fIniPago.setTime(fechasPago.getFecha());
                    Calendar fIni = Calendar.getInstance();
                    fIni.setTime(fechasPago.getIniPeriodoPago());
                    Calendar fFin = Calendar.getInstance();
                    fFin = getFechaFin(fIni);
                    List<FechaPagoVO> fechaPagoVOs = new ArrayList<FechaPagoVO>();
                    //////////////
                    fechaPagoVOs = fechas.getFechasPago(fIniPago, fIni, fFin, periodo);
                    fechaPagoVOs.remove(0);

                    ValorFormaPago valorFormaPago1 = new ValorFormaPago();
                    valorFormaPago1.setConcepto(valorFormaPago.getConcepto());
                    valorFormaPago1.setId(valorFormaPago.getId());
                    valorFormaPago1.setObservacion(valorFormaPago.getObservacion());
                    valorFormaPago1.setPeriodoPago(valorFormaPago.getPeriodoPago());
                    String nombreTipoPago = valorFormaPago.getTipoPago().getNombre();
                    valorFormaPago1.setNombreTipoPago(nombreTipoPago);
                    valorFormaPago1.setValorPago(valorFormaPago.getValorPago());

                    ValorFormaFechasPagoVO valorFormaFechasPagoVO = new ValorFormaFechasPagoVO();
                    valorFormaFechasPagoVO.setFechaPagoVOs(fechaPagoVOs);
                    valorFormaFechasPagoVO.setValorFormaPago(valorFormaPago);
                    valorFormaFechasPagoVOs.add(valorFormaFechasPagoVO);
                    /*Set setFechasPagos = new HashSet();
                    setFechasPagos = convertirFechasPagoVoToFechasPago(fechaPagoVOs);
                    RespuestaVO respuestaVO1 = valorFormaPagoDao.saveFechasPago(setFechasPagos, valorFormaPago.getId(), (short) 2);
                    if (respuestaVO1.getIdRespuesta() == -1) {
                    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + "No se pudo guardar las nuevas fechas de pago" + Mensajes.SALTO_LINEA + respuestaVO1.getMensajeRespuesta());
                    hacerCommit = false;
                    break;
                    }*/
                }
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, "", valorFormaFechasPagoVOs);
                /*if (hacerCommit) {
                commit();
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                }*/
            } else {
                //respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + "La generación de fechas de pago, solo es permitida para contratos vigentes e indefinidos");
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, "La generación de fechas de pago, solo es permitida para contratos vigentes e indefinidos");
            }
        } catch (Exception e) {
            System.out.println();
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            //close();
            return respuestaVO;
        }
    }

    private FechasPago getLastFechaValorFormaPago(ValorFormaPago valorFormaPago) {
        FechasPago fechasPago = null;
        try {
            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("valorFormaPago", valorFormaPago));
            criteria.addOrder(Order.asc("fecha"));
            List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
            fechasPagos = criteria.list();
            fechasPago = new FechasPago();
            fechasPago = fechasPagos.get(fechasPagos.size() - 1);
        } catch (Exception e) {
            System.out.println();
        } finally {
            return fechasPago;
        }
    }

    private Calendar getFechaFin(Calendar calendar1) {

        int year = calendar1.get(Calendar.YEAR);
        int mes = calendar1.get(Calendar.MONTH);


        int newYear = year;
        int newMes;
        int newDia;

        if (mes == 11) {
            newMes = 5;
            newYear = year + 1;
        } else {
            newMes = mes + 6;
            if (newMes > 11) {
                int diferencia = newMes - 11;
                newMes = diferencia - 1;
                newYear = year + 1;
            }
        }

        Fechas fechas = new Fechas();
        newDia = fechas.getNumeroDiasMes(newMes + 1, newYear);
        Calendar calendarRetorno = Calendar.getInstance();
        calendarRetorno.set(Calendar.YEAR, newYear);
        calendarRetorno.set(Calendar.MONTH, newMes);
        calendarRetorno.set(Calendar.DAY_OF_MONTH, newDia);

        return calendarRetorno;
    }

    private Set convertirFechasPagoVoToFechasPago(List<FechaPagoVO> fechaPagoVOs) {
        Set fechasPagos = new HashSet();
        for (FechaPagoVO fechaPagoVO : fechaPagoVOs) {
            FechasPago fechasPago = new FechasPago(fechaPagoVO.getFechaPago(), fechaPagoVO.getFechaIni(), fechaPagoVO.getFechaFin());
            fechasPagos.add(fechasPago);
        }
        return fechasPagos;
    }

    public RespuestaVO guardarNuevasFechasPago(ValorFormaPagoFxVO[] valorFormaPagoFxVOs) {
        RespuestaVO respuestaVO = new RespuestaVO();
        boolean hacerCommit = true;
        try {
            begin();
            ValorFormaPagoDao valorFormaPagoDao = new ValorFormaPagoDao();
            for (ValorFormaPagoFxVO valorFormaPagoFxVO : valorFormaPagoFxVOs) {
                int idValorFormaPago = valorFormaPagoFxVO.getIdValorFormaPago();
                ValorFormaPago valorFormaPago = new ValorFormaPago(idValorFormaPago);
                List<FechaPagoVO> fechaPagoVOs = new ArrayList<FechaPagoVO>();
                fechaPagoVOs = valorFormaPagoFxVO.getFechaPagoVOs();
                Set setFechasPagos = new HashSet();
                setFechasPagos = convertirFechasPagoVoToFechasPago(fechaPagoVOs);
                RespuestaVO respuestaVO1 = valorFormaPagoDao.saveFechasPago(setFechasPagos, valorFormaPago.getId(), (short) 2);
                if (respuestaVO1.getIdRespuesta() == -1) {
                    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + "No se pudo guardar las nuevas fechas de pago" + Mensajes.SALTO_LINEA + respuestaVO1.getMensajeRespuesta());
                    hacerCommit = false;
                    break;
                }
            }
            if (hacerCommit) {
                commit();
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
            }
        } catch (Exception e) {
            System.out.println();
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }
}
