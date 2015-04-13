/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

import Entidades.CausacionNomina;
import Entidades.CausacionPs;

/**
 *
 * @author Alejandro
 */
public class CausacionVO {

    private CausacionNomina causacionNomina;
    private CausacionPs causacionPs;
    private int idFechaPago;
    private int idValorFormaPago;
    private int idRecursoHumano;
    private int idContrato;

    public CausacionVO() {
    }

    public CausacionVO(CausacionNomina causacionNomina,
            int idfechaPago,
            int idvalorFormaPago,
            int idrecursoHumano,
            int idcontrato) {
        this.causacionNomina = causacionNomina;
        this.idContrato = idcontrato;
        this.idFechaPago = idfechaPago;
        this.idValorFormaPago = idvalorFormaPago;
        this.idRecursoHumano = idrecursoHumano;
    }

    public CausacionVO(CausacionPs causacionPs,
            int idfechaPago,
            int idvalorFormaPago,
            int idrecursoHumano,
            int idcontrato) {
        this.causacionPs = causacionPs;
        this.idContrato = idcontrato;
        this.idFechaPago = idfechaPago;
        this.idValorFormaPago = idvalorFormaPago;
        this.idRecursoHumano = idrecursoHumano;
    }

    /**
     * @return the fechaPago
     */
    public CausacionNomina getCausacionNomina() {
        return causacionNomina;
    }

    /**
     * @param causacionNomina the causacionNomina to set
     */
    public void setCausacionNomina(CausacionNomina causacionNomina) {
        this.causacionNomina = causacionNomina;
    }

    /**
     * @return the idFechaPago
     */
    public int getIdFechaPago() {
        return idFechaPago;
    }

    /**
     * @param idFechaPago the idFechaPago to set
     */
    public void setIdFechaPago(int idFechaPago) {
        this.idFechaPago = idFechaPago;
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
     * @return the idRecursoHumano
     */
    public int getIdRecursoHumano() {
        return idRecursoHumano;
    }

    /**
     * @param idRecursoHumano the idRecursoHumano to set
     */
    public void setIdRecursoHumano(int idRecursoHumano) {
        this.idRecursoHumano = idRecursoHumano;
    }

    /**
     * @return the idContrato
     */
    public int getIdContrato() {
        return idContrato;
    }

    /**
     * @param idContrato the idContrato to set
     */
    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    /**
     * @return the causacionPs
     */
    public CausacionPs getCausacionPs() {
        return causacionPs;
    }

    /**
     * @param causacionPs the causacionPs to set
     */
    public void setCausacionPs(CausacionPs causacionPs) {
        this.causacionPs = causacionPs;
    }
}
