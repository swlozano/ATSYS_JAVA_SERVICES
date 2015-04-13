package Entidades;
// Generated 19/10/2009 12:36:38 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Iva generated by hbm2java
 */
public class Iva  implements java.io.Serializable {


     private short id;
     private double porcentaje;
     private String nombre;
     private Set facturaIvas = new HashSet(0);

    public Iva() {
    }

    public Iva(short id) {
        this.id = id;
    }
	
    public Iva(short id, double porcentaje, String nombre) {
        this.id = id;
        this.porcentaje = porcentaje;
        this.nombre = nombre;
    }
    public Iva(short id, double porcentaje, String nombre, Set facturaIvas) {
       this.id = id;
       this.porcentaje = porcentaje;
       this.nombre = nombre;
       this.facturaIvas = facturaIvas;
    }
   
    public short getId() {
        return this.id;
    }
    
    public void setId(short id) {
        this.id = id;
    }
    public double getPorcentaje() {
        return this.porcentaje;
    }
    
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set getFacturaIvas() {
        return this.facturaIvas;
    }
    
    public void setFacturaIvas(Set facturaIvas) {
        this.facturaIvas = facturaIvas;
    }




}


