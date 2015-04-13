/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Abono;
import Entidades.DetalleFactura;
import Entidades.Factura;
import Entidades.FacturaIva;
import Entidades.FacturaRetencion;
import Entidades.Iva;
import Entidades.Persona;
import Entidades.Retenciones;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.DatosFacturaVO;
import VO.DetalleFacturaFx;
import VO.DetalleFacturaVO;
import VO.FacturaIvaVO;
import VO.FacturaRetencionVO;
import VO.FacturaVO;
import VO.PersonaVO;
import VO.RespuestaVO;
import VO.RetencionVO;
import VO.ValorIdVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class FacturaDao extends DAO {

    Utilidades utilidades = new Utilidades();
    private double totalDetalleFactura;
    private double subTotalDetalleFactura;
    private double subTotalFactura;
    Vector<Double> valoresIvaFactura;
    Vector<Double> valoresFacturaRetencion;

    private List<DetalleFacturaVO> convertirDetallesFacturasALista(Object[] detallesFacturas) {
        List<DetalleFacturaVO> detalleFacturaVOs = new ArrayList<DetalleFacturaVO>();

        for (Object detalleFactura : detallesFacturas) {
            List<RetencionVO> retencionesVOs = new ArrayList<RetencionVO>();
            Object[] objDetalle = (Object[]) detalleFactura;
            Object[] idsRetenciones = (Object[]) objDetalle[3];
            for (Object id : idsRetenciones) {
                RetencionVO retencionesVO = new RetencionVO(Integer.parseInt(id.toString()));
                retencionesVOs.add(retencionesVO);
            }
            short cantidad = Short.parseShort(objDetalle[1].toString());
            String descrip = objDetalle[0].toString();
            Double precio = Double.parseDouble(objDetalle[2].toString());
            DetalleFacturaVO detalleFacturaVO = new DetalleFacturaVO(cantidad, descrip, precio, retencionesVOs);
            detalleFacturaVOs.add(detalleFacturaVO);
        }
        return detalleFacturaVOs;

    }

    private RespuestaVO comprobarExitenciaDatos(int idPersona/*, int[] idsIvas, int[] idsRetenciones*/) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 0, "");
        try {
            //begin();
            if (utilidades.buscarPropiedad(Persona.class, "id", idPersona, Utilidades.INT)) {
                respuestaVO = new RespuestaVO((short) 1, Mensajes.PERSONA_NO_EXISTE);
            } /*else {
            boolean ban = false;
            for (int idIva : idsIvas) {
            ban = false;
            if (utilidades.buscarPropiedad(Iva.class, "id", (short) idIva, Utilidades.SHORT)) {
            respuestaVO = new RespuestaVO((short) 1, Mensajes.IVA_NO_EXISTE_PART1 + idIva + Mensajes.IVA_NO_EXISTE_PART2);
            ban = true;
            break;
            }
            }
            if (!ban) {
            for (int idRetencion : idsRetenciones) {
            if (utilidades.buscarPropiedad(Retenciones.class, "id", (short) idRetencion, Utilidades.SHORT)) {
            respuestaVO = new RespuestaVO((short) 1, Mensajes.RETENCION_NO_EXISTE_PART1 + idRetencion + Mensajes.RETENCION_NO_EXISTE_PART2);
            break;
            }
            }
            }
            }*/
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) 2, "Ha ocurrido la siguiente excepción: " + e);
        } finally {
            //close();
            return respuestaVO;
        }

    }

    private int[] obtenerAllIdsRetenciones(List<DetalleFacturaVO> detalleFacturaVOs) {
        Vector vector = new Vector();
        for (DetalleFacturaVO detalleFacturaVO : detalleFacturaVOs) {
            for (RetencionVO rtciones : detalleFacturaVO.getRetencionesVOs()) {
                vector.add(rtciones.getId());
            }
        }

        int[] idsRetenciones = new int[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            idsRetenciones[i] = Integer.parseInt(vector.elementAt(i).toString());
        }
        return idsRetenciones;
    }

    private int saveFactura(int idPersona, Date fechaFactura, Date fechaPactada, String numeroFactura, String estadoFacura) {
        int idFacturaGuardada = -1;
        try {
            begin();
            Serializable serializable = getSession().save(new Factura(new Persona(idPersona), fechaFactura, fechaPactada, numeroFactura, estadoFacura));
            idFacturaGuardada = Integer.parseInt(serializable.toString());
            //commit();
        } catch (Exception e) {
            idFacturaGuardada = -1;
        } finally {
            //close();
            return idFacturaGuardada;
        }
    }

    private double calcularValorDtalleFtraRtcion(double precio, int cantidad, double porcentaje) {
        return ((porcentaje * cantidad) / 100) * (precio * cantidad);
    }

    private RespuestaVO saveFacturaRetencion(List<ValorIdVO> valorIdVOs, int idDetalleFactura, double precio, int cantidad) {

        int idFacturaRetencionSaved = -1;
        boolean guardoFacturaRetencion = true;
        RespuestaVO respuestaVO;
        String excepcion = null;
        double porcentajeRetencion;
        this.totalDetalleFactura = 0;
        this.totalDetalleFactura = precio * cantidad;
        this.subTotalDetalleFactura = 0;
        this.subTotalDetalleFactura = precio * cantidad;
        try {
            for (ValorIdVO valorIdVO : valorIdVOs) {
                //porcentajeRetencion = ((Retenciones) utilidades.getObjetoEspecifico(Retenciones.class, "id", retencionesVO.getId(), (short) Utilidades.SHORT)).getPorcentaje();
                //idFacturaRetencionSaved = -1;
                //double valorRetencion = this.calcularValorDtalleFtraRtcion(precio, cantidad, porcentajeRetencion);
                double borrar = valorIdVO.getValor();
                idFacturaRetencionSaved = Integer.parseInt((getSession().save(new FacturaRetencion(new Retenciones((short) valorIdVO.getId()), new DetalleFactura(idDetalleFactura), valorIdVO.getValor()))).toString());
                if (idFacturaRetencionSaved == -1) {
                    guardoFacturaRetencion = false;
                    break;
                } else {
                    this.totalDetalleFactura = totalDetalleFactura - valorIdVO.getValor();
                    this.valoresFacturaRetencion.add(Double.parseDouble(valorIdVO.getValor() + ""));
                }
            }

        } catch (Exception e) {
            excepcion = e.toString();
            guardoFacturaRetencion = false;
        } finally {
            if (guardoFacturaRetencion) {
                respuestaVO = new RespuestaVO((short) 1, "");
            } else {
                respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_GUARDO_FACTURA_RETENCION + "\n" + Mensajes.EXCEPCION + excepcion);
            }

            return respuestaVO;
        }
    }

    private RespuestaVO saveDetallesFacturas(int idFactura, DetalleFacturaFx[] detalleFacturaFxs) {
        int idDetalleFacturaSaved;
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        this.subTotalFactura = 0;
        try {
            for (DetalleFacturaFx detalleFacturaFx : detalleFacturaFxs) {
                idDetalleFacturaSaved = -1;
                Serializable serializable = getSession().save(new DetalleFactura(new Factura(idFactura), detalleFacturaFx.getDescripcionBienServicio(), detalleFacturaFx.getCantidad(), detalleFacturaFx.getPrecio(), 0, 0));
                idDetalleFacturaSaved = Integer.parseInt(serializable.toString());
                if (idDetalleFacturaSaved != -1) {
                    RespuestaVO respuestaVOFacturaRetencion = this.saveFacturaRetencion(detalleFacturaFx.getValorIdVOs(), idDetalleFacturaSaved, detalleFacturaFx.getPrecio(), detalleFacturaFx.getCantidad());
                    if (respuestaVOFacturaRetencion.getIdRespuesta() == -1) {
                        respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_GUARDO_ITEM + "\n" + respuestaVOFacturaRetencion.getMensajeRespuesta());
                        break;
                    } else {
                        //this.subTotalFactura = subTotalFactura + this.subTotalDetalleFactura;
                        DetalleFactura detalleFactura = (DetalleFactura) utilidades.getObjetoEspecifico(DetalleFactura.class, "id", idDetalleFacturaSaved, Utilidades.INT);
                        detalleFactura.setSubtotal(0/*this.subTotalDetalleFactura*/);
                        detalleFactura.setTotal(/*totalDetalleFactura*/detalleFacturaFx.getPrecio() * detalleFacturaFx.getCantidad());
                        this.subTotalFactura = subTotalFactura + detalleFactura.getTotal();
                        getSession().update(detalleFactura);
                    }
                } else {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_GUARDO_ITEM + detalleFacturaFx.getDescripcionBienServicio());
                }
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_GUARDO_ITEM + "\n" + Mensajes.EXCEPCION + e);
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO saveIvasFactura(int[] idsIvas, int idFactura) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        int idFacturaIvaSaved = -1;
        try {
            if (idsIvas[0] == -1) {
                return null;
            }
            for (int id : idsIvas) {
                double valorIvaFactura = this.subTotalFactura * (((Iva) utilidades.getObjetoEspecifico(Iva.class, "id", (short) id, Utilidades.SHORT)).getPorcentaje() / 100);
                valoresIvaFactura.add(valorIvaFactura);
                idFacturaIvaSaved = -1;
                idFacturaIvaSaved = Integer.parseInt(getSession().save(new FacturaIva(new Factura(idFactura), new Iva((short) id), valorIvaFactura)).toString());
                if (idFacturaIvaSaved == -1) {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_GUARDO_IVA + Mensajes.IDENTIFICADOR + id);
                    break;
                }
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_GUARDO_IVA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private double calTtalValsIvaFactura() {
        double total = 0;
        for (Double valIvaFactura : valoresIvaFactura) {
            total = total + valIvaFactura;
        }
        return total;
    }

    private double calTtalValsFacturaRetencion() {
        double total = 0;
        for (Double valFacturaRtcion : valoresFacturaRetencion) {
            total = total + valFacturaRtcion;
        }
        return total;
    }

    private double getTotalRetencionesItemsFactura(int idFactura) {
        double totalRetenciones = 0;
        try {
            Criteria criteria = getSession().createCriteria(DetalleFactura.class);
            criteria.add(Restrictions.eq("factura", new Factura(idFactura)));
            List<DetalleFactura> detalleFacturas = new ArrayList<DetalleFactura>();
            detalleFacturas = criteria.list();
            for (DetalleFactura detalleFactura : detalleFacturas) {
                List<FacturaRetencion> facturaRetencions = new ArrayList<FacturaRetencion>();
                facturaRetencions = getFacturaRetenciones(detalleFactura);
                double valor = getValorRetencionItem(facturaRetencions);
                totalRetenciones = totalRetenciones + valor;
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return totalRetenciones;
        }
    }

    private double getValorRetencionItem(List<FacturaRetencion> facturaRetencions) {
        double valor = 0;
        try {
            for (FacturaRetencion facturaRetencion : facturaRetencions) {
                valor = valor + facturaRetencion.getValor();
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return valor;
        }
    }

    private List<FacturaRetencion> getFacturaRetenciones(DetalleFactura detalleFactura) {
        List<FacturaRetencion> facturaRetencions = new ArrayList<FacturaRetencion>();
        try {
            Criteria criteria = getSession().createCriteria(FacturaRetencion.class);
            criteria.add(Restrictions.eq("detalleFactura", detalleFactura));
            facturaRetencions = criteria.list();
        } catch (Exception e) {
            System.out.println();
        } finally {
            return facturaRetencions;
        }
    }

    public RespuestaVO crear(int idPersona, Date fechaFactura, Date fechaPactada, short idIva, DetalleFacturaFx[] detalleFacturaFxs, String numeroFactura, String estadoFactura) {

        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
        RespuestaVO respuestaVOExistenDatos;
        int idFacturaGuardada;
        valoresIvaFactura = new Vector<Double>();
        valoresFacturaRetencion = new Vector<Double>();

        try {

            begin();
            boolean respuesta = utilidades.buscarPropiedad(Factura.class, "numeroFactura", numeroFactura, Utilidades.STRING);
            if (!respuesta) {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.NUMERO_FACTURA_YA_INGRESADO);
                return respuestaVO;
            }
            respuestaVOExistenDatos = this.comprobarExitenciaDatos(idPersona/*, idsIvas, this.obtenerAllIdsRetenciones(detalleFacturaVOs)*/);
            if (respuestaVOExistenDatos.getIdRespuesta() == 0) {
                idFacturaGuardada = this.saveFactura(idPersona, fechaFactura, fechaPactada, numeroFactura, estadoFactura);
                if (idFacturaGuardada != -1) {
                    RespuestaVO respuestaVODetalleFactura = this.saveDetallesFacturas(idFacturaGuardada, detalleFacturaFxs);
                    if (respuestaVODetalleFactura.getIdRespuesta() == -1) {
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVODetalleFactura.getMensajeRespuesta());
                    } else {
                        int idsIvas[] = {idIva};
                        RespuestaVO respuestaVOFacturaIva = this.saveIvasFactura(idsIvas, idFacturaGuardada);
                        if (respuestaVOFacturaIva.getIdRespuesta() != (short) -1) {
                            double totalIvaFactura = this.calTtalValsIvaFactura();
                            double totalFacturaRetencion = this.calTtalValsFacturaRetencion();

                            double valorIva = 0;
                            double porcentajeIva = 0;
                            if (idsIvas[0] != -1) {
                                porcentajeIva = ((Iva) utilidades.getObjetoEspecifico(Iva.class, "id", idIva, (short) Utilidades.SHORT)).getPorcentaje();
                                valorIva = this.subTotalFactura * (porcentajeIva / 100);
                            }
                            double totalFactura = (this.subTotalFactura + valorIva);

                            Factura factura = (Factura) utilidades.getObjetoEspecifico(Factura.class, "id", idFacturaGuardada, Utilidades.INT);
                            factura.setSubtutal(subTotalFactura);
                            factura.setTotal(totalFactura);
                            //factura.setSaldo(this.calcularValorSaldo(idFacturaGuardada));
                            //double totalRetenciones = getTotalRetencionesItemsFactura(idPersona);
                            factura.setSaldo(totalFactura - totalFacturaRetencion);
                            getSession().update(factura);
                            commit();
                            //close();
                        } else {
                            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOFacturaIva.getMensajeRespuesta());
                        }
                    }
                } else {
                    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
                }

            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOExistenDatos.getMensajeRespuesta());
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private double getValorRtencionesDetalleFacturaFx(DetalleFacturaFx[] detalleFacturaFxs) {
        double totalRtencion = 0;
        try {
            for (DetalleFacturaFx detalleFacturaFx : detalleFacturaFxs) {
                List<ValorIdVO> valorIdVOs = new ArrayList<ValorIdVO>();
                valorIdVOs = detalleFacturaFx.getValorIdVOs();
                for (ValorIdVO valorIdVO : valorIdVOs) {
                    totalRtencion = totalRtencion + valorIdVO.getValor();
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return totalRtencion;
        }
    }

    public RespuestaVO actualizar(int idFactura, int idPersona, Date fechaFactura, Date fechaPactada, int idIva, DetalleFacturaFx[] detalleFacturaFxs, String numeroFactura, String estadoFactura) {

        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
        RespuestaVO respuestaVOExistenDatos;
        valoresIvaFactura = new Vector<Double>();
        valoresFacturaRetencion = new Vector<Double>();
        try {
            //List<DetalleFacturaVO> detalleFacturaVOs = new ArrayList<DetalleFacturaVO>();
            //detalleFacturaVOs = this.convertirDetallesFacturasALista(detallesFacturas);
            begin();

            Factura fac = (Factura) getSession().get(Factura.class, idFactura);
            if ((utilidades.buscarPropiedad(Factura.class, "numeroFactura", numeroFactura, Utilidades.STRING)) || fac.getNumeroFactura().equals(numeroFactura)) {
                respuestaVOExistenDatos = this.comprobarExitenciaDatos(idPersona);

                if (respuestaVOExistenDatos.getIdRespuesta() == 0) {
                    RespuestaVO respuestaVOActualizarFactura = this.actualizarFactura(idFactura, idPersona, fechaFactura, fechaPactada, numeroFactura);
                    if (respuestaVOActualizarFactura.getIdRespuesta() == 1) {
                        RespuestaVO respuestaVOEliminarDlleFact = this.elimnarDetallesFactura(idFactura);
                        if (respuestaVOEliminarDlleFact.getIdRespuesta() == -1) {
                            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOEliminarDlleFact.getMensajeRespuesta());
                        } else {
                            RespuestaVO respuestaVOElimFacturaIva = this.eliminarFacturaIva(idFactura);
                            if (respuestaVOElimFacturaIva.getIdRespuesta() == -1) {
                                respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOElimFacturaIva.getMensajeRespuesta());
                            } else {

                                RespuestaVO respuestaVOSaveDetalleFactura = this.saveDetallesFacturas(idFactura, detalleFacturaFxs);
                                if (respuestaVOSaveDetalleFactura.getIdRespuesta() == -1) {
                                    respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOSaveDetalleFactura.getMensajeRespuesta());
                                } else {
                                    RespuestaVO respuestaVOFacturaIva;
                                    if (idIva != -1) {
                                        int idsIvas[] = {idIva};
                                        respuestaVOFacturaIva = this.saveIvasFactura(idsIvas, idFactura);
                                    } else {
                                        respuestaVOFacturaIva = new RespuestaVO((short) 1, "");
                                    }
                                    if (respuestaVOFacturaIva.getIdRespuesta() != (short) -1) {

                                        DatosFacturaVO datosFacturaVO = new DatosFacturaVO();
                                        datosFacturaVO = calcularAllDatosFactura(idFactura);

                                        if (datosFacturaVO != null) {
                                            Factura factura = (Factura) utilidades.getObjetoEspecifico(Factura.class, "id", idFactura, Utilidades.INT);
                                            factura.setSubtutal(datosFacturaVO.getSubtotal());
                                            factura.setTotal(datosFacturaVO.getTotal());

                                            double totalRetenciones = getValorRtencionesDetalleFacturaFx(detalleFacturaFxs);
                                            double total = datosFacturaVO.getTotal();
                                            factura.setSaldo(total - totalRetenciones);
                                            factura.setEstadoFactura(estadoFactura);
                                            getSession().update(factura);
                                            commit();
                                        } else {
                                            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + " No se concreto el cálculo de los nuevos valores para: subtotal, total y saldo. ");
                                        }

                                    } else {
                                        respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOElimFacturaIva.getMensajeRespuesta());
                                    }
                                }

                            }
                        }
                    } else {
                        respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOActualizarFactura.getMensajeRespuesta());
                    }
                } else {
                    respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOExistenDatos.getMensajeRespuesta());
                }
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.NUMERO_FACTURA_YA_INGRESADO);
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private RespuestaVO actualizarFactura(int idFactura, int idPersona, Date fechaFacturacion, Date fechaPactadaPago, String numeroFactura) {
        RespuestaVO respuestaVO = new RespuestaVO((short) -1, "");
        Factura nuevaFactura = new Factura();
        //Factura nuevaFactura = new Factura();
        try {
            nuevaFactura = (Factura) utilidades.getObjetoEspecifico(Factura.class, "id", idFactura, Utilidades.INT);

            if (idPersona != nuevaFactura.getPersona().getId() || fechaFacturacion.compareTo(nuevaFactura.getFechaFacturacion()) != 0 || fechaPactadaPago.compareTo(nuevaFactura.getFechaFacturacion()) != 0) {
                nuevaFactura.setPersona(new Persona(idPersona));
                nuevaFactura.setFechaFacturacion(fechaFacturacion);
                nuevaFactura.setFechaPactadaPago(fechaPactadaPago);
                nuevaFactura.setNumeroFactura(numeroFactura);
                getSession().update(nuevaFactura);
                respuestaVO = new RespuestaVO((short) 1, "");
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO elimnarDetallesFactura(int idFactura) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 0, "");
        int idDlleFact = -1;
        List<Object> detalleFacturaVOs = new ArrayList<Object>();
        try {
            detalleFacturaVOs = utilidades.getListaObjetos(DetalleFactura.class, "factura", new Factura(idFactura), Utilidades.OBJECT);
            for (Object obj : detalleFacturaVOs) {
                DetalleFactura detalleFacturaVO2 = new DetalleFactura();
                detalleFacturaVO2 = (DetalleFactura) obj;
                idDlleFact = detalleFacturaVO2.getId();
                RespuestaVO respuestaVOEliminFactRtcion = this.eliminarFacturasRetencion(detalleFacturaVO2.getId());
                if (respuestaVOEliminFactRtcion.getIdRespuesta() == -1) {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.SALTO_LINEA + respuestaVOEliminFactRtcion.getMensajeRespuesta());
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_DESASIGNO_DETALLE_FACTURA);
                    break;

                } else {
                    DetalleFactura detalleFactura = new DetalleFactura();
                    detalleFactura = (DetalleFactura) utilidades.getObjetoEspecifico(DetalleFactura.class, "id", detalleFacturaVO2.getId(), Utilidades.INT);
                    getSession().delete(detalleFactura);
                    respuestaVO = new RespuestaVO((short) 1, "");
                }
            }
        } catch (Exception e) {
            if (idDlleFact == -1) {
                respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_DESASIGNO_DETALLE_FACTURA + Mensajes.EXCEPCION + e.toString());
            } else {
                respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_DESASIGNO_DETALLE_FACTURA + Mensajes.IDENTIFICADOR + idDlleFact + Mensajes.EXCEPCION + e.toString());
            }
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO eliminarFacturasRetencion(int idDetalleFactura) {
        List<Object> objs = new ArrayList<Object>();
        RespuestaVO respuestaVO = new RespuestaVO((short) 0, "");
        try {
            objs = utilidades.getListaObjetos(FacturaRetencion.class, "detalleFactura", new DetalleFactura(idDetalleFactura), Utilidades.OBJECT);
            for (Object object : objs) {
                FacturaRetencion facturaRetencion = new FacturaRetencion();
                facturaRetencion = (FacturaRetencion) object;
                getSession().delete(facturaRetencion);
            }
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_DESASIGNO_RETENCIONES + idDetalleFactura + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }
    }

    private RespuestaVO eliminarFacturaIva(int idFactura) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 0, "");
        List<Object> facturaIvas = new ArrayList<Object>();
        int idIva = -1;
        try {
            facturaIvas = utilidades.getListaObjetos(FacturaIva.class, "factura", new Factura(idFactura), Utilidades.OBJECT);
            for (Object object : facturaIvas) {
                FacturaIva facturaIva = new FacturaIva();
                facturaIva = (FacturaIva) object;
                idIva = facturaIva.getIva().getId();
                getSession().delete(facturaIva);
            }
            respuestaVO = new RespuestaVO((short) 1, "");
        } catch (Exception e) {
            if (idIva == -1) {
                respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_DESASIGNO_IVA + Mensajes.EXCEPCION + e.toString());
            } else {
                respuestaVO = new RespuestaVO((short) -1, Mensajes.NO_DESASIGNO_IVA + Mensajes.IDENTIFICADOR + idIva + Mensajes.EXCEPCION + e.toString());
            }
        } finally {
            return respuestaVO;
        }

    }

    public RespuestaVO eliminarFactura(int idFactura) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        try {
            begin();
            Factura factura = new Factura();
            factura = (Factura) utilidades.getObjetoEspecifico(Factura.class, "id", idFactura, Utilidades.INT);
            getSession().delete(factura);
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }
    private static final short FECHA_FACTURACION = 1;
    private static final short FECHA_PACTADA_PAGO = 2;
    private static final short AMBAS_FECHA = 3;
    private static final short ESTADO_PAGADA = 1;
    private static final short ESTADO_NO_PAGADA = 2;

    public List<FacturaVO> listar(Calendar fechaIni, Calendar fechaFin, short tipoFecha, short tipoEstado, int idPersona) {
        List<FacturaVO> facturaVOs = new ArrayList<FacturaVO>();


        try {


            List<Factura> facturas = new ArrayList<Factura>();
            begin();
            Criteria criteria = getSession().createCriteria(Factura.class);
            if (idPersona != -1) {
                criteria.add(Restrictions.eq("persona", new Persona(idPersona)));
            }

            switch (tipoEstado) {
                case ESTADO_NO_PAGADA:
                    criteria.add(Restrictions.gt("saldo", new Double(0)));
                    break;
                case ESTADO_PAGADA:
                    criteria.add(Restrictions.lt("saldo", new Double(0.1)));
                    break;
            }

            if (fechaIni != null && fechaFin != null) {

                Date[] dates = utilidades.formatearCalendar(fechaIni, fechaFin);

                switch (tipoFecha) {
                    case FECHA_FACTURACION:
                        criteria.add(Restrictions.between("fechaFacturacion", dates[0], dates[1]));
                        //Criterion fechaFactura = Restrictions.between("fechaFacturacion", dates[0], dates[1]);
                        break;
                    case FECHA_PACTADA_PAGO:
                        criteria.add(Restrictions.between("fechaPactadaPago", dates[0], dates[1]));
                        //Criterion fechaPactada = Restrictions.between("fechaPactadaPago", dates[0], dates[1]);
                        break;
                    default:
                        Criterion fechaFactura = Restrictions.between("fechaFacturacion", dates[0], dates[1]);
                        Criterion fechaPactada = Restrictions.between("fechaPactadaPago", dates[0], dates[1]);
                        criteria.add(Restrictions.or(fechaFactura, fechaPactada));
                        break;
                }
            }
            criteria.addOrder(Order.asc("id"));
            facturas = criteria.list();
            facturaVOs = this.facturasToFacturaVOs(facturas);
        } catch (Exception e) {
            System.out.println("");
        } finally {
            close();
            return facturaVOs;
        }
    }

    private List<FacturaVO> facturasToFacturaVOs(List<Factura> facturas) {
        List<FacturaVO> facturaVOs = new ArrayList<FacturaVO>();
        for (Factura factura : facturas) {
            double total = factura.getTotal();
            FacturaVO facturaVO = new FacturaVO(factura.getId(), new PersonaVO(factura.getPersona().getId(), factura.getPersona().getNombreRs(), factura.getPersona().getNit(), factura.getPersona().getCedula()), factura.getFechaFacturacion(), factura.getFechaPactadaPago(), factura.getSubtutal(), factura.getTotal(), factura.getSaldo(), factura.getNumeroFactura(), factura.getEstadoFactura());
            List<DetalleFactura> detalleFacturas = new ArrayList<DetalleFactura>();
            Criteria criteria = getSession().createCriteria(DetalleFactura.class);
            detalleFacturas = criteria.add(Restrictions.eq("factura", factura)).list();
            facturaVO.setDetalleFacturaVOs(this.detallesFacturaToDetallesFacturaVO(detalleFacturas));

            Set<FacturaIva> facturaIvas = new HashSet<FacturaIva>();
            facturaIvas = factura.getFacturaIvas();

            List<FacturaIvaVO> facturaIvaVOs = new ArrayList<FacturaIvaVO>();
            double valorIva = 0;
            double porcentaje = 0;
            for (FacturaIva facturaIva : facturaIvas) {
                FacturaIvaVO facturaIvaVO = new FacturaIvaVO(facturaIva.getId(),
                        new Iva(facturaIva.getIva().getId(),
                        facturaIva.getIva().getPorcentaje(),
                        facturaIva.getIva().getNombre()),
                        facturaIva.getValor());
                facturaIvaVOs.add(facturaIvaVO);
                porcentaje = facturaIvaVO.getIva().getPorcentaje();
            }
            valorIva = facturaVO.getSubtutal() * (porcentaje / 100);
            facturaVO.setPorcentajeIva(porcentaje);
            facturaVO.setValorIva(valorIva);
            facturaVO.setFacturaIvaVOs(facturaIvaVOs);
            facturaVO.setValorRetenciones(calcularValorTodasRtenciones(facturaVO.getDetalleFacturaVOs()));
            facturaVO.setTotalApagar(facturaVO.getTotal() - facturaVO.getValorRetenciones());
            facturaVOs.add(facturaVO);
        }
        return facturaVOs;
    }

    private List<DetalleFacturaVO> detallesFacturaToDetallesFacturaVO(List<DetalleFactura> detalleFacturas) {
        List<DetalleFacturaVO> detalleFacturaVOs = new ArrayList<DetalleFacturaVO>();
        for (DetalleFactura detalleFactura : detalleFacturas) {
            DetalleFacturaVO detalleFacturaVO = new DetalleFacturaVO(detalleFactura.getId(), detalleFactura.getCantidad(), detalleFactura.getDescripcionBienServicio(), detalleFactura.getPrecio(), detalleFactura.getSubtotal(), detalleFactura.getTotal());
            detalleFacturaVO.setFacturaRetencionVOs(this.getFacturaRetencionesVO(detalleFacturaVO.getId()));
            detalleFacturaVOs.add(detalleFacturaVO);
        }

        return detalleFacturaVOs;
    }

    private List<FacturaRetencionVO> getFacturaRetencionesVO(int idDetalleFactura) {
        List<FacturaRetencionVO> facturaRetencionVOs = new ArrayList<FacturaRetencionVO>();
        try {
            Criteria criteria = getSession().createCriteria(FacturaRetencion.class);
            criteria.add(Restrictions.eq("detalleFactura", new DetalleFactura(idDetalleFactura)));
            List<FacturaRetencion> facturaRetencions = new ArrayList<FacturaRetencion>();
            facturaRetencions = criteria.list();

            for (FacturaRetencion facturaRetencion : facturaRetencions) {
                FacturaRetencionVO facturaRetencionVO = new FacturaRetencionVO(facturaRetencion.getId(), facturaRetencion.getValor(), new RetencionVO(facturaRetencion.getRetenciones().getId(), facturaRetencion.getRetenciones().getNombre(), facturaRetencion.getRetenciones().getPorcentaje()));
                facturaRetencionVOs.add(facturaRetencionVO);
            }
        } catch (Exception e) {
            System.out.println("");
        } finally {
            return facturaRetencionVOs;
        }

    }

    public RespuestaVO crearAbono(int idFactura, Date fechaAbono, double valor) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_ABONO_MSG);
        try {
            begin();
            Factura factura = (Factura) utilidades.getObjetoEspecifico(Factura.class, "id", idFactura, Utilidades.INT);
            if (true) {
                getSession().save(new Abono(new Factura(idFactura), fechaAbono, valor));
                double saldo = this.calcularValorSaldo(idFactura);
                if (true) {
                    if (fechaAbono.compareTo(factura.getFechaFacturacion()) < 0) {
                        respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_ABONO_MSG + Mensajes.SALTO_LINEA + Mensajes.FECHA_ABONO_MAYOR_FECHA_FACTURACION);
                    } else {
                        factura.setSaldo(saldo);
                        commit();
                        respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
                    }
                } else {
                    respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.ABONOS_ES_MAYOR_TOTAL_FACTURA);
                }

            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_ABONO_MSG + Mensajes.ABONO_MAYOR_SALDO_MSG);
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO eliminarAbono(int idAbono) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        try {
            begin();

            Abono abono = new Abono();
            abono = (Abono) utilidades.getObjetoEspecifico(Abono.class, "id", idAbono, Utilidades.INT);

            Factura factura = new Factura();
            factura = (Factura) utilidades.getObjetoEspecifico(Factura.class, "id", abono.getFactura().getId(), Utilidades.INT);

            getSession().delete(abono);

            double saldo = this.calcularValorSaldo(factura.getId());
            factura.setSaldo(saldo);
            getSession().update(factura);
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO actualizarAbono(int id, Date fechaAbono, double valor) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);

        try {
            begin();
            Abono abono = new Abono();
            abono = (Abono) utilidades.getObjetoEspecifico(Abono.class, "id", id, Utilidades.INT);

            abono.setFechaAbono(fechaAbono);
            abono.setValor(valor);

            double saldo = this.calcularValorSaldo(abono.getFactura().getId());
            if (saldo >= 0) {
                abono.getFactura().setSaldo(saldo);
                getSession().update(abono.getFactura());
                commit();
                respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.ABONOS_ES_MAYOR_TOTAL_FACTURA);
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private double calcularValorSaldo(int idFactura) {
        double saldo = 0;
        try {

            /*
             * obtiene cada uno de los valores de la retenciones asignados a los
             * detalles o items de una factura. Estos valores se obtienen de la
             * tabla Factura_retencion*/
            List<DetalleFactura> detalleFacturas = new ArrayList<DetalleFactura>();
            Criteria criteria = getSession().createCriteria(DetalleFactura.class);
            criteria.add(Restrictions.eq("factura", new Factura(idFactura)));
            detalleFacturas = criteria.list();

            double valorTotalPorRetenciones = 0;
            for (DetalleFactura detalleFactura : detalleFacturas) {
                List<FacturaRetencion> facturaRetencions = new ArrayList<FacturaRetencion>();
                criteria = getSession().createCriteria(FacturaRetencion.class);
                criteria.add(Restrictions.eq("detalleFactura", detalleFactura));
                facturaRetencions = criteria.list();
                for (FacturaRetencion facturaRetencion : facturaRetencions) {
                    valorTotalPorRetenciones = valorTotalPorRetenciones + facturaRetencion.getValor();
                }
            }
            /*
             * Obtenemos el valor total de la factura y los restamos con el valorTotalPorRetenciones para
             * generar el total a pagar
             */
            Factura factura = (Factura) utilidades.getObjetoEspecifico(Factura.class, "id", idFactura, Utilidades.INT);
            saldo = factura.getTotal() - valorTotalPorRetenciones;
            /*
             * Obtenemos los abonos para ir restandoselos al saldo
             */
            criteria = getSession().createCriteria(Abono.class);
            criteria.add(Restrictions.eq("factura", new Factura(idFactura)));
            List<Abono> abonos = new ArrayList<Abono>();

            abonos = criteria.list();

            for (Abono abono : abonos) {
                saldo = saldo - abono.getValor();
            }

        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            return saldo;
        }
    }

    public List<Abono> listarAbonosFactura(int idFactura) {
        List<Abono> abonos = new ArrayList<Abono>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Abono.class);
            criteria.add(Restrictions.eq("factura", new Factura(idFactura)));
            abonos = criteria.list();
            abonos = normalizarAbonos(abonos);
        } catch (Exception e) {
            System.out.print("");
        } finally {
            close();
            return abonos;
        }
    }

    private List<Abono> normalizarAbonos(List<Abono> abonos) {
        List<Abono> abonos1 = new ArrayList<Abono>();
        for (Abono abono : abonos) {
            Abono abono1 = new Abono(abono.getId(), abono.getFechaAbono(), abono.getValor());
            abonos1.add(abono1);
        }
        return abonos1;

    }

    public List<Retenciones> listarRetenciones() {
        List<Retenciones> retencioneses = new ArrayList<Retenciones>();
        Criteria criteria;
        try {
            begin();
            criteria = getSession().createCriteria(Retenciones.class);
            retencioneses = criteria.list();
            List<Retenciones> retencioneses1 = new ArrayList<Retenciones>();
            for (Retenciones retenciones : retencioneses) {
                Retenciones retenciones1 = new Retenciones(retenciones.getId(), retenciones.getNombre(), retenciones.getPorcentaje());
                retencioneses1.add(retenciones1);
            }
            retencioneses = retencioneses1;
        } catch (Exception e) {
            retencioneses = null;
        } finally {
            close();
            return retencioneses;
        }
    }

    public List<Iva> listarIvas() {
        List<Iva> ivas = new ArrayList<Iva>();
        Criteria criteria;
        try {
            begin();
            criteria = getSession().createCriteria(Iva.class);
            ivas = criteria.list();
            List<Iva> ivas1 = new ArrayList<Iva>();
            for (Iva iva : ivas) {
                Iva iva1 = new Iva(iva.getId(), iva.getPorcentaje(), iva.getNombre());
                ivas1.add(iva1);
            }
            ivas = ivas1;
        } catch (Exception e) {
            ivas = null;
        } finally {
            close();
            return ivas;
        }
    }
    /*
    public RespuestaVO eliminarDetallesFactura(int[] idsDetallesFacturas)
    {
    RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS,Mensajes.NO_ELIMINO_DATOS_MSG );
    try
    {
    begin();
    for (int id : idsDetallesFacturas)
    {
    DetalleFactura detalleFactura = new DetalleFactura();
    detalleFactura = (DetalleFactura) utilidades.getObjetoEspecifico(DetalleFactura.class,"id", id,Utilidades.INT);
    if(detalleFactura != null)
    {
    getSession().delete(detalleFactura);
    }
    }
    commit();
    respuestaVO = new RespuestaVO(Mensajes.ELIMINO_DATOS,Mensajes.ELIMINO_DATOS_MSG );
    }
    catch (Exception e)
    {
    respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
    }
    finally
    {
    close();
    return respuestaVO;
    }
    }
    
    public void calcularTotalFactura(int idFactura)
    {
    List<DetalleFactura> detalleFacturas = new ArrayList<DetalleFactura>();
    try
    {
    Criteria criteria = getSession().createCriteria(DetalleFactura.class);
    detalleFacturas = criteria.add(Restrictions.eq("factura",new Factura(idFactura))).list();

    }
    catch (Exception e)
    {

    }
    finally
    {

    }

    }
     */

    public List<FacturaVO> listarPagadas(Calendar fechaIni, Calendar fechaFin) {
        List<FacturaVO> facturaVOs = new ArrayList<FacturaVO>();
        try {
            begin();
            String s = "0";
            Criteria criteria = getSession().createCriteria(Factura.class);
            criteria.add(Restrictions.eq("saldo", Double.parseDouble(s)));
            if (fechaIni != null && fechaFin != null) {
                Date[] dates = utilidades.formatearCalendar(fechaIni, fechaFin);
                Criterion fechaFactura = Restrictions.between("fechaFacturacion", dates[0], dates[1]);
                Criterion fechaPactada = Restrictions.between("fechaPactadaPago", dates[0], dates[1]);
                criteria.add(Restrictions.or(fechaFactura, fechaPactada));
            }
            facturaVOs = criteria.list();
        } catch (Exception e) {
            facturaVOs = null;
        } finally {
            close();
            return facturaVOs;
        }
    }

    public List<FacturaVO> listarNoPagadas(Calendar fechaIni, Calendar fechaFin) {
        List<FacturaVO> facturaVOs = new ArrayList<FacturaVO>();
        try {
            begin();
            String s = "0";
            Criteria criteria = getSession().createCriteria(Factura.class);
            criteria.add(Restrictions.ne("saldo", Double.parseDouble(s)));
            if (fechaIni != null && fechaFin != null) {
                Date[] dates = utilidades.formatearCalendar(fechaIni, fechaFin);
                Criterion fechaFactura = Restrictions.between("fechaFacturacion", dates[0], dates[1]);
                Criterion fechaPactada = Restrictions.between("fechaPactadaPago", dates[0], dates[1]);
                criteria.add(Restrictions.or(fechaFactura, fechaPactada));
            }

            facturaVOs = criteria.list();
        } catch (Exception e) {
            facturaVOs = null;
        } finally {
            close();
            return facturaVOs;
        }
    }

    private double calcularValorTodasRtenciones(List<DetalleFacturaVO> detalleFacturaVOs) {
        double retorno = 0;
        try {
            for (DetalleFacturaVO detalleFacturaVO : detalleFacturaVOs) {
                Criteria criteria = getSession().createCriteria(FacturaRetencion.class);
                criteria.add(Restrictions.eq("detalleFactura", new DetalleFactura(detalleFacturaVO.getId())));
                List<FacturaRetencion> facturaRetencions = new ArrayList<FacturaRetencion>();
                facturaRetencions = criteria.list();
                for (FacturaRetencion facturaRetencion : facturaRetencions) {
                    retorno = retorno + facturaRetencion.getValor();
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return retorno;
        }
    }

    /*
     * Esta funcion permite calcular el 
     * - subtotal 
     * - iva 
     * - total
     * - Valor de las retenciones
     * - Total a pagar
     * -  asldo 
     *
     */
    private DatosFacturaVO calcularAllDatosFactura(int idFactura) {
        DatosFacturaVO datosFacturaVO = null;
        try {
            Criteria criteria = getSession().createCriteria(DetalleFactura.class);
            criteria.add(Restrictions.eq("factura", new Factura(idFactura)));
            List<DetalleFactura> detalleFacturas = criteria.list();
            double subtotal = 0;
            double retenciones = 0;
            for (DetalleFactura detalleFactura : detalleFacturas) {
                subtotal = subtotal + detalleFactura.getTotal();
                criteria = getSession().createCriteria(FacturaRetencion.class);
                criteria.add(Restrictions.eq("detalleFactura", detalleFactura));
                List<FacturaRetencion> facturaRetencions = criteria.list();
                for (FacturaRetencion facturaRetencion : facturaRetencions) {
                    retenciones = retenciones + facturaRetencion.getRetenciones().getValor();
                }
            }

            criteria = getSession().createCriteria(FacturaIva.class);
            criteria.add(Restrictions.eq("factura", new Factura(idFactura)));
            List<FacturaIva> facturaIvas = criteria.list();
            double valorIva = 0;
            if (facturaIvas.size() > 0) {
                short idIva = facturaIvas.get(0).getIva().getId();
                Criteria criteriaIva = getSession().createCriteria(Iva.class);
                criteriaIva.add(Restrictions.eq("id", idIva));
                Iva iva = (Iva) criteriaIva.uniqueResult();
                valorIva = subtotal * (iva.getPorcentaje() / 100);
            }
            double total = subtotal + valorIva;
            double totalPagar = total - retenciones;

            criteria = getSession().createCriteria(Abono.class);
            criteria.add(Restrictions.eq("factura", new Factura(idFactura)));
            List<Abono> abonos = criteria.list();
            double valorAbonos = 0;
            double saldo = totalPagar;
            for (Abono abono : abonos) {
                valorAbonos = valorAbonos + abono.getValor();
            }
            saldo = saldo - valorAbonos;
            datosFacturaVO = new DatosFacturaVO(subtotal, valorIva, total, retenciones, totalPagar, saldo);
        } catch (Exception e) {
            return null;
        } finally {
            return datosFacturaVO;
        }
    }
}
