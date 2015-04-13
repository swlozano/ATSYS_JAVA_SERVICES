/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Banco;
import Entidades.CuentaBancaria;
import Entidades.CuentasPorPagar;
import Entidades.EstadoCuentaXPagar;
import Entidades.Persona;
import Entidades.RecursoHumano;
import Entidades.TipoCuentaBancaria;
import Entidades.TipoGenCuentaPago;
import Utilidades.DAO;
import Utilidades.Mensajes;
import VO.CuentaPorPagarVO;
import VO.RespuestaVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class CuentaPorPagarDao extends DAO {

    public RespuestaVO crear(Date fechaOrigen, Integer idRecursoHumano,
            Integer idPersona, String concepto, Integer idCuentaBancaria,
            Integer idBanco, Integer idTipoCuentaBanco, String numeroCuenta,
            Double valor, Short idEstadoCuentaXPagar, Short idGeneradorCuentaPago,
            Integer idCampo, Date fechaPago) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            CuentasPorPagar cuentasPorPagar = new CuentasPorPagar(fechaOrigen, concepto, valor, idEstadoCuentaXPagar, idGeneradorCuentaPago);
            cuentasPorPagar.setFechaPago(fechaPago);
            
            if (idRecursoHumano != -1) {
                cuentasPorPagar.setIdRecursoHumano(idRecursoHumano);
            }
            
            if (idPersona != -1 && idRecursoHumano == -1) {

                cuentasPorPagar.setIdPersona(idPersona);
            }
            //borrar
           // idCuentaBancaria = -1;
                    //
            if (idCuentaBancaria != -1) {
                cuentasPorPagar.setIdCuentaBancaria(idCuentaBancaria);
            }
            
            if (idBanco != -1 && idCuentaBancaria == -1) {
                cuentasPorPagar.setIdBanco(idBanco);
            }
            if (idTipoCuentaBanco != -1 && idBanco != -1 && idCuentaBancaria == -1) {
                cuentasPorPagar.setIdTipoCuentaBanco(idTipoCuentaBanco);
                cuentasPorPagar.setNumeroCuenta(numeroCuenta);
            }
            if (idCampo != -1) {
                cuentasPorPagar.setIdCampo(idCampo);
            }
            getSession().save(cuentasPorPagar);
            commit();
            respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO eliminar(int idCuentaPorPagar) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CuentasPorPagar.class);
            criteria.add(Restrictions.eq("id", idCuentaPorPagar));
            CuentasPorPagar cuentasPorPagar = (CuentasPorPagar) criteria.uniqueResult();
            //CuentasPorPagar cuentasPorPagar = new CuentasPorPagar(idCuentaPorPagar);
            getSession().delete(cuentasPorPagar);
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public List<CuentaPorPagarVO> listar(int idRecursoHumano, int idPersona, short idEstadoCuentaXpagar, short idGenCuentaPorPagar) {
        List<CuentasPorPagar> cuentasPorPagars = new ArrayList<CuentasPorPagar>();
        List<CuentaPorPagarVO> cuentaPorPagarVOs = new ArrayList<CuentaPorPagarVO>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CuentasPorPagar.class);

            if (idRecursoHumano != -1 || idPersona != -1) {
                if (idRecursoHumano != -1) {
                    criteria.add(Restrictions.eq("idRecursoHumano", idRecursoHumano));
                } else {
                    if (idPersona != -1) {
                        criteria.add(Restrictions.eq("idPersona", idPersona));
                    }
                }
            }

            if (idEstadoCuentaXpagar != -1) {
                criteria.add(Restrictions.eq("idEstadoCuentaXPagar", idEstadoCuentaXpagar));
            }

            if (idGenCuentaPorPagar != -1) {
                criteria.add(Restrictions.eq("idGeneradorCuentaPago", idGenCuentaPorPagar));
            }
            cuentasPorPagars = criteria.list();
            cuentaPorPagarVOs = cuentasPorPagarsToCuentaPorPagarVOs(cuentasPorPagars);
        } catch (Exception e) {
            cuentasPorPagars = null;
            cuentaPorPagarVOs = null;
        } finally {
            close();
            return cuentaPorPagarVOs;
        }
    }

    private List<CuentaPorPagarVO> cuentasPorPagarsToCuentaPorPagarVOs(List<CuentasPorPagar> cuentasPorPagars) {
        List<CuentaPorPagarVO> cuentaPorPagarVOs = new ArrayList<CuentaPorPagarVO>();
        try {
            Criteria criteria;
            for (CuentasPorPagar cuentaPorPagar : cuentasPorPagars) {
                CuentaPorPagarVO cuentaPorPagarVO = new CuentaPorPagarVO();
                cuentaPorPagarVO.setFechaOrigen(cuentaPorPagar.getFechaOrigen());
                cuentaPorPagarVO.setIdCuentaPorPagar(cuentaPorPagar.getId());
                //int idRecursoHumano = cuentaPorPagar.getIdRecursoHumano();
                //PARA OBTENER EL NOMBRE DEL RECURSO HUMANO
                if (cuentaPorPagar.getIdRecursoHumano() != null) {

                    criteria = getSession().createCriteria(RecursoHumano.class);
                    criteria.add(Restrictions.eq("id", cuentaPorPagar.getIdRecursoHumano()));
                    RecursoHumano recursoHumano = (RecursoHumano) criteria.uniqueResult();
                    cuentaPorPagarVO.setNombre(recursoHumano.getNombre() + " " + recursoHumano.getApellido());
                    cuentaPorPagarVO.setIdRhPersona(cuentaPorPagar.getIdRecursoHumano());
                }

                if (cuentaPorPagar.getIdPersona() != null && cuentaPorPagar.getIdRecursoHumano() == null) {
                    criteria = getSession().createCriteria(Persona.class);
                    criteria.add(Restrictions.eq("id", cuentaPorPagar.getIdPersona()));
                    Persona persona = (Persona) criteria.uniqueResult();
                    cuentaPorPagarVO.setNombre(persona.getNombreRs());
                    cuentaPorPagarVO.setIdRhPersona(cuentaPorPagar.getIdPersona());
                }

                cuentaPorPagarVO.setConcepto(cuentaPorPagar.getConcepto());

                if (cuentaPorPagar.getIdCuentaBancaria() != null) {
                    criteria = getSession().createCriteria(CuentaBancaria.class);
                    criteria.add(Restrictions.eq("id", cuentaPorPagar.getIdCuentaBancaria()));
                    CuentaBancaria cuentaBancaria = (CuentaBancaria) criteria.uniqueResult();
                    cuentaPorPagarVO.setIdCuentaBanco(cuentaBancaria.getId());

                    cuentaPorPagarVO.setBanco(cuentaBancaria.getBanco().getNombre());
                    cuentaPorPagarVO.setIdBanco(cuentaBancaria.getBanco().getId());

                    cuentaPorPagarVO.setTipoCuenta(cuentaBancaria.getTipoCuentaBancaria().getNombre());
                    cuentaPorPagarVO.setIdTipoCuenta(cuentaBancaria.getTipoCuentaBancaria().getId());

                    cuentaPorPagarVO.setNumeroCuenta(cuentaBancaria.getNumero());

                } else {
                    criteria = getSession().createCriteria(Banco.class);
                    criteria.add(Restrictions.eq("id", cuentaPorPagar.getIdBanco()));
                    Banco banco = (Banco) criteria.uniqueResult();
                    cuentaPorPagarVO.setBanco(banco.getNombre());
                    cuentaPorPagarVO.setIdBanco(cuentaPorPagar.getIdBanco());

                    criteria = getSession().createCriteria(TipoCuentaBancaria.class);
                    criteria.add(Restrictions.eq("id", Short.parseShort(cuentaPorPagar.getIdTipoCuentaBanco() + "")));
                    TipoCuentaBancaria tipoCuentaBancaria = (TipoCuentaBancaria) criteria.uniqueResult();
                    cuentaPorPagarVO.setTipoCuenta(tipoCuentaBancaria.getNombre());
                    cuentaPorPagarVO.setIdTipoCuenta(Short.parseShort(cuentaPorPagar.getIdTipoCuentaBanco() + ""));

                    cuentaPorPagarVO.setNumeroCuenta(cuentaPorPagar.getNumeroCuenta());
                }

                cuentaPorPagarVO.setValor(cuentaPorPagar.getValor());
                cuentaPorPagarVO.setFechaPago(cuentaPorPagar.getFechaPago());

                criteria = getSession().createCriteria(EstadoCuentaXPagar.class);
                criteria.add(Restrictions.eq("id", cuentaPorPagar.getIdEstadoCuentaXPagar()));
                EstadoCuentaXPagar estadoCuentaXPagar = (EstadoCuentaXPagar) criteria.uniqueResult();
                cuentaPorPagarVO.setEstadoCuenta(estadoCuentaXPagar.getNombre());
                cuentaPorPagarVO.setIdEstadoCuenta(cuentaPorPagar.getIdEstadoCuentaXPagar());

                criteria = getSession().createCriteria(TipoGenCuentaPago.class);
                criteria.add(Restrictions.eq("id", cuentaPorPagar.getIdGeneradorCuentaPago()));
                TipoGenCuentaPago tipoGenCuentaPago = (TipoGenCuentaPago) criteria.uniqueResult();
                cuentaPorPagarVO.setGeneradorDeCuenta(tipoGenCuentaPago.getNombre());
                cuentaPorPagarVO.setIdGeneradorDeCuenta(cuentaPorPagar.getIdGeneradorCuentaPago());

                cuentaPorPagarVOs.add(cuentaPorPagarVO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return cuentaPorPagarVOs;
        }
    }

    public RespuestaVO modificarEstadoCuenta(int idCuentaPorPagar, short idEstadoCuenta) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CuentasPorPagar.class);
            criteria.add(Restrictions.eq("id", idCuentaPorPagar));

            CuentasPorPagar cuentasPorPagar = (CuentasPorPagar) criteria.uniqueResult();
            cuentasPorPagar.setIdEstadoCuentaXPagar(idEstadoCuenta);

            getSession().update(cuentasPorPagar);
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_ESTADO_CUENTA_PAGAR_CORECTAMENTE);
        } catch (Exception e) {
            System.out.println();
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO modificar(int idCpc, Date fechaOrigen, Integer idRecursoHumano,
            Integer idPersona, String concepto, Integer idCuentaBancaria,
            Integer idBanco, Integer idTipoCuentaBanco, String numeroCuenta,
            Double valor, Short idEstadoCuentaXPagar, Short idGeneradorCuentaPago,
            Integer idCampo, Date fechaPago) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CuentasPorPagar.class);
            criteria.add(Restrictions.eq("id", idCpc));
            CuentasPorPagar cuentasPorPagar = (CuentasPorPagar) criteria.uniqueResult();

            cuentasPorPagar.setConcepto(concepto);
            cuentasPorPagar.setFechaOrigen(fechaOrigen);
            cuentasPorPagar.setFechaPago(fechaPago);
            if (idCuentaBancaria != -1) {
                cuentasPorPagar.setIdCuentaBancaria(idCuentaBancaria);
            } else {
                cuentasPorPagar.setIdBanco(idBanco);
                cuentasPorPagar.setIdTipoCuentaBanco(idTipoCuentaBanco);
                cuentasPorPagar.setNumeroCuenta(numeroCuenta);
            }
            if (idCampo != -1) {
                cuentasPorPagar.setIdCampo(idCampo);
            }
            cuentasPorPagar.setIdCampo(idCampo);
            cuentasPorPagar.setIdEstadoCuentaXPagar(idEstadoCuentaXPagar);
            cuentasPorPagar.setIdGeneradorCuentaPago(idGeneradorCuentaPago);

            if (idPersona != -1) {
                cuentasPorPagar.setIdPersona(idPersona);
            } else {
                cuentasPorPagar.setIdRecursoHumano(idRecursoHumano);
            }

            cuentasPorPagar.setValor(valor);

            getSession().update(cuentasPorPagar);
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);

        } catch (Exception e) {
            System.out.println();
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }

    }

    public List<EstadoCuentaXPagar> listarEstadosCpp() {
        List<EstadoCuentaXPagar> estadoCuentaXPagars = new ArrayList<EstadoCuentaXPagar>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(EstadoCuentaXPagar.class);
            estadoCuentaXPagars = criteria.list();
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return estadoCuentaXPagars;
        }
    }

    public List<TipoGenCuentaPago> listarGeneneradorCpp() {
        List<TipoGenCuentaPago> tipoGenCuentaPagos = new ArrayList<TipoGenCuentaPago>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(TipoGenCuentaPago.class);
            tipoGenCuentaPagos = criteria.list();
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return tipoGenCuentaPagos;
        }
    }
}
