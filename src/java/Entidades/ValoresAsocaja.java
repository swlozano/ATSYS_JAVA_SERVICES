package Entidades;
// Generated 9/11/2009 07:01:15 PM by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;

/**
 * ValoresAsocaja generated by hbm2java
 */
public class ValoresAsocaja implements java.io.Serializable {

    private int id;
    private TipoAsocaja tipoAsocaja;
    private String porcentajeEmpresa;
    private int ano;
    private String aplicaA;
    private Set causacionEmpresas = new HashSet(0);
    private String porcentajeEmpleado;

    public ValoresAsocaja() {
    }

    public ValoresAsocaja(int id, String porcentajeEmpresa, int ano, String aplicaA) {
        this.id = id;
        this.porcentajeEmpresa = porcentajeEmpresa;
        this.ano = ano;
        this.aplicaA = aplicaA;
    }

    public ValoresAsocaja(int id, String porcentajeEmpresa, int ano, String aplicaA, Set causacionEmpresas) {
        this.id = id;
        this.porcentajeEmpresa = porcentajeEmpresa;
        this.ano = ano;
        this.aplicaA = aplicaA;
        this.causacionEmpresas = causacionEmpresas;
    }

    public ValoresAsocaja(int idValAsocaja) {
        this.id = idValAsocaja;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getAplicaA() {
        return this.aplicaA;
    }

    public void setAplicaA(String aplicaA) {
        this.aplicaA = aplicaA;
    }

    public Set getCausacionEmpresas() {
        return this.causacionEmpresas;
    }

    public void setCausacionEmpresas(Set causacionEmpresas) {
        this.causacionEmpresas = causacionEmpresas;
    }

    /**
     * @return the tipoAsocaja
     */
    public TipoAsocaja getTipoAsocaja() {
        return tipoAsocaja;
    }

    /**
     * @param tipoAsocaja the tipoAsocaja to set
     */
    public void setTipoAsocaja(TipoAsocaja tipoAsocaja) {
        this.tipoAsocaja = tipoAsocaja;
    }

    /**
     * @return the porcentajeEmpresa
     */
    public String getPorcentajeEmpresa() {
        return porcentajeEmpresa;
    }

    /**
     * @param porcentajeEmpresa the porcentajeEmpresa to set
     */
    public void setPorcentajeEmpresa(String porcentajeEmpresa) {
        this.porcentajeEmpresa = porcentajeEmpresa;
    }

    /**
     * @return the porcentajeEmpleado
     */
    public String getPorcentajeEmpleado() {
        return porcentajeEmpleado;
    }

    /**
     * @param porcentajeEmpleado the porcentajeEmpleado to set
     */
    public void setPorcentajeEmpleado(String porcentajeEmpleado) {
        this.porcentajeEmpleado = porcentajeEmpleado;
    }
}


