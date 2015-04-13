/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VO;

import java.util.List;

/**
 *
 * @author Alejandro
 */
public class RespuestaVO
{
    private short  idRespuesta;
    private String mensajeRespuesta;
    private int[] idsFechasCorteCausacion;
    private List<ValorFormaFechasPagoVO> valorFormaFechasPagoVOs;

    public RespuestaVO(short idRespuesta, String mensajeRespuesta, int[] idsFechasCorteCausacion) {
        this.idRespuesta = idRespuesta;
        this.mensajeRespuesta = mensajeRespuesta;
        this.idsFechasCorteCausacion = idsFechasCorteCausacion;
    }

    public RespuestaVO(short idRespuesta, String mensajeRespuesta, List<ValorFormaFechasPagoVO> valorFormaFechasPagoVOs) {
        this.idRespuesta = idRespuesta;
        this.mensajeRespuesta = mensajeRespuesta;
        this.valorFormaFechasPagoVOs = valorFormaFechasPagoVOs;
    }



    /**
     * @return the idRespuesta
     */

    
    public RespuestaVO()
    {

    }
    public RespuestaVO(short  idRespuesta, String mensajeRespuesta)
    {
        this.idRespuesta=idRespuesta;
        this.mensajeRespuesta=mensajeRespuesta;
    }

    public short  getIdRespuesta() {
        return idRespuesta;
    }


    /**
     * @param idRespuesta the idRespuesta to set
     */
    public void setIdRespuesta(short  idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    /**
     * @return the mensajeRespuesta
     */
    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    /**
     * @param mensajeRespuesta the mensajeRespuesta to set
     */
    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    /**
     * @return the idsFechasCorteCausacion
     */
    public int[] getIdsFechasCorteCausacion() {
        return idsFechasCorteCausacion;
    }

    /**
     * @param idsFechasCorteCausacion the idsFechasCorteCausacion to set
     */
    public void setIdsFechasCorteCausacion(int[] idsFechasCorteCausacion) {
        this.idsFechasCorteCausacion = idsFechasCorteCausacion;
    }

    /**
     * @return the valorFormaFechasPagoVOs
     */
    public List<ValorFormaFechasPagoVO> getValorFormaFechasPagoVOs() {
        return valorFormaFechasPagoVOs;
    }

    /**
     * @param valorFormaFechasPagoVOs the valorFormaFechasPagoVOs to set
     */
    public void setValorFormaFechasPagoVOs(List<ValorFormaFechasPagoVO> valorFormaFechasPagoVOs) {
        this.valorFormaFechasPagoVOs = valorFormaFechasPagoVOs;
    }
}
