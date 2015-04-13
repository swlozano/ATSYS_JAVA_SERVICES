/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import java.util.List;

/**
 *
 * @author Alejandro
 */
public class UsuarioSistemaVO {

     private int id;
     private String nombre;
     private String apellido;
     private int cedula;
     private String login;
     private String contrasena;
     private String correoElectronico;
     private short isAdmin;
     private List<UsuarioSistemaRolVO> usuarioSistemaRols;

    public UsuarioSistemaVO(int id,String nombre,short isAdmin,String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.isAdmin = isAdmin;
        this.apellido = apellido;
    }

    public UsuarioSistemaVO() {

    }

     public UsuarioSistemaVO(int id,String nombre,String apellido,int cedula,String login,String contrasena,String correoElectronico,List<UsuarioSistemaRolVO> usuarioSistemaRols,short isAdmin)
     {
        this.apellido = apellido;
        this.cedula = cedula;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.id = id;
        this.login = login;
        this.nombre = nombre;
        this.usuarioSistemaRols = usuarioSistemaRols;
        this.isAdmin = isAdmin;
     }             /**
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
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the cedula
     */
    public int getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the usuarioSistemaRols
     */
    public List<UsuarioSistemaRolVO> getUsuarioSistemaRols() {
        return usuarioSistemaRols;
    }

    /**
     * @param usuarioSistemaRols the usuarioSistemaRols to set
     */
    public void setUsuarioSistemaRols(List<UsuarioSistemaRolVO> usuarioSistemaRols) {
        this.usuarioSistemaRols = usuarioSistemaRols;
    }

    /**
     * @return the isAdmin
     */
    public short getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(short isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the usuarioSistemaRols
     */
 
     
     

}
