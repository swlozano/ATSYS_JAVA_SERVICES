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
public class UsuarioSistemaRolVO {

    private int id;
    private Rol_VO rol;

    public UsuarioSistemaRolVO() {
        
    }

    public UsuarioSistemaRolVO(int id,  Rol_VO rol) {
        this.id = id;
        this.rol = rol;
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
     * @return the rol
     */
    public Rol_VO getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol_VO rol) {
        this.rol = rol;
    }

    /**
     * @return the rolx
     */
   
    

}
