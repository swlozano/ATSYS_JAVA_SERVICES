/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

/**
 *
 * @author Alejandro
 */
public class ValorIdVO {

    private int id;
    private double  valor;

    public ValorIdVO() {
    }

    public ValorIdVO(int id, double  valor) {
        this.id = id;
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
     * @return the valor
     */
}
