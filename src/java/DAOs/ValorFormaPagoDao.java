/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Contrato;
import Entidades.FechasPago;
import Entidades.RetencionPagos;
import Entidades.TipoPago;
import Entidades.ValorFormaPago;
import Entidades.VfpRetPagos;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.FechaPagoVO;
import VO.RespuestaVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class ValorFormaPagoDao extends DAO {

    public static boolean TIENE_IBC;
    public static int CONTADOR_IBCS;

    public List<TipoPago> listarTipoPago() {
        List<TipoPago> tipoPagos = new ArrayList<TipoPago>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(TipoPago.class);
            tipoPagos = criteria.list();

            List<TipoPago> tipoPagos1 = new ArrayList<TipoPago>();
            for (TipoPago tipoPago : tipoPagos) {
                TipoPago tipoPago1 = new TipoPago(tipoPago.getId(), tipoPago.getNombre());
                tipoPago1.setValorFormaPagos(null);
                tipoPagos1.add(tipoPago1);
            }
            tipoPagos = tipoPagos1;
        } catch (Exception e) {
            tipoPagos = null;
        } finally {
            close();
            return tipoPagos;
        }
    }

    public List<RetencionPagos> listarRetencionesPagos() {
        List<RetencionPagos> retencionPagoses = new ArrayList<RetencionPagos>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(RetencionPagos.class);
            retencionPagoses = criteria.list();

            List<RetencionPagos> retencionPagoses1 = new ArrayList<RetencionPagos>();
            for (RetencionPagos retencionPagos : retencionPagoses) {
                RetencionPagos retencionPagos1 = new RetencionPagos(retencionPagos.getId(), retencionPagos.getNombre(), retencionPagos.getPorcentaje(), retencionPagos.getFechaExpiracion(), retencionPagos.getVigente());
                retencionPagoses1.add(retencionPagos1);
            }
            retencionPagoses = retencionPagoses1;
        } catch (Exception e) {
            retencionPagoses = null;
        } finally {
            close();
            return retencionPagoses;
        }
    }

    public List<ValorFormaPago> ObjectsToValoresFormaPago(Object[] valores_forma_pago) {
        //Utilidades utilidades = new Utilidades();
        List<ValorFormaPago> valorFormaPagos = new ArrayList<ValorFormaPago>();
        for (Object object : valores_forma_pago) {
            List<RetencionPagos> retencionPagoses = new ArrayList<RetencionPagos>();
            Set fechasPagos = new HashSet();

            Object[] objsFormaPago = (Object[]) object;
            ArrayList objsFechasPago = (ArrayList) objsFormaPago[7];
            //Object[] objsFechasPago = (Object[]) objsFormaPago[7];

            for (Object obj : objsFechasPago) {

                Object[] objFechaPago = (Object[]) obj;
                FechasPago fechasPago = new FechasPago((Date) objFechaPago[0], (Date) objFechaPago[1], (Date) objFechaPago[2]);
                fechasPagos.add(fechasPago);
            }

            Object[] idsRetencionesPago = (Object[]) objsFormaPago[8];

            for (Object idFormaPago : idsRetencionesPago) {
                int idFp = Integer.parseInt(idFormaPago.toString());
                RetencionPagos retencionPagos = new RetencionPagos(idFp);
                retencionPagoses.add(retencionPagos);
            }

            TipoPago tipoPago = new TipoPago(Short.parseShort(objsFormaPago[0].toString()), "");
            String concepto = (String) objsFormaPago[1];
            String observacion = (String) objsFormaPago[2];
            short periodoPago = Short.parseShort(objsFormaPago[3].toString());
            Date fechaIniPago = (Date) objsFormaPago[4];
            short esFavorito = Short.parseShort(objsFormaPago[5].toString());
            double valor_pago = Double.parseDouble(objsFormaPago[6].toString());
            int esIbc = Integer.parseInt(objsFormaPago[9].toString());

            ValorFormaPago valorFormaPago = new ValorFormaPago(tipoPago, concepto, observacion, periodoPago, fechaIniPago, esFavorito, valor_pago, fechasPagos, retencionPagoses);
            valorFormaPago.setEsIbc((short) esIbc);
            valorFormaPagos.add(valorFormaPago);
        }
        return valorFormaPagos;
    }
    Utilidades utilidades = new Utilidades();

    public RespuestaVO guardarValoresFormaPago(List<ValorFormaPago> valorFormaPagos, int idContrato, short idTipoContrato) {
        RespuestaVO respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_VAL_FORMA_PAGO_MSG);
        TIENE_IBC = true;
        CONTADOR_IBCS = 1;
        try {
            for (ValorFormaPago valorFormaPago : valorFormaPagos) {
                //Ojo aqui en esta version hay que cambiar el Set<date> por Set
                Set fechasPago = new HashSet();
                fechasPago = valorFormaPago.getFechasPagos();
                valorFormaPago.setContrato(new Contrato(idContrato));

                if (utilidades.buscarPropiedad(TipoPago.class, "id", valorFormaPago.getTipoPago().getId(), Utilidades.SHORT)) {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_VAL_FORMA_PAGO_MSG + Mensajes.SALTO_LINEA + Mensajes.TIPO_PAGO_NO_EXISTE_MSG);
                    break;
                }
                /*if (valorFormaPago.getEsIbc() == 1) {
                TIENE_IBC = true;
                CONTADOR_IBCS++;
                }*/
                Serializable serializable = getSession().save(valorFormaPago);
                int idValFormaPago = Integer.parseInt(serializable.toString());
                RespuestaVO respuestaVOFechasPago = this.saveFechasPago(fechasPago, idValFormaPago, idTipoContrato);
                if (respuestaVOFechasPago.getIdRespuesta() == -1) {
                    respuestaVO = new RespuestaVO((short) -1, respuestaVOFechasPago.getMensajeRespuesta());
                    break;
                }

                RespuestaVO respuestaSaveRetenPagos = this.saveRetencionesPago(valorFormaPago.getRetencionPagoses(), idValFormaPago);
                if (respuestaSaveRetenPagos.getIdRespuesta() == -1) {
                    respuestaVO = new RespuestaVO((short) -1, respuestaSaveRetenPagos.getMensajeRespuesta());
                    break;
                }

                respuestaVO = new RespuestaVO((short) 1, "");
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_VAL_FORMA_PAGO_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }

    }

    public RespuestaVO saveFechasPago(Set fechasPago, int idValFormaPago, short idTipoContrato) {
        RespuestaVO respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_FECHAS_PAGO_MSG);
        try {
            for (Object object : fechasPago) {
                FechasPago fechasPago1 = new FechasPago();
                fechasPago1 = (FechasPago) object;
                fechasPago1.setValorFormaPago(new ValorFormaPago(idValFormaPago));
                fechasPago1.setIdTipoContrato(idTipoContrato);
                fechasPago1.setActiva((short)1);
                //FechasPago fechasPago1 = new FechasPago(new ValorFormaPago(idValFormaPago), object);
                getSession().save(fechasPago1);
            }
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_INGRESO_FECHAS_PAGO_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO saveRetencionesPago(List<RetencionPagos> retencionPagoses, int idValFormaPago) {
        RespuestaVO respuestaVO = new RespuestaVO((short) -1, "");
        try {
            if (retencionPagoses.size() == 0) {
                respuestaVO = new RespuestaVO((short) 1, "");
            }
            for (RetencionPagos retencionPagos : retencionPagoses) {
                if (utilidades.buscarPropiedad(RetencionPagos.class, "id", retencionPagos.getId(), Utilidades.INT)) {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.RETENCION_PAGO_NO_EXISTE_MSG + Mensajes.IDENTIFICADOR + retencionPagos.getId());
                    break;
                } else {
                    VfpRetPagos vfpRetPagos = new VfpRetPagos();
                    vfpRetPagos.setRetencionPagos(retencionPagos);
                    vfpRetPagos.setValorFormaPago(new ValorFormaPago(idValFormaPago));
                    getSession().save(vfpRetPagos);
                    respuestaVO = new RespuestaVO((short) 1, "");
                }

            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    public List<ValorFormaPago> listarValoresFormaPago(int idContrato) {
        List<ValorFormaPago> valorFormaPagos = new ArrayList<ValorFormaPago>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(ValorFormaPago.class);
            criteria.add(Restrictions.eq("contrato", new Contrato(idContrato)));
            valorFormaPagos = criteria.list();

            List<ValorFormaPago> valorFormaPagos1 = new ArrayList<ValorFormaPago>();

            for (ValorFormaPago valorFormaPago : valorFormaPagos) {
                TipoPago tipoPago = new TipoPago(valorFormaPago.getTipoPago().getId(), valorFormaPago.getTipoPago().getNombre());
                Contrato contrato = new Contrato(idContrato);
                ValorFormaPago valorFormaPago1 = new ValorFormaPago(valorFormaPago.getId(), tipoPago, valorFormaPago.getConcepto(), valorFormaPago.getObservacion(), valorFormaPago.getPeriodoPago(), valorFormaPago.getFechaInicioPago(), valorFormaPago.getEsFavorito(), valorFormaPago.getValorPago(), contrato);
                valorFormaPagos1.add(valorFormaPago1);

            }
            valorFormaPagos = valorFormaPagos1;
        } catch (Exception e) {
            valorFormaPagos = null;
        } finally {
            close();
            return valorFormaPagos;
        }
    }

    public   List<FechaPagoVO> listarFechasPagoByValorFormaPago(int idValorFormaPago) {
        List<FechaPagoVO> fechaPagoVOs = new ArrayList<FechaPagoVO>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("valorFormaPago",new ValorFormaPago(idValorFormaPago)));
            List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
            fechasPagos = criteria.list();
            for (FechasPago fechasPago : fechasPagos) {
                FechaPagoVO fechaPagoVO = new FechaPagoVO(fechasPago.getFecha(), fechasPago.getIniPeriodoPago(),fechasPago.getFinPeriodoPago());
                fechaPagoVOs.add(fechaPagoVO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return fechaPagoVOs;
        }
    }

    
}
