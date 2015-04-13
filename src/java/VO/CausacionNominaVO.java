/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VO;

/**
 *
 * @author Alejandro
 */
public class CausacionNominaVO {

    private int id;
    private int idTributaria;
    private int idFechasCorteCausacion;
    private int idFechasPago;
    private double subtotal;
    private short diasTrabajados;
    private double totalAPagar;
    private double ibc;
    private String nombreEmpleado;
    private double tiempo;
    private double vacaciones;
    private int cedula;
    private double totalCausEmpresa;
    private int[] idsBonificaciones;
    private ValorIdVO[] deducciones;
    private ValorIdVO[] provisiones;
    private ValorIdVO[] causacionEmpresa;

    public CausacionNominaVO() {
    }

    public CausacionNominaVO(int idTributaria, int idFechasCorteCausacion, int idFechasPago, double subtotal, short diasTrabajados, double totalAPagar, double ibc, String nombreEmpleado, double tiempo, double vacaciones, int cedula, double totalCausEmpresa, int[] idsBonificaciones, ValorIdVO[] deducciones, ValorIdVO[] provisiones, ValorIdVO[] causacionEmpresa) {

        this.idTributaria = idTributaria;
        this.idFechasCorteCausacion = idFechasCorteCausacion;
        this.idFechasPago = idFechasPago;
        this.subtotal = subtotal;
        this.diasTrabajados = diasTrabajados;
        this.totalAPagar = totalAPagar;
        this.ibc = ibc;
        this.nombreEmpleado = nombreEmpleado;
        this.tiempo = tiempo;
        this.vacaciones = vacaciones;
        this.cedula = cedula;
        this.totalCausEmpresa = totalCausEmpresa;
        this.idsBonificaciones = idsBonificaciones;
        this.deducciones = deducciones;
        this.provisiones = provisiones;
        this.causacionEmpresa = causacionEmpresa;
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
     * @return the idTributaria
     */
    public int getIdTributaria() {
        return idTributaria;
    }

    /**
     * @param idTributaria the idTributaria to set
     */
    public void setIdTributaria(int idTributaria) {
        this.idTributaria = idTributaria;
    }

    /**
     * @return the idFechasCorteCausacion
     */
    public int getIdFechasCorteCausacion() {
        return idFechasCorteCausacion;
    }

    /**
     * @param idFechasCorteCausacion the idFechasCorteCausacion to set
     */
    public void setIdFechasCorteCausacion(int idFechasCorteCausacion) {
        this.idFechasCorteCausacion = idFechasCorteCausacion;
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
     * @return the nombreEmpleado
     */
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    /**
     * @param nombreEmpleado the nombreEmpleado to set
     */
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    /**
     * @return the tiempo
     */
    public double getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * @return the vacaciones
     */
    public double getVacaciones() {
        return vacaciones;
    }

    /**
     * @param vacaciones the vacaciones to set
     */
    public void setVacaciones(double vacaciones) {
        this.vacaciones = vacaciones;
    }

    /**
     * @return the cedula
     */
    public int getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the totalCausEmpresa
     */
    public double getTotalCausEmpresa() {
        return totalCausEmpresa;
    }

    /**
     * @param totalCausEmpresa the totalCausEmpresa to set
     */
    public void setTotalCausEmpresa(double totalCausEmpresa) {
        this.totalCausEmpresa = totalCausEmpresa;
    }

    /**
     * @return the idsBonificaciones
     */
    public int[] getIdsBonificaciones() {
        return idsBonificaciones;
    }

    /**
     * @param idsBonificaciones the idsBonificaciones to set
     */
    public void setIdsBonificaciones(int[] idsBonificaciones) {
        this.idsBonificaciones = idsBonificaciones;
    }

    /**
     * @return the deducciones
     */
    public ValorIdVO[] getDeducciones() {
        return deducciones;
    }

    /**
     * @param deducciones the deducciones to set
     */
    public void setDeducciones(ValorIdVO[] deducciones) {
        this.deducciones = deducciones;
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

}
