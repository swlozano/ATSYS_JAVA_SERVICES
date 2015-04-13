/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Contrato;
import Entidades.CuentaBancaria;
import Entidades.CuentaCobroEstado;
import Entidades.CuentaDeCobro;
import Entidades.Estado;
import Entidades.FechasPago;
import Entidades.Permisos;
import Entidades.RecursoHumano;
import Entidades.Rol;
import Entidades.RolPermisos;
import Entidades.TipoPago;
import Entidades.UsuarioSistemaRol;
import Entidades.UsuarioSys;
import Entidades.ValorFormaPago;
import Utilidades.DAO;
import Utilidades.EtiquetasHtml;
import Utilidades.Mail;
import Utilidades.Mensajes;
import VO.CuentaCobroEstadoVO;
import VO.DatosCuentaCobroVO;
import VO.RecursoHumanoVO;
import VO.RespuestaVO;
import java.io.Serializable;
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
public class CuentasCobroDao extends DAO {

    public RecursoHumanoVO ingresoSistemaCC(String login, String contrasena) {
        RecursoHumano recursoHumano = new RecursoHumano();
        RecursoHumanoVO recursoHumanoVO = null;
        try {
            begin();
            Criteria criteria = getSession().createCriteria(RecursoHumano.class);
            criteria.add(Restrictions.eq("login", login));
            criteria.add(Restrictions.eq("contrasena", contrasena));
            recursoHumano = (RecursoHumano) criteria.uniqueResult();
            /*List<CuentaBancariaVO> cuentaBancariaVOs = new ArrayList<CuentaBancariaVO>();
            RecursoHumanoDao recursoHumanoDao = new RecursoHumanoDao();
            cuentaBancariaVOs = recursoHumanoDao.getCuentasBancariasRh(recursoHumano.getId());
            recursoHumano.setCuentaBancariaVOs(cuentaBancariaVOs);*/
            RecursoHumanoDao recursoHumanoDao = new RecursoHumanoDao();
            recursoHumanoVO = new RecursoHumanoVO();
            recursoHumanoVO = recursoHumanoDao.recursoHumanoToRecursoHumanoVO(recursoHumano);
        } catch (Exception e) {
            recursoHumano = null;
            recursoHumanoVO = null;
        } finally {
            close();
            return recursoHumanoVO;
        }
    }
    private final static short PAGADAS = 1;
    private final static short POR_PAGADAR = 2;
    private final static short PENDIENTES = 3;
    private final static short TODAS = 4;
    private final static short GENERADAS = 5;
    /***********************************/
    private final static short FIJO = 1;
    private final static short PORCENTAJE = 2;
    private final static short OTRO = 3;
    private final static short TODOS_TIPO_PAGO = 4;

    public List<FechasPago> listarCuentasCobroPorContrato(int idContrato, int idValorFormaPago, short tipoTolistar, short tipoPago, Calendar calIni, Calendar calFin) {
        ValorFormaPago valorFormaPago = new ValorFormaPago();
        List<FechasPago> fechasPagos = new ArrayList<FechasPago>();

        try {
            begin();
            /*******************LISTAR VALORES FORMA PAGO DEL CONTRATO*************************/
            Criteria criValFormaPag = getSession().createCriteria(ValorFormaPago.class);
            criValFormaPag.add(Restrictions.eq("id", idValorFormaPago));
            criValFormaPag.add(Restrictions.eq("contrato", new Contrato(idContrato)));

            switch (tipoPago) {
                case FIJO:
                    criValFormaPag.add(Restrictions.eq("tipoPago", new TipoPago((short) 1)));
                    break;
                case PORCENTAJE:
                    criValFormaPag.add(Restrictions.eq("tipoPago", new TipoPago((short) 2)));
                    break;
                case OTRO:
                    criValFormaPag.add(Restrictions.eq("tipoPago", new TipoPago((short) 3)));
                    break;
                default:
                    valorFormaPago = (ValorFormaPago) criValFormaPag.uniqueResult();
                    break;
            }
            valorFormaPago = (ValorFormaPago) criValFormaPag.uniqueResult();
            if (valorFormaPago != null) {
                switch (tipoTolistar) {
                    case PAGADAS:
                        fechasPagos = getFechasPagadas(valorFormaPago, calIni, calFin);
                        break;
                    case POR_PAGADAR:
                        fechasPagos = getFechasPorPagadar(valorFormaPago, calIni, calFin);
                        fechasPagos = compararFechasPagoGeneradas(fechasPagos, false);
                        break;
                    case PENDIENTES:
                        fechasPagos = getFechasPedientes(valorFormaPago, idContrato);
                        fechasPagos = compararFechasPagoGeneradas(fechasPagos, false);
                        break;
                    case GENERADAS:
                        fechasPagos = getCuentasCobroGeneradas(idContrato);
                        break;
                    default:
                        fechasPagos = getFechasTodasFechasPago(valorFormaPago, calIni, calFin);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            //close();
            return fechasPagos;
        }
    }

    private List<FechasPago> getFechasPagadas(ValorFormaPago valorFormaPago, Calendar calIni, Calendar calFin) {
        List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
        try {
            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("valorFormaPago", valorFormaPago));
            criteria.add(Restrictions.eq("idTipoContrato", (short) 1));
            criteria.add(Restrictions.eq("pagada", (short) 1));
            if (calIni != null && calFin != null) {
                criteria.add(Restrictions.between("fecha", calIni.getTime(), calFin.getTime()));
            }
            fechasPagos = criteria.list();
            if (fechasPagos != null) {
                fechasPagos = normalizarFechasPago(fechasPagos);
            }
        } catch (Exception e) {
            fechasPagos = null;
        } finally {
            return fechasPagos;
        }
    }

    private List<FechasPago> getFechasPorPagadar(ValorFormaPago valorFormaPago, Calendar calIni, Calendar calFin) {
        List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
        try {
            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("valorFormaPago", valorFormaPago));
            criteria.add(Restrictions.eq("idTipoContrato", (short) 1));
            criteria.add(Restrictions.eq("pagada", (short) 0));
            if (calIni != null && calFin != null) {
                criteria.add(Restrictions.between("fecha", calIni.getTime(), calFin.getTime()));
            }
            fechasPagos = criteria.list();
            if (fechasPagos != null) {
                fechasPagos = normalizarFechasPago(fechasPagos);
            }
        } catch (Exception e) {
            fechasPagos = null;
        } finally {
            return fechasPagos;
        }
    }

