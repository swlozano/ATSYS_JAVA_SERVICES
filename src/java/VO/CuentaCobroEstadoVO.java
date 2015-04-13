/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import Entidades.Estado;
import java.util.Date;

/**
 *
 * @author Alejandro
 */
public class CuentaCobroEstadoVO {
    private int id;
    private Date fechaSolicitud;
    private String cobrador;
    private double valor;
    private String concepto;
    private String ciudad;
    private Date fechaInicio;
    private Date fechaFin;
    private String observasion;
    private Estado estado;

    public CuentaCobroEstadoVO(int id, Date fechaSolicitud, String cobrador, double valor, String concepto, String ciudad, Date fechaInicio, Date fechaFin, String observasion, Estado estado) {
        this.id = id;
        this.fechaSolicitud = fechaSolicitud;
        this.cobrador = cobrador;
        this.valor = valor;
        this.concepto = concepto;
        this.ciudad = ciudad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.observasion = observasion;
        this.estado = estado;
    }

    public CuentaCobroEstadoVO() {
        
    }




    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fechaSolicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud the fechaSolicitud to set
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the cobrador
     */
    public String getCobrador() {
        return cobrador;
    }

    /**
     * @param cobrador the cobrador to set
     */
    public void setCobrador(String cobrador) {
        this.cobrador = cobrador;
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
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the observasion
     */
    public String getObservasion() {
        return observasion;
    }

    /**
     * @param observasion the observasion to set
     */
    public void setObservasion(String observasion) {
        this.observasion = observasion;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
