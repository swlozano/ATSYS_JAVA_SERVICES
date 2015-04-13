/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Bonificacion;
import Entidades.CausaNominaProvision;

import Entidades.CausaPsDeduccion;
import Entidades.CausacionBonificacion;
import Entidades.CausacionDeduccionNomina;
import Entidades.CausacionNomina;
import Entidades.Deducciones;
import Entidades.FechasCorteCausacion;
import Entidades.OtroMasNomina;
import Entidades.Provisiones;
import Entidades.RegtroValRtencion;
import Entidades.RetencionPagos;
import Entidades.TipoAsocaja;
import Entidades.Tributaria;
import Entidades.ValorFormaPago;
import Entidades.ValoresAsocaja;
import Entidades.VfpRetPagos;
import Utilidades.DAO;
import Utilidades.Mensajes;
import VO.RespuestaVO;
import VO.ValorIdVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class CausacionDao extends DAO {

    public static final short TIPO_CAUSACION_NOMINA = 1;
    public static final short TIPO_CAUSACION_PS = 2;

    protected Tributaria getTributaria(int yearToCausar) {
        Tributaria tributaria = null;
        try {
            Criteria criteria = getSession().createCriteria(Tributaria.class);
            criteria.add(Restrictions.eq("ano", yearToCausar));
            tributaria = new Tributaria();
            tributaria = (Tributaria) criteria.uniqueResult();
        } catch (Exception e) {
            System.out.println();
        } finally {
            return tributaria;
        }
    }

    protected double getSalarioMinimoByYear(int yearToCausar) {
        double smlv = 0;
        try {
            Criteria criteria = getSession().createCriteria(Tributaria.class);
            criteria.add(Restrictions.eq("ano", yearToCausar));
            Tributaria tributaria = (Tributaria) criteria.uniqueResult();
            smlv = tributaria.getSmlv();
        } catch (Exception e) {
            System.out.println();
        } finally {
            return smlv;
        }
    }

    protected double getAuxTransporteByYear(int yearToCausar) {
        double auxTransporte = 0;
        try {
            Criteria criteria = getSession().createCriteria(Tributaria.class);
            criteria.add(Restrictions.eq("ano", yearToCausar));
            Tributaria tributaria = (Tributaria) criteria.uniqueResult();
            auxTransporte = tributaria.getAuxilioTrasporte();
        } catch (Exception e) {
            System.out.println();
        } finally {
            return auxTransporte;
        }
    }

    protected String[] getPorcentajeSaludPensaionEmploy(int yearToCausar) {
        String[] porecentajes = new String[2];
        try {
            //valor por Salud
            Criteria criteria = getSession().createCriteria(ValoresAsocaja.class);
            criteria.add(Restrictions.eq("ano", yearToCausar));
            criteria.add(Restrictions.eq("tipoAsocaja", new TipoAsocaja((short) 1)));
            ValoresAsocaja valoresAsocaja = (ValoresAsocaja) criteria.uniqueResult();
            porecentajes[0] = valoresAsocaja.getPorcentajeEmpleado();

            //valor por Penscion
            criteria = getSession().createCriteria(ValoresAsocaja.class);
            criteria.add(Restrictions.eq("ano", yearToCausar));
            criteria.add(Restrictions.eq("tipoAsocaja", new TipoAsocaja((short) 2)));
            ValoresAsocaja valoresAsocaja1 = (ValoresAsocaja) criteria.uniqueResult();
            porecentajes[1] = valoresAsocaja1.getPorcentajeEmpleado();

        } catch (Exception e) {
            System.out.println();
        } finally {
            return porecentajes;
        }
    }

    public List<RetencionPagos> consultarRetencionPagos(int idValorFormaPago) {
        List<RetencionPagos> retencionPagoses = new ArrayList<RetencionPagos>();
        try {
            begin();
            List<VfpRetPagos> vfpRetPagoses = new ArrayList<VfpRetPagos>();
            Criteria criteria = getSession().createCriteria(VfpRetPagos.class);
            criteria.add(Restrictions.eq("valorFormaPago", new ValorFormaPago(idValorFormaPago)));
            vfpRetPagoses = criteria.list();

            for (VfpRetPagos vfpRetPagos : vfpRetPagoses) {
                RetencionPagos retencionPagos = new RetencionPagos();
                retencionPagos = vfpRetPagos.getRetencionPagos();
                retencionPagos.setVfpRetPagoses(null);
                retencionPagoses.add(retencionPagos);
            }

        } catch (Exception e) {
            retencionPagoses = null;
        } finally {
            close();
            return retencionPagoses;
        }

    }

    public List<Bonificacion> listarBonificaciones() {
        List<Bonificacion> bonificacions = new ArrayList<Bonificacion>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Bonificacion.class);
            bonificacions = criteria.list();
        } catch (Exception e) {
            bonificacions = null;
        } finally {
            close();
            return bonificacions;
        }
    }

    public List<Provisiones> listarProvisiones() {
        List<Provisiones> provisioneses = new ArrayList<Provisiones>();

        try {
            begin();
            Criteria criteria = getSession().createCriteria(Provisiones.class);
            provisioneses = criteria.list();
        } catch (Exception e) {
            provisioneses = null;

        } finally {
            close();
            return provisioneses;
        }
    }

    public List<Deducciones> listarDeducciones() {
        List<Deducciones> deduccioneses = new ArrayList<Deducciones>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Deducciones.class);
            deduccioneses = criteria.list();
        } catch (Exception e) {
            deduccioneses = null;
        } finally {
            close();
            return deduccioneses;
        }
    }

    /*protected RespuestaVO guardarCausacionBonificacion(int idCausacion, int idBonificacion, short tipoCausacion) {
    RespuestaVO respuestaVO = new RespuestaVO((short) - 1, "");
    try {
    Bonificacion bonificacion = new Bonificacion(idBonificacion);
    switch (tipoCausacion) {
    case TIPO_CAUSACION_NOMINA:
    CausacionNomina causacionNomina = new CausacionNomina(idCausacion);
    getSession().save(new CausacionBonificacion(causacionNomina, bonificacion));
    respuestaVO = new RespuestaVO((short) 1, "");
    break;
    case TIPO_CAUSACION_PS:

    getSession().save(new CausPsBonificacion(idBonificacion, idCausacion));
    respuestaVO = new RespuestaVO((short) 1, "");
    break;
    }
    } catch (Exception e) {
    respuestaVO = new RespuestaVO((short) - 1, Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
    } finally {
    return respuestaVO;
    }
    }*/
    public RespuestaVO guardarAllDeducciones(int idCausacion, int[] idsDeducciones, short tipoCausacion) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            RespuestaVO respuestaVODeduc;
            if (idsDeducciones != null) {
                for (int idDeduccion : idsDeducciones) {
                    respuestaVODeduc = guardarCausacionDeduccion(idCausacion, idDeduccion, tipoCausacion);
                    if (respuestaVODeduc.getIdRespuesta() == -1) {
                        respuestaVO = new RespuestaVO((short) -1, respuestaVODeduc.getMensajeRespuesta());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO guardarCausacionDeduccion(int idCausacion, int idDeduccion, short tipoCausacion) {
        RespuestaVO respuestaVO = new RespuestaVO((short) - 1, "");
        try {
            Deducciones deducciones = new Deducciones(idDeduccion);
            switch (tipoCausacion) {
                case TIPO_CAUSACION_NOMINA:
                    CausacionNomina causacionNomina = new CausacionNomina(idCausacion);
                    getSession().save(new CausacionDeduccionNomina(idCausacion, idDeduccion));
                    respuestaVO = new RespuestaVO((short) 1, "");
                    break;
                case TIPO_CAUSACION_PS:
                    getSession().save(new CausaPsDeduccion(idCausacion, idDeduccion));
                    respuestaVO = new RespuestaVO((short) 1, "");
                    break;
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) - 1, Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    public RespuestaVO guardarAllProvisiones(int idCausacion, ValorIdVO[] provisiones, short tipoCausacion) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            RespuestaVO respuestaVOProvisiones;
            if (provisiones != null) {
                for (ValorIdVO valorIdVO : provisiones) {
                    respuestaVOProvisiones = guardarCausacionProvision(idCausacion, valorIdVO.getId(), valorIdVO.getValor(), tipoCausacion);
                    if (respuestaVOProvisiones.getIdRespuesta() == -1) {
                        respuestaVO = new RespuestaVO((short) -1, respuestaVOProvisiones.getMensajeRespuesta());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO guardarCausacionProvision(int idCausacion, int idProvision, double valor, short tipoCausacion) {
        RespuestaVO respuestaVO = new RespuestaVO((short) - 1, "");
        try {
            Provisiones provision = new Provisiones(idProvision);
            switch (tipoCausacion) {
                case TIPO_CAUSACION_NOMINA:
                    CausacionNomina causacionNomina = new CausacionNomina(idCausacion);
                    getSession().save(new CausaNominaProvision(causacionNomina, provision, valor));
                    respuestaVO = new RespuestaVO((short) 1, "");
                    break;
                case TIPO_CAUSACION_PS:
                    //   getSession().save(new CausaProvisionPs(idCausacion, idProvision, valor));
                    //   respuestaVO = new RespuestaVO((short) 1, "");
                    break;
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) - 1, Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    public RespuestaVO guardarAllOtrosMas(int idCausacion, OtroMasNomina[] otrosMases) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            RespuestaVO respuestaVO1;
            if (otrosMases != null) {
                for (OtroMasNomina otrosMas : otrosMases) {
                }
                for (OtroMasNomina otrosMas : otrosMases) {
                    respuestaVO1 = guardarOtrosMas(idCausacion, otrosMas.getNombre(), otrosMas.getObservacion(), otrosMas.getValor(), otrosMas.getAfectaIbc());
                    if (respuestaVO1.getIdRespuesta() == -1) {
                        respuestaVO = new RespuestaVO((short) -1, respuestaVO1.getMensajeRespuesta());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO guardarOtrosMas(int idCausacion, String nombre, String observacion, double valor, short afectaIbc) {
        RespuestaVO respuestaVO = new RespuestaVO((short) - 1, "");
        try {
            OtroMasNomina otrosMas = new OtroMasNomina(nombre, observacion, valor, afectaIbc, idCausacion);
            getSession().save(otrosMas);
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) - 1, Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    /*public RespuestaVO guardarCausacionEmpresa(int idCausacionNomina, int idValAsocaja, float valor, short tipoCausacion) {
    RespuestaVO respuestaVO = new RespuestaVO((short) - 1, "");
    try {
    ValoresAsocaja valorAsocaja = new ValoresAsocaja(idValAsocaja);
    switch (tipoCausacion) {
    case TIPO_CAUSACION_NOMINA:
    CausacionNomina causacionNomina = new CausacionNomina(idCausacion);
    getSession().save(new CausacionEmpresa(causacionNomina, valorAsocaja, valor));
    respuestaVO = new RespuestaVO((short) 1, "");
    break;
    case TIPO_CAUSACION_PS:

    break;
    }
    } catch (Exception e) {
    respuestaVO = new RespuestaVO((short) - 1, Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
    } finally {
    return respuestaVO;
    }
    }*/
    public RespuestaVO guardarFechaCorte(Date fechaCorte, short tipoCausacion, int mesCausado, int yearCausado) {
        RespuestaVO respuestaVO = new RespuestaVO((short) - 1, "");
        try {
            switch (tipoCausacion) {
                case TIPO_CAUSACION_NOMINA:
                    Serializable serializable = getSession().save(new FechasCorteCausacion(fechaCorte, (short) 1, mesCausado, yearCausado));
                    int idFechaCorte = Integer.parseInt(serializable.toString());
                    respuestaVO = new RespuestaVO((short) idFechaCorte, "");
                    break;
                case TIPO_CAUSACION_PS:
                    Serializable serializable1 = getSession().save(new FechasCorteCausacion(fechaCorte, (short) 0, mesCausado, yearCausado));
                    int idFechaCorte1 = Integer.parseInt(serializable1.toString());
                    respuestaVO = new RespuestaVO((short) idFechaCorte1, "");
                    break;
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) - 1, Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    public RespuestaVO guardarRetenciones(int idCausacion, int idRetencion, float valor, short tipoCausacion) {
        RespuestaVO respuestaVO = new RespuestaVO((short) - 1, "");
        try {
            //RetencionPagos retencionPagos = new RetencionPagos(idRetencion);
            switch (tipoCausacion) {
                case TIPO_CAUSACION_NOMINA:
                    //CausacionNomina causacionNomina = new CausacionNomina(idCausacion);
                    //getSession().save(new CausaNominaProvision(causacionNomina, provision, valor));
                    //respuestaVO = new RespuestaVO((short) 1, "");
                    break;
                case TIPO_CAUSACION_PS:
                    getSession().save(new RegtroValRtencion(idCausacion, valor, idRetencion));
                    respuestaVO = new RespuestaVO((short) 1, "");
                    break;
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) - 1, Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    public RespuestaVO consultarExistenciaCausacion(int mesCausacion, int yearCausado, short tipoCausacion) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 0, "");
        try {
            begin();
            Criteria criteria = getSession().createCriteria(FechasCorteCausacion.class);
            criteria.add(Restrictions.eq("mesCausado", mesCausacion));
            criteria.add(Restrictions.eq("yearCausado", yearCausado));
            criteria.add(Restrictions.eq("tipoCausacion", tipoCausacion));

            List<FechasCorteCausacion> fechasCorteCausacions = new ArrayList<FechasCorteCausacion>();
            fechasCorteCausacions = criteria.list();
            int[] idsFechasCorteCausacion = new int[fechasCorteCausacions.size()];
            if (fechasCorteCausacions.size() > 0) {
                for (int i = 0; i < idsFechasCorteCausacion.length; i++) {
                    idsFechasCorteCausacion[i] = fechasCorteCausacions.get(i).getId();
                }
                respuestaVO = new RespuestaVO((short) Mensajes.DATOS_YA_EXISTEN, Mensajes.FECHA_A_CAUSAR_YA_CAUSADA, idsFechasCorteCausacion);
            } else {
                respuestaVO = new RespuestaVO((short) Mensajes.DATOS_NO_EXISTEN, "", idsFechasCorteCausacion);
            }

        } catch (Exception e) {
            System.out.println();
            respuestaVO = new RespuestaVO((short) Mensajes.HA_OCURRIDO_EXEP, Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private int[] getIdsBonificacionNomina(CausacionNomina causacionNomina) {
        int[] retorno = null;
        try {
            int idCausacionNomina = causacionNomina.getId();
            Criteria criteria = getSession().createCriteria(CausacionBonificacion.class);
            criteria.add(Restrictions.eq("causacionNomina", new CausacionNomina(idCausacionNomina)));
            List<CausacionBonificacion> causacionBonificacions = new ArrayList<CausacionBonificacion>();
            causacionBonificacions = criteria.list();
            retorno = new int[causacionBonificacions.size()];
            for (int i = 0; i < retorno.length; i++) {
                retorno[i] = causacionBonificacions.get(i).getBonificacion().getId();
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return retorno;
        }
    }

    private ValorIdVO[] getDeducciones(CausacionNomina causacionNomina) {
        ValorIdVO[] valorIdVOs = null;
        try {
            Criteria criteria = getSession().createCriteria(CausacionDeduccionNomina.class);
            criteria.add(Restrictions.eq("idCausacion", causacionNomina.getId()));
            List<CausacionDeduccionNomina> causacionDeduccionNominas = new ArrayList<CausacionDeduccionNomina>();
            causacionDeduccionNominas = criteria.list();
            valorIdVOs = new ValorIdVO[causacionDeduccionNominas.size()];
            int cont = 0;
            for (CausacionDeduccionNomina causacionDeduccionNomina : causacionDeduccionNominas) {
                criteria = getSession().createCriteria(Deducciones.class);
                criteria.add(Restrictions.eq("id", causacionDeduccionNomina.getIdDeduccion()));
                Deducciones deducciones = (Deducciones) criteria.uniqueResult();
                ValorIdVO valorIdVO = new ValorIdVO(deducciones.getId(), Float.parseFloat(deducciones.getValor() + ""));
                valorIdVOs[cont] = valorIdVO;
                cont++;
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return valorIdVOs;
        }
    }

    private ValorIdVO[] getProvisiones(CausacionNomina causacionNomina) {
        ValorIdVO[] valorIdVOs = null;
        try {
            Criteria criteria = getSession().createCriteria(CausaNominaProvision.class);
            criteria.add(Restrictions.eq("causacionNomina", causacionNomina));
            List<CausaNominaProvision> causaNominaProvisions = new ArrayList<CausaNominaProvision>();
            causaNominaProvisions = criteria.list();
            valorIdVOs = new ValorIdVO[causaNominaProvisions.size()];
            int cont = 0;
            for (CausaNominaProvision causaNominaProvision : causaNominaProvisions) {
                ValorIdVO valorIdVO = new ValorIdVO(causaNominaProvision.getProvisiones().getId(), Float.parseFloat(causaNominaProvision.getValor() + ""));
                valorIdVOs[cont] = valorIdVO;
                cont++;
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            return valorIdVOs;
        }
    }

    public List<FechasCorteCausacion> listarFechasCausadas(int tipoCausacion) {
        List<FechasCorteCausacion> fechasCorteCausacions = new ArrayList<FechasCorteCausacion>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(FechasCorteCausacion.class);

            switch (tipoCausacion) {
                case 0://PRESTACION DE SERVICIOS
                    criteria.add(Restrictions.eq("tipoCausacion", (short) 0));
                    break;
                case 1:// PARA NOMINA
                    criteria.add(Restrictions.eq("tipoCausacion", (short) 1));
                    break;
            }

            fechasCorteCausacions = criteria.list();

        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return fechasCorteCausacions;
        }
    }

    public double calcularTotalDeducciones(int[] idsCausaciones) {
        double totalDeducciones = 0;
        try {
            if (idsCausaciones != null) {
                List<Deducciones> deduccioneses = new ArrayList<Deducciones>();
                deduccioneses = getListaDeducciones(idsCausaciones);
                for (Deducciones deducciones : deduccioneses) {
                    totalDeducciones = totalDeducciones + deducciones.getValor();
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return totalDeducciones;
        }
    }

    public double calcularTotalDeduccionesAfectanIbc(int[] idsCausaciones) {
        double totalDeduccionesAfectanIbc = 0;
        try {
            if (idsCausaciones != null) {
                List<Deducciones> deduccioneses = new ArrayList<Deducciones>();
                deduccioneses = getListaDeducciones(idsCausaciones);
                for (Deducciones deducciones : deduccioneses) {
                    if (deducciones.getAfectaIbc() == (short) 1) {
                        totalDeduccionesAfectanIbc = totalDeduccionesAfectanIbc + deducciones.getValor();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return totalDeduccionesAfectanIbc;
        }
    }

    private List<Deducciones> getListaDeducciones(int[] idsCausaciones) {
        List<Deducciones> deduccioneses = new ArrayList<Deducciones>();
        try {
            Criteria criteria;
            for (int i : idsCausaciones) {
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

    public double calcularTotalOtrosMas(OtroMasNomina[] otrosMases) {
        double totalOtrasMas = 0;
        try {
            if (otrosMases != null) {
                for (OtroMasNomina otrosMas : otrosMases) {
                    totalOtrasMas = totalOtrasMas + otrosMas.getValor();
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return totalOtrasMas;
        }
    }

    public double calcularTotalOtrosMasAfectaIbc(OtroMasNomina[] otrosMases) {
        double totalOtrasMasAfectaIbc = 0;
        try {
            if (otrosMases != null) {
                for (OtroMasNomina otrosMas : otrosMases) {
                    if (otrosMas.getAfectaIbc() == (short) 1) {
                        totalOtrasMasAfectaIbc = totalOtrasMasAfectaIbc + otrosMas.getValor();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return totalOtrasMasAfectaIbc;
        }
    }
}
