package Entidades;
// Generated 19/11/2009 09:48:00 PM by Hibernate Tools 3.2.1.GA

/**
 * CausaPsDeduccion generated by hbm2java
 */
public class CausaPsDeduccion implements java.io.Serializable {

    private int id;
    private int idCausacionPs;
    private int idDeducion;

    public CausaPsDeduccion() {
    }

    public CausaPsDeduccion(int id, int idCausacionPs, int idDeducion) {
        this.id = id;
        this.idCausacionPs = idCausacionPs;
        this.idDeducion = idDeducion;
    }

    public CausaPsDeduccion(int idCausacionPs, int idDeducion) {
        this.idCausacionPs = idCausacionPs;
        this.idDeducion = idDeducion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCausacionPs() {
        return this.idCausacionPs;
    }

    public void setIdCausacionPs(int idCausacionPs) {
        this.idCausacionPs = idCausacionPs;
    }

    public int getIdDeducion() {
        return this.idDeducion;
    }

    public void setIdDeducion(int idDeducion) {
        this.idDeducion = idDeducion;
    }
}

