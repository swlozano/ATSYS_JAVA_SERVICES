/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import Entidades.Persona;
import java.util.Date;

/**
 *
 * @author Alejandro
 */
public class MovimientoVO {
    private int id;
    private String concepto;
    private String centroCosto;
    private String persona;
    private int  idCentroCosto;
    private int  idPersona;
    private double valor;
    private double  saldo;
    private Date fecha;
    private short isApertura;
    private short tipoMovimiento;
    private CajaMenorVO cajaMenorVO;
    private PersonaVO personaResponsable;

    public MovimientoVO(){}

    public MovimientoVO(int id,String concepto,String centroCosto,String  persona,double valor,double  saldo,Date fecha)
    {
        this.centroCosto = centroCosto;
        this.concepto = concepto;
        this.fecha = fecha;
        this.id = id;
        this.persona = persona;
        this.saldo = saldo;
        this.valor = valor;
    }
    public MovimientoVO(int id,String concepto,String centroCosto,String persona,double valor,double  saldo,Date fecha,short isApertura)
    {
        this.centroCosto = centroCosto;
        this.concepto = concepto;
        this.fecha = fecha;
        this.id = id;
        this.persona = persona;
        this.saldo = saldo;
        this.valor = valor;
        this.isApertura = isApertura;
    }
    public MovimientoVO(int id,String concepto,String centroCosto,String persona,double valor,double  saldo,Date fecha,short isApertura,short tipoMovimiento)
    {
        this.centroCosto = centroCosto;
        this.concepto = concepto;
        this.fecha = fecha;
        this.id = id;
        this.persona = persona;
        this.saldo = saldo;
        this.valor = valor;
        this.isApertura = isApertura;
        this.tipoMovimiento = tipoMovimiento;
    }
     public MovimientoVO(int id,String concepto,String centroCosto,String persona,double valor,double  saldo,Date fecha,short isApertura,short tipoMovimiento , int idCentroCosto, int idPersona,CajaMenorVO cajaMenorVO)
    {
        this.centroCosto = centroCosto;
        this.concepto = concepto;
        this.fecha = fecha;
        this.id = id;
        this.persona = persona;
        this.saldo = saldo;
        this.valor = valor;
        this.isApertura = isApertura;
        this.tipoMovimiento = tipoMovimiento;
        this.idCentroCosto = idCentroCosto;
        this.idPersona = idPersona;
        this.cajaMenorVO = cajaMenorVO;
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
     * @return the concepto
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
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the isApertura
     */
    public short getIsApertura() {
        return isApertura;
    }

    /**
     * @param isApertura the isApertura to set
     */
    public void setIsApertura(short isApertura) {
        this.isApertura = isApertura;
    }

    /**
     * @return the tipoMovimiento
     */
    public short getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(short tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the centroCosto
     */
   
    public int getIdCentroCosto() {
        return idCentroCosto;
    }

    /**
     * @param idCentroCosto the idCentroCosto to set
     */
    public void setIdCentroCosto(int idCentroCosto) {
        this.idCentroCosto = idCentroCosto;
    }

    /**
     * @return the idPersona
     */
    public int getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the centroCosto
     */
    public String getCentroCosto() {
        return centroCosto;
    }

    /**
     * @param centroCosto the centroCosto to set
     */
    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    /**
     * @return the persona
     */
    public String getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(String persona) {
        this.persona = persona;
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
     * @return the personaResponsable
     */
    public PersonaVO getPersonaResponsable() {
        return personaResponsable;
    }

    /**
     * @param personaResponsable the personaResponsable to set
     */
    public void setPersonaResponsable(PersonaVO personaResponsable) {
        this.personaResponsable = personaResponsable;
    }

   
    /**
     * @return the centroCostoVO
     */
   
}
