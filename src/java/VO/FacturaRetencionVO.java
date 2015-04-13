/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import Entidades.FacturaRetencion;

/**
 *
 * @author Alejandro
 */
public class FacturaRetencionVO {

     private int id;
     private double valor;
     private RetencionVO retencionVO;


     public FacturaRetencionVO(){}
     public FacturaRetencionVO(int id,double valor,RetencionVO retencionVO)
     {
        this.id = id;
        this.valor = valor;
        this.retencionVO = retencionVO;
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

    /**
     * @return the retencionVO
     */
    public RetencionVO getRetencionVO() {
        return retencionVO;
    }

    /**
     * @param retencionVO the retencionVO to set
     */
    public void setRetencionVO(RetencionVO retencionVO) {
        this.retencionVO = retencionVO;
    }

}
