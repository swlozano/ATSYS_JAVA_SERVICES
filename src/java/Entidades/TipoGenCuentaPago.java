package Entidades;
// Generated 13/01/2010 05:01:26 PM by Hibernate Tools 3.2.1.GA



/**
 * TipoGenCuentaPago generated by hbm2java
 */
public class TipoGenCuentaPago  implements java.io.Serializable {


     private short id;
     private String nombre;

    public TipoGenCuentaPago() {
    }

    public TipoGenCuentaPago(short id, String nombre) {
       this.id = id;
       this.nombre = nombre;
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




}


