/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class RecursoHumanoVO {
    private int id;
    private String lugarNacimiento;
    private String lugarExpedicion;
    private String arp;
    private String afp;
    private String eps;
    private String nombre;
    private String apellido;
    private int cedula;
    private String correoElectronico;
    private String telefono;
    private String direccionDomicilio;
    private String celular;
    private Date fechaNacimiento;
    private String rut;
    private short presentoRut;
    private short presentoCedula;
    private short presntoHv;
    private short presentoEntrevista;
    private String login;
    private String contrasena;

    //No pertecen a la entidad
    private List<CuentaBancariaVO> cuentaBancariaVOs;

    public RecursoHumanoVO() {
    }

    public RecursoHumanoVO(int id, String nombre, String apellido, int cedula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
    }
    

    public RecursoHumanoVO(int id, String lugarNacimiento, String lugarExpedicion, String arp, String afp, String eps, String nombre, String apellido, int cedula, String correoElectronico, String telefono, String direccionDomicilio, String celular, Date fechaNacimiento, String rut, short presentoRut, short presentoCedula, short presntoHv, short presentoEntrevista, String login, String contrasena, List<CuentaBancariaVO> cuentaBancariaVOs) {
        this.id = id;
        this.lugarNacimiento = lugarNacimiento;
        this.lugarExpedicion = lugarExpedicion;
        this.arp = arp;
        this.afp = afp;
        this.eps = eps;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccionDomicilio = direccionDomicilio;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.rut = rut;
        this.presentoRut = presentoRut;
        this.presentoCedula = presentoCedula;
        this.presntoHv = presntoHv;
        this.presentoEntrevista = presentoEntrevista;
        this.login = login;
        this.contrasena = contrasena;
        this.cuentaBancariaVOs = cuentaBancariaVOs;
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
     * @return the lugarNacimiento
     */
    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    /**
     * @param lugarNacimiento the lugarNacimiento to set
     */
    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    /**
     * @return the lugarExpedicion
     */
    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    /**
     * @param lugarExpedicion the lugarExpedicion to set
     */
    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    /**
     * @return the arp
     */
    public String getArp() {
        return arp;
    }

    /**
     * @param arp the arp to set
     */
    public void setArp(String arp) {
        this.arp = arp;
    }

    /**
     * @return the afp
     */
    public String getAfp() {
        return afp;
    }

    /**
     * @param afp the afp to set
     */
    public void setAfp(String afp) {
        this.afp = afp;
    }

    /**
     * @return the eps
     */
    public String getEps() {
        return eps;
    }

    /**
     * @param eps the eps to set
     */
    public void setEps(String eps) {
        this.eps = eps;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
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
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the direccionDomicilio
     */
    public String getDireccionDomicilio() {
        return direccionDomicilio;
    }

    /**
     * @param direccionDomicilio the direccionDomicilio to set
     */
    public void setDireccionDomicilio(String direccionDomicilio) {
        this.direccionDomicilio = direccionDomicilio;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
     * @return the presentoRut
     */
    public short getPresentoRut() {
        return presentoRut;
    }

    /**
     * @param presentoRut the presentoRut to set
     */
    public void setPresentoRut(short presentoRut) {
        this.presentoRut = presentoRut;
    }

    /**
     * @return the presentoCedula
     */
    public short getPresentoCedula() {
        return presentoCedula;
    }

    /**
     * @param presentoCedula the presentoCedula to set
     */
    public void setPresentoCedula(short presentoCedula) {
        this.presentoCedula = presentoCedula;
    }

    /**
     * @return the presntoHv
     */
    public short getPresntoHv() {
        return presntoHv;
    }

    /**
     * @param presntoHv the presntoHv to set
     */
    public void setPresntoHv(short presntoHv) {
        this.presntoHv = presntoHv;
    }

    /**
     * @return the presentoEntrevista
     */
    public short getPresentoEntrevista() {
        return presentoEntrevista;
    }

    /**
     * @param presentoEntrevista the presentoEntrevista to set
     */
    public void setPresentoEntrevista(short presentoEntrevista) {
        this.presentoEntrevista = presentoEntrevista;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the cuentaBancariaVOs
     */
    public List<CuentaBancariaVO> getCuentaBancariaVOs() {
        return cuentaBancariaVOs;
    }

    /**
     * @param cuentaBancariaVOs the cuentaBancariaVOs to set
     */
    public void setCuentaBancariaVOs(List<CuentaBancariaVO> cuentaBancariaVOs) {
        this.cuentaBancariaVOs = cuentaBancariaVOs;
    }
}
