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
public class FacturaReportN1VO {

    //Nivel 1 totales
    private double totalesAPagar;
    private double totalesPagados;
    private double totalesSubtotales;
    private double totalesNumeroVentas;
    private Date fechaInicio;
    private Date fechaFin;
    private List<FacturaReportN2VO> facturaReportN2VOs;

    public FacturaReportN1VO() {
    }

    public FacturaReportN1VO(double totalesAPagar, double totalesPagados, double totalesSubtotales, double totalesNumeroVentas) {
        this.totalesAPagar = totalesAPagar;
        this.totalesPagados = totalesPagados;
        this.totalesSubtotales = totalesSubtotales;
        this.totalesNumeroVentas = totalesNumeroVentas;
    }

    

    /**
     * @return the totalesAPagar
     */
    public double getTotalesAPagar() {
        return totalesAPagar;
    }

    /**
     * @param totalesAPagar the totalesAPagar to set
     */
    public void setTotalesAPagar(double totalesAPagar) {
        this.totalesAPagar = totalesAPagar;
    }

    /**
     * @return the totalesPagados
     */
    public double getTotalesPagados() {
        return totalesPagados;
    }

    /**
     * @param totalesPagados the totalesPagados to set
     */
    public void setTotalesPagados(double totalesPagados) {
        this.totalesPagados = totalesPagados;
    }

    /**
     * @return the totalesSubtotales
     */
    public double getTotalesSubtotales() {
        return totalesSubtotales;
    }

    /**
     * @param totalesSubtotales the totalesSubtotales to set
     */
    public void setTotalesSubtotales(double totalesSubtotales) {
        this.totalesSubtotales = totalesSubtotales;
    }

    /**
     * @return the totalesNumeroVentas
     */
    public double getTotalesNumeroVentas() {
        return totalesNumeroVentas;
    }

    /**
     * @param totalesNumeroVentas the totalesNumeroVentas to set
     */
    public void setTotalesNumeroVentas(double totalesNumeroVentas) {
        this.totalesNumeroVentas = totalesNumeroVentas;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the facturaReportN2VOs
     */
    public List<FacturaReportN2VO> getFacturaReportN2VOs() {
        return facturaReportN2VOs;
    }

    /**
     * @param facturaReportN2VOs the facturaReportN2VOs to set
     */
    public void setFacturaReportN2VOs(List<FacturaReportN2VO> facturaReportN2VOs) {
        this.facturaReportN2VOs = facturaReportN2VOs;
    }
}
