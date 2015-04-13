/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

/**
 *
 * @author Alejandro
 */
public class CuentaBancariaVO {

    private int id;
    private String nombreBanco;
    private int idBanco;
    private String nombreTipoCuentaBancaria;
    private int idTipoCuentaBancaria;
    private String numero;
    private short esActual;
    private RecursoHumanoVO recursoHumanoVO;

    public CuentaBancariaVO() {
    }

    public CuentaBancariaVO(int id, String nombreBanco, int idBanco, String nombreTipoCuentaBancaria, int idTipoCuentaBancaria, String numero, short esActual,RecursoHumanoVO recursoHumanoVO) {
        this.id = id;
        this.nombreBanco = nombreBanco;
        this.idBanco = idBanco;
        this.nombreTipoCuentaBancaria = nombreTipoCuentaBancaria;
        this.idTipoCuentaBancaria = idTipoCuentaBancaria;
        this.numero = numero;
        this.esActual = esActual;
        this.recursoHumanoVO = recursoHumanoVO;
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
     * @return the nombreBanco
     */
    public String getNombreBanco() {
        return nombreBanco;
    }

    /**
     * @param nombreBanco the nombreBanco to set
     */
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    /**
     * @return the idBanco
     */
    public int getIdBanco() {
        return idBanco;
    }

    /**
     * @param idBanco the idBanco to set
     */
    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    /**
     * @return the nombreTipoCuentaBancaria
     */
    public String getNombreTipoCuentaBancaria() {
        return nombreTipoCuentaBancaria;
    }

    /**
     * @param nombreTipoCuentaBancaria the nombreTipoCuentaBancaria to set
     */
    public void setNombreTipoCuentaBancaria(String nombreTipoCuentaBancaria) {
        this.nombreTipoCuentaBancaria = nombreTipoCuentaBancaria;
    }

    /**
     * @return the idTipoCuentaBancaria
     */
    public int getIdTipoCuentaBancaria() {
        return idTipoCuentaBancaria;
    }

    /**
     * @param idTipoCuentaBancaria the idTipoCuentaBancaria to set
     */
    public void setIdTipoCuentaBancaria(int idTipoCuentaBancaria) {
        this.idTipoCuentaBancaria = idTipoCuentaBancaria;
    }

    /**
     * @return the numero
     */
    
    /**
     * @return the esActual
     */
    public short getEsActual() {
        return esActual;
    }

    /**
     * @param esActual the esActual to set
     */
    public void setEsActual(short esActual) {
        this.esActual = esActual;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the recursoHumanoVO
     */
    public RecursoHumanoVO getRecursoHumanoVO() {
        return recursoHumanoVO;
    }

    /**
     * @param recursoHumanoVO the recursoHumanoVO to set
     */
    public void setRecursoHumanoVO(RecursoHumanoVO recursoHumanoVO) {
        this.recursoHumanoVO = recursoHumanoVO;
    }
}
