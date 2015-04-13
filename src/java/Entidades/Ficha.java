package Entidades;
// Generated 25/11/2009 03:10:15 PM by Hibernate Tools 3.2.1.GA

import VO.DatoDinamicoVO;
import java.util.Date;

/**
 * Ficha generated by hbm2java
 */
public class Ficha implements java.io.Serializable {

    private int id;
    private int idContrato;
    private short idTipoFicha;
    private String concepto;
    private String observacion;
    private Double valorPrestamo;
    private Date fechaDelPago;
    private String texto;
    private Date fechaInicio;
    private Date fechaFin;
    private Short estado;
    private Short tipoPrestamo;
    private Short llamadoAtencion;
    private Short buenComportamiento;
    private DatoDinamicoVO tipoFichaVO;
    private DatoDinamicoVO estadoVO;
    

    public Ficha() {
    }

    public Ficha(int idContrato, short idTipoFicha, String concepto, String observacion,
            String texto, short llamadoAtencion, short buenComportamiento) {
        this.idContrato = idContrato;
        this.idTipoFicha = idTipoFicha;
        this.concepto = concepto;
        this.observacion = observacion;
        this.texto = texto;
        this.llamadoAtencion = llamadoAtencion;
        this.buenComportamiento = buenComportamiento;
    }
    //(int idContrato, short idTipoFicha, String concepto, String observacion, Double valorPrestamo,
    //        Calendar fechaDelPago, String texto, short tipoPrestamo

    public Ficha(int idContrato, short idTipoFicha, String concepto, String observacion, String texto, Date fechaInicio, Date fechaFin) {
        this.idContrato = idContrato;
        this.idTipoFicha = idTipoFicha;
        this.concepto = concepto;
        this.observacion = observacion;
        this.texto = texto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Ficha(int idContrato, short idTipoFicha, String concepto, String observacion, Double valorPrestamo, Date fechaDelPago, String texto,
            Short tipoPrestamo) {

        this.idContrato = idContrato;
        this.idTipoFicha = idTipoFicha;
        this.concepto = concepto;
        this.observacion = observacion;
        this.valorPrestamo = valorPrestamo;
        this.fechaDelPago = fechaDelPago;
        this.texto = texto;
        this.tipoPrestamo = tipoPrestamo;

    }

    public Ficha(int id, int idContrato, short idTipoFicha, String concepto, String observacion) {
        this.id = id;
        this.idContrato = idContrato;
        this.idTipoFicha = idTipoFicha;
        this.concepto = concepto;
        this.observacion = observacion;
    }

    public Ficha(int id, int idContrato, short idTipoFicha, String concepto, String observacion, Double valorPrestamo, Date fechaDelPago, String texto, Date fechaInicio, Date fechaFin, Short estado, Short tipoPrestamo, Short llamadoAtencion, Short buenComportamiento) {
        this.id = id;
        this.idContrato = idContrato;
        this.idTipoFicha = idTipoFicha;
        this.concepto = concepto;
        this.observacion = observacion;
        this.valorPrestamo = valorPrestamo;
        this.fechaDelPago = fechaDelPago;
        this.texto = texto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.tipoPrestamo = tipoPrestamo;
        this.llamadoAtencion = llamadoAtencion;
        this.buenComportamiento = buenComportamiento;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdContrato() {
        return this.idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public short getIdTipoFicha() {
        return this.idTipoFicha;
    }

    public void setIdTipoFicha(short idTipoFicha) {
        this.idTipoFicha = idTipoFicha;
    }

    public String getConcepto() {
        return this.concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getValorPrestamo() {
        return this.valorPrestamo;
    }

    public void setValorPrestamo(Double valorPrestamo) {
        this.valorPrestamo = valorPrestamo;
    }

    public Date getFechaDelPago() {
        return this.fechaDelPago;
    }

    public void setFechaDelPago(Date fechaDelPago) {
        this.fechaDelPago = fechaDelPago;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Short getEstado() {
        return this.estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Short getTipoPrestamo() {
        return this.tipoPrestamo;
    }

    public void setTipoPrestamo(Short tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public Short getLlamadoAtencion() {
        return this.llamadoAtencion;
    }

    public void setLlamadoAtencion(Short llamadoAtencion) {
        this.llamadoAtencion = llamadoAtencion;
    }

    public Short getBuenComportamiento() {
        return this.buenComportamiento;
    }

    public void setBuenComportamiento(Short buenComportamiento) {
        this.buenComportamiento = buenComportamiento;
    }

    /**
     * @return the tipoFichaVO
     */
    public DatoDinamicoVO getTipoFichaVO() {
        return tipoFichaVO;
    }

    /**
     * @param tipoFichaVO the tipoFichaVO to set
     */
    public void setTipoFichaVO(DatoDinamicoVO tipoFichaVO) {
        this.tipoFichaVO = tipoFichaVO;
    }

    /**
     * @return the estadoVO
     */
    public DatoDinamicoVO getEstadoVO() {
        return estadoVO;
    }

    /**
     * @param estadoVO the estadoVO to set
     */
    public void setEstadoVO(DatoDinamicoVO estadoVO) {
        this.estadoVO = estadoVO;
    }
}


