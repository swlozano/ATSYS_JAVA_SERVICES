package Entidades;
// Generated 22/10/2009 12:43:06 AM by Hibernate Tools 3.2.1.GA



/**
 * FacturaIva generated by hbm2java
 */
public class FacturaIva  implements java.io.Serializable {


     private int id;
     private Factura factura;
     private Iva iva;
     private double valor;

    public FacturaIva() {
    }

    public FacturaIva(int id, Factura factura, Iva iva, double valor) {
       this.id = id;
       this.factura = factura;
       this.iva = iva;
       this.valor = valor;
    }

    public FacturaIva( Factura factura, Iva iva, double valor) {
       
       this.factura = factura;
       this.iva = iva;
       this.valor = valor;
    }
   
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Factura getFactura() {
        return this.factura;
    }
    
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    public Iva getIva() {
        return this.iva;
    }
    
    public void setIva(Iva iva) {
        this.iva = iva;
    }
    public double getValor() {
        return this.valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }




}


