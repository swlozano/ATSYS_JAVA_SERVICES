package Entidades;
// Generated 2/09/2009 03:44:00 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CajaMenor generated by hbm2java
 */
public class CajaMenor  implements java.io.Serializable {


     private int id;
     private String nombre;
     private Date fechaCreacion;
     private double montoInicio;
     private double valorMinimoNotificacion;
     private double montoMinimoReintegro;
     private double valor;
     private short esCuenta;
     private Set notificacionCajaMenors = new HashSet(0);
     private Set movimientoses = new HashSet(0);

    public CajaMenor() {
    }

    public CajaMenor(int id) {
        this.id =  id;
    }

    public CajaMenor(Object object) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
	
    public CajaMenor(int id, String nombre, Date fechaCreacion, double montoInicio, double valorMinimoNotificacion, double montoMinimoReintegro, double valor, short esCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.montoInicio = montoInicio;
        this.valorMinimoNotificacion = valorMinimoNotificacion;
        this.montoMinimoReintegro = montoMinimoReintegro;
        this.valor = valor;
        this.esCuenta = esCuenta;
    }
    public CajaMenor(int id, String nombre, Date fechaCreacion, double montoInicio, double valorMinimoNotificacion, double montoMinimoReintegro, double valor, short esCuenta, Set notificacionCajaMenors, Set movimientoses) {
       this.id = id;
       this.nombre = nombre;
       this.fechaCreacion = fechaCreacion;
       this.montoInicio = montoInicio;
       this.valorMinimoNotificacion = valorMinimoNotificacion;
       this.montoMinimoReintegro = montoMinimoReintegro;
       this.valor = valor;
       this.esCuenta = esCuenta;
       this.notificacionCajaMenors = notificacionCajaMenors;
       this.movimientoses = movimientoses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public double getMontoInicio() {
        return this.montoInicio;
    }
    
    public void setMontoInicio(double montoInicio) {
        this.montoInicio = montoInicio;
    }
    public double getValorMinimoNotificacion() {
        return this.valorMinimoNotificacion;
    }
    
    public void setValorMinimoNotificacion(double valorMinimoNotificacion) {
        this.valorMinimoNotificacion = valorMinimoNotificacion;
    }
    public double getMontoMinimoReintegro() {
        return this.montoMinimoReintegro;
    }
    
    public void setMontoMinimoReintegro(double montoMinimoReintegro) {
        this.montoMinimoReintegro = montoMinimoReintegro;
    }
    public double getValor() {
        return this.valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public Set getNotificacionCajaMenors() {
        return this.notificacionCajaMenors;
    }
    
    public void setNotificacionCajaMenors(Set notificacionCajaMenors) {
        this.notificacionCajaMenors = notificacionCajaMenors;
    }
    public Set getMovimientoses() {
        return this.movimientoses;
    }
    
    public void setMovimientoses(Set movimientoses) {
        this.movimientoses = movimientoses;
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


