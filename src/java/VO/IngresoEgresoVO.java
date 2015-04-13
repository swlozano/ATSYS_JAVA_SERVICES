/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

/**
 *
 * @author Alejandro
 */
public class IngresoEgresoVO {

    private double  valorIngreso;
   private double  valorEgreso;

   public IngresoEgresoVO()
   {

   }

   public IngresoEgresoVO(double  valorIngreso,double  valorEgreso)
   {
        this.valorIngreso = valorIngreso;
        this.valorEgreso = valorEgreso;
   }
    /**
     * @return the valorIngreso
     */
    public double getValorIngreso() {
        return valorIngreso;
    }

    /**
     * @param valorIngreso the valorIngreso to set
     */
    public void setValorIngreso(double valorIngreso) {
        this.valorIngreso = valorIngreso;
    }

    /**
     * @return the valorEgreso
     */
    public double getValorEgreso() {
        return valorEgreso;
    }

    /**
     * @param valorEgreso the valorEgreso to set
     */
    public void setValorEgreso(double valorEgreso) {
        this.valorEgreso = valorEgreso;
    }

}
