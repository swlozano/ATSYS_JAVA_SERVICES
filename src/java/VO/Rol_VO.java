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
public class Rol_VO {
    private int id;
    private String nombre;
    private List<RolPermisosesVO> rolPermisoses;
    
    

    /**
     * @return the id
     */

    public Rol_VO(int id,String nom,List<RolPermisosesVO> rpvos)
    {
        this.id = id;
        this.nombre = nom;
        this.rolPermisoses = rpvos;
    }

    public Rol_VO()
    {}

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
     * @return the rolPermisoses
     */
    public List<RolPermisosesVO> getRolPermisoses() {
        return rolPermisoses;
    }

    /**
     * @param rolPermisoses the rolPermisoses to set
     */
    public void setRolPermisoses(List<RolPermisosesVO> rolPermisoses) {
        this.rolPermisoses = rolPermisoses;
    }

   
    

}
