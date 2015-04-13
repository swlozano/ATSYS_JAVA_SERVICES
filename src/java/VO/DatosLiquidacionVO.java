/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import java.util.Date;

/**
 *
 * @author Alejandro
 */
public class DatosLiquidacionVO {

    private double sueldoBasico;
    private double auxTransporte;
    private Date fechaTerminacion;

    public DatosLiquidacionVO(double sueldoBasico, double auxTransporte, Date fechaTerminacion) {
        this.sueldoBasico = sueldoBasico;
        this.auxTransporte = auxTransporte;
        this.fechaTerminacion = fechaTerminacion;
    }

    
    /**
     * @return the sueldoBasico
     */
    public double getSueldoBasico() {
        return sueldoBasico;
    }

    /**
     * @param sueldoBasico the sueldoBasico to set
     */
    public void setSueldoBasico(double sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    /**
     * @return the auxTransporte
     */
    public double getAuxTransporte() {
        return auxTransporte;
    }

    /**
     * @param auxTransporte the auxTransporte to set
     */
    public void setAuxTransporte(double auxTransporte) {
        this.auxTransporte = auxTransporte;
    }

    /**
     * @return the fechaTerminacion
     */
    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    /**
     * @param fechaTerminacion the fechaTerminacion to set
     */
    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }


}
