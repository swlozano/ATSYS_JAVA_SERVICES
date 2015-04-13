package Entidades;
// Generated 9/11/2009 07:01:15 PM by Hibernate Tools 3.2.1.GA



/**
 * CausacionBonificacion generated by hbm2java
 */
public class CausacionBonificacion  implements java.io.Serializable {


     private short id;
     private CausacionNomina causacionNomina;
     private Bonificacion bonificacion;

    public CausacionBonificacion() {
    }

    public CausacionBonificacion( CausacionNomina causacionNomina, Bonificacion bonificacion) {
       
       this.causacionNomina = causacionNomina;
       this.bonificacion = bonificacion;
    }
    public CausacionBonificacion(short id, CausacionNomina causacionNomina, Bonificacion bonificacion) {
       this.id = id;
       this.causacionNomina = causacionNomina;
       this.bonificacion = bonificacion;
    }
   
    public short getId() {
        return this.id;
    }
    
    public void setId(short id) {
        this.id = id;
    }
    public CausacionNomina getCausacionNomina() {
        return this.causacionNomina;
    }
    
    public void setCausacionNomina(CausacionNomina causacionNomina) {
        this.causacionNomina = causacionNomina;
    }
    public Bonificacion getBonificacion() {
        return this.bonificacion;
    }
    
    public void setBonificacion(Bonificacion bonificacion) {
        this.bonificacion = bonificacion;
    }




}


