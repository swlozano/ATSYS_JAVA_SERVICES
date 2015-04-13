/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

/**
 *
 * @author Alejandro
 */
public class ProvisionesVO {
    private String nombre;
    private String observacion;
    private double valor;

    public ProvisionesVO() {
    }


    public ProvisionesVO(String nombre, String observacion, double valor) {
        this.nombre = nombre;
        this.observacion = observacion;
        this.valor = valor;
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
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }



}
