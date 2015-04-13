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
public class ValorFormaPagoFxVO {
    private int idValorFormaPago;
    private List<FechaPagoVO> fechaPagoVOs;

    public ValorFormaPagoFxVO() {
    }

    

    public ValorFormaPagoFxVO(int idValorFormaPago, List<FechaPagoVO> fechaPagoVOs) {
        this.idValorFormaPago = idValorFormaPago;
        this.fechaPagoVOs = fechaPagoVOs;
    }

    /**
     * @return the idValorFormaPago
     */
    public int getIdValorFormaPago() {
        return idValorFormaPago;
    }

    /**
     * @param idValorFormaPago the idValorFormaPago to set
     */
    public void setIdValorFormaPago(int idValorFormaPago) {
        this.idValorFormaPago = idValorFormaPago;
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
