/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOs;

import Entidades.CentroDeCosto;
import Entidades.Movimientos;
import Utilidades.AdException;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.CentroCostoVO;
import VO.RespuestaVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;

/**
 *
 * @author Alejandro
 */
public class CentroCostoDao extends DAO
{

    VO.RespuestaVO respuesta;
    Utilidades utilidades =  new Utilidades();
    CentroDeCosto centroCosto;

    public VO.RespuestaVO create(String nombre, String ciudad) throws AdException
    {
        try
        {
            nombre =  utilidades.formaterTexto(nombre);
           
            respuesta = new RespuestaVO(Mensajes.NO_INGRESO_DATOS,Mensajes.NO_INGRESO_DATOS_MSG);
           begin();
           if(utilidades.buscarPropiedad(Entidades.CentroDeCosto.class,"nombre",nombre,Utilidades.STRING))
           {
               centroCosto = new CentroDeCosto(0, nombre, ciudad);
               getSession().save(centroCosto);
               commit();
               respuesta = new RespuestaVO(Mensajes.INGRESO_DATOS,Mensajes.INGRESO_DATOS_MSG);

           }
           else
           {
                respuesta = new RespuestaVO(Mensajes.NOMBRE_CENTRO_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.NOMBRE_CENTRO_ESTA_ASIGNADO_MSG);
           }
        }
        catch (Exception e)
        {
             rollback();
            throw new AdException("  No se creo el centro de costo " + e);
        }
        finally
        {
            return respuesta;
        }
    }

    public RespuestaVO  actualizar(int id, String nombre, String ciudad) throws AdException
    {
        nombre = utilidades.formaterTexto(nombre);
        respuesta = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS,Mensajes.NO_ACTUALIZO_DATOS_MSG);
        begin();
        centroCosto = new CentroDeCosto();
        centroCosto =   (CentroDeCosto) getSession().get(CentroDeCosto.class, id);
        
        try
        {
            if((utilidades.buscarPropiedad(CentroDeCosto.class,"nombre",nombre,Utilidades.STRING))||centroCosto.getNombre().equals(nombre) )
            {

                centroCosto.setNombre(nombre);
                centroCosto.setCiudad(ciudad);
                getSession().update(centroCosto);
                commit();
                respuesta = new RespuestaVO(Mensajes.ACTUALIZO_DATOS,Mensajes.ACTUALIZO_DATOS_MSG);
            }
            else
            {
                 respuesta = new RespuestaVO(Mensajes.NOMBRE_CENTRO_ESTA_ASIGNADO, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.NOMBRE_CENTRO_ESTA_ASIGNADO_MSG);
            }
        }
        catch (Exception e)
        {
            rollback();
            throw new AdException(" No se modifico el centro de costo " + e);
        }
        finally
        {
            close();
            return respuesta;
        }
    }

    public VO.RespuestaVO eliminar(int id) throws AdException
    {
        respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        try
        {
            begin();
            centroCosto = new CentroDeCosto();
            centroCosto =    (CentroDeCosto) getSession().get(CentroDeCosto.class, id);

            if(utilidades.buscarPropiedad(Movimientos.class,"centroDeCosto",centroCosto, Utilidades.OBJECT))
            {
                CajaMenorDao cajaMenorDao = new CajaMenorDao();
                int[] ids = cajaMenorDao.getCentroCostoYPersonaEmpresa();

                if(id != ids[1])
                {
                    begin();
                    getSession().delete(getSession().get(CentroDeCosto.class, id));
                    commit();
                    respuesta = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
                }
                else
                {
                    respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS,Mensajes.NO_ELIMINO_DATOS_MSG +" - " + Mensajes.CENTRO_PERTECE_A_LA_EMPRESA_MSG);
                }
             }
            else
            {
                respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS,Mensajes.NO_ELIMINO_DATOS_MSG +" - " + Mensajes.CENTRO_TIENE_ASIGNADOS_USUARIOS_MSG);
            }
        }
        catch (Exception e)
        {
            rollback();
            throw new AdException(" No se elimino el centro de costo " + e);
        }
        finally
        {
            close();
            return  respuesta ;
        }

    }

    public List listar() throws AdException
    {
        //respuesta = new RespuestaVO(Mensajes.NO_OBTUVO_DATOS, Mensajes.NO_OBTUVO_DATOS_MSG);
       List<CentroDeCosto> centroDeCostos = new ArrayList<CentroDeCosto>();
      
        try
        {
            begin();
            Criteria criteria = getSession().createCriteria(CentroDeCosto.class);
            centroDeCostos = criteria.list();
            commit();

            for (Iterator<CentroDeCosto> it = centroDeCostos.iterator(); it.hasNext();)
            {
                 it.next().setMovimientoses(null);
            }
        }
        catch (HibernateException e)
        {
            centroDeCostos = null;
            rollback();
            throw new AdException(" No se obtuvieron la lista de roles " + e);
        }
        finally
        {
            //close();
            if(centroDeCostos.isEmpty())
                centroDeCostos = null;
            return centroDeCostos;
        }

    }

    private List<CentroCostoVO> convertCentrosCostosToCentrosCostosVO(List<CentroDeCosto> centroDeCostos)
    {
        List<CentroCostoVO> centroCostoVOs = new ArrayList<CentroCostoVO>();
        for (CentroDeCosto centroDeCosto : centroDeCostos)
        {
           centroCostoVOs.add(convertCentroCostoToCentroCostoVO(centroDeCosto));
        }
        return centroCostoVOs;

    }
    private CentroCostoVO convertCentroCostoToCentroCostoVO(CentroDeCosto centroDeCosto)
    {
        return new CentroCostoVO(centroDeCosto.getId(), centroDeCosto.getNombre(),centroDeCosto.getCiudad());
    }
}