    private List<FechasPago> getFechasTodasFechasPago(ValorFormaPago valorFormaPago, Calendar calIni, Calendar calFin) {
        List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
        try {
            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("valorFormaPago", valorFormaPago));
            criteria.add(Restrictions.eq("idTipoContrato", (short) 1));
            if (calIni != null && calFin != null) {
                criteria.add(Restrictions.between("fecha", calIni.getTime(), calFin.getTime()));
            }
            fechasPagos = criteria.list();
            if (fechasPagos != null) {
                fechasPagos = normalizarFechasPago(fechasPagos);
            }
        } catch (Exception e) {
            fechasPagos = null;
        } finally {
            return fechasPagos;
        }
    }

    private List<FechasPago> getFechasPedientes(ValorFormaPago valorFormaPago, int idContrato) {
        List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
        Date fechaActual = new Date();
        Date fechaInicioContrato = new Date();

        Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        try {

            Contrato contrato = new Contrato();
            Criteria criteriaContrato = getSession().createCriteria(Contrato.class);
            criteriaContrato.add(Restrictions.eq("id", idContrato));
            contrato = (Contrato) criteriaContrato.uniqueResult();
            fechaInicioContrato = contrato.getFechaInicio();
            fechaInicio.setTime(fechaInicioContrato);
            fechaFin.setTime(fechaActual);

            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("valorFormaPago", valorFormaPago));
            criteria.add(Restrictions.eq("idTipoContrato", (short) 1));
            criteria.add(Restrictions.eq("pagada", (short) 0));
            criteria.add(Restrictions.between("fecha", fechaInicio.getTime(), fechaFin.getTime()));
            fechasPagos = criteria.list();
            if (fechasPagos != null) {
                fechasPagos = normalizarFechasPago(fechasPagos);
            }
        } catch (Exception e) {
            fechasPagos = null;
        } finally {
            return fechasPagos;
        }
    }

    private List<FechasPago> normalizarFechasPago(List<FechasPago> fechasPagos) {
        List<FechasPago> fechasPagos1 = new ArrayList<FechasPago>();
        for (FechasPago fechasPago : fechasPagos) {
            FechasPago fechasPago1 = new FechasPago(fechasPago.getId(), fechasPago.getFecha(), fechasPago.getIniPeriodoPago(), fechasPago.getFinPeriodoPago(), fechasPago.getPagada(), fechasPago.getIdTipoContrato());
            fechasPagos1.add(fechasPago1);
        }
        return fechasPagos1;
    }

    public CuentaDeCobro detalleCuentaCobro(int idFechaPago) {
        CuentaDeCobro cuentaDeCobro = new CuentaDeCobro();
        List<CuentaCobroEstado> cuentaCobroEstados = new ArrayList<CuentaCobroEstado>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CuentaDeCobro.class);
            criteria.add(Restrictions.eq("idFechaPago", idFechaPago));
            cuentaDeCobro = (CuentaDeCobro) criteria.uniqueResult();
            if (cuentaDeCobro != null) {
                Criteria criEstadoCc = getSession().createCriteria(CuentaCobroEstado.class);
                criEstadoCc.add(Restrictions.eq("idCuentaCobro", cuentaDeCobro.getId()));
                criEstadoCc.addOrder(Order.asc("fechaCambioEstado"));
                cuentaCobroEstados = criEstadoCc.list();
                cuentaCobroEstados = normalizarCuentaCobroEstado(cuentaCobroEstados);
                cuentaDeCobro.setCobroEstados(cuentaCobroEstados);
            }
        } catch (Exception e) {
            cuentaDeCobro = null;
        } finally {
            close();
            return cuentaDeCobro;
        }
    }

    private List<CuentaCobroEstado> normalizarCuentaCobroEstado(List<CuentaCobroEstado> cuentaCobroEstados) {
        List<CuentaCobroEstado> cuentaCobroEstados1 = new ArrayList<CuentaCobroEstado>();
        try {
            Criteria criteria;
            for (CuentaCobroEstado cuentaCobroEstado : cuentaCobroEstados) {
                criteria = getSession().createCriteria(Estado.class);
                criteria.add(Restrictions.eq("id", cuentaCobroEstado.getIdEstado()));
                Estado estado = new Estado();
                estado = (Estado) criteria.uniqueResult();
                CuentaCobroEstado cuentaCobroEstado1 = new CuentaCobroEstado();
                cuentaCobroEstado1 = cuentaCobroEstado;
                cuentaCobroEstado1.setNombreEstado(estado.getNombre());
                cuentaCobroEstados1.add(cuentaCobroEstado1);
            }
        } catch (Exception e) {
            cuentaCobroEstados1 = null;
        } finally {
            return cuentaCobroEstados1;
        }
    }

    public RespuestaVO generarCuentaCobro(int idContrato, int idCuentaBancaria, int id_fecha_pago, String concepto, String observacion, float valor) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, "");
        try {
            begin();
            CuentaDeCobro cuentaDeCobro = new CuentaDeCobro(idContrato, idCuentaBancaria, id_fecha_pago, new Date(), concepto, observacion, valor);
            cuentaDeCobro.setIdUltimoEstado((short) 3);
            Serializable serializable = getSession().save(cuentaDeCobro);
            int idCuentaCobro = Integer.parseInt(serializable.toString());
            RespuestaVO respuestaVO1 = generarCuentaCobroEstado(idCuentaCobro, (short) 3);
            if (respuestaVO1.getIdRespuesta() == 1) {
                commit();
                Criteria criContrato = getSession().createCriteria(Contrato.class);
                criContrato.add(Restrictions.eq("id", idContrato));
                Contrato contrato = new Contrato();
                contrato = (Contrato) criContrato.uniqueResult();

                Criteria criCuentaBank = getSession().createCriteria(CuentaBancaria.class);
                criCuentaBank.add(Restrictions.eq("id", idCuentaBancaria));
                CuentaBancaria cuentaBancaria = (CuentaBancaria) criCuentaBank.uniqueResult();

                java.lang.String msg = Mail.ID_CC + idCuentaCobro + EtiquetasHtml.BR;
                msg += Mail.ID_CONTRATO + idContrato + EtiquetasHtml.BR;
                msg += Mail.RECURSO_HUMANO + contrato.getRecursoHumano().getNombre() + " " + contrato.getRecursoHumano().getApellido() + EtiquetasHtml.BR;
                msg += Mail.NUMERO_CUENTA_BANK + cuentaBancaria.getNumero() + EtiquetasHtml.BR;
                msg += Mail.FECHA_DE_SOLICITUD + new Date() + EtiquetasHtml.BR;
                msg += Mail.CONCEPTO + concepto + EtiquetasHtml.BR;
                msg += Mail.OBSERVACION + observacion + EtiquetasHtml.BR;
                msg += Mail.VALOR + valor + EtiquetasHtml.BR;
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.ENVIO_PETICION_DE_CC + " " + enviarEmailCC(msg, Mail.ASUNTO_CUENTA_DE_COBRO));
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_ENVIO_PETICION_DE_CC + Mensajes.SALTO_LINEA + Mensajes.INTENTELO_DE_NUEVO);
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_ENVIO_PETICION_DE_CC + Mensajes.SALTO_LINEA + Mensajes.INTENTELO_DE_NUEVO);
        } finally {
            close();
            return respuestaVO;
        }
    }

    private String enviarEmailCC(String mensaje, String asunto) {
        List<RolPermisos> rolPermisoses = new ArrayList<RolPermisos>();
        Vector correos = new Vector();
        try {
            Criteria criteria = getSession().createCriteria(RolPermisos.class);
            criteria.add(Restrictions.eq("permisos", new Permisos((short) 49)));
            rolPermisoses = criteria.list();
            Criteria criteria1;
            for (RolPermisos rolPermisos : rolPermisoses) {
                List<UsuarioSistemaRol> usuarioSistemaRols = new ArrayList<UsuarioSistemaRol>();
                criteria1 = getSession().createCriteria(UsuarioSistemaRol.class);
                criteria1.add(Restrictions.eq("rol", new Rol(rolPermisos.getRol().getId())));
                usuarioSistemaRols = criteria1.list();
                for (UsuarioSistemaRol usuarioSistemaRol : usuarioSistemaRols) {
                    correos.add(usuarioSistemaRol.getUsuarioSys().getCorreoElectronico());
                }
            }

            String[] emails = new String[correos.size()];
            correos.copyInto(emails);

            criteria1 = getSession().createCriteria(UsuarioSys.class);
            criteria1.add(Restrictions.eq("isAdmin", (short) 1));
            UsuarioSys us = (UsuarioSys) criteria1.uniqueResult();

            Mail mail = new Mail();
            close();
            return mail.sendMail(us.getCorreoElectronico(), emails, asunto, mensaje);

        } catch (Exception e) {
            return " - No se envió el correo  de notificación ";
        }
    }

    private RespuestaVO generarCuentaCobroEstado(int idCuentaCobro, short idEstado) {
        RespuestaVO respuestaVO = new RespuestaVO((short) -1, "");
        try {
            CuentaCobroEstado cuentaCobroEstado = new CuentaCobroEstado(idCuentaCobro, idEstado, new Date());
            Serializable serializable = getSession().save(cuentaCobroEstado);
            int idcuentaCobroEstado = Integer.parseInt(serializable.toString());
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, "");
        } finally {
            return respuestaVO;
        }
    }
    private static final short DEVUELTA = 1;
    private static final short CORREGIDA = 2;
    private static final short VALIDACION = 3;
    private static final short AGENDAMIENTO = 4;
    private static final short PAGADA = 5;

    public List<CuentaCobroEstadoVO> listarCuentasCobro(short tipoEstado) {
        List<CuentaDeCobro> cuentaDeCobros = new ArrayList<CuentaDeCobro>();
        List<CuentaCobroEstadoVO> cuentaCobroEstadoVOs = new ArrayList<CuentaCobroEstadoVO>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CuentaDeCobro.class);
            switch (tipoEstado) {
                case DEVUELTA:
                    criteria.add(Restrictions.eq("idUltimoEstado", DEVUELTA));
                    break;
                case CORREGIDA:
                    criteria.add(Restrictions.eq("idUltimoEstado", CORREGIDA));
                    break;
                case VALIDACION:
                    criteria.add(Restrictions.eq("idUltimoEstado", VALIDACION));
                    break;
                case AGENDAMIENTO:
                    criteria.add(Restrictions.eq("idUltimoEstado", AGENDAMIENTO));
                    break;
                case PAGADA:
                    criteria.add(Restrictions.eq("idUltimoEstado", PAGADA));
                    break;
                //Todas
                default:
                    break;
            }

            cuentaDeCobros = criteria.list();
            cuentaCobroEstadoVOs = cuentaCobroToCuentaCobroEstadoVO(cuentaDeCobros);
            //cuentaCobroEstadoVOs = cuentaCobroEstadoToCcVO(cuentaDeCobros);
        } catch (Exception e) {
            cuentaCobroEstadoVOs = null;
        } finally {
            close();
            return cuentaCobroEstadoVOs;
        }
    }

    private List<CuentaCobroEstadoVO> cuentaCobroToCuentaCobroEstadoVO(List<CuentaDeCobro> cuentaDeCobros) {

        List<CuentaCobroEstadoVO> newCuentaCobroEstadoVOs = new ArrayList<CuentaCobroEstadoVO>();
        try {
            for (CuentaDeCobro cuentaDeCobro : cuentaDeCobros) {
                String cobrador = getSolicitanteCiudad(cuentaDeCobro.getIdContrato())[0];
                String ciudad = getSolicitanteCiudad(cuentaDeCobro.getIdContrato())[1];
                Date fechas[] = new Date[2];
                fechas = getFechas(cuentaDeCobro.getIdFechaPago());
                Estado estado = new Estado();
                estado = getEstado(cuentaDeCobro.getIdUltimoEstado());
                CuentaCobroEstadoVO cuentaCobroEstadoVO = new CuentaCobroEstadoVO(cuentaDeCobro.getId(), cuentaDeCobro.getFechaSolicitud(), cobrador, cuentaDeCobro.getValor(), cuentaDeCobro.getConcepto(), ciudad, fechas[0], fechas[1], cuentaDeCobro.getObservacion(), estado);
                newCuentaCobroEstadoVOs.add(cuentaCobroEstadoVO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return newCuentaCobroEstadoVOs;
        }
    }

    public CuentasCobroDao() {
    }

    private Estado getEstado(int idEstado) {
        Estado estado = new Estado();
        try {
            Criteria criteria = getSession().createCriteria(Estado.class);
            criteria.add(Restrictions.eq("id", (short) idEstado));
            estado = (Estado) criteria.uniqueResult();
        } catch (Exception e) {
            System.out.println();
        } finally {
            return estado;
        }
    }

    private Date[] getFechas(int idFechaPago) {
        FechasPago fechasPago = new FechasPago();
        Date fechas[] = new Date[2];
        try {
            Criteria criteria = getSession().createCriteria(FechasPago.class);
            criteria.add(Restrictions.eq("id", idFechaPago));
            fechasPago = (FechasPago) criteria.uniqueResult();

            fechas[0] = fechasPago.getIniPeriodoPago();
            fechas[1] = fechasPago.getFinPeriodoPago();

        } catch (Exception e) {
            System.out.println();
        } finally {
            return fechas;
        }
    }

    private String[] getSolicitanteCiudad(int idContrato) {
        Contrato contrato = new Contrato();
        String solicitante = null;
        String ciudad;
        String solicidCiudad[] = new String[2];
        try {
            Criteria criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("id", idContrato));
            contrato = (Contrato) criteria.uniqueResult();
            solicitante = contrato.getRecursoHumano().getNombre() + " " + contrato.getRecursoHumano().getApellido();
            ciudad = contrato.getCountryByIdNacionLabores().getName();
            solicidCiudad[0] = solicitante;
            solicidCiudad[1] = ciudad;
        } catch (Exception e) {
            System.out.println();
        } finally {
            return solicidCiudad;
        }
    }

    public List<CuentaCobroEstadoVO> filtrarCuentasCobroPorRh(int idRh, short tipoEstado) {
        List<CuentaCobroEstado> cuentaCobroEstados = new ArrayList<CuentaCobroEstado>();
        List<CuentaCobroEstadoVO> cuentaCobroEstadoVOsAll = new ArrayList<CuentaCobroEstadoVO>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("recursoHumano", new RecursoHumano(idRh)));
            List<Contrato> contratos = criteria.list();

            for (Contrato contrato : contratos) {
                List<CuentaDeCobro> cuentaDeCobros = new ArrayList<CuentaDeCobro>();
                criteria = getSession().createCriteria(CuentaDeCobro.class);
                criteria.add(Restrictions.eq("idContrato", contrato.getId()));
                cuentaDeCobros = criteria.list();
                for (CuentaDeCobro cuentaDeCobro : cuentaDeCobros) {
                    List<CuentaCobroEstadoVO> cuentaCobroEstadoVOs = new ArrayList<CuentaCobroEstadoVO>();
                    criteria = getSession().createCriteria(CuentaCobroEstado.class);
                    criteria.add(Restrictions.eq("idCuentaCobro", cuentaDeCobro.getId()));
                    switch (tipoEstado) {
                        case DEVUELTA:
                            criteria.add(Restrictions.eq("idEstado", DEVUELTA));
                            break;
                        case CORREGIDA:
                            criteria.add(Restrictions.eq("idEstado", CORREGIDA));
                            break;
                        case VALIDACION:
                            criteria.add(Restrictions.eq("idEstado", VALIDACION));
                            break;
                        case AGENDAMIENTO:
                            criteria.add(Restrictions.eq("idEstado", AGENDAMIENTO));
                            break;
                        case PAGADA:
                            criteria.add(Restrictions.eq("idEstado", PAGADA));
                            break;
                        //Todas
                        default:
                            break;
                    }
                    cuentaCobroEstados = criteria.list();
                    cuentaCobroEstadoVOs = cuentaCobroEstadoToCcVO(cuentaCobroEstados);
                    for (CuentaCobroEstadoVO cuentaCobroEstadoVO : cuentaCobroEstadoVOs) {
                        cuentaCobroEstadoVOsAll.add(cuentaCobroEstadoVO);
                    }
                }
            }
        } catch (Exception e) {
            cuentaCobroEstadoVOsAll = null;
        } finally {
            close();
            return cuentaCobroEstadoVOsAll;
        }

    }

    public List<CuentaCobroEstadoVO> filrarCcPorContrato(int idContrato, short tipoEstado) {
        List<CuentaCobroEstadoVO> cuentaCobroEstadoVOsAll = new ArrayList<CuentaCobroEstadoVO>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("id", idContrato));
            Contrato contrato = (Contrato) criteria.uniqueResult();

            criteria = getSession().createCriteria(CuentaDeCobro.class);
            criteria.add(Restrictions.eq("idContrato", contrato.getId()));
            List<CuentaDeCobro> cuentaDeCobros = new ArrayList<CuentaDeCobro>();
            cuentaDeCobros = criteria.list();

            for (CuentaDeCobro cuentaDeCobro : cuentaDeCobros) {
                List<CuentaCobroEstado> cuentaCobroEstados = new ArrayList<CuentaCobroEstado>();
                criteria = getSession().createCriteria(CuentaCobroEstado.class);
                criteria.add(Restrictions.eq("idCuentaCobro", cuentaDeCobro.getId()));
                switch (tipoEstado) {
                    case DEVUELTA:
                        criteria.add(Restrictions.eq("idEstado", DEVUELTA));
                        break;
                    case CORREGIDA:
                        criteria.add(Restrictions.eq("idEstado", CORREGIDA));
                        break;
                    case VALIDACION:
                        criteria.add(Restrictions.eq("idEstado", VALIDACION));
                        break;
                    case AGENDAMIENTO:
                        criteria.add(Restrictions.eq("idEstado", AGENDAMIENTO));
                        break;
                    case PAGADA:
                        criteria.add(Restrictions.eq("idEstado", PAGADA));
                        break;
                }
                List<CuentaCobroEstadoVO> cuentaCobroEstadoVOs = new ArrayList<CuentaCobroEstadoVO>();
                cuentaCobroEstados = criteria.list();
                cuentaCobroEstadoVOs = cuentaCobroEstadoToCcVO(cuentaCobroEstados);
                for (CuentaCobroEstadoVO cuentaCobroEstadoVO : cuentaCobroEstadoVOs) {
                    cuentaCobroEstadoVOsAll.add(cuentaCobroEstadoVO);
                }
            }
        } catch (Exception e) {
            cuentaCobroEstadoVOsAll = null;
        } finally {
            close();
            return cuentaCobroEstadoVOsAll;
        }
    }

    public List<CuentaCobroEstadoVO> filrarCcPorFechas(Calendar fechaIni, Calendar fechaFin, short tipoEstado) {
        List<CuentaCobroEstadoVO> cuentaCobroEstadoVOsAll = new ArrayList<CuentaCobroEstadoVO>();
        try {
            begin();

            Criteria criteria = getSession().createCriteria(CuentaDeCobro.class);
            criteria.add(Restrictions.between("fechaSolicitud", fechaIni.getTime(), fechaFin.getTime()));
            List<CuentaDeCobro> cuentaDeCobros = new ArrayList<CuentaDeCobro>();
            cuentaDeCobros = criteria.list();

            for (CuentaDeCobro cuentaDeCobro : cuentaDeCobros) {
                List<CuentaCobroEstado> cuentaCobroEstados = new ArrayList<CuentaCobroEstado>();
                criteria = getSession().createCriteria(CuentaCobroEstado.class);
                criteria.add(Restrictions.eq("idCuentaCobro", cuentaDeCobro.getId()));
                switch (tipoEstado) {
                    case DEVUELTA:
                        criteria.add(Restrictions.eq("idEstado", DEVUELTA));
                        break;
                    case CORREGIDA:
                        criteria.add(Restrictions.eq("idEstado", CORREGIDA));
                        break;
                    case VALIDACION:
                        criteria.add(Restrictions.eq("idEstado", VALIDACION));
                        break;
                    case AGENDAMIENTO:
                        criteria.add(Restrictions.eq("idEstado", AGENDAMIENTO));
                        break;
                    case PAGADA:
                        criteria.add(Restrictions.eq("idEstado", PAGADA));
                        break;
                }
                List<CuentaCobroEstadoVO> cuentaCobroEstadoVOs = new ArrayList<CuentaCobroEstadoVO>();
                cuentaCobroEstados = criteria.list();
                cuentaCobroEstadoVOs = cuentaCobroEstadoToCcVO(cuentaCobroEstados);
                for (CuentaCobroEstadoVO cuentaCobroEstadoVO : cuentaCobroEstadoVOs) {
                    cuentaCobroEstadoVOsAll.add(cuentaCobroEstadoVO);
                }
            }
        } catch (Exception e) {
            cuentaCobroEstadoVOsAll = null;
        } finally {
            close();
            return cuentaCobroEstadoVOsAll;
        }
    }

    private List<CuentaCobroEstadoVO> cuentaCobroEstadoToCcVO(List<CuentaCobroEstado> cuentaCobroEstados) {
        List<CuentaCobroEstadoVO> cuentaCobroEstadoVOs = new ArrayList<CuentaCobroEstadoVO>();
        try {
            Criteria criteria;

            for (CuentaCobroEstado cuentaCobroEstado : cuentaCobroEstados) {
                CuentaCobroEstadoVO cuentaCobroEstadoVO = new CuentaCobroEstadoVO();
                criteria = getSession().createCriteria(CuentaDeCobro.class);
                criteria.add(Restrictions.eq("id", cuentaCobroEstado.getIdCuentaCobro()));
                CuentaDeCobro cuentaDeCobro = (CuentaDeCobro) criteria.uniqueResult();

                criteria = getSession().createCriteria(FechasPago.class);
                criteria.add(Restrictions.eq("id", cuentaDeCobro.getIdFechaPago()));
                FechasPago fechasPago = (FechasPago) criteria.uniqueResult();

                criteria = getSession().createCriteria(Contrato.class);
                criteria.add(Restrictions.eq("id", cuentaDeCobro.getIdContrato()));
                Contrato contrato = (Contrato) criteria.uniqueResult();

                criteria = getSession().createCriteria(RecursoHumano.class);
                criteria.add(Restrictions.eq("id", contrato.getRecursoHumano().getId()));
                RecursoHumano recursoHumano = (RecursoHumano) criteria.uniqueResult();

                cuentaCobroEstadoVO.setId(cuentaDeCobro.getId());
                cuentaCobroEstadoVO.setFechaSolicitud(cuentaDeCobro.getFechaSolicitud());
                cuentaCobroEstadoVO.setCobrador(recursoHumano.getNombre() + " " + recursoHumano.getApellido());
                cuentaCobroEstadoVO.setValor(cuentaDeCobro.getValor());
                cuentaCobroEstadoVO.setConcepto(cuentaDeCobro.getConcepto());
                cuentaCobroEstadoVO.setCiudad(contrato.getCountryByIdNacionLabores().getName());
                cuentaCobroEstadoVO.setFechaInicio(fechasPago.getIniPeriodoPago());
                cuentaCobroEstadoVO.setFechaInicio(fechasPago.getFinPeriodoPago());
                cuentaCobroEstadoVO.setObservasion(cuentaDeCobro.getObservacion());
                cuentaCobroEstadoVOs.add(cuentaCobroEstadoVO);
            }
        } catch (Exception e) {
            cuentaCobroEstadoVOs = null;
        } finally {
            return cuentaCobroEstadoVOs;
        }

    }

    public RespuestaVO cambiarEstadoCuentaCobro(int idCuentaCobro, short idEstado) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
        CuentaPorPagarDao cuentaPorPagarDao = new CuentaPorPagarDao();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CuentaDeCobro.class);
            criteria.add(Restrictions.eq("id", idCuentaCobro));
            CuentaDeCobro cuentaDeCobro = new CuentaDeCobro();
            cuentaDeCobro = (CuentaDeCobro) criteria.uniqueResult();
            cuentaDeCobro.setIdUltimoEstado(idEstado);
            getSession().update(cuentaDeCobro);
            if (cuentaDeCobro != null) {
                CuentaCobroEstado cuentaCobroEstado = new CuentaCobroEstado(idCuentaCobro, idEstado, new Date());
                getSession().save(cuentaCobroEstado);
                commit();
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + " Cuenta de cobro no existe ");
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            //////ENVIAR A MODULO DE CUENTAS POR PAGAR
            RespuestaVO respuestaVOcuentaPorPagar = new RespuestaVO(Mensajes.INGRESO_DATOS, null);
            if (idEstado == (short) 4) {
                CuentaDeCobro cuentaDeCobro1 = getRecursoHumano(idCuentaCobro);
                respuestaVOcuentaPorPagar = cuentaPorPagarDao.crear(new Date(), Integer.parseInt(cuentaDeCobro1.getConcepto()), -1, "", cuentaDeCobro1.getIdCuentaBancaria(), -1, -1, "", cuentaDeCobro1.getValor(), (short) 2, (short) 4, idCuentaCobro, null);
            }
            if (respuestaVOcuentaPorPagar.getIdRespuesta() == Mensajes.INGRESO_DATOS) {
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOcuentaPorPagar.getMensajeRespuesta());
            }
            return respuestaVO;
        }
    }

    private List<FechasPago> compararFechasPagoGeneradas(List<FechasPago> fechasPagos, boolean generadas) {
        List<FechasPago> fechasPagosNoGeneradas = new ArrayList<FechasPago>();
        List<FechasPago> fechasPagosGeneradas = new ArrayList<FechasPago>();
        try {
            Criteria criteria;
            for (FechasPago fechasPago : fechasPagos) {
                criteria = getSession().createCriteria(CuentaDeCobro.class);
                criteria.add(Restrictions.eq("idFechaPago", fechasPago.getId()));
                CuentaDeCobro cuentaDeCobro = new CuentaDeCobro();
                cuentaDeCobro = (CuentaDeCobro) criteria.uniqueResult();
                if (cuentaDeCobro == null) {
                    fechasPagosNoGeneradas.add(fechasPago);
                } else {
                    Estado estado = new Estado();
                    estado = getEstado(cuentaDeCobro.getIdUltimoEstado());
                    fechasPago.setEstado(estado.getNombre());
                    fechasPagosGeneradas.add(fechasPago);
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            if (generadas) {
                return fechasPagosGeneradas;
            } else {
                return fechasPagosNoGeneradas;
            }
        }
    }

    private List<FechasPago> getCuentasCobroGeneradas(int idContrato) {
        List<FechasPago> fechasPagos = new ArrayList<FechasPago>();
        try {
            List<CuentaDeCobro> cuentaDeCobros = new ArrayList<CuentaDeCobro>();
            Criteria criteria = getSession().createCriteria(CuentaDeCobro.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            criteria.addOrder(Order.asc("id"));
            cuentaDeCobros = criteria.list();

            for (CuentaDeCobro cuentaDeCobro : cuentaDeCobros) {
                /*Date fechas[] = new Date[2];
                fechas = getFechas(cuentaDeCobro.getIdFechaPago());*/
                Estado estado = new Estado();
                estado = getEstado(cuentaDeCobro.getIdUltimoEstado());

                FechasPago fechasPago = new FechasPago();

                Criteria criteria1 = getSession().createCriteria(FechasPago.class);
                criteria1.add(Restrictions.eq("id", cuentaDeCobro.getIdFechaPago()));
                fechasPago = (FechasPago) criteria1.uniqueResult();
                fechasPago.setEstado(estado.getNombre());
                fechasPagos.add(fechasPago);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return fechasPagos;
        }
    }

    public DatosCuentaCobroVO generarDatosCuentaCobro(int idRh, int idValorFormaPago) {
        DatosCuentaCobroVO datosCuentaCobroVO = new DatosCuentaCobroVO();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(RecursoHumano.class);
            criteria.add(Restrictions.eq("id", idRh));
            RecursoHumano recursoHumano = new RecursoHumano();
            recursoHumano = (RecursoHumano) criteria.uniqueResult();

            criteria = getSession().createCriteria(ValorFormaPago.class);
            criteria.add(Restrictions.eq("id", idValorFormaPago));
            ValorFormaPago valorFormaPago = new ValorFormaPago();
            valorFormaPago = (ValorFormaPago) criteria.uniqueResult();

            datosCuentaCobroVO.setCedula(recursoHumano.getCedula());
            datosCuentaCobroVO.setCelular(recursoHumano.getCelular());
            datosCuentaCobroVO.setConcepto(valorFormaPago.getConcepto());
            datosCuentaCobroVO.setDireccion(recursoHumano.getDireccionDomicilio());
            datosCuentaCobroVO.setEmail(recursoHumano.getCorreoElectronico());
            datosCuentaCobroVO.setFecha(new Date());
            datosCuentaCobroVO.setNombreApellido(recursoHumano.getNombre() + " " + recursoHumano.getApellido());
            datosCuentaCobroVO.setTelefono(recursoHumano.getTelefono());
            datosCuentaCobroVO.setValorPago(valorFormaPago.getValorPago());

        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return datosCuentaCobroVO;
        }
    }

    private CuentaDeCobro getRecursoHumano(int idCuentaCobro) {
        int idRh;
        CuentaDeCobro cuentaDeCobro = new CuentaDeCobro();
        try {
            Criteria criteria = getSession().createCriteria(CuentaDeCobro.class);
            criteria.add(Restrictions.eq("id", idCuentaCobro));
            cuentaDeCobro = (CuentaDeCobro) criteria.uniqueResult();

            criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("id", cuentaDeCobro.getIdContrato()));
            Contrato contrato = (Contrato) criteria.uniqueResult();

            idRh = contrato.getRecursoHumano().getId();
            cuentaDeCobro.setConcepto(idRh + "");
        } catch (Exception e) {
            System.out.println();
        } finally {
            return cuentaDeCobro;
        }
    }
}
