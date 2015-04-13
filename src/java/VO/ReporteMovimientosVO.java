/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import Entidades.Persona;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class ReporteMovimientosVO {
    private List<MovimientoVO> movimientoVOs;
    private IngresoEgresoVO ingresoEgresoVO;
    private CajaMenorVO cajaMenorVO;
    private CentroCostoVO centroCostoVO;
    private Persona persona;
    

    public ReporteMovimientosVO()
    {}

    public ReporteMovimientosVO(List<MovimientoVO> movimientoVOs,IngresoEgresoVO ingresoEgresoVO,Persona persona)
    {
        this.ingresoEgresoVO = ingresoEgresoVO;
        this.movimientoVOs = movimientoVOs;
        this.persona = persona;
    }

    public ReporteMovimientosVO(List<MovimientoVO> movimientoVOs,IngresoEgresoVO ingresoEgresoVO,CentroCostoVO centroCostoVO)
    {
        this.ingresoEgresoVO = ingresoEgresoVO;
        this.movimientoVOs = movimientoVOs;
        this.centroCostoVO = centroCostoVO;
    }

    public ReporteMovimientosVO(List<MovimientoVO> movimientoVOs,IngresoEgresoVO ingresoEgresoVO,CajaMenorVO cajaMenorVO)
    {
        this.ingresoEgresoVO = ingresoEgresoVO;
        this.movimientoVOs = movimientoVOs;
        this.cajaMenorVO = cajaMenorVO;
    }
    /**
     * @return the movimientoVOs
     */
    public List<MovimientoVO> getMovimientoVOs() {
        return movimientoVOs;
    }

    /**
     * @param movimientoVOs the movimientoVOs to set
     */
    public void setMovimientoVOs(List<MovimientoVO> movimientoVOs) {
        this.movimientoVOs = movimientoVOs;
    }

    /**
     * @return the ingresoEgresoVO
     */
    public IngresoEgresoVO getIngresoEgresoVO() {
        return ingresoEgresoVO;
    }

    /**
     * @param ingresoEgresoVO the ingresoEgresoVO to set
     */
    public void setIngresoEgresoVO(IngresoEgresoVO ingresoEgresoVO) {
        this.ingresoEgresoVO = ingresoEgresoVO;
    }

    /**
     * @return the cajaMenorVO
     */
    public CajaMenorVO getCajaMenorVO() {
        return cajaMenorVO;
    }

    /**
     * @param cajaMenorVO the cajaMenorVO to set
     */
    public void setCajaMenorVO(CajaMenorVO cajaMenorVO) {
        this.cajaMenorVO = cajaMenorVO;
    }

    /**
     * @return the centroCostoVO
     */
    public CentroCostoVO getCentroCostoVO() {
        return centroCostoVO;
    }

    /**
     * @param centroCostoVO the centroCostoVO to set
     */
    public void setCentroCostoVO(CentroCostoVO centroCostoVO) {
        this.centroCostoVO = centroCostoVO;
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
