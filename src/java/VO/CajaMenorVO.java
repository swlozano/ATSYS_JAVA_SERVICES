/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class CajaMenorVO {

    private int id;
    private String nombre;
    private double valor;
    private Date fechaCreacion;
    private double montoInicio;
    private double valorMinimoNotificacion;
    private double montoMinimoReintegro;
    private short esCuenta;
    private List<NotificacionCajaMenorsVO> notificacionCajaMenors;
    
    public CajaMenorVO(int id, String nombre, double valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }
    public CajaMenorVO(int id, String nombre, double valor,short esCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.esCuenta = esCuenta;
    }

    public CajaMenorVO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        
    }

    /**
     * @return the id
     */

    public CajaMenorVO(int id,String nombre,double valor,Date fechaCreacion,double montoInicio,double valorMinimoNotificacion,double montoMinimoReintegro,List<NotificacionCajaMenorsVO> notificacionCajaMenors,short esCuenta)
    {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.fechaCreacion = fechaCreacion;
        this.montoInicio = montoInicio;
        this.montoMinimoReintegro = montoMinimoReintegro;
        this.notificacionCajaMenors = notificacionCajaMenors;
        this.esCuenta = esCuenta;
        this.valorMinimoNotificacion = valorMinimoNotificacion;
    }

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
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the montoInicio
     */
    public double getMontoInicio() {
        return montoInicio;
    }

    /**
     * @param montoInicio the montoInicio to set
     */
    public void setMontoInicio(double montoInicio) {
        this.montoInicio = montoInicio;
    }

    /**
     * @return the valorMinimoNotificacion
     */
    public double getValorMinimoNotificacion() {
        return valorMinimoNotificacion;
    }

    /**
     * @param valorMinimoNotificacion the valorMinimoNotificacion to set
     */
    public void setValorMinimoNotificacion(double valorMinimoNotificacion) {
        this.valorMinimoNotificacion = valorMinimoNotificacion;
    }

    /**
     * @return the montoMinimoReintegro
     */
    public double getMontoMinimoReintegro() {
        return montoMinimoReintegro;
    }

    /**
     * @param montoMinimoReintegro the montoMinimoReintegro to set
     */
    public void setMontoMinimoReintegro(double montoMinimoReintegro) {
        this.montoMinimoReintegro = montoMinimoReintegro;
    }

    /**
     * @return the tieneApertura
     */
   

    /**
     * @return the notificacionCajaMenors
     */
    public List<NotificacionCajaMenorsVO> getNotificacionCajaMenors() {
        return notificacionCajaMenors;
    }

    /**
     * @param notificacionCajaMenors the notificacionCajaMenors to set
     */
    public void setNotificacionCajaMenors(List<NotificacionCajaMenorsVO> notificacionCajaMenors) {
        this.notificacionCajaMenors = notificacionCajaMenors;
    }

    /**
     * @return the esCuenta
     */
    public short getEsCuenta() {
        return esCuenta;
    }

    /**
     * @param esCuenta the esCuenta to set
     */
    public void setEsCuenta(short esCuenta) {
        this.esCuenta = esCuenta;
    }


}
