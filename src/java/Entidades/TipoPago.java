package Entidades;
// Generated 4/11/2009 10:54:35 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * TipoPago generated by hbm2java
 */
public class TipoPago  implements java.io.Serializable {


     private short id;
     private String nombre;
     private Set valorFormaPagos = new HashSet(0);

    public TipoPago() {
    }

	
    public TipoPago(short id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public TipoPago(short id, String nombre, Set valorFormaPagos) {
       this.id = id;
       this.nombre = nombre;
       this.valorFormaPagos = valorFormaPagos;
    }

    public TipoPago(short id) {
        this.id = id;
    }
   
    public short getId() {
        return this.id;
    }
    
    public void setId(short id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set getValorFormaPagos() {
        return this.valorFormaPagos;
    }
    
    public void setValorFormaPagos(Set valorFormaPagos) {
        this.valorFormaPagos = valorFormaPagos;
    }




}


