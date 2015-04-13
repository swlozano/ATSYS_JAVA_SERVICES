/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOs;

import Utilidades.DAO;
import Utilidades.Mensajes;
import VO.RespuestaVO;

/**
 *
 * @author Alejandro
 */
public class Formato extends DAO
{
    public RespuestaVO actualizar()
    {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS,Mensajes.NO_ACTUALIZO_DATOS_MSG);
        try
        {
            begin();
        }
        catch (Exception e)
        {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS,Mensajes.NO_ACTUALIZO_DATOS_MSG +Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        }
        finally
        {
            close();
            return respuestaVO;
        }
    }
}
