package Entidades;
// Generated 5/11/2009 04:41:29 PM by Hibernate Tools 3.2.1.GA



/**
 * VfpRetPagos generated by hbm2java
 */
public class VfpRetPagos  implements java.io.Serializable {


     private int id;
     private ValorFormaPago valorFormaPago;
     private RetencionPagos retencionPagos;

    public VfpRetPagos() {
    }

    public VfpRetPagos(int id, ValorFormaPago valorFormaPago, RetencionPagos retencionPagos) {
       this.id = id;
       this.valorFormaPago = valorFormaPago;
       this.retencionPagos = retencionPagos;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public ValorFormaPago getValorFormaPago() {
        return this.valorFormaPago;
    }
    
    public void setValorFormaPago(ValorFormaPago valorFormaPago) {
        this.valorFormaPago = valorFormaPago;
    }
    public RetencionPagos getRetencionPagos() {
        return this.retencionPagos;
    }
    
    public void setRetencionPagos(RetencionPagos retencionPagos) {
        this.retencionPagos = retencionPagos;
    }




}

