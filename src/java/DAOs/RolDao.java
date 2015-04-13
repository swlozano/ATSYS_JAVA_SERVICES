/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOs;

import Entidades.Permisos;
import Entidades.UsuarioSistemaRol;
import Entidades.UsuarioSys;
import Utilidades.DAO;
import  Entidades.Rol;
import Entidades.RolPermisos;
import Utilidades.AdException;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.PermisoVO;
import VO.RespuestaVO;
import VO.Rol_VO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.bcel.generic.ARRAYLENGTH;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class RolDao extends DAO
{

    private UsuarioSistemaRol usuarioSysRol;
    private RolPermisos rolPermisos;
    VO.RespuestaVO respuesta;
    Utilidades utilidades =  new Utilidades();
    Entidades.Rol rol;

    public VO.RespuestaVO asignarRolToUsuario(int idUsu,short[] idsRoles) throws AdException
    {
        try
        {
            respuesta = new RespuestaVO(Mensajes.NO_ASIGNO_ROL_TO_US, Mensajes.NO_ASIGNO_ROL_TO_US_MSG);
            UsuarioSys usys = new UsuarioSys(idUsu, "", "",0, "", "","",(short)0);
            for (short s : idsRoles)
            {
                usuarioSysRol = new UsuarioSistemaRol(0, usys ,new Rol(s, ""));
                List list =  getSession().createSQLQuery("SELECT * FROM USUARIO_SISTEMA_ROL WHERE USUARIO_SISTEMA_ROL.ID_ROL =" + s + "and USUARIO_SISTEMA_ROL.ID_USUARIO_SISTEMA =" + idUsu ).list();
                if(list.isEmpty())
                    getSession().save(usuarioSysRol);
            }
          
            respuesta = new RespuestaVO(Mensajes.ASIGNO_ROL_TO_US, Mensajes.ASIGNO_ROL_TO_US_MSG);

        }
        catch (Exception e)
        {
            rollback();
            throw new AdException(" No se creo el usuario " + e);
        }
        finally
        {
            
            return respuesta;
        }
    }
    public VO.RespuestaVO create(String nombre,short[] idPermisos) throws AdException
    {
        RespuestaVO respuestaVO = new RespuestaVO();
        try
        {
           respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS,Mensajes.NO_INGRESO_DATOS_MSG);
           begin();
           if(utilidades.buscarPropiedad(Entidades.Rol.class,"nombre",nombre,Utilidades.STRING))
           {
               rol = new Rol((short)0, nombre);
               getSession().save(rol);
               commit();
               respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS,Mensajes.INGRESO_DATOS_MSG);

                Criteria criteria = getSession().createCriteria(Rol.class);
                criteria.add(Restrictions.eq("nombre", nombre));
                List<Rol> list = criteria.list();
                if(!list.isEmpty())
                {
                    this.asignarPermisosToRol(list.get(0).getId(), idPermisos);
                }
                System.out.print("");

           }
           else
           {
                respuestaVO = new RespuestaVO(Mensajes.NOMBRE_ROL_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.NOMBRE_ROL_ESTA_ASIGNADO_MSG);
           }
        }
        catch (Exception e)
        {
             rollback();
            throw new AdException("  No se creo el rol " + e);
        }
        finally
        {
            return respuestaVO;
        }
    }

    public RespuestaVO  actualizar(int id, String nombre,short[] idPermisos) throws AdException
    {
        try
        {
            respuesta = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS,Mensajes.NO_ACTUALIZO_DATOS_MSG);

            if(getSession().isOpen())
                close();
           
            begin();
            rol = new Rol();
            Criteria c = getSession().createCriteria(Rol.class);
            c.add(Restrictions.eq("id",(short) id));
            rol =  (Rol) c.uniqueResult();
            System.out.println("xc");
            if((utilidades.buscarPropiedad(Rol.class,"nombre",nombre,Utilidades.STRING))||rol.getNombre().equals(nombre) )
            {
                rol.setNombre(nombre);
                getSession().update(rol);
                commit();
                respuesta = new RespuestaVO(Mensajes.ACTUALIZO_DATOS,Mensajes.ACTUALIZO_DATOS_MSG);
               Query query = getSession().createSQLQuery("DELETE from ROL_PERMISOS where ROL_PERMISOS.ID_ROL =" + (short) id);
               //query.setShort("id", (short) id);
               query.executeUpdate();
               //commit();
               this.asignarPermisosToRol((short)id,idPermisos);

            }
            else
            {
                 respuesta = new RespuestaVO(Mensajes.NOMBRE_ROL_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.NOMBRE_ROL_ESTA_ASIGNADO_MSG);
            }
        }
        catch (Exception e)
        {
            rollback();
            throw new AdException(" No se modifico el rolusuario " + e);
        }
        finally
        {
            close();
            return respuesta;
        }
    }

    public VO.RespuestaVO eliminar(short id) throws AdException
    {
        respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        try
        {
            begin();
            rol = new Rol();
            rol =    (Rol) getSession().get(Rol.class, id);

            if(utilidades.buscarPropiedad(UsuarioSistemaRol.class,"rol",rol, Utilidades.OBJECT))
            {
                getSession().delete(getSession().get(Rol.class, id));
                commit();
                respuesta = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
            }
            else
            {
                respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS,Mensajes.NO_ELIMINO_DATOS_MSG +" - " + Mensajes.ROL_TIENE_ASIGNADOS_USUARIOS_MSG);
            }
        }
        catch (Exception e)
        {
            rollback();
            throw new AdException(" No se elimino el rol " + e);
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
       List<Rol> roles = new ArrayList<Rol>();
       List<Rol_VO> rol_VOs = new ArrayList<Rol_VO>();
       
        try
        {
           if(getSession().isOpen())
                close();

            begin();
            Criteria criteria = getSession().createCriteria(Rol.class);
            roles = criteria.list();
            commit();
            rol_VOs = this.convertRolesToRoles_VO(roles);

        }
        catch (HibernateException e)
        {
            rol_VOs = null;
            
            rollback();
            throw new AdException(" No se obtuvieron la lista de roles " + e);
        }
        finally
        {
           
            return rol_VOs;
        }

    }

    public VO.RespuestaVO asignarPermisosToRol(short idRol,short[] idPermisos) throws AdException
    {
        try
        {
            respuesta = new RespuestaVO(Mensajes.NO_ASIGNO_PERMISOS_TO_ROL, Mensajes.NO_ASIGNO_PERMISOS_TO_ROL_MSG);
            begin();
            Rol rol = new Rol(idRol,"");
            for (short s : idPermisos)
            {
                rolPermisos = new RolPermisos(0, rol, new Permisos(s,""));
                List list =  getSession().createSQLQuery("SELECT * FROM ROL_PERMISOS WHERE ROL_PERMISOS.ID_PERMISO =" + s + "and ROL_PERMISOS.ID_ROL =" + idRol ).list();
                if(list.isEmpty())
                    getSession().save(rolPermisos);
            }
            commit();
            respuesta = new RespuestaVO(Mensajes.ASIGNO_PERMISOS_TO_ROL, Mensajes.ASIGNO_PERMISOS_TO_ROL_MSG);

        }
        catch (Exception e)
        {
            rollback();
            throw new AdException(" No se asigno el rol a usuario " + e);
        }
        finally
        {
            close();
            return respuesta;
        }
    }

    public List listarPermisos()
    {

       List<Permisos>  permisoses = new ArrayList<Permisos>();
       List<PermisoVO> permisoVOs = new ArrayList<PermisoVO>();
        try
        {
           if(getSession().isOpen())
                close();

            begin();
            Criteria criteria = getSession().createCriteria(Permisos.class);
            permisoses = criteria.list();
            commit();
            permisoVOs = this.convertPermisosToPermisosVO(permisoses);
        }
        catch (HibernateException e)
        {

            permisoVOs = null;
            rollback();
            throw new AdException(" No se obtuvieron la lista de roles " + e);
        }
        finally
        {
            //close();
            return permisoVOs;
        }
    }

    private  List<Rol_VO>  convertRolesToRoles_VO(List<Rol> rols)
    {
        UsuarioSistemaDao usuarioSistemaDao = new UsuarioSistemaDao();
        List<Rol_VO> rol_VOs = new ArrayList<Rol_VO>();
        
        for (Rol rolx : rols)
        {
            Rol_VO rol_VO  = new Rol_VO(rolx.getId(),rolx.getNombre(),usuarioSistemaDao.covertSetRolPermisosesToListaRolPermisosesVO(rolx.getRolPermisoses()));
            rol_VOs.add(rol_VO);
        }
        return rol_VOs;
    }

    private List<PermisoVO> convertPermisosToPermisosVO(List<Permisos> permisoses)
    {
        List<PermisoVO> permisoVOs = new ArrayList<PermisoVO>();
        for (Permisos permisos : permisoses)
        {
            PermisoVO permisoVO = new PermisoVO(permisos.getId(),permisos.getNombre());
            permisoVOs.add(permisoVO);
        }
        return permisoVOs;
    }
}
