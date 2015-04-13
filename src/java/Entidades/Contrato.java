package Entidades;
// Generated 4/11/2009 10:54:35 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contrato generated by hbm2java
 */
public class Contrato implements java.io.Serializable {

    private int id;
    private OficioDesempenar oficioDesempenar;
    private TiposContrato tiposContrato;
    private Country countryByIdNacionLabores;
    private RecursoHumano recursoHumano;
    private Country countryByIdNacionContrato;
    private Date fechaInicio;
    private Date fechaFin;
    private short estado;
    private Date fechaRealTerminacion;
    private double tiempo;
    private Set valorFormaPagos = new HashSet(0);
    private short pagosCancelados;
    //MIOS
    private List<ValorFormaPago> listvaloFormaPagos;

    public Contrato() {
    }

    public Contrato(int id,
            OficioDesempenar oficioDesempenar,
            TiposContrato tiposContrato,
            Country countryByIdNacionLabores,
            RecursoHumano recursoHumano,
            Country countryByIdNacionContrato,
            Date fechaInicio,
            Date fechaFin,
            short estado,
            Date fechaRealTerminacion,
            double tiempo,
            short pagosCancelados) {
        this.countryByIdNacionContrato = countryByIdNacionContrato;
        this.countryByIdNacionLabores = countryByIdNacionLabores;
        this.tiposContrato = tiposContrato;
        this.estado = estado;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.id = id;
        this.oficioDesempenar = oficioDesempenar;
        this.pagosCancelados = pagosCancelados;
        this.recursoHumano = recursoHumano;
        this.tiempo = tiempo;

    }

    public Contrato(int id) {
        this.id = id;
    }

    public Contrato(OficioDesempenar oficioDesempenar, TiposContrato tiposContrato, Country countryByIdNacionLabores, RecursoHumano recursoHumano, Country countryByIdNacionContrato, Date fechaInicio, Date fechaFin, short estado, Date fechaRealTerminacion, double tiempo) {

        this.oficioDesempenar = oficioDesempenar;
        this.tiposContrato = tiposContrato;
        this.countryByIdNacionLabores = countryByIdNacionLabores;
        this.recursoHumano = recursoHumano;
        this.countryByIdNacionContrato = countryByIdNacionContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.fechaRealTerminacion = fechaRealTerminacion;
        this.tiempo = tiempo;
    }

    public Contrato(int id, OficioDesempenar oficioDesempenar, TiposContrato tiposContrato, Country countryByIdNacionLabores, RecursoHumano recursoHumano, Country countryByIdNacionContrato, Date fechaInicio, Date fechaFin, short estado, Date fechaRealTerminacion, double tiempo) {
        this.id = id;
        this.oficioDesempenar = oficioDesempenar;
        this.tiposContrato = tiposContrato;
        this.countryByIdNacionLabores = countryByIdNacionLabores;
        this.recursoHumano = recursoHumano;
        this.countryByIdNacionContrato = countryByIdNacionContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.fechaRealTerminacion = fechaRealTerminacion;
        this.tiempo = tiempo;
    }

    public Contrato(int id, OficioDesempenar oficioDesempenar, TiposContrato tiposContrato, Country countryByIdNacionLabores, RecursoHumano recursoHumano, Country countryByIdNacionContrato, Date fechaInicio, Date fechaFin, short estado, Date fechaRealTerminacion, double tiempo, Set valorFormaPagos) {
        this.id = id;
        this.oficioDesempenar = oficioDesempenar;
        this.tiposContrato = tiposContrato;
        this.countryByIdNacionLabores = countryByIdNacionLabores;
        this.recursoHumano = recursoHumano;
        this.countryByIdNacionContrato = countryByIdNacionContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.fechaRealTerminacion = fechaRealTerminacion;
        this.tiempo = tiempo;
        this.valorFormaPagos = valorFormaPagos;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OficioDesempenar getOficioDesempenar() {
        return this.oficioDesempenar;
    }

    public void setOficioDesempenar(OficioDesempenar oficioDesempenar) {
        this.oficioDesempenar = oficioDesempenar;
    }

    public TiposContrato getTiposContrato() {
        return this.tiposContrato;
    }

    public void setTiposContrato(TiposContrato tiposContrato) {
        this.tiposContrato = tiposContrato;
    }

    public Country getCountryByIdNacionLabores() {
        return this.countryByIdNacionLabores;
    }

    public void setCountryByIdNacionLabores(Country countryByIdNacionLabores) {
        this.countryByIdNacionLabores = countryByIdNacionLabores;
    }

    public RecursoHumano getRecursoHumano() {
        return this.recursoHumano;
    }

    public void setRecursoHumano(RecursoHumano recursoHumano) {
        this.recursoHumano = recursoHumano;
    }

    public Country getCountryByIdNacionContrato() {
        return this.countryByIdNacionContrato;
    }

    public void setCountryByIdNacionContrato(Country countryByIdNacionContrato) {
        this.countryByIdNacionContrato = countryByIdNacionContrato;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public short getEstado() {
        return this.estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public Date getFechaRealTerminacion() {
        return this.fechaRealTerminacion;
    }

    public void setFechaRealTerminacion(Date fechaRealTerminacion) {
        this.fechaRealTerminacion = fechaRealTerminacion;
    }

    public double getTiempo() {
        return this.tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public Set getValorFormaPagos() {
        return this.valorFormaPagos;
    }

    public void setValorFormaPagos(Set valorFormaPagos) {
        this.valorFormaPagos = valorFormaPagos;
    }

    /**
     * @return the pagosCancelados
     */
    public short getPagosCancelados() {
        return pagosCancelados;
    }

    /**
     * @param pagosCancelados the pagosCancelados to set
     */
    public void setPagosCancelados(short pagosCancelados) {
        this.pagosCancelados = pagosCancelados;
    }

    /**
     * @return the listvaloFormaPagos
     */
    public //MIOS
            List<ValorFormaPago> getListvaloFormaPagos() {
        return listvaloFormaPagos;
    }

    /**
     * @param listvaloFormaPagos the listvaloFormaPagos to set
     */
    public void setListvaloFormaPagos(List<ValorFormaPago> listvaloFormaPagos) {
        this.listvaloFormaPagos = listvaloFormaPagos;
    }
}


