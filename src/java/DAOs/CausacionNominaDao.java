/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.CausaNominaProvision;
import Entidades.CausacionDeduccionNomina;
import Entidades.CausacionEmpresa;
import Entidades.CausacionNomina;
import Entidades.Contrato;
import Entidades.Deducciones;
import Entidades.FechasPago;
import Entidades.OtroMasNomina;
import Entidades.Provisiones;
import Entidades.TiposContrato;
import Entidades.Tributaria;
import Entidades.ValorFormaPago;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.ProvisionesVO;
import VO.RecursoHumanoVO;
import VO.RespuestaVO;
//import VO.ValorIdVO;
import VO.ValorIdVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class CausacionNominaDao extends DAO {

    Utilidades utilidades = new Utilidades();

    public List<CausacionNomina> listarCausacionNomina(int mes, int year/*, short tipoContrato*/) {
        List<CausacionNomina> causacionNominas = new ArrayList<CausacionNomina>();
        try {
            begin();
            //OBTENEMOS LAS FECHAS
            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("idTipoContrato", (short) 2 /*tipoContrato*/));
            java.sql.Date rangoFechas[] = utilidades.normalizarFechasBusqueda(mes, year);
            criteria.add(Restrictions.between("iniPeriodoPago", rangoFechas[0], rangoFechas[1]));
            criteria.add(Restrictions.eq("activa", (short) 1));
            List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
            fechasPagos = criteria.list();
            Set<Contrato> contratos = new HashSet<Contrato>();
            contratos = getContratosToCausar(fechasPagos);
            causacionNominas = crearListaCausacionNominaVO(contratos, year);
        } catch (Exception e) {
            System.out.println();
        } finally {
            //close();
            return causacionNominas;
        }
    }

    private Object[] getDatosRh(int idRh) {
        Object[] objects = new Object[3];
        try {
            RecursoHumanoDao recursoHumanoDao = new RecursoHumanoDao();
            RecursoHumanoVO recursoHumanoVO = recursoHumanoDao.getRecursoHumanoById(idRh);
            objects[0] = recursoHumanoVO.getId();
            objects[1] = recursoHumanoVO.getNombre() + " " + recursoHumanoVO.getApellido();
            objects[2] = recursoHumanoVO.getCedula();
        } catch (Exception e) {
            System.out.println();
        } finally {
            return objects;
        }
    }

    private Object[] getDatosTipoContrato(Contrato contrato) {
        Object[] objects = new Object[2];
        String nombreTipoContrato;
        short idTipoContrato;
        try {

            TiposContrato tiposContrato = new TiposContrato();
            tiposContrato = contrato.getTiposContrato();
            idTipoContrato = tiposContrato.getId();
            if (tiposContrato.getId() == 1/*Prestacion de servicios*/) {
                nombreTipoContrato = tiposContrato.getTipoContrato();

            } else {

                if (contrato.getFechaFin() == null) {
                    nombreTipoContrato = "Indefinido";
                } else {
                    nombreTipoContrato = "Termino fijo";
                }
            }
            objects[0] = idTipoContrato;
            objects[1] = nombreTipoContrato;

        } catch (Exception e) {
            System.out.println();
        } finally {
            return objects;
        }
    }

    private double getSalario(Contrato contrato) {
        double salario = 0;
        try {
            Set<ValorFormaPago> valorFormaPagos = new HashSet<ValorFormaPago>();
            valorFormaPagos = contrato.getValorFormaPagos();
            Iterator<ValorFormaPago> iterator = valorFormaPagos.iterator();
            while (iterator.hasNext()) {
                ValorFormaPago valorFormaPago = new ValorFormaPago();
                valorFormaPago = iterator.next();
                salario = salario + valorFormaPago.getValorPago();
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return salario;
        }
    }

    private double getIbc(double salario, int yearToCausar) {
        double ibc = 0;
        try {
            CausacionDao causacionDao = new CausacionDao();
            double smlv = causacionDao.getSalarioMinimoByYear(yearToCausar);
            if (salario > smlv) {
                ibc = salario;
            } else {
                ibc = smlv;
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return ibc;
        }
    }

    private double getAuxTransporte(double salario, int yearToCausar) {
        double auxTransporte = 0;
        try {
            CausacionDao causacionDao = new CausacionDao();
            double smlv = causacionDao.getSalarioMinimoByYear(yearToCausar);
            auxTransporte = causacionDao.getAuxTransporteByYear(yearToCausar);
            if (salario > (smlv * 2)) {
                auxTransporte = 0;
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return auxTransporte;
        }
    }

    private double[] getDescuentoPorSaludPension(double ibc, int yearToCausar) {
        double[] retorno = new double[2];
        try {
            double descuentoPorSalud = 0;
            double descuentoPension = 0;
            String[] valores = new String[2];

            CausacionDao causacionDao = new CausacionDao();
            valores = causacionDao.getPorcentajeSaludPensaionEmploy(yearToCausar);

            double valSalud = Double.parseDouble(valores[0]);
            double valPension = Double.parseDouble(valores[1]);

            descuentoPorSalud = ibc * ((valSalud * 10) / 1000);
            descuentoPension = ibc * ((valPension * 10) / 1000);

            retorno[0] = descuentoPorSalud;
            retorno[1] = descuentoPension;

        } catch (Exception e) {
            System.out.println();
        } finally {
            return retorno;
        }
    }

    private double calcularSubtotal(double salario, int diasTrabajados) {
        return (salario * diasTrabajados) / 30;
    }

    private List<CausacionNomina> crearListaCausacionNominaVO(Set<Contrato> contratos, int yearToCausar) {
        List<CausacionNomina> causacionNominas = new ArrayList<CausacionNomina>();
        try {
            Iterator<Contrato> conIterator = contratos.iterator();
            while (conIterator.hasNext()) {
                Contrato contrato = new Contrato();
                contrato = conIterator.next();

                int idContrato = contrato.getId();

                //Datos de recursoHumano
                Object[] objRh = new Object[3];
                objRh = getDatosRh(contrato.getRecursoHumano().getId());
                int idRecursoHumano = Integer.parseInt(objRh[0].toString());
                String nombreRh = objRh[1].toString();
                String identificacion = objRh[2].toString();
                //Datos del tipo de contrato
                Object[] objTipoContrato = new Object[2];
                objTipoContrato = getDatosTipoContrato(contrato);
                short idTipoContrato = Short.parseShort(objTipoContrato[0].toString());
                String tipoContrato = objTipoContrato[1].toString();

                double salario = getSalario(contrato);
                double ibc = getIbc(salario, yearToCausar);
                String tiempo = contrato.getTiempo() + "";

                short diasTrabajados = 30;
                double subtotal = calcularSubtotal(salario, diasTrabajados);
                double auxilioTransporte = getAuxTransporte(salario, yearToCausar);
                Tributaria tributaria = null;
                int idTributaria = -1;
                if (auxilioTransporte > 0) {
                    CausacionDao causacionDao = new CausacionDao();
                    tributaria = new Tributaria();
                    tributaria = causacionDao.getTributaria(yearToCausar);
                    idTributaria = tributaria.getId();
                }
                double[] valoresSaludPension = new double[2];
                valoresSaludPension = getDescuentoPorSaludPension(ibc, yearToCausar);
                double descuentoSalud = valoresSaludPension[0];
                double descuentoPension = valoresSaludPension[1];
                double totalOtrasDeducciones = 0;
                double totalOtrosMas = 0;
                double totalAPagar = ((((subtotal + auxilioTransporte) - descuentoSalud) - descuentoPension) - totalOtrasDeducciones) + totalOtrosMas;
                CausacionEmpresa causacionEmpresa = new CausacionEmpresa();
                CausacionEmpresaDao causacionEmpresaDao = new CausacionEmpresaDao();
                causacionEmpresa = causacionEmpresaDao.crearCausacionEmpresa(ibc, diasTrabajados, salario, auxilioTransporte);
                double totalCausacionEmpresa = Math.round(causacionEmpresa.getTotal());
                CausacionNomina causacionNomina = new CausacionNomina(idContrato, idTributaria, salario, ibc, diasTrabajados, subtotal, auxilioTransporte, descuentoSalud, descuentoPension, totalOtrasDeducciones, totalOtrosMas, totalAPagar, totalCausacionEmpresa, idRecursoHumano, nombreRh, identificacion, tipoContrato, idTipoContrato, tiempo, tributaria, causacionEmpresa);
                causacionNominas.add(causacionNomina);
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return causacionNominas;
        }
    }

    protected Set<Contrato> getContratosToCausar(List<FechasPago> fechasPagos) {
        Set<Contrato> contratos = new HashSet<Contrato>();
        try {
            Set<ValorFormaPago> valorFormaPagos = new HashSet<ValorFormaPago>();
            valorFormaPagos = getValoresFormaPago(fechasPagos);
            valorFormaPagos = setFechasPagoToValoresFormaPago(valorFormaPagos, fechasPagos);
            contratos = getContratos(valorFormaPagos);
            contratos = setValorFormaPagoToContrato(contratos, valorFormaPagos);
        } catch (Exception e) {
            System.out.println();
        } finally {
            return contratos;
        }

    }

    private Set<ValorFormaPago> getValoresFormaPago(List<FechasPago> fechasPagos) {
        Set<ValorFormaPago> valorFormaPagos = new HashSet<ValorFormaPago>();
        try {
            for (FechasPago fechasPago : fechasPagos) {
                valorFormaPagos.add(fechasPago.getValorFormaPago());
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return valorFormaPagos;
        }

    }

    private Set<ValorFormaPago> setFechasPagoToValoresFormaPago(Set<ValorFormaPago> valorFormaPagos, List<FechasPago> fechasPagos) {
        Set<ValorFormaPago> newValorFormaPagos = new HashSet<ValorFormaPago>();
        try {
            Iterator<ValorFormaPago> iterator = valorFormaPagos.iterator();
            while (iterator.hasNext()) {
                ValorFormaPago valorFormaPago = new ValorFormaPago();
                valorFormaPago =
                        iterator.next();
                Set<FechasPago> newFechasPagos = new HashSet<FechasPago>();
                for (FechasPago fechasPago : fechasPagos) {
                    if (fechasPago.getValorFormaPago().getId() == valorFormaPago.getId()) {
                        newFechasPagos.add(fechasPago);
                    }

                }
                valorFormaPago.setFechasPagos(newFechasPagos);
                newValorFormaPagos.add(valorFormaPago);
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return newValorFormaPagos;
        }

    }

    private Set<Contrato> getContratos(Set<ValorFormaPago> valorFormaPagos) {
        Set<Contrato> contratos = new HashSet<Contrato>();
        try {
            java.util.Iterator<ValorFormaPago> valoIterator = valorFormaPagos.iterator();
            while (valoIterator.hasNext()) {
                ValorFormaPago valorFormaPago = valoIterator.next();
                contratos.add(valorFormaPago.getContrato());
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            System.out.println();
            return contratos;
        }
    }

    private Set<Contrato> setValorFormaPagoToContrato(Set<Contrato> contratos, Set<ValorFormaPago> valorFormaPagos) {
        Set<Contrato> newContratos = new HashSet<Contrato>();
        try {
            Iterator<Contrato> conIterator = contratos.iterator();
            while (conIterator.hasNext()) {
                Contrato contrato = new Contrato();
                contrato =
                        conIterator.next();

                Set<ValorFormaPago> newFormaPagos = new HashSet<ValorFormaPago>();
                Iterator<ValorFormaPago> valoIterator = valorFormaPagos.iterator();
                while (valoIterator.hasNext()) {
                    ValorFormaPago valorFormaPago = new ValorFormaPago();
                    valorFormaPago =
                            valoIterator.next();
                    if (valorFormaPago.getContrato().getId() == contrato.getId()) {
                        newFormaPagos.add(valorFormaPago);
                    }
                }
                contrato.setValorFormaPagos(newFormaPagos);
                newContratos.add(contrato);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return newContratos;
        }
    }

    public RespuestaVO crear(CausacionNomina[] causacionNominas, Date fechaCorte, int mesCausado, int yearCausado) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
        try {
            begin();
            CausacionDao causacionDao = new CausacionDao();
            RespuestaVO respuestaVOFechasC = causacionDao.guardarFechaCorte(fechaCorte, (short) 1, mesCausado, yearCausado);
            boolean hacerCommit = true;
            if (respuestaVOFechasC.getIdRespuesta() != -1) {
                Criteria criteria;
                for (CausacionNomina causacionNomina : causacionNominas) {

                    causacionNomina.setIdFechaCorte(respuestaVOFechasC.getIdRespuesta());
                    Serializable serializable = getSession().save(causacionNomina);
                    int idCausacion = Integer.parseInt(serializable.toString());

                    RespuestaVO respuestaVODeducciones = causacionDao.guardarAllDeducciones(idCausacion, causacionNomina.getIdsDeducciones(), (short) 1);
                    if (respuestaVODeducciones.getIdRespuesta() == -1) {
                        hacerCommit = false;
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVODeducciones.getMensajeRespuesta());
                        break;
                    }

                    RespuestaVO respuestaVOProvisiones = causacionDao.guardarAllProvisiones(idCausacion, causacionNomina.getProvisiones(), (short) 1);
                    if (respuestaVOProvisiones.getIdRespuesta() == -1) {
                        hacerCommit = false;
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOProvisiones.getMensajeRespuesta());
                        break;
                    }

                    RespuestaVO respuestaVOtrosMas = causacionDao.guardarAllOtrosMas(idCausacion, causacionNomina.getOtrosMases());
                    if (respuestaVOtrosMas.getIdRespuesta() == -1) {
                        hacerCommit = false;
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOtrosMas.getMensajeRespuesta());
                        break;
                    }

                    RespuestaVO respuestaVOCausEmpresa = guardarCausacionEmpresa(idCausacion, causacionNomina.getCausacionEmpresa());
                    if (respuestaVOCausEmpresa.getIdRespuesta() == -1) {
                        hacerCommit = false;
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOCausEmpresa.getMensajeRespuesta());
                        break;
                    }
                }
                if (hacerCommit) {
                    commit();
                    respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                }
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOFechasC.getMensajeRespuesta());
            }


        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
            System.out.println();
        } finally {
            System.out.println();
            close();
            return respuestaVO;
        }

    }

    private RespuestaVO guardarCausacionEmpresa(
            int idCausacionNomina, CausacionEmpresa causacionEmpresa) {
        RespuestaVO respuestaVO = new RespuestaVO((short) -1, "");
        try {
            causacionEmpresa.setIdCausacionNomina(idCausacionNomina);
            getSession().save(causacionEmpresa);
            respuestaVO =
                    new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }

    }

    public List<CausacionNomina> calcularCausacionNomina(CausacionNomina[] causacionNominas, int yearToCausar) {
        List<CausacionNomina> causacionNominas1 = new ArrayList<CausacionNomina>();
        try {
            CausacionDao causacionDao = new CausacionDao();
            for (CausacionNomina causacionNomina : causacionNominas) {

                double subtotal = calcularSubtotal(causacionNomina.getSalario(), causacionNomina.getDiasTrabajados());//*
                double totalOtrasDeducciones = causacionDao.calcularTotalDeducciones(causacionNomina.getIdsDeducciones());//
                double totalDeduccionesAfectanIbc = causacionDao.calcularTotalDeduccionesAfectanIbc(causacionNomina.getIdsDeducciones());

                causacionNomina.setIbc(causacionNomina.getIbc() + totalDeduccionesAfectanIbc);

                double totalOtrosMas = causacionDao.calcularTotalOtrosMas(causacionNomina.getOtrosMases());//
                double totalOtrosMasAfectaIbc = causacionDao.calcularTotalOtrosMasAfectaIbc(causacionNomina.getOtrosMases());

                causacionNomina.setIbc(causacionNomina.getIbc() + totalOtrosMasAfectaIbc);

                double[] valoresSaludPension = new double[2];
                valoresSaludPension =
                        getDescuentoPorSaludPension(causacionNomina.getIbc(), yearToCausar);
                double descuentoSalud = valoresSaludPension[0];//
                double descuentoPension = valoresSaludPension[1];//
                //
                double total_A_Pagar = subtotal + causacionNomina.getAuxilioTransporte() - descuentoSalud - descuentoPension - totalOtrasDeducciones + totalOtrosMas;

                causacionNomina.setSubtotal(subtotal);
                causacionNomina.setTotalOtrasDeducciones(totalOtrasDeducciones);
                causacionNomina.setTotalOtrasDeducciones(totalOtrasDeducciones);
                causacionNomina.setTotalOtrosMas(totalOtrosMas);
                causacionNomina.setDescuentoSalud(descuentoSalud);
                causacionNomina.setDescuentoPension(descuentoPension);
                causacionNomina.setTotalAPagar(total_A_Pagar);
                causacionNominas1.add(causacionNomina);
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return causacionNominas1;
        }
    }

    public List<CausacionNomina> listarCausacionNominaByFechasCorte(int[] idsFechasCorte) {
        List<CausacionNomina> causacionNominasN1 = new ArrayList<CausacionNomina>();
        try {
            begin();
            Criteria criteria;
            for (int i : idsFechasCorte) {
                criteria = getSession().createCriteria(CausacionNomina.class);
                criteria.add(Restrictions.eq("idFechaCorte", i));
                List<CausacionNomina> causacionNominasN2 = new ArrayList<CausacionNomina>();
                causacionNominasN2 = criteria.list();
                for (CausacionNomina causacionNomina : causacionNominasN2) {
                    causacionNominasN1.add(causacionNomina);
                }
            }
            causacionNominasN1 = addValoresToListaCausacionNomina(causacionNominasN1);
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            close();
            return causacionNominasN1;
        }
    }

    private List<CausacionNomina> addValoresToListaCausacionNomina(List<CausacionNomina> causacionNominas) {
        List<CausacionNomina> causacionNominasN1 = new ArrayList<CausacionNomina>();
        try {
            Criteria criteria;
            for (CausacionNomina causacionNomina : causacionNominas) {
                criteria = getSession().createCriteria(CausacionEmpresa.class);
                criteria.add(Restrictions.eq("idCausacionNomina", causacionNomina.getId()));
                CausacionEmpresa causacionEmpresa = new CausacionEmpresa();
                causacionEmpresa = (CausacionEmpresa) criteria.uniqueResult();
                causacionNomina.setCausacionEmpresa(causacionEmpresa);
                causacionNomina.setIdsDeducciones(getIdsDeducciones(causacionNomina));
                causacionNomina.setDeduccioneses(getDeduccionesNomina(causacionNomina.getIdsDeducciones()));
                causacionNomina.setProvisiones(getProvisiones(causacionNomina));
                causacionNomina.setProvisionesVOs(this.PROVISIONES_VOS);
                causacionNomina.setOtrosMases(getOtrosMas(causacionNomina));

                ContratoDao contratoDao = new ContratoDao();
                Contrato contrato = contratoDao.getContratoById(causacionNomina.getIdContrato());

                //Datos de recursoHumano
                Object[] objRh = new Object[3];
                objRh = getDatosRh(contrato.getRecursoHumano().getId());
                int idRecursoHumano = Integer.parseInt(objRh[0].toString());
                String nombreRh = objRh[1].toString();
                String identificacion = objRh[2].toString();
                //Datos del tipo de contrato
                Object[] objTipoContrato = new Object[2];
                objTipoContrato = getDatosTipoContrato(contrato);
                short idTipoContrato = Short.parseShort(objTipoContrato[0].toString());
                String tipoContrato = objTipoContrato[1].toString();

                causacionNomina.setIdRecursoHumano(idRecursoHumano);
                causacionNomina.setNombreRh(nombreRh);
                causacionNomina.setIdentificacion(identificacion);
                causacionNomina.setTipoContrato(tipoContrato);
                causacionNomina.setIdTipoContrato(idTipoContrato);
                causacionNomina.setTiempo(contrato.getTiempo() + "");

                causacionNominasN1.add(causacionNomina);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return causacionNominasN1;
        }
    }

    private List<Deducciones> getDeduccionesNomina(int[] idsDeducciones) {
        List<Deducciones> deduccioneses = new ArrayList<Deducciones>();
        try {
            Criteria criteria;
            for (int i : idsDeducciones) {
                criteria = getSession().createCriteria(Deducciones.class);
                criteria.add(Restrictions.eq("id", i));
                Deducciones deducciones = new Deducciones();
                deducciones = (Deducciones) criteria.uniqueResult();
                deduccioneses.add(deducciones);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return deduccioneses;
        }
    }

    private int[] getIdsDeducciones(CausacionNomina causacionNomina) {
        int[] idsDeducciones = null;
        try {
            Criteria criteria = getSession().createCriteria(CausacionDeduccionNomina.class);
            criteria.add(Restrictions.eq("idCausacion", causacionNomina.getId()));
            List<CausacionDeduccionNomina> causacionDeduccionNominas = new ArrayList<CausacionDeduccionNomina>();
            causacionDeduccionNominas = criteria.list();
            idsDeducciones = new int[causacionDeduccionNominas.size()];

            for (int i = 0; i < causacionDeduccionNominas.size(); i++) {
                CausacionDeduccionNomina causacionDeduccionNomina = causacionDeduccionNominas.get(i);
                idsDeducciones[i] = causacionDeduccionNomina.getId();
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return idsDeducciones;
        }
    }
    private List<ProvisionesVO> PROVISIONES_VOS;

    private ValorIdVO[] getProvisiones(CausacionNomina causacionNomina) {
        ValorIdVO[] valorIdVOs = null;
        this.PROVISIONES_VOS = new ArrayList<ProvisionesVO>();
        try {
            Criteria criteria = getSession().createCriteria(CausaNominaProvision.class);
            criteria.add(Restrictions.eq("causacionNomina", causacionNomina));
            List<CausaNominaProvision> causaNominaProvisions = new ArrayList<CausaNominaProvision>();
            causaNominaProvisions = criteria.list();
            valorIdVOs = new ValorIdVO[causaNominaProvisions.size()];
            Criteria criteria1;
            for (int i = 0; i < causaNominaProvisions.size(); i++) {
                CausaNominaProvision causaNominaProvision = causaNominaProvisions.get(i);
                ValorIdVO valorIdVO = new ValorIdVO(causaNominaProvision.getId(), causaNominaProvision.getValor());
                valorIdVOs[i] = valorIdVO;
                ProvisionesVO provisionesVO = new ProvisionesVO();
                provisionesVO.setValor(valorIdVO.getValor());
                criteria1 = getSession().createCriteria(Provisiones.class);
                criteria1.add(Restrictions.eq("id", causaNominaProvision.getProvisiones().getId()));
                Provisiones provisiones = new Provisiones();
                provisiones = (Provisiones) criteria1.uniqueResult();
                provisionesVO.setNombre(provisiones.getNombre());
                provisionesVO.setObservacion(provisiones.getObservacion());
                PROVISIONES_VOS.add(provisionesVO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return valorIdVOs;
        }
    }

    private OtroMasNomina[] getOtrosMas(CausacionNomina causacionNomina) {
        OtroMasNomina[] otrosMases = null;
        try {
            Criteria criteria = getSession().createCriteria(OtroMasNomina.class);
            criteria.add(Restrictions.eq("idCausacionNomina", causacionNomina.getId()));
            List<OtroMasNomina> list = new ArrayList<OtroMasNomina>();
            list = criteria.list();
            otrosMases = new OtroMasNomina[list.size()];
            for (int i = 0; i < list.size(); i++) {
                OtroMasNomina otroMasNomina = list.get(i);
                otrosMases[i]=otroMasNomina;
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return otrosMases;
        }
    }
}
