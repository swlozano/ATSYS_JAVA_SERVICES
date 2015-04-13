/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author Alejandro
 */
public class ValFormPagoFchasPagVO {

    private int idValorFormPago;
    private Set<Date> dates;

    public ValFormPagoFchasPagVO()
    {
    }

    public ValFormPagoFchasPagVO(int idValorFormPago, Set<Date> dates) {
        this.dates = dates;
        this.idValorFormPago = idValorFormPago;
    }

    /**
     * @return the idValorFormPago
     */
    public int getIdValorFormPago() {
        return idValorFormPago;
    }

    /**
     * @param idValorFormPago the idValorFormPago to set
     */
    public void setIdValorFormPago(int idValorFormPago) {
        this.idValorFormPago = idValorFormPago;
    }

    /**
     * @return the dates
     */
    public Set<Date> getDates() {
        return dates;
    }

    /**
     * @param dates the dates to set
     */
    public void setDates(Set<Date> dates) {
        this.dates = dates;
    }
}
