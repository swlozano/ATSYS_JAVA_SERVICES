/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

/**
 *
 * @author Alejandro
 */
public class NotificacionCajaMenorsVO {
    
     private int id;
     private short esResponsable;
     private UsuarioSistemaVO usuarioSys;

    public NotificacionCajaMenorsVO() {
        
    }

     public NotificacionCajaMenorsVO(int id,short esResponsable,UsuarioSistemaVO usuarioSys)
     {
        this.id = id;
        this.esResponsable = esResponsable;
        this.usuarioSys = usuarioSys;
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
     * @return the esResponsable
     */
    public short getEsResponsable() {
        return esResponsable;
    }

    /**
     * @param esResponsable the esResponsable to set
     */
    public void setEsResponsable(short esResponsable) {
        this.esResponsable = esResponsable;
    }

     /**
     * @return the usuarioSys
     */
    public UsuarioSistemaVO getUsuarioSys() {
        return usuarioSys;
    }

    /**
     * @param usuarioSys the usuarioSys to set
     */
    public void setUsuarioSys(UsuarioSistemaVO usuarioSys) {
        this.usuarioSys = usuarioSys;
    }
}
