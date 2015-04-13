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
public class RolPermisosesVO
{
    private int id;
    private PermisoVO permisos;

    public RolPermisosesVO(int id, PermisoVO permisoVO) {
        this.id = id;
        this.permisos = permisoVO;
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
     * @return the permisos
     */
    public PermisoVO getPermisos() {
        return permisos;
    }

    /**
     * @param permisos the permisos to set
     */
    public void setPermisos(PermisoVO permisos) {
        this.permisos = permisos;
    }

   
}
