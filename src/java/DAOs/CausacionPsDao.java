/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Bonificacion;
import Entidades.CausPsBonificacion;

import Entidades.CausaPsDeduccion;
import Entidades.CausacionPs;
import Entidades.Contrato;
import Entidades.Deducciones;
import Entidades.FechasPago;
import Entidades.OtrosMasPs;
import Entidades.ProvisionPs;
import Entidades.RecursoHumano;
import Entidades.RetencionPagos;
import Entidades.ValorFormaPago;
import Entidades.VfpRetPagos;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.CausacionPsVO;
import VO.CausacionVO;
import VO.RecursoHumanoVO;
import VO.RespuestaVO;
import VO.ValorFijoPsVO;
import VO.ValorIdVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class CausacionPsDao extends DAO {

    Utilidades utilidades = new Utilidades();

    public List<CausacionPs> generarCausacion(int mes, int year/*, short tipoContrato*/) {
        CausacionNominaDao causacionNominaDao = new CausacionNominaDao();
        List<CausacionPs> causacionPses = null;
        try {
            begin();
            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("idTipoContrato", (short) 1 /*tipoContrato*/));
            java.sql.Date rangoFechas[] = utilidades.normalizarFechasBusqueda(mes, year);
            criteria.add(Restrictions.between("finPeriodoPago", rangoFechas[0], rangoFechas[1]));
            criteria.add(Restrictions.eq("activa", (short) 1));
            List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
            fechasPagos = criteria.list();
            Set<Contrato> contratos = new HashSet<Contrato>();
            contratos = causacionNominaDao.getContratosToCausar(fechasPagos);
            causacionPses = new ArrayList<CausacionPs>();
            causacionPses = crearListaCausacionPsVO(contratos);
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return causacionPses;
        }
    }

    private List<CausacionPs> crearListaCausacionPsVO(Set<Contrato> contratos) {
        List<CausacionPs> causacionPses = new ArrayList<CausacionPs>();
        try {
            RecursoHumanoDao recursoHumanoDao = new RecursoHumanoDao();
            for (Contrato contrato : contratos) {
                RecursoHumanoVO recursoHumanoVO = new RecursoHumanoVO();
                recursoHumanoVO = recursoHumanoDao.getRecursoHumanoById(contrato.getRecursoHumano().getId());
                String identificacion = recursoHumanoVO.getCedula() + "";
                String rut = recursoHumanoVO.getRut() + "";
                String contratista = recursoHumanoVO.getNombre() + " " + recursoHumanoVO.getApellido();
                int idContrato = contrato.getId();
                Set<ValorFormaPago> valorFormaPagos = new HashSet<ValorFormaPago>();
                valorFormaPagos = contrato.getValorFormaPagos();
                ValorFijoPsVO[] valorFijoPsVOs = getValoresFijosPsVO(valorFormaPagos);
                double basico = getBasico(valorFijoPsVOs);
                double totalRetencionesBasicos = getTotalRetetencionesBasicos(valorFijoPsVOs);
                short diasTrabajados = 30;
                double totalDevengados = getTotalDevengados(basico, diasTrabajados);
                double totalOtrosMas = 0;
                double total = getTotal(totalDevengados, totalOtrosMas);
                OtrosMasPs[] otrosMasPses = getOtrosMasPs(valorFormaPagos);
                double totalRetencionesOtros = 0;
                double totalRetenciones = getTotalRetenciones(totalRetencionesBasicos, totalRetencionesOtros);
                double totalDeducciones = 0;
                double totalAPagar = getTotalAPagar(total, totalRetenciones, totalDeducciones);
                CausacionPs causacionPs = new CausacionPs(idContrato, basico, diasTrabajados, totalDevengados, totalOtrosMas, total, totalRetenciones, totalDeducciones, totalAPagar, otrosMasPses, valorFijoPsVOs, identificacion, rut, contratista, totalRetencionesBasicos, totalRetencionesOtros);
                causacionPses.add(causacionPs);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return causacionPses;
        }

    }

    public List<CausacionPs> calcularCausacionPS(CausacionPs[] causacionPses) {
        List<CausacionPs> causacionPses1 = new ArrayList<CausacionPs>();
        try {
            for (CausacionPs causacionPs : causacionPses) {
                double basico = causacionPs.getBasico();
                short diasTrabajados = causacionPs.getDiasTrabajados();
                double totalDevengados = getTotalDevengados(basico, diasTrabajados);
                double totalOtrosMas = getTotalOtrosMas(causacionPs.getOtrosMasPses());
                double total = getTotal(totalDevengados, totalOtrosMas);
                double totalRetencionesBasicos = causacionPs.getTotalRetencionesBasicos();
                double totalRetencionesOtros = getTotalRetencionesOtros(causacionPs.getOtrosMasPses());
                double totalRetenciones = getTotalRetenciones(totalRetencionesBasicos, totalRetencionesOtros);
                double totalDeducciones = 0;
                if (causacionPs.getIdsDeducciones() != null) {
                    totalDeducciones = getTotalDeducciones(causacionPs.getIdsDeducciones());
                }
                double totalAPagar = getTotalAPagar(total, totalRetenciones, totalDeducciones);

                int idContrato = causacionPs.getIdContrato();
                int[] idsDeducciones = causacionPs.getIdsDeducciones();
                ProvisionPs[] provisionPses = causacionPs.getProvisionPses();
                OtrosMasPs[] otrosMasPses = causacionPs.getOtrosMasPses();
                ValorFijoPsVO[] valorFijoPsVOs = causacionPs.getValorFijoPsVOs();
                String identificacion = causacionPs.getIdentificacion();
                String rut = causacionPs.getRut();
                String contratista = causacionPs.getContratista();

                CausacionPs causacionPs1 = new CausacionPs(basico, diasTrabajados, totalDevengados, totalOtrosMas, total, totalRetenciones, totalDeducciones, totalAPagar, idsDeducciones, provisionPses, otrosMasPses, valorFijoPsVOs, identificacion, rut, contratista, totalRetencionesBasicos, totalRetencionesOtros, idContrato);
                causacionPses1.add(causacionPs1);
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return causacionPses1;
        }
    }

    public RespuestaVO crear(CausacionPs[] causacionPses, Date fechaCorte, int mesCausado, int yearCausado) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            CausacionDao causacionDao = new CausacionDao();
            RespuestaVO respuestaVOFechasC = causacionDao.guardarFechaCorte(fechaCorte, (short) 2, mesCausado, yearCausado);
            if (respuestaVOFechasC.getIdRespuesta() != -1) {
                Criteria criteria;
                boolean hacerCommit = true;
                for (CausacionPs causacionPs : causacionPses) {
                    causacionPs.setIdFechaCorte(respuestaVOFechasC.getIdRespuesta());
                    Serializable serializable = getSession().save(causacionPs);
                    int id = Integer.parseInt(serializable.toString());
                    if (causacionPs.getIdsDeducciones() != null) {
                        RespuestaVO respuestaVO1 = causacionDao.guardarAllDeducciones(id, causacionPs.getIdsDeducciones(), (short) 2);
                        if (respuestaVO1.getIdRespuesta() == (short) -1) {
                            hacerCommit = false;
                            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVO1.getMensajeRespuesta());
                            break;
                        }
                    }

                    if (causacionPs.getProvisionPses() != null) {
                        RespuestaVO respuestaVO1 = guardarProvisionesPs(causacionPs.getProvisionPses(), id);
                        if (respuestaVO1.getIdRespuesta() == (short) -1) {
                            hacerCommit = false;
                            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVO1.getMensajeRespuesta());
                            break;
                        }
                    }

                    if (causacionPs.getOtrosMasPses() != null) {
                        RespuestaVO respuestaVO1 = guardarOtrosMasPs(causacionPs.getOtrosMasPses(), id);
                        if (respuestaVO1.getIdRespuesta() == (short) -1) {
                            hacerCommit = false;
                            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVO1.getMensajeRespuesta());
                            break;
                        }
                    }
                }
                if (hacerCommit) {
                    commit();
                    respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                }
            }

        } catch (Exception e) {
            System.out.println();
            respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO guardarOtrosMasPs(OtrosMasPs[] otrosMasPses, int idCausacionPs) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            for (OtrosMasPs otrosMasPs : otrosMasPses) {
                otrosMasPs.setIdCausacionPs(idCausacionPs);
                getSession().save(otrosMasPs);
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO guardarProvisionesPs(ProvisionPs[] provisionPses, int idCausacionPs) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {

            for (ProvisionPs provisionPs : provisionPses) {
                provisionPs.setIdCausacionPs(idCausacionPs);
                getSession().save(provisionPs);
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private double getTotalDeducciones(int[] idsDeducciones) {
        double total = 0;
        try {
            Criteria criteria;
            for (int i : idsDeducciones) {
                criteria = getSession().createCriteria(Deducciones.class);
                criteria.add(Restrictions.eq("id", i));
                Deducciones deducciones = (Deducciones) criteria.uniqueResult();
                total = total + deducciones.getValor();
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return total;
        }

    }

    private double getTotalRetencionesOtros(OtrosMasPs[] otrosMasPses) {
        double totalRetencion = 0;
        for (OtrosMasPs otrosMasPs : otrosMasPses) {
            totalRetencion = totalRetencion + otrosMasPs.getValorRetencion();
        }

        return totalRetencion;
    }

    private double getTotalOtrosMas(OtrosMasPs[] otrosMasPses) {
        double totalOtrosMas = 0;
        for (OtrosMasPs otrosMasPs : otrosMasPses) {
            totalOtrosMas = totalOtrosMas + otrosMasPs.getValor();
        }

        return totalOtrosMas;

    }

    private double getTotalAPagar(double total, double totalRetenciones, double totalDeducciones) {
        return (total - totalRetenciones) - totalDeducciones;
    }

    private OtrosMasPs[] getOtrosMasPs(Set<ValorFormaPago> valorFormaPagos) {
        List<OtrosMasPs> omps = new ArrayList<OtrosMasPs>();
        OtrosMasPs[] omps1;
        for (ValorFormaPago valorFormaPago : valorFormaPagos) {
            if (valorFormaPago.getTipoPago().getId() != (short) 1) {

                OtrosMasPs otrosMasPs = new OtrosMasPs();
                otrosMasPs.setNombre(valorFormaPago.getConcepto());
                otrosMasPs.setObservacion(valorFormaPago.getObservacion());
                otrosMasPs.setValor(valorFormaPago.getValorPago());
                double retencion = getRetencionValorFormaPago(valorFormaPago);
                otrosMasPs.setPorcentaje(retencion);
                double valorRetencion = 0;
                double total = otrosMasPs.getValor();
                if (retencion > 0) {
                    valorRetencion = otrosMasPs.getValor() * (retencion / 100);
                    total =
                            total - valorRetencion;
                }

                otrosMasPs.setValorRetencion(valorRetencion);
                otrosMasPs.setTotal(total);
                omps.add(otrosMasPs);
            }

        }
        omps1 = new OtrosMasPs[omps.size()];
        for (int i = 0; i <
                omps.size(); i++) {
            OtrosMasPs otrosMasPs = omps.get(i);
            omps1[i] = otrosMasPs;
        }

        return omps1;
    }

    private double getTotalRetenciones(double totalRetencionesBasicos, double totalRetencionesOtros) {
        return totalRetencionesBasicos + totalRetencionesOtros;

    }

    private double getTotal(double totalDevengados, double totalOtrosMas) {
        return totalDevengados + totalOtrosMas;
    }

    private double getTotalDevengados(double basico, short diasTrabajados) {
        return (basico * diasTrabajados) / 30;

    }

    private ValorFijoPsVO[] getValoresFijosPsVO(Set<ValorFormaPago> valorFormaPagos) {
        List<ValorFijoPsVO> valorFijoPsVOs = new ArrayList<ValorFijoPsVO>();
        ValorFijoPsVO[] valorFijoPsVOs1;
        for (ValorFormaPago valorFormaPago : valorFormaPagos) {
            if (valorFormaPago.getTipoPago().getId() == (short) 1) {
                ValorFijoPsVO valorFijoPsVO = new ValorFijoPsVO();
                valorFijoPsVO.setConcepto(valorFormaPago.getConcepto());
                valorFijoPsVO.setValor(valorFormaPago.getValorPago());
                double retencion = getRetencionValorFormaPago(valorFormaPago);
                valorFijoPsVO.setRetencion(retencion);
                double valorRetencion = 0;
                if (retencion > 0) {
                    valorRetencion = valorFijoPsVO.getValor() * (retencion / 100);
                }

                valorFijoPsVO.setValorRetencion(valorRetencion);
                valorFijoPsVOs.add(valorFijoPsVO);
            }

        }
        valorFijoPsVOs1 = new ValorFijoPsVO[valorFijoPsVOs.size()];

        for (int i = 0; i <
                valorFijoPsVOs.size(); i++) {
            ValorFijoPsVO valorFijoPsVO = valorFijoPsVOs.get(i);
            valorFijoPsVOs1[i] = valorFijoPsVO;
        }

        return valorFijoPsVOs1;
    }

    private double getBasico(ValorFijoPsVO[] valorFijoPsVOs) {
        double basico = 0;
        for (ValorFijoPsVO valorFijoPsVO : valorFijoPsVOs) {
            basico = basico + valorFijoPsVO.getValor();
        }

        return basico;
    }

    private double getTotalRetetencionesBasicos(ValorFijoPsVO[] valorFijoPsVOs) {
        double totalRetenciones = 0;
        for (ValorFijoPsVO valorFijoPsVO : valorFijoPsVOs) {
            totalRetenciones = totalRetenciones + valorFijoPsVO.getValorRetencion();
        }

        return totalRetenciones;
    }

    private double getRetencionValorFormaPago(ValorFormaPago valorFormaPago) {
        double retencion = 0;
        try {

            Criteria criteria = getSession().createCriteria(
                    VfpRetPagos.class);
            criteria.add(Restrictions.eq("valorFormaPago", valorFormaPago));
            List<VfpRetPagos> vfpRetPagoses = new ArrayList<VfpRetPagos>();
            vfpRetPagoses = criteria.list();








            if (vfpRetPagoses != null) {
                VfpRetPagos vrp = vfpRetPagoses.get(0);
                if (vrp != null) {
                    criteria = getSession().createCriteria(RetencionPagos.class);
                    criteria.add(Restrictions.eq("id", vrp.getRetencionPagos().getId()));
                    RetencionPagos retencionPagos = (RetencionPagos) criteria.uniqueResult();
                    if (retencionPagos != null) {
                        retencion = retencionPagos.getPorcentaje();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return retencion;
        }

    }

    /*
    public RespuestaVO crear(CausacionPsVO[] causacionPsVOs, Date fechaCorte, int mesCausado, int yearCausado) {
    RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
    boolean hacerCommit = true;
    try {
    begin();
    CausacionDao causacionDao = new CausacionDao();
    RespuestaVO respuestaVOGuardarFechaCorte = causacionDao.guardarFechaCorte(fechaCorte, CausacionDao.TIPO_CAUSACION_PS, mesCausado, yearCausado);
    FechasCorteCausacion fechasCorteCausacion;
    if (respuestaVOGuardarFechaCorte.getIdRespuesta() == -1) {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGuardarFechaCorte.getMensajeRespuesta());
    return respuestaVO;
    } else {
    fechasCorteCausacion = new FechasCorteCausacion(respuestaVOGuardarFechaCorte.getIdRespuesta());
    }
    for (CausacionPsVO causacionPsVO : causacionPsVOs) {
    CausacionPs causacionPs = new CausacionPs();
    causacionPs = this.causacionPsVoToCausacionPs(causacionPsVO);
    causacionPs.setIdFechaCorte(fechasCorteCausacion.getId());

    ////////////////////////////BORAR
    NewCausacionPs newCausacionPs = new NewCausacionPs();
    newCausacionPs.setBasico(causacionPs.getBasico());
    newCausacionPs.setContratista(causacionPs.getContratista());
    newCausacionPs.setDevengados(causacionPs.getDevengados());
    newCausacionPs.setDiasTrabajados(causacionPs.getDiasTrabajados());
    newCausacionPs.setIdFechaCorte(causacionPs.getIdFechaCorte());
    newCausacionPs.setIdFechasPago(causacionPs.getIdFechasPago());
    newCausacionPs.setRut(causacionPs.getRut());
    newCausacionPs.setTotalAPagar(causacionPs.getTotalAPagar());
    newCausacionPs.setTotalBonificaciones(causacionPs.getTotalBonificaciones());
    ////////////////////////////////
    Serializable serializable = getSession().save(newCausacionPs);
    int idCausacionPs = Integer.parseInt(serializable.toString());
    ValorIdVO[] registro_val_retencion = causacionPsVO.getRegistro_val_retencion();
    RespuestaVO respuestaVOGuardarRetencion = new RespuestaVO((short) 1, "");
    if (registro_val_retencion != null) {
    for (ValorIdVO valorIdVO : registro_val_retencion) {
    respuestaVOGuardarRetencion = causacionDao.guardarRetenciones(idCausacionPs, valorIdVO.getId(), valorIdVO.getValor(), CausacionDao.TIPO_CAUSACION_PS);
    if (respuestaVOGuardarRetencion.getIdRespuesta() == -1) {
    break;
    }
    }
    }
    if (respuestaVOGuardarRetencion.getIdRespuesta() == -1) {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGuardarRetencion.getMensajeRespuesta());
    hacerCommit = false;
    break;

    }

    ValorIdVO[] bonificaciones = causacionPsVO.getBonificacion();
    RespuestaVO respuestaVOGuardarBonifica = new RespuestaVO((short) 1, "");
    if (bonificaciones != null) {
    for (ValorIdVO valorIdVO : bonificaciones) {
    respuestaVOGuardarBonifica = causacionDao.guardarCausacionBonificacion(idCausacionPs, valorIdVO.getId(), CausacionDao.TIPO_CAUSACION_PS);
    if (respuestaVOGuardarBonifica.getIdRespuesta() == -1) {
    break;
    }
    }
    }
    if (respuestaVOGuardarBonifica.getIdRespuesta() == -1) {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGuardarBonifica.getMensajeRespuesta());
    hacerCommit = false;
    break;
    }
    ValorIdVO[] deducciones = causacionPsVO.getDeduccion();
    RespuestaVO respuestaVOGuardarDeducciones = new RespuestaVO((short) 1, "");
    if (deducciones != null) {
    for (ValorIdVO valorIdVO : deducciones) {
    respuestaVOGuardarDeducciones = causacionDao.guardarCausacionDeduccion(idCausacionPs, valorIdVO.getId(), valorIdVO.getValor(), CausacionDao.TIPO_CAUSACION_PS);
    if (respuestaVOGuardarDeducciones.getIdRespuesta() == -1) {
    break;
    }
    }
    }
    if (respuestaVOGuardarDeducciones.getIdRespuesta() == -1) {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGuardarDeducciones.getMensajeRespuesta());
    hacerCommit = false;
    break;
    }

    ValorIdVO[] provisiones = causacionPsVO.getProvision();
    RespuestaVO respuestaVOGuardarProvision = new RespuestaVO((short) 1, "");
    if (provisiones != null) {
    for (ValorIdVO valorIdVOProvision : provisiones) {
    respuestaVOGuardarProvision = causacionDao.guardarCausacionProvision(idCausacionPs, valorIdVOProvision.getId(), valorIdVOProvision.getValor(), CausacionDao.TIPO_CAUSACION_PS);
    if (respuestaVOGuardarProvision.getIdRespuesta() == -1) {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGuardarDeducciones.getMensajeRespuesta());
    break;
    }
    }
    }
    if (respuestaVOGuardarProvision.getIdRespuesta() == -1) {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOGuardarProvision.getMensajeRespuesta());
    hacerCommit = false;
    break;
    }

    }

    if (hacerCommit) {
    commit();
    respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
    }

    } catch (Exception e) {
    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
    System.out.println();
    } finally {
    close();
    return respuestaVO;
    }
    }*/

    /*    private CausacionPs causacionPsVoToCausacionPs(CausacionPsVO causacionPsVO) {
    CausacionPs causacionPs = new CausacionPs();
    causacionPs.setBasico(causacionPsVO.getBasico());
    causacionPs.setContratista(causacionPsVO.getContratista());
    causacionPs.setDevengados(causacionPsVO.getDevengados());
    causacionPs.setDiasTrabajados(causacionPsVO.getDiasTrabajados());
    causacionPs.setIdFechasPago(causacionPsVO.getIdFechasPago());
    causacionPs.setRut(causacionPsVO.getRut());
    causacionPs.setTotalAPagar(causacionPsVO.getTotalAPagar());
    causacionPs.setTotalBonificaciones(causacionPsVO.getTotalBonificaciones());

    return causacionPs;
    }*/
    public List<CausacionVO> calcularValoresCausacion(CausacionPsVO[] causacionPsVOs) {
        double devengados = 0;
        double totalBonificacion = 0;
        double total = 0;
        double valorTotalRetencion = 0;
        double totalAPagar = 0;
        double valDeducciones = 0;
        List<CausacionVO> causacionVOs = new ArrayList<CausacionVO>();
        try {
            begin();
            for (CausacionPsVO causacionPsVO : causacionPsVOs) {
                //CALCULAR DEVENGADOS
                devengados = calcularDevengados(causacionPsVO.getBasico(), causacionPsVO.getDiasTrabajados());
                //CALCULAR TOTAL DE LAS BONIFICACIONES
                if (causacionPsVO.getBonificacion() != null) {
                    totalBonificacion = calcularValorBonificaciones(causacionPsVO.getBonificacion());
                }

                total = calcularTotal(devengados, totalBonificacion);
                valorTotalRetencion =
                        calcularValorRetenciones(causacionPsVO.getIdFechasPago(), total);
                valDeducciones =
                        calcularDeducciones(causacionPsVO.getDeduccion());
                totalAPagar =
                        total - valorTotalRetencion - valDeducciones;

                /////////////////////////////////////////////
                devengados =
                        Math.round(devengados);
                totalBonificacion =
                        Math.round(totalBonificacion);
                total =
                        Math.round(total);
                valorTotalRetencion =
                        Math.round(valorTotalRetencion);
                totalAPagar =
                        Math.round(totalAPagar);
                valDeducciones =
                        Math.round(valDeducciones);

                CausacionVO causacionVO = new CausacionVO();
                //causacionVO = crearCausacionVO(devengados, totalBonificacion, total, valorTotalRetencion, totalAPagar, valDeducciones, causacionPsVO);
                causacionVOs.add(causacionVO);
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return causacionVOs;
        }

    }

    private double calcularDevengados(double basico, int diasTrabajados) {
        return (basico * diasTrabajados) / 30;
    }

    private double calcularValorBonificaciones(ValorIdVO[] bonificacion) {
        double totalBonifi = 0;
        try {
            Criteria criteria;
            if (bonificacion !=
                    null) {
                for (ValorIdVO valorIdVO : bonificacion) {
                    criteria = getSession().createCriteria(Bonificacion.class);
                    criteria.add(Restrictions.eq("id", valorIdVO.getId()));
                    Bonificacion bonificacion1 = (Bonificacion) criteria.uniqueResult();
                    totalBonifi = totalBonifi + bonificacion1.getValor();
                }
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return totalBonifi;
        }

    }

    private double calcularTotal(double devengados, double totalBonificacion) {
        return devengados + totalBonificacion;
    }

    private double calcularValorRetenciones(int idFechasPago, double total) {
        double valRetencion = 0;
        double valorTotalRet = 0;
        try {

            Criteria criteria = getSession().createCriteria(
                    FechasPago.class);
            criteria.add(Restrictions.eq("id", idFechasPago));
            FechasPago fechasPago = (FechasPago) criteria.uniqueResult();
            List<RetencionPagos> retencionPagoses = new ArrayList<RetencionPagos>();
            ValorFormaPago valorFormaPago = fechasPago.getValorFormaPago();
            retencionPagoses = valorFormaPago.getRetencionPagoses();















            if (retencionPagoses != null) {
                for (RetencionPagos retencionPagos : retencionPagoses) {
                    valRetencion = total * (retencionPagos.getPorcentaje() / 100);
                    total = total - valRetencion;
                    valorTotalRet = valorTotalRet + valRetencion;
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return valorTotalRet;
        }

    }

    private double calcularDeducciones(ValorIdVO[] deduccion) {

        double valDeducciones = 0;
        try {
            Criteria criteria;
            if (deduccion !=
                    null) {
                for (ValorIdVO valorIdVO : deduccion) {
                    criteria = getSession().createCriteria(Deducciones.class);
                    criteria.add(Restrictions.eq("id", valorIdVO.getId()));
                    Deducciones deducciones = (Deducciones) criteria.uniqueResult();
                    valDeducciones = valDeducciones + deducciones.getValor();
                }
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return valDeducciones;
        }

    }

    private int getIdValorFormaPago(int idFechaPago) {
        int idValFormaPgo = -1;
        try {

            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("id", idFechaPago));
            FechasPago fechasPago = (FechasPago) criteria.uniqueResult();
            idValFormaPgo = fechasPago.getValorFormaPago().getId();
        } catch (Exception e) {
            idValFormaPgo = -1;
        } finally {
            return idValFormaPgo;
        }

    }

    private int getIdContrato(int idValorFormaPago) {
        int idContrato = -1;
        try {

            Criteria criteria = getSession().createCriteria(ValorFormaPago.class);
            criteria.add(Restrictions.eq("id", idValorFormaPago));
            ValorFormaPago valorformaPago = (ValorFormaPago) criteria.uniqueResult();
            idContrato = valorformaPago.getContrato().getId();
        } catch (Exception e) {
            idContrato = -1;
        } finally {
            return idContrato;
        }

    }

    private int getIdRh(int idContrato) {
        int idRh = -1;
        try {

            Criteria criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("id", idContrato));
            Contrato contrato = (Contrato) criteria.uniqueResult();
            idRh = contrato.getRecursoHumano().getId();
        } catch (Exception e) {
            idRh = -1;
        } finally {
            return idRh;
        }

    }

    public List<CausacionPs> listarCausacionPsByFechasCorte(int[] idsFechasCorte) {
        List<CausacionPs> causacionPsesRetorno = new ArrayList<CausacionPs>();
        try {
            begin();
            Criteria criteria;
            for (int i : idsFechasCorte) {
                List<CausacionPs> causacionPses = new ArrayList<CausacionPs>();
                criteria = getSession().createCriteria(CausacionPs.class);
                criteria.add(Restrictions.eq("idFechaCorte", i));
                causacionPses = criteria.list();
                for (CausacionPs causacionPs : causacionPses) {
                    causacionPs.setIdsDeducciones(getIdsDeducciones(causacionPs.getId()));
                    causacionPs.setProvisionPses(getProvisionesPs(causacionPs.getId()));
                    causacionPs.setOtrosMasPses(getOtrosMasPs(causacionPs.getId()));
                    //////////////////////////////////////////////////////////////////////
                    RecursoHumanoVO recursoHumanoVO = getRecursoHumanoByContrato(causacionPs.getIdContrato());
                    String identificacion = recursoHumanoVO.getCedula()+"";
                    String rut = recursoHumanoVO.getRut();
                    String contratista = recursoHumanoVO.getNombre() + " " + recursoHumanoVO.getApellido();
                    double totalRetencionesBasicos = 0;
                    double totalRetencionesOtros = getTotalRetencionesOtros(causacionPs.getOtrosMasPses());
                    causacionPs.setIdentificacion(identificacion);
                    causacionPs.setRut(rut);
                    causacionPs.setContratista(contratista);
                    causacionPs.setContratista(contratista);
                    causacionPs.setTotalRetencionesBasicos(totalRetencionesBasicos);
                    causacionPs.setTotalRetencionesOtros(totalRetencionesOtros);
                    causacionPsesRetorno.add(causacionPs);
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return causacionPsesRetorno;
        }
    }

    private RecursoHumanoVO getRecursoHumanoByContrato(int idContrato) {
        RecursoHumanoVO recursoHumanoVO = new RecursoHumanoVO();
        try {
            ContratoDao contratoDao = new ContratoDao();
            Contrato contrato = contratoDao.getContratoById(idContrato);
            RecursoHumanoDao recursoHumanoDao = new RecursoHumanoDao();
            recursoHumanoVO = recursoHumanoDao.getRecursoHumanoById(contrato.getRecursoHumano().getId());
        } catch (Exception e) {
            System.out.println();
        } finally {
            return recursoHumanoVO;
        }
    }

    private OtrosMasPs[] getOtrosMasPs(int idCausacionPs) {
        OtrosMasPs[] omps = null;
        try {
            Criteria criteria = getSession().createCriteria(OtrosMasPs.class);
            criteria.add(Restrictions.eq("idCausacionPs", idCausacionPs));
            List<OtrosMasPs> otrosMasPses = new ArrayList<OtrosMasPs>();
            otrosMasPses = criteria.list();
            omps = new OtrosMasPs[otrosMasPses.size()];
            for (int i = 0; i < otrosMasPses.size(); i++) {
                OtrosMasPs otrosMasPs = otrosMasPses.get(i);
                omps[i] = otrosMasPs;
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return omps;
        }

    }

    private ProvisionPs[] getProvisionesPs(int idCausacionPs) {
        ProvisionPs[] pps = null;
        try {
            Criteria criteria = getSession().createCriteria(ProvisionPs.class);
            criteria.add(Restrictions.eq("idCausacionPs", idCausacionPs));
            List<ProvisionPs> provisionPses = new ArrayList<ProvisionPs>();
            provisionPses = criteria.list();
            pps = new ProvisionPs[provisionPses.size()];
            for (int i = 0; i < provisionPses.size(); i++) {
                ProvisionPs provisionPs = provisionPses.get(i);
                pps[i] = provisionPs;

            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return pps;
        }

    }

    private int[] getIdsDeducciones(int idCausacionPs) {
        int[] idsDeducciones = null;
        try {
            Criteria criteria = getSession().createCriteria(CausaPsDeduccion.class);
            criteria.add(Restrictions.eq("idCausacionPs", idCausacionPs));
            List<CausaPsDeduccion> causaPsDeduccions = new ArrayList<CausaPsDeduccion>();
            causaPsDeduccions = criteria.list();
            idsDeducciones = new int[causaPsDeduccions.size()];
            for (int i = 0; i < causaPsDeduccions.size(); i++) {
                CausaPsDeduccion causaPsDeduccion = causaPsDeduccions.get(i);
                idsDeducciones[i] = causaPsDeduccion.getIdDeducion();
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return idsDeducciones;
        }
    }

    private ValorIdVO[] getValIdVoBonificaciones(int idCausacionPs) {
        ValorIdVO[] valorIdVOs = null;
        try {

            Criteria criteria = getSession().createCriteria(
                    CausPsBonificacion.class);
            criteria.add(Restrictions.eq("idCausacionPs", idCausacionPs));
            List<CausPsBonificacion> causPsBonificacions = new ArrayList<CausPsBonificacion>();
            causPsBonificacions = criteria.list();
            valorIdVOs = new ValorIdVO[causPsBonificacions.size()];



            for (int i = 0;
                    i < causPsBonificacions.size();
                    i++) {
                valorIdVOs[i] = new ValorIdVO(causPsBonificacions.get(i).getIdBonificacion(), 0);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return valorIdVOs;
        }

    }

    private ValorIdVO[] getValIdVoProvisiones(int idCausacionPs) {
        ValorIdVO[] valorIdVOs = null;
        try {
            /*Criteria criteria = getSession().createCriteria(CausaProvisionPs.class);
            criteria.add(Restrictions.eq("idCausaPs", idCausacionPs));
            List<CausaProvisionPs> causaProvisionPses = new ArrayList<CausaProvisionPs>();
            causaProvisionPses = criteria.list();
            valorIdVOs = new ValorIdVO[causaProvisionPses.size()];
            for (int i = 0; i < causaProvisionPses.size(); i++) {
            valorIdVOs[i] = new ValorIdVO(causaProvisionPses.get(i).getIdProvision(), Float.parseFloat(causaProvisionPses.get(i).getValor() + ""));
            }*/
        } catch (Exception e) {
            System.out.println();
        } finally {
            return valorIdVOs;
        }

    }

    private ValorIdVO[] getValIdVoDeduccion(int idCausacionPs) {
        ValorIdVO[] valorIdVOs = null;
        try {

            Criteria criteria = getSession().createCriteria(
                    CausaPsDeduccion.class);
            criteria.add(Restrictions.eq("idCausacionPs", idCausacionPs));
            List<CausaPsDeduccion> causaPsDeduccions = new ArrayList<CausaPsDeduccion>();
            causaPsDeduccions = criteria.list();
            valorIdVOs = new ValorIdVO[causaPsDeduccions.size()];



            for (int i = 0;
                    i < causaPsDeduccions.size();
                    i++) {
                valorIdVOs[i] = new ValorIdVO(causaPsDeduccions.get(i).getIdDeducion(), 0);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return valorIdVOs;
        }
    }
}
