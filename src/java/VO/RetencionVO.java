/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

/**
 *
 * @author Alejandro
 */
public class RetencionVO
{
     private int  id;
     private String nombre;
     private String porcentaje;

     public RetencionVO(){}
     public RetencionVO(int  id){
         this.id = id;
     }
     public RetencionVO(int  id,String nombre,String porcentaje)
     {
         this.id = id;
         this.nombre = nombre;
         this.porcentaje = porcentaje;
     }


    /**
     * @return the id
     */
   

    /**
     * @param id the id to set
  

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
     * @return the porcentaje
     */
    public String getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param porcentaje the porcentaje to set
     */
    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
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
}
