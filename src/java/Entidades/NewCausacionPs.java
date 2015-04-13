package Entidades;
// Generated 22-ene-2010 18:20:31 by Hibernate Tools 3.2.1.GA



/**
 * NewCausacionPs generated by hbm2java
 */
public class NewCausacionPs  implements java.io.Serializable {


     private int id;
     private int idFechasPago;
     private String rut;
     private String contratista;
     private double basico;
     private short diasTrabajados;
     private double devengados;
     private double totalBonificaciones;
     private double totalAPagar;
     private int idFechaCorte;

    public NewCausacionPs() {
    }

    public NewCausacionPs(int id, int idFechasPago, String rut, String contratista, double basico, short diasTrabajados, double devengados, double totalBonificaciones, double totalAPagar, int idFechaCorte) {
       this.id = id;
       this.idFechasPago = idFechasPago;
       this.rut = rut;
       this.contratista = contratista;
       this.basico = basico;
       this.diasTrabajados = diasTrabajados;
       this.devengados = devengados;
       this.totalBonificaciones = totalBonificaciones;
       this.totalAPagar = totalAPagar;
       this.idFechaCorte = idFechaCorte;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public int getIdFechasPago() {
        return this.idFechasPago;
    }
    
    public void setIdFechasPago(int idFechasPago) {
        this.idFechasPago = idFechasPago;
    }
    public String getRut() {
        return this.rut;
    }
    
    public void setRut(String rut) {
        this.rut = rut;
    }
    public String getContratista() {
        return this.contratista;
    }
    
    public void setContratista(String contratista) {
        this.contratista = contratista;
    }
    public double getBasico() {
        return this.basico;
    }
    
    public void setBasico(double basico) {
        this.basico = basico;
    }
    public short getDiasTrabajados() {
        return this.diasTrabajados;
    }
    
    public void setDiasTrabajados(short diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }
    public double getDevengados() {
        return this.devengados;
    }
    
    public void setDevengados(double devengados) {
        this.devengados = devengados;
    }
    public double getTotalBonificaciones() {
        return this.totalBonificaciones;
    }
    
    public void setTotalBonificaciones(double totalBonificaciones) {
        this.totalBonificaciones = totalBonificaciones;
    }
    public double getTotalAPagar() {
        return this.totalAPagar;
    }
    
    public void setTotalAPagar(double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }
    public int getIdFechaCorte() {
        return this.idFechaCorte;
    }
    
    public void setIdFechaCorte(int idFechaCorte) {
        this.idFechaCorte = idFechaCorte;
    }




}


