/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

import Entidades.OtroMasNomina;
import Entidades.Tributaria;

/**
 *
 * @author Alejandro
 */
public class CausacionNominaNewVO {
    
    private int idCausacionNomina;
    private int idRecursoHumano;
    private int nombreRh;
    private String identificacion;
    private String tipoContrato;
    private short idTipoContrato;
    private double salario;
    private double ibc;
    private String tiempo;
    private int diasTrabajados;
    private double subtotal;
    private double auxTransporte;
    private Tributaria tributaria;
    private double descuentoPorSalud;
    private double descuentoPension;
    private double totalOtrasDeducciones;
    private double totalOtrosMas;
    private double totalApagar;
    private double totalCausacionEmpresa;

    private int[] idsDeducciones;
    private ValorIdVO[] provisiones;
    private ValorIdVO[] causacionEmpresa;
    private OtroMasNomina[] otrosMases;



    public CausacionNominaNewVO() {
    }
    /**
     * @return the idCausacionNomina
     */
    public int getIdCausacionNomina() {
        return idCausacionNomina;
    }
    /**
     * @param idCausacionNomina the idCausacionNomina to set
     */
    public void setIdCausacionNomina(int idCausacionNomina) {
        this.idCausacionNomina = idCausacionNomina;
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
     * @return the nombreRh
     */
    public int getNombreRh() {
        return nombreRh;
    }

    /**
     * @param nombreRh the nombreRh to set
     */
    public void setNombreRh(int nombreRh) {
        this.nombreRh = nombreRh;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the tipoContrato
     */
    public String getTipoContrato() {
        return tipoContrato;
    }

    /**
     * @param tipoContrato the tipoContrato to set
     */
    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    /**
     * @return the idTipoContrato
     */
    public short getIdTipoContrato() {
        return idTipoContrato;
    }

    /**
     * @param idTipoContrato the idTipoContrato to set
     */
    public void setIdTipoContrato(short idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    /**
     * @return the salario
     */
    public double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * @return the ibc
     */
    public double getIbc() {
        return ibc;
    }

    /**
     * @param ibc the ibc to set
     */
    public void setIbc(double ibc) {
        this.ibc = ibc;
    }

    /**
     * @return the tiempo
     */
    public String getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * @return the diasTrabajados
     */
    public int getDiasTrabajados() {
        return diasTrabajados;
    }

    /**
     * @param diasTrabajados the diasTrabajados to set
     */
    public void setDiasTrabajados(int diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the auxTransporte
     */
    public double getAuxTransporte() {
        return auxTransporte;
    }

    /**
     * @param auxTransporte the auxTransporte to set
     */
    public void setAuxTransporte(double auxTransporte) {
        this.auxTransporte = auxTransporte;
    }

    /**
     * @return the tributaria
     */
    public Tributaria getTributaria() {
        return tributaria;
    }

    /**
     * @param tributaria the tributaria to set
     */
    public void setTributaria(Tributaria tributaria) {
        this.tributaria = tributaria;
    }

    /**
     * @return the descuentoPorSalud
     */
    public double getDescuentoPorSalud() {
        return descuentoPorSalud;
    }

    /**
     * @param descuentoPorSalud the descuentoPorSalud to set
     */
    public void setDescuentoPorSalud(double descuentoPorSalud) {
        this.descuentoPorSalud = descuentoPorSalud;
    }

    /**
     * @return the descuentoPension
     */
    public double getDescuentoPension() {
        return descuentoPension;
    }

    /**
     * @param descuentoPension the descuentoPension to set
     */
    public void setDescuentoPension(double descuentoPension) {
        this.descuentoPension = descuentoPension;
    }

    /**
     * @return the totalOtrasDeducciones
     */
    public double getTotalOtrasDeducciones() {
        return totalOtrasDeducciones;
    }

    /**
     * @param totalOtrasDeducciones the totalOtrasDeducciones to set
     */
    public void setTotalOtrasDeducciones(double totalOtrasDeducciones) {
        this.totalOtrasDeducciones = totalOtrasDeducciones;
    }

    /**
     * @return the totalOtrosMas
     */
    public double getTotalOtrosMas() {
        return totalOtrosMas;
    }

    /**
     * @param totalOtrosMas the totalOtrosMas to set
     */
    public void setTotalOtrosMas(double totalOtrosMas) {
        this.totalOtrosMas = totalOtrosMas;
    }

    /**
     * @return the totalApagar
     */
    public double getTotalApagar() {
        return totalApagar;
    }

    /**
     * @param totalApagar the totalApagar to set
     */
    public void setTotalApagar(double totalApagar) {
        this.totalApagar = totalApagar;
    }

    /**
     * @return the totalCausacionEmpresa
     */
    public double getTotalCausacionEmpresa() {
        return totalCausacionEmpresa;
    }

    /**
     * @param totalCausacionEmpresa the totalCausacionEmpresa to set
     */
    public void setTotalCausacionEmpresa(double totalCausacionEmpresa) {
        this.totalCausacionEmpresa = totalCausacionEmpresa;
    }

    /**
     * @return the idsDeducciones
     */
    public int[] getIdsDeducciones() {
        return idsDeducciones;
    }

    /**
     * @param idsDeducciones the idsDeducciones to set
     */
    public void setIdsDeducciones(int[] idsDeducciones) {
        this.idsDeducciones = idsDeducciones;
    }

    /**
     * @return the provisiones
     */
    public ValorIdVO[] getProvisiones() {
        return provisiones;
    }

    /**
     * @param provisiones the provisiones to set
     */
    public void setProvisiones(ValorIdVO[] provisiones) {
        this.provisiones = provisiones;
    }

    /**
     * @return the causacionEmpresa
     */
    public ValorIdVO[] getCausacionEmpresa() {
        return causacionEmpresa;
    }

    /**
     * @param causacionEmpresa the causacionEmpresa to set
     */
    public void setCausacionEmpresa(ValorIdVO[] causacionEmpresa) {
        this.causacionEmpresa = causacionEmpresa;
    }

    /**
     * @return the otrosMases
     */
    public OtroMasNomina[] getOtrosMases() {
        return otrosMases;
    }

    /**
     * @param otrosMases the otrosMases to set
     */
    public void setOtrosMases(OtroMasNomina[] otrosMases) {
        this.otrosMases = otrosMases;
    }
}
