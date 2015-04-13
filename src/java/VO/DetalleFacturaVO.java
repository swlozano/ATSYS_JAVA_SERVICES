/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import Entidades.Retenciones;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class DetalleFacturaVO {

     private int id;
     private short cantidad;
     private String descripcionBienServicio;
     private double precio;
     private double subtotal;
     private double total;
     private List<FacturaRetencionVO> facturaRetencionVOs;
     private List<RetencionVO> retencionesVOs;

     public DetalleFacturaVO(short cantidad,String descripcionBienServicio,double precio,List<RetencionVO> retencionesVOs)
     {
        this.cantidad = cantidad;
        this.descripcionBienServicio = descripcionBienServicio;
        this.precio = precio;
        this.retencionesVOs = retencionesVOs;
     }
     public DetalleFacturaVO()
     {
        
     }
     public DetalleFacturaVO(int id, short cantidad,String descripcionBienServicio,double precio,double subtotal,double total)
     {
        this.id = id;
        this.cantidad = cantidad;
        this.descripcionBienServicio = descripcionBienServicio;
        this.precio = precio;
        this.subtotal = subtotal;
        this.total = total;
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
     * @return the facturaVO
     */
   
    /**
     * @return the idFactura
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
     * @return the facturaRetencionVOs
     */
    public List<FacturaRetencionVO> getFacturaRetencionVOs() {
        return facturaRetencionVOs;
    }

    /**
     * @param facturaRetencionVOs the facturaRetencionVOs to set
     */
    public void setFacturaRetencionVOs(List<FacturaRetencionVO> facturaRetencionVOs) {
        this.facturaRetencionVOs = facturaRetencionVOs;
    }

    /**
     * @return the retencionesVOs
     */
    public List<RetencionVO> getRetencionesVOs() {
        return retencionesVOs;
    }

    /**
     * @param retencionesVOs the retencionesVOs to set
     */
    public void setRetencionesVOs(List<RetencionVO> retencionesVOs) {
        this.retencionesVOs = retencionesVOs;
    }

     /**
     * @param retencionesVOs the retencionesVOs to set
     */
    
}
