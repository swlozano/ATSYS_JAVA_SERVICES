/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import java.util.Date;

/**
 *
 * @author Alejandro
 */
public class CuentaPorPagarVO {
    private Date fechaOrigen;
    private String nombre;
    private String concepto;
    private String banco;
    private String tipoCuenta;
    private String numeroCuenta;
    private double valor;
    private Date fechaPago;
    private String estadoCuenta;
    private String generadorDeCuenta;

    private int idRhPersona;
    private int idBanco;
    private short idTipoCuenta;
    private short  idEstadoCuenta;
    private short idGeneradorDeCuenta;
    private int idCuentaBanco;

    private int idCuentaPorPagar;

    /**
     * @return the fechaOrigen
     */
    public Date getFechaOrigen() {
        return fechaOrigen;
    }

    /**
     * @param fechaOrigen the fechaOrigen to set
     */
    public void setFechaOrigen(Date fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * @return the tipoCuenta
     */
    public String getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @param tipoCuenta the tipoCuenta to set
     */
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the fechaPago
     */
    public Date getFechaPago() {
        return fechaPago;
    }

    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * @return the estadoCuenta
     */
    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    /**
     * @param estadoCuenta the estadoCuenta to set
     */
    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    /**
     * @return the generadorDeCuenta
     */
    public String getGeneradorDeCuenta() {
        return generadorDeCuenta;
    }

    /**
     * @param generadorDeCuenta the generadorDeCuenta to set
     */
    public void setGeneradorDeCuenta(String generadorDeCuenta) {
        this.generadorDeCuenta = generadorDeCuenta;
    }

    /**
     * @return the idRhPersona
     */
    public int getIdRhPersona() {
        return idRhPersona;
    }

    /**
     * @param idRhPersona the idRhPersona to set
     */
    public void setIdRhPersona(int idRhPersona) {
        this.idRhPersona = idRhPersona;
    }

    /**
     * @return the idBanco
     */
    public int getIdBanco() {
        return idBanco;
    }

    /**
     * @param idBanco the idBanco to set
     */
    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    /**
     * @return the idTipoCuenta
     */
    public short getIdTipoCuenta() {
        return idTipoCuenta;
    }

    /**
     * @param idTipoCuenta the idTipoCuenta to set
     */
    public void setIdTipoCuenta(short idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    /**
     * @return the idEstadoCuenta
     */
    public short getIdEstadoCuenta() {
        return idEstadoCuenta;
    }

    /**
     * @param idEstadoCuenta the idEstadoCuenta to set
     */
    public void setIdEstadoCuenta(short idEstadoCuenta) {
        this.idEstadoCuenta = idEstadoCuenta;
    }

    /**
     * @return the idGeneradorDeCuenta
     */
    public short getIdGeneradorDeCuenta() {
        return idGeneradorDeCuenta;
    }

    /**
     * @param idGeneradorDeCuenta the idGeneradorDeCuenta to set
     */
    public void setIdGeneradorDeCuenta(short idGeneradorDeCuenta) {
        this.idGeneradorDeCuenta = idGeneradorDeCuenta;
    }

    /**
     * @return the idCuentaBanco
     */
    public int getIdCuentaBanco() {
        return idCuentaBanco;
    }

    /**
     * @param idCuentaBanco the idCuentaBanco to set
     */
    public void setIdCuentaBanco(int idCuentaBanco) {
        this.idCuentaBanco = idCuentaBanco;
    }

    /**
     * @return the idCuentaPorPagar
     */
    public int getIdCuentaPorPagar() {
        return idCuentaPorPagar;
    }

    /**
     * @param idCuentaPorPagar the idCuentaPorPagar to set
     */
    public void setIdCuentaPorPagar(int idCuentaPorPagar) {
        this.idCuentaPorPagar = idCuentaPorPagar;
    }
    
    
}
