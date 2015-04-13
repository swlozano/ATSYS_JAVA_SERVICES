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
public class DetalleFacturaFx {

    private int id;
    private short cantidad;
    private String descripcionBienServicio;
    private double precio;
    //private double subtotal;
    private double total;
    private List<ValorIdVO> valorIdVOs;

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
     * @return the cantidad
     */
    public short getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(short cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the descripcionBienServicio
     */
    public String getDescripcionBienServicio() {
        return descripcionBienServicio;
    }

    /**
     * @param descripcionBienServicio the descripcionBienServicio to set
     */
    public void setDescripcionBienServicio(String descripcionBienServicio) {
        this.descripcionBienServicio = descripcionBienServicio;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the subtotal
     */
   /*public double getSubtotal() {
        return subtotal;
    }*/

    /**
     * @param subtotal the subtotal to set
     */
    /*public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }*/

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
     * @return the valorIdVOs
     */
    public List<ValorIdVO> getValorIdVOs() {
        return valorIdVOs;
    }

    /**
     * @param valorIdVOs the valorIdVOs to set
     */
    public void setValorIdVOs(List<ValorIdVO> valorIdVOs) {
        this.valorIdVOs = valorIdVOs;
    }
}
