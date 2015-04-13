/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import Entidades.ValorFormaPago;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class ValorFormaFechasPagoVO {
    private ValorFormaPago valorFormaPago;
    private List<FechaPagoVO> fechaPagoVOs;

    /**
     * @return the valorFormaPago
     */
    public ValorFormaPago getValorFormaPago() {
        return valorFormaPago;
    }

    /**
     * @param valorFormaPago the valorFormaPago to set
     */
    public void setValorFormaPago(ValorFormaPago valorFormaPago) {
        this.valorFormaPago = valorFormaPago;
    }

    /**
     * @return the fechaPagoVOs
     */
    public List<FechaPagoVO> getFechaPagoVOs() {
        return fechaPagoVOs;
    }

    /**
     * @param fechaPagoVOs the fechaPagoVOs to set
     */
    public void setFechaPagoVOs(List<FechaPagoVO> fechaPagoVOs) {
        this.fechaPagoVOs = fechaPagoVOs;
    }

}
