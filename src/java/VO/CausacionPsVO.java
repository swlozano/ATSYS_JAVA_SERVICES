/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

/**
 *
 * @author Alejandro
 */
public class CausacionPsVO {

    private int id;
    private int idFechasPago;
    private String rut;
    private String contratista;
    private double basico;
    private short diasTrabajados;
    private double devengados;
    private double totalBonificaciones;
    private double totalAPagar;
    private ValorIdVO[] Registro_val_retencion;
    private ValorIdVO[] bonificacion;
    private ValorIdVO[] provision;
    private ValorIdVO[] deduccion;

   
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
     * @return the idFechasPago
     */
    public int getIdFechasPago() {
        return idFechasPago;
    }

    /**
     * @param idFechasPago the idFechasPago to set
     */
    public void setIdFechasPago(int idFechasPago) {
        this.idFechasPago = idFechasPago;
    }

    /**
     * @return the rut
     */
    public String getRut() {
        return rut;
    }

    /**
     * @param rut the rut to set
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * @return the contratista
     */
    public String getContratista() {
        return contratista;
    }

    /**
     * @param contratista the contratista to set
     */
    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    /**
     * @return the basico
     */
    public double getBasico() {
        return basico;
    }

    /**
     * @param basico the basico to set
     */
    public void setBasico(double basico) {
        this.basico = basico;
    }

    /**
     * @return the diasTrabajados
     */
    public short getDiasTrabajados() {
        return diasTrabajados;
    }

    /**
     * @param diasTrabajados the diasTrabajados to set
     */
    public void setDiasTrabajados(short diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }

    /**
     * @return the devengados
     */
    public double getDevengados() {
        return devengados;
    }

    /**
     * @param devengados the devengados to set
     */
    public void setDevengados(double devengados) {
        this.devengados = devengados;
    }

    /**
     * @return the totalBonificaciones
     */
    public double getTotalBonificaciones() {
        return totalBonificaciones;
    }

    /**
     * @param totalBonificaciones the totalBonificaciones to set
     */
    public void setTotalBonificaciones(double totalBonificaciones) {
        this.totalBonificaciones = totalBonificaciones;
    }

    /**
     * @return the totalAPagar
     */
    public double getTotalAPagar() {
        return totalAPagar;
    }

    /**
     * @param totalAPagar the totalAPagar to set
     */
    public void setTotalAPagar(double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    /**
     * @return the Registro_val_retencion
     */
    public ValorIdVO[] getRegistro_val_retencion() {
        return Registro_val_retencion;
    }

    /**
     * @param Registro_val_retencion the Registro_val_retencion to set
     */
    public void setRegistro_val_retencion(ValorIdVO[] Registro_val_retencion) {
        this.Registro_val_retencion = Registro_val_retencion;
    }

    /**
     * @return the bonificacion
     */
    public ValorIdVO[] getBonificacion() {
        return bonificacion;
    }

    /**
     * @param bonificacion the bonificacion to set
     */
    public void setBonificacion(ValorIdVO[] bonificacion) {
        this.bonificacion = bonificacion;
    }

    /**
     * @return the provision
     */
    public ValorIdVO[] getProvision() {
        return provision;
    }

    /**
     * @param provision the provision to set
     */
    public void setProvision(ValorIdVO[] provision) {
        this.provision = provision;
    }

    /**
     * @return the deduccion
     */
    public ValorIdVO[] getDeduccion() {
        return deduccion;
    }

    /**
     * @param deduccion the deduccion to set
     */
    public void setDeduccion(ValorIdVO[] deduccion) {
        this.deduccion = deduccion;
    }
}
