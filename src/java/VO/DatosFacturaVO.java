/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

/**
 *
 * @author Alejandro
 */
public class DatosFacturaVO {
    private double subtotal;
    private double iva;
    private double total;
    private double retenciones;
    private double totalPagar;
    private double saldo;

    public DatosFacturaVO(double subtotal, double iva, double total, double retenciones, double totalPagar, double saldo) {
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.retenciones = retenciones;
        this.totalPagar = totalPagar;
        this.saldo = saldo;
    }

    public DatosFacturaVO() {
        
    }


    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the iva
     */
    public double getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(double iva) {
        this.iva = iva;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the retenciones
     */
    public double getRetenciones() {
        return retenciones;
    }

    /**
     * @param retenciones the retenciones to set
     */
    public void setRetenciones(double retenciones) {
        this.retenciones = retenciones;
    }

    /**
     * @return the totalPagar
     */
    public double getTotalPagar() {
        return totalPagar;
    }

    /**
     * @param totalPagar the totalPagar to set
     */
    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    /**
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

}
