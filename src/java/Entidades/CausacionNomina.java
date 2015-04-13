package Entidades;
// Generated 8/02/2010 03:03:27 PM by Hibernate Tools 3.2.1.GA

import VO.ProvisionesVO;
import VO.ValorIdVO;
import java.util.List;

/**
 * CausacionNomina generated by hbm2java
 */
public class CausacionNomina implements java.io.Serializable {

    private int id;
    private int idContrato;
    private int idFechaCorte;
    private int idTributaria;
    private double salario;
    private double ibc;
    private short diasTrabajados;
    private double subtotal;
    private double auxilioTransporte;
    private double descuentoSalud;
    private double descuentoPension;
    private double totalOtrasDeducciones;
    private double totalOtrosMas;
    private double totalAPagar;
    private double totalCausEmpresa;
    private double vacaciones;
    //NO HACEN PARTE DE LA TABLA, SOLO ES INFORMACION
    private int idRecursoHumano;
    private String nombreRh;
    private String identificacion;
    private String tipoContrato;
    private short idTipoContrato;
    private String tiempo;
    private Tributaria tributaria;
    private int[] idsDeducciones;
    private ValorIdVO[] provisiones;
    private OtroMasNomina[] otrosMases;
    private CausacionEmpresa causacionEmpresa;
    //to print
    private List<Deducciones> deduccioneses;
    private List<ProvisionesVO> provisionesVOs;

    public CausacionNomina(int id) {
        this.id = id;
    }


    public CausacionNomina(int idContrato, int idTributaria, double salario, double ibc, short diasTrabajados, double subtotal, double auxilioTransporte, double descuentoSalud, double descuentoPension, double totalOtrasDeducciones, double totalOtrosMas, double totalAPagar, double totalCausEmpresa, int idRecursoHumano, String nombreRh, String identificacion, String tipoContrato, short idTipoContrato, String tiempo, Tributaria tributaria, CausacionEmpresa causacionEmpresa) {
        this.idContrato = idContrato;
        this.idTributaria = idTributaria;
        this.salario = salario;
        this.ibc = ibc;
        this.diasTrabajados = diasTrabajados;
        this.subtotal = subtotal;
        this.auxilioTransporte = auxilioTransporte;
        this.descuentoSalud = descuentoSalud;
        this.descuentoPension = descuentoPension;
        this.totalOtrasDeducciones = totalOtrasDeducciones;
        this.totalOtrosMas = totalOtrosMas;
        this.totalAPagar = totalAPagar;
        this.totalCausEmpresa = totalCausEmpresa;
        this.idRecursoHumano = idRecursoHumano;
        this.nombreRh = nombreRh;
        this.identificacion = identificacion;
        this.tipoContrato = tipoContrato;
        this.idTipoContrato = idTipoContrato;
        this.tiempo = tiempo;
        this.tributaria = tributaria;
        this.causacionEmpresa = causacionEmpresa;
    }

    

    public CausacionNomina() {
    }

    public CausacionNomina(int id, int idContrato, int idFechaCorte, int idTributaria, double salario, double ibc, short diasTrabajados, double subtotal, double auxilioTransporte, double descuentoSalud, double descuentoPension, double totalOtrasDeducciones, double totalOtrosMas, double totalAPagar, double totalCausEmpresa, double vacaciones) {
        this.id = id;
        this.idContrato = idContrato;
        this.idFechaCorte = idFechaCorte;
        this.idTributaria = idTributaria;
        this.salario = salario;
        this.ibc = ibc;
        this.diasTrabajados = diasTrabajados;
        this.subtotal = subtotal;
        this.auxilioTransporte = auxilioTransporte;
        this.descuentoSalud = descuentoSalud;
        this.descuentoPension = descuentoPension;
        this.totalOtrasDeducciones = totalOtrasDeducciones;
        this.totalOtrosMas = totalOtrosMas;
        this.totalAPagar = totalAPagar;
        this.totalCausEmpresa = totalCausEmpresa;
        this.vacaciones = vacaciones;
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
     * @return the idFechaCorte
     */
    public int getIdFechaCorte() {
        return idFechaCorte;
    }

    /**
     * @param idFechaCorte the idFechaCorte to set
     */
    public void setIdFechaCorte(int idFechaCorte) {
        this.idFechaCorte = idFechaCorte;
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
     * @return the auxilioTransporte
     */
    public double getAuxilioTransporte() {
        return auxilioTransporte;
    }

    /**
     * @param auxilioTransporte the auxilioTransporte to set
     */
    public void setAuxilioTransporte(double auxilioTransporte) {
        this.auxilioTransporte = auxilioTransporte;
    }

    /**
     * @return the descuentoSalud
     */
    public double getDescuentoSalud() {
        return descuentoSalud;
    }

    /**
     * @param descuentoSalud the descuentoSalud to set
     */
    public void setDescuentoSalud(double descuentoSalud) {
        this.descuentoSalud = descuentoSalud;
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
    public String getNombreRh() {
        return nombreRh;
    }

    /**
     * @param nombreRh the nombreRh to set
     */
    public void setNombreRh(String nombreRh) {
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

    /**
     * @return the causacionEmpresa
     */
    public CausacionEmpresa getCausacionEmpresa() {
        return causacionEmpresa;
    }

    /**
     * @param causacionEmpresa the causacionEmpresa to set
     */
    public void setCausacionEmpresa(CausacionEmpresa causacionEmpresa) {
        this.causacionEmpresa = causacionEmpresa;
    }

    /**
     * @return the deduccioneses
     */
    public List<Deducciones> getDeduccioneses() {
        return deduccioneses;
    }

    /**
     * @param deduccioneses the deduccioneses to set
     */
    public void setDeduccioneses(List<Deducciones> deduccioneses) {
        this.deduccioneses = deduccioneses;
    }

    /**
     * @return the provisionesVOs
     */
    public List<ProvisionesVO> getProvisionesVOs() {
        return provisionesVOs;
    }

    /**
     * @param provisionesVOs the provisionesVOs to set
     */
    public void setProvisionesVOs(List<ProvisionesVO> provisionesVOs) {
        this.provisionesVOs = provisionesVOs;
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

 
    
}


