/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class FacturaVO {

    private int id;
    private PersonaVO personaVO;
    private Date fechaFacturacion;
    private Date fechaPactadaPago;
    private double subtutal;
    private double total;
    private double saldo;
    private List<DetalleFacturaVO> detalleFacturaVOs;
    private List<FacturaIvaVO> facturaIvaVOs;
    private double valorIva;
    private double porcentajeIva;
    private double valorRetenciones;
    private double totalApagar;
    private String numeroFactura;
    private String estadoFactura;
    /**
     * @return the id
     */
    public FacturaVO() {
    }

    public FacturaVO(int id,
            PersonaVO persona,
            Date fechaFacturacion,
            Date fechaPactadaPago,
            double subtutal,
            double total,
            double saldo) {
        this.id = id;
        this.personaVO = persona;
        this.fechaFacturacion = fechaFacturacion;
        this.fechaPactadaPago = fechaPactadaPago;
        this.subtutal = subtutal;
        this.total = total;
        this.saldo = saldo;
    }

    public FacturaVO(int id,
            PersonaVO persona,
            Date fechaFacturacion,
            Date fechaPactadaPago,
            double subtutal,
            double total,
            double saldo, String numeroFactura,String estadoFactura) {
        this.id = id;
        this.personaVO = persona;
        this.fechaFacturacion = fechaFacturacion;
        this.fechaPactadaPago = fechaPactadaPago;
        this.subtutal = subtutal;
        this.total = total;
        this.saldo = saldo;
        this.numeroFactura = numeroFactura;
        this.estadoFactura = estadoFactura;
    }

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
     * @return the persona
     */
    public PersonaVO getPersona() {
        return personaVO;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonaVO persona) {
        this.personaVO = persona;
    }

    /**
     * @return the fechaFacturacion
     */
    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    /**
     * @param fechaFacturacion the fechaFacturacion to set
     */
    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    /**
     * @return the fechaPactadaPago
     */
    public Date getFechaPactadaPago() {
        return fechaPactadaPago;
    }

    /**
     * @param fechaPactadaPago the fechaPactadaPago to set
     */
    public void setFechaPactadaPago(Date fechaPactadaPago) {
        this.fechaPactadaPago = fechaPactadaPago;
    }

    /**
     * @return the subtutal
     */
    public double getSubtutal() {
        return subtutal;
    }

    /**
     * @param subtutal the subtutal to set
     */
    public void setSubtutal(double subtutal) {
        this.subtutal = subtutal;
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

    /**
     * @return the detalleFacturaVOs
     */
    public List<DetalleFacturaVO> getDetalleFacturaVOs() {
        return detalleFacturaVOs;
    }

    /**
     * @param detalleFacturaVOs the detalleFacturaVOs to set
     */
    public void setDetalleFacturaVOs(List<DetalleFacturaVO> detalleFacturaVOs) {
        this.detalleFacturaVOs = detalleFacturaVOs;
    }

    /**
     * @return the facturaIvaVOs
     */
    public List<FacturaIvaVO> getFacturaIvaVOs() {
        return facturaIvaVOs;
    }

    /**
     * @param facturaIvaVOs the facturaIvaVOs to set
     */
    public void setFacturaIvaVOs(List<FacturaIvaVO> facturaIvaVOs) {
        this.facturaIvaVOs = facturaIvaVOs;
    }

    /**
     * @return the valorIva
     */
    public double getValorIva() {
        return valorIva;
    }

    /**
     * @param valorIva the valorIva to set
     */
    public void setValorIva(double valorIva) {
        this.valorIva = valorIva;
    }

    /**
     * @return the porcentajeIva
     */
    public double getPorcentajeIva() {
        return porcentajeIva;
    }

    /**
     * @param porcentajeIva the porcentajeIva to set
     */
    public void setPorcentajeIva(double porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    /**
     * @return the valorRetenciones
     */
    public double getValorRetenciones() {
        return valorRetenciones;
    }

    /**
     * @param valorRetenciones the valorRetenciones to set
     */
    public void setValorRetenciones(double valorRetenciones) {
        this.valorRetenciones = valorRetenciones;
    }

    /**
     * @return the totalApagar
     */
    public double getTotalApagar() {
        return totalApagar;
    }

    /**
     * @param totalApagar the totalApagar to set
     */
    public void setTotalApagar(double totalApagar) {
        this.totalApagar = totalApagar;
    }

    /**
     * @return the numeroFactura
     */
    public String getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * @param numeroFactura the numeroFactura to set
     */
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    /**
     * @return the estadoFactura
     */
    public String getEstadoFactura() {
        return estadoFactura;
    }

    /**
     * @param estadoFactura the estadoFactura to set
     */
    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    /**
     * @return the valorTodasRetenciones
     */
    
}
