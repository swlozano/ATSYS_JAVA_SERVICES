/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

/**
 *
 * @author Alejandro
 */
public class CentroCostoVO {

     private int id;
     private String nombre;
     private String ciudad;

     public CentroCostoVO(int id,String nombre,String ciudad)
     {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
     }
     public CentroCostoVO(int id,String nombre)
     {
        this.id = id;
        this.nombre = nombre;
        
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
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

   

}
