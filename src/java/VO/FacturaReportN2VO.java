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
public class FacturaReportN2VO {
     //nivel 2 personas
    private PersonaVO personaVO;
    private double totalesAPagar;
    private double totalesPagados;
    private double totalesSubtotales;
    private double totalesNumeroVentas;
    private List<FacturaVO> facturaVOs;

    public FacturaReportN2VO(PersonaVO personaVO, double totalesAPagar, double totalesPagados, double totalesSubtotales, double totalesNumeroVentas, List<FacturaVO> facturaVOs) {
        this.personaVO = personaVO;
        this.totalesAPagar = totalesAPagar;
        this.totalesPagados = totalesPagados;
        this.totalesSubtotales = totalesSubtotales;
        this.totalesNumeroVentas = totalesNumeroVentas;
        this.facturaVOs = facturaVOs;
    }
    
    /**
     * @return the personaVO
     */
    public PersonaVO getPersonaVO() {
        return personaVO;
    }

    /**
     * @param personaVO the personaVO to set
     */
    public void setPersonaVO(PersonaVO personaVO) {
        this.personaVO = personaVO;
    }

    
    public List<FacturaVO> getFacturaVOs() {
        return facturaVOs;
    }

    /**
     * @param facturaVOs the facturaVOs to set
     */
    public void setFacturaVOs(List<FacturaVO> facturaVOs) {
        this.facturaVOs = facturaVOs;
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
}
