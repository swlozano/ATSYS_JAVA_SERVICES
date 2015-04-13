package Entidades;
// Generated 19/12/2009 02:09:48 PM by Hibernate Tools 3.2.1.GA

/**
 * Deducciones generated by hbm2java
 */
public class Deducciones implements java.io.Serializable {

    private int id;
    private String nombre;
    private double valor;
    private short afectaIbc;
    private String observacion;

    public Deducciones(int id) {
        this.id = id;
    }

    public Deducciones() {
    }

    public Deducciones(int id, String nombre, double valor) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the afectaIbc
     */
    public short getAfectaIbc() {
        return afectaIbc;
    }

    /**
     * @param afectaIbc the afectaIbc to set
     */
    public void setAfectaIbc(short afectaIbc) {
        this.afectaIbc = afectaIbc;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}

