package Entidades;
// Generated 2/09/2009 03:44:00 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Rol generated by hbm2java
 */
public class Rol  implements java.io.Serializable {


     private short id;
     private String nombre;
     private Set usuarioSistemaRols = new HashSet(0);
     private Set rolPermisoses = new HashSet(0);

    public Rol() {
    }

	
    public Rol(short id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Rol(short id, String nombre, Set usuarioSistemaRols, Set rolPermisoses) {
       this.id = id;
       this.nombre = nombre;
       this.usuarioSistemaRols = usuarioSistemaRols;
       this.rolPermisoses = rolPermisoses;
    }

    public Rol(short id) {
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
    public Set getUsuarioSistemaRols() {
        return this.usuarioSistemaRols;
    }
    
    public void setUsuarioSistemaRols(Set usuarioSistemaRols) {
        this.usuarioSistemaRols = usuarioSistemaRols;
    }
    public Set getRolPermisoses() {
        return this.rolPermisoses;
    }
    
    public void setRolPermisoses(Set rolPermisoses) {
        this.rolPermisoses = rolPermisoses;
    }




}

