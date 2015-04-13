/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import Entidades.FacturaIva;
import Entidades.Iva;

/**
 *
 * @author Alejandro
 */
public class FacturaIvaVO {

    private int id;
    private FacturaVO facturaVO;
    private Iva iva;
    private double valor;

    public FacturaIvaVO()
    {}

    public FacturaIvaVO(int id, Iva iva,  double valor)
    {
        this.id = id;
        this.iva = iva;
        this.valor = valor;
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
    public FacturaVO getFacturaVO() {
        return facturaVO;
    }

    /**
     * @param facturaVO the facturaVO to set
     */
    public void setFacturaVO(FacturaVO facturaVO) {
        this.facturaVO = facturaVO;
    }

    /**
     * @return the iva
     */
    public Iva getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(Iva iva) {
        this.iva = iva;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

}
