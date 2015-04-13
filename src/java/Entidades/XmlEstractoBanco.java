package Entidades;
// Generated 2/09/2009 03:44:00 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * XmlEstractoBanco generated by hbm2java
 */
public class XmlEstractoBanco  implements java.io.Serializable {


     private int id;
     private Date fecha;
     private String descripcion;
     private String sucursal;
     private Double dcto;
     private Double valor;
     private Short saldo;
     private Set movimientoses = new HashSet(0);

    public XmlEstractoBanco() {
    }

	
    public XmlEstractoBanco(int id) {
        this.id = id;
    }
    public XmlEstractoBanco(int id, Date fecha, String descripcion, String sucursal, Double dcto, Double valor, Short saldo, Set movimientoses) {
       this.id = id;
       this.fecha = fecha;
       this.descripcion = descripcion;
       this.sucursal = sucursal;
       this.dcto = dcto;
       this.valor = valor;
       this.saldo = saldo;
       this.movimientoses = movimientoses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getSucursal() {
        return this.sucursal;
    }
    
    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    public Double getDcto() {
        return this.dcto;
    }
    
    public void setDcto(Double dcto) {
        this.dcto = dcto;
    }
    public Double getValor() {
        return this.valor;
    }
    
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Short getSaldo() {
        return this.saldo;
    }
    
    public void setSaldo(Short saldo) {
        this.saldo = saldo;
    }
    public Set getMovimientoses() {
        return this.movimientoses;
    }
    
    public void setMovimientoses(Set movimientoses) {
        this.movimientoses = movimientoses;
    }




}

