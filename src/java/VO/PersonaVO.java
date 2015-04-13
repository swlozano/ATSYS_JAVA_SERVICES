/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import java.math.BigDecimal;

/**
 *
 * @author Alejandro
 */
public class PersonaVO
{
    private int id;
    private BigDecimal nit;
    private Integer cedula;
    private Integer telefono;
    private String nombreRs;
    private short esEmpresa;
    private String cual;
    private String numeroCual;

    public PersonaVO() {
    }

    
    public  PersonaVO(int id,BigDecimal nit,Integer cedula,Integer telefono,String nombreRs,short esEmpresa,String cual,String numeroCual)
    {
        this.cedula = cedula;
        this.id = id;
        this.nit = nit;
        this.nombreRs = nombreRs;
        this.telefono = telefono;
        this.esEmpresa = esEmpresa;
        this.cual = cual;
        this.numeroCual = numeroCual;
    }

    public PersonaVO(int id,  String nombreRs, BigDecimal nit, Integer cedula) {
        this.id = id;
        this.nit = nit;
        this.cedula = cedula;
        this.nombreRs = nombreRs;
    }

    public  PersonaVO(int id,String nombreRs)
    {
        this.id = id;
        this.nombreRs = nombreRs;
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
     * @return the nit
     */
    public BigDecimal getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(BigDecimal nit) {
        this.nit = nit;
    }

    /**
     * @return the cedula
     */
    public Integer getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the telefono
     */
    public Integer getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the nombreRs
     */
    public String getNombreRs() {
        return nombreRs;
    }

    /**
     * @param nombreRs the nombreRs to set
     */
    public void setNombreRs(String nombreRs) {
        this.nombreRs = nombreRs;
    }

    /**
     * @return the esEmpresa
     */
    public short getEsEmpresa() {
        return esEmpresa;
    }

    /**
     * @param esEmpresa the esEmpresa to set
     */
    public void setEsEmpresa(short esEmpresa) {
        this.esEmpresa = esEmpresa;
    }

    /**
     * @return the cual
     */
    public String getCual() {
        return cual;
    }

    /**
     * @param cual the cual to set
     */
    public void setCual(String cual) {
        this.cual = cual;
    }

    /**
     * @return the numeroCual
     */
    public String getNumeroCual() {
        return numeroCual;
    }

    /**
     * @param numeroCual the numeroCual to set
     */
    public void setNumeroCual(String numeroCual) {
        this.numeroCual = numeroCual;
    }
}
