package Entidades;
// Generated 2/09/2009 03:44:00 PM by Hibernate Tools 3.2.1.GA



/**
 * UsuarioSistemaRol generated by hbm2java
 */
public class UsuarioSistemaRol  implements java.io.Serializable {


     private int id;
     private UsuarioSys usuarioSys;
     private Rol rol;

    public UsuarioSistemaRol() {
    }

    public UsuarioSistemaRol(int id, UsuarioSys usuarioSys, Rol rol) {
       this.id = id;
       this.usuarioSys = usuarioSys;
       this.rol = rol;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public UsuarioSys getUsuarioSys() {
        return this.usuarioSys;
    }
    
    public void setUsuarioSys(UsuarioSys usuarioSys) {
        this.usuarioSys = usuarioSys;
    }
    public Rol getRol() {
        return this.rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }




}


