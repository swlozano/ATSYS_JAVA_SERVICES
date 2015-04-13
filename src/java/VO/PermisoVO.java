/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

/**
 *
 * @author Alejandro
 */
public class PermisoVO {

    private short id;
    private String nombre;

    public PermisoVO() {
        
    }

    /**
     * @return the id
     */

    public PermisoVO(short id,String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public short getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(short id) {
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

}
