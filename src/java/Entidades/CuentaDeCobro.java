package Entidades;
// Generated 10/12/2009 11:55:28 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.List;

/**
 * CuentaDeCobro generated by hbm2java
 */
public class CuentaDeCobro implements java.io.Serializable {

    private int id;
    private int idContrato;
    private int idCuentaBancaria;
    private int idFechaPago;
    private Date fechaSolicitud;
    private String concepto;
    private String observacion;
    private double valor;
    private short idUltimoEstado;
    private List<CuentaCobroEstado> cobroEstados;

    public CuentaDeCobro() {
    }

    public CuentaDeCobro(int id, int idContrato, int idCuentaBancaria, int idFechaPago, Date fechaSolicitud, String concepto, String observacion, double valor) {
        this.id = id;
        this.idContrato = idContrato;
        this.idCuentaBancaria = idCuentaBancaria;
        this.idFechaPago = idFechaPago;
        this.fechaSolicitud = fechaSolicitud;
        this.concepto = concepto;
        this.observacion = observacion;
        this.valor = valor;
    }

    public CuentaDeCobro(int idContrato, int idCuentaBancaria, int idFechaPago, Date fechaSolicitud, String concepto, String observacion, double valor) {
        this.idContrato = idContrato;
        this.idCuentaBancaria = idCuentaBancaria;
        this.idFechaPago = idFechaPago;
        this.fechaSolicitud = fechaSolicitud;
        this.concepto = concepto;
        this.observacion = observacion;
        this.valor = valor;
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

    public int getIdCuentaBancaria() {
        return this.idCuentaBancaria;
    }

    public void setIdCuentaBancaria(int idCuentaBancaria) {
        this.idCuentaBancaria = idCuentaBancaria;
    }

    public int getIdFechaPago() {
        return this.idFechaPago;
    }

    public void setIdFechaPago(int idFechaPago) {
        this.idFechaPago = idFechaPago;
    }

    public Date getFechaSolicitud() {
        return this.fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
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

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the cobroEstados
     */
    public List<CuentaCobroEstado> getCobroEstados() {
        return cobroEstados;
    }

    /**
     * @param cobroEstados the cobroEstados to set
     */
    public void setCobroEstados(List<CuentaCobroEstado> cobroEstados) {
        this.cobroEstados = cobroEstados;
    }

    /**
     * @return the idUltimoEstado
     */
    public short getIdUltimoEstado() {
        return idUltimoEstado;
    }

    /**
     * @param idUltimoEstado the idUltimoEstado to set
     */
    public void setIdUltimoEstado(short idUltimoEstado) {
        this.idUltimoEstado = idUltimoEstado;
    }
}


