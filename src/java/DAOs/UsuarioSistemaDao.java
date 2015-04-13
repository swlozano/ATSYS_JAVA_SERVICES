/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Movimientos;
import Entidades.NotificacionCajaMenor;
import Entidades.Permisos;
import Entidades.Rol;
import Entidades.RolPermisos;
import Entidades.UsuarioSistemaRol;
import Entidades.UsuarioSys;
import Utilidades.AdException;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.PermisoVO;
import VO.RespuestaVO;
import VO.RolPermisosesVO;
import VO.UsuarioSistemaRolVO;
import VO.UsuarioSistemaVO;
import VO.Rol_VO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class UsuarioSistemaDao extends DAO {

    public UsuarioSistemaDao() {
    }
    VO.RespuestaVO respuesta;
    Utilidades utilidades = new Utilidades();
    Entidades.UsuarioSys usuSys = null;

    public VO.RespuestaVO create(String nombre, String apellido, int cedula, String login, String contrasena, String correoElectronico, short[] idRoles) throws AdException {
        RolDao rolDao;
        login = login.toLowerCase();
        correoElectronico = correoElectronico.toLowerCase();
        try {
            respuesta = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
            begin();
            if (utilidades.buscarPropiedad(UsuarioSys.class, "login", login, Utilidades.STRING)) {
                if (utilidades.buscarPropiedad(UsuarioSys.class, "cedula", cedula, Utilidades.INT)) {
                    if (utilidades.buscarPropiedad(UsuarioSys.class, "correoElectronico", correoElectronico, Utilidades.STRING)) {
                        usuSys = new UsuarioSys(0, nombre, apellido, cedula, login, contrasena, correoElectronico, (short) 0);
                        getSession().save(usuSys);
                        respuesta = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);

                        Criteria criteria = getSession().createCriteria(UsuarioSys.class);
                        criteria.add(Restrictions.eq("login", login));
                        criteria.add(Restrictions.eq("contrasena", contrasena));
                        List<UsuarioSys> list = criteria.list();
                        if (!list.isEmpty()) {
                            rolDao = new RolDao();
                            rolDao.asignarRolToUsuario(list.get(0).getId(), idRoles);
                        }
                        System.out.print("");
                    } else {
                        respuesta = new RespuestaVO(Mensajes.CORREO_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.CORREO_ESTA_ASIGNADO_MSG);
                    }
                } else {
                    respuesta = new RespuestaVO(Mensajes.CEDULA_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.CEDULA_ESTA_ASIGNADO_MSG);
                }
            } else {
                respuesta = new RespuestaVO(Mensajes.LOGIN_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.LOGIN_ESTA_ASIGNADO_MSG);
            }
        } catch (HibernateException e) {
            rollback();
            throw new AdException(" No se creo el usuario " + e);
        } finally {
            commit();
            close();
            return respuesta;
        }
    }

    public RespuestaVO actualizar(int id, String nombre, String apellido, int cedula, String login, String contrasena, String correoElectronico, short[] idRoles) throws AdException {
        RolDao rolDao;
        String msgAlterno = "";
        List l1;
        try {
            respuesta = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
            begin();
            usuSys = (UsuarioSys) getSession().get(UsuarioSys.class, id);

            if ((utilidades.buscarPropiedad(UsuarioSys.class, "login", login, Utilidades.STRING)) || usuSys.getLogin().equals(login)) {
                if ((utilidades.buscarPropiedad(UsuarioSys.class, "cedula", cedula, Utilidades.INT)) || usuSys.getCedula() == cedula) {
                    if ((utilidades.buscarPropiedad(UsuarioSys.class, "correoElectronico", correoElectronico, Utilidades.STRING)) || usuSys.getCorreoElectronico().equals(correoElectronico)) {

                        Criteria c = getSession().createCriteria(Movimientos.class);
                        c.add(Restrictions.eq("idUsuario", id));
                        l1 = c.list();

                        if (l1.isEmpty()) {
                            usuSys.setCedula(cedula);
                            usuSys.setLogin(login);

                        } else {
                            msgAlterno = "No actualizo la cedula y el login: debido a que el usuario ha realizado movimientos de caja menor";
                        }

                        usuSys.setApellido(apellido);
                        usuSys.setContrasena(contrasena);
                        usuSys.setCorreoElectronico(correoElectronico);
                        usuSys.setNombre(nombre);

                        getSession().update(usuSys);

                        Criteria criteria = getSession().createCriteria(UsuarioSys.class);
                        criteria.add(Restrictions.eq("login", login));
                        criteria.add(Restrictions.eq("contrasena", contrasena));
                        List<UsuarioSys> list = criteria.list();
                        if (!list.isEmpty()) {
                            String hql = "DELETE from USUARIO_SISTEMA_ROL where USUARIO_SISTEMA_ROL.ID_USUARIO_SISTEMA = :id";
                            Query query = getSession().createSQLQuery(hql);
                            query.setInteger("id", id);
                            int row = query.executeUpdate();
                            commit();
                            begin();
                            rolDao = new RolDao();
                            rolDao.asignarRolToUsuario(list.get(0).getId(), idRoles);
                        }
                        System.out.print("");
                        respuesta = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
                        commit();
                    } else {
                        respuesta = new RespuestaVO(Mensajes.CORREO_ESTA_ASIGNADO, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.CORREO_ESTA_ASIGNADO_MSG);
                    }
                } else {
                    respuesta = new RespuestaVO(Mensajes.CEDULA_ESTA_ASIGNADO, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.CEDULA_ESTA_ASIGNADO_MSG);
                }
            } else {
                respuesta = new RespuestaVO(Mensajes.LOGIN_ESTA_ASIGNADO, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.LOGIN_ESTA_ASIGNADO_MSG);
            }

        } catch (HibernateException e) {
            rollback();
            throw new AdException(" No se modifico el usuario " + e);
        } finally {

            close();
            return respuesta;
        }
    }

    public VO.RespuestaVO eliminar(int id) throws AdException {
        respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        try {
            begin();
            usuSys = new UsuarioSys();
            usuSys = (UsuarioSys) getSession().get(UsuarioSys.class, id);

            if (utilidades.buscarPropiedad(UsuarioSistemaRol.class, "usuarioSys", usuSys, Utilidades.OBJECT)) {

                Criteria criteria = getSession().createCriteria(NotificacionCajaMenor.class);
                criteria.add(Restrictions.eq("usuarioSys", new UsuarioSys(id)));
                criteria.add(Restrictions.eq("esResponsable", (short) 1));

                List lista = criteria.list();

                if (lista.isEmpty()) {
                    getSession().delete(getSession().get(UsuarioSys.class, id));
                    commit();
                    respuesta = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
                } else {
                    respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.USUARIO_TIENE_ASIGNADOS_CAJA_MENOR_MSG);
                }
            } else {
                respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.USUARIO_TIENE_ASIGNADOS_ROLES_MSG);
            }


        } catch (HibernateException e) {
            rollback();
            throw new AdException(" No se elimino el usuario " + e);
        } finally {
            close();
            return respuesta;
        }
    }

    public List listar() throws AdException {

        List<UsuarioSys> usuarios = new ArrayList<UsuarioSys>();
        List<UsuarioSistemaVO> usuarioSistemaVOs = new ArrayList();
        try {

            begin();
            Criteria criteria = getSession().createCriteria(UsuarioSys.class);
            usuarios = criteria.list();
            commit();
            usuarioSistemaVOs = covertListUsuariosSysToUsuarioSistemaVO(usuarios);

        } catch (HibernateException e) {
            usuarios = null;
            rollback();
            throw new AdException(" No se obtuvieron la lista de usuarios " + e);
        } finally {
            //close();
            if (usuarios.isEmpty()) {
                usuarios = null;
            }
            return usuarioSistemaVOs;
            //return reporteMovimientosVO;

        }

    }

    public UsuarioSistemaVO ingresoSistema(String login, String contrasena) {
        UsuarioSys us = new UsuarioSys();
        UsuarioSistemaVO usuarioSistemaVO = new UsuarioSistemaVO();

        List<RolPermisosesVO> borrar = new ArrayList<RolPermisosesVO>();
        try {
            login = login.toLowerCase();
            begin();
            Criteria criteria = getSession().createCriteria(UsuarioSys.class);
            criteria.add(Restrictions.eq("login", login));
            criteria.add(Restrictions.like("contrasena", contrasena));
            us = (UsuarioSys) criteria.uniqueResult();
            us.setNotificacionCajaMenors(null);
            Set setListaUsuarioSistemaRoles = us.getUsuarioSistemaRols();

            List<UsuarioSistemaRolVO> usuarioSistemaRolVOs = new ArrayList<UsuarioSistemaRolVO>();
            UsuarioSistemaRolVO usuarioSistemaRolVO = new UsuarioSistemaRolVO();
            List<RolPermisosesVO> rolPermisosesVOs = new ArrayList<RolPermisosesVO>();
            RolPermisosesVO rolPermisosesVO;
            PermisoVO permisoVO = new PermisoVO();

            for (Object setUsuarioSistemaRol : setListaUsuarioSistemaRoles) {
                UsuarioSistemaRol usuarioSistemaRol = (UsuarioSistemaRol) setUsuarioSistemaRol;
                Rol rol = usuarioSistemaRol.getRol();
                Set setRolPermisos = rol.getRolPermisoses();
                rolPermisosesVOs = new ArrayList<RolPermisosesVO>();
                for (Object setRolPermiso : setRolPermisos) {
                    RolPermisos rolPermisos = new RolPermisos();
                    rolPermisos = (RolPermisos) setRolPermiso;
                    Permisos permisos = new Permisos();
                    permisos = rolPermisos.getPermisos();
                    permisoVO = new PermisoVO(permisos.getId(), permisos.getNombre());
                    rolPermisosesVO = new RolPermisosesVO(rolPermisos.getId(), permisoVO);
                    rolPermisosesVOs.add(rolPermisosesVO);
                }
                borrar = rolPermisosesVOs;
                Rol_VO pVO = new Rol_VO(rol.getId(), rol.getNombre(), borrar);
                //rolVO = new RolVO(rol.getId(),rol.getNombre(),""/*,rolPermisosesVOs*/);
                usuarioSistemaRolVO = new UsuarioSistemaRolVO(usuarioSistemaRol.getId(), pVO);
                usuarioSistemaRolVOs.add(usuarioSistemaRolVO);
                System.out.print("");

            }

            usuarioSistemaVO.setUsuarioSistemaRols(usuarioSistemaRolVOs);
            usuarioSistemaVO.setApellido(us.getApellido());
            usuarioSistemaVO.setCedula(us.getCedula());
            usuarioSistemaVO.setContrasena(us.getContrasena());
            usuarioSistemaVO.setCorreoElectronico(us.getCorreoElectronico());
            usuarioSistemaVO.setId(us.getId());
            usuarioSistemaVO.setLogin(us.getLogin());
            usuarioSistemaVO.setNombre(us.getNombre());
            usuarioSistemaVO.setIsAdmin(us.getIsAdmin());


            //commit();
        } catch (Exception e) {
            us = null;
            usuarioSistemaVO = null;
        } finally {

            //return usuarioSistemaVO;
            return usuarioSistemaVO;
        }
    }

    private List<UsuarioSistemaVO> covertListUsuariosSysToUsuarioSistemaVO(List<UsuarioSys> usuarioSyses) {
        List<UsuarioSistemaVO> usuarioSistemaVOs = new ArrayList<UsuarioSistemaVO>();
        for (UsuarioSys usuarioSys : usuarioSyses) {
            if (usuarioSys.getIsAdmin() != 1) {
                UsuarioSistemaVO usuarioSistemaVO = new UsuarioSistemaVO(usuarioSys.getId(), usuarioSys.getNombre(), usuarioSys.getApellido(), usuarioSys.getCedula(), usuarioSys.getLogin(), usuarioSys.getContrasena(), usuarioSys.getCorreoElectronico(), covertSetUsuariosSistemaRolToListUsuarioSistemaRolVO(usuarioSys.getUsuarioSistemaRols()), usuarioSys.getIsAdmin());
                usuarioSistemaVOs.add(usuarioSistemaVO);
            }
        }
        return usuarioSistemaVOs;
    }

    private List<UsuarioSistemaRolVO> covertSetUsuariosSistemaRolToListUsuarioSistemaRolVO(Set SetUsuarioSistemaRols) {
        List<UsuarioSistemaRolVO> usuarioSistemaRolVOs = new ArrayList<UsuarioSistemaRolVO>();
        for (Object setUsuarioSistemaRol : SetUsuarioSistemaRols) {
            UsuarioSistemaRol usuarioSistemaRol = new UsuarioSistemaRol();
            usuarioSistemaRol = (UsuarioSistemaRol) setUsuarioSistemaRol;
            usuarioSistemaRolVOs.add(covertUsuariosSistemaRolToUsuarioSistemaRolVO(usuarioSistemaRol));
        }
        return usuarioSistemaRolVOs;
    }

    private UsuarioSistemaRolVO covertUsuariosSistemaRolToUsuarioSistemaRolVO(UsuarioSistemaRol usuarioSistemaRol) {
        return new UsuarioSistemaRolVO(usuarioSistemaRol.getId(), this.covertRolToRol_VO(usuarioSistemaRol.getRol()));
    }

    private Rol_VO covertRolToRol_VO(Rol rol) {
        return new Rol_VO(rol.getId(), rol.getNombre(), covertSetRolPermisosesToListaRolPermisosesVO(rol.getRolPermisoses()));
    }

    private RolPermisosesVO covertRolPermisosToRolPermisosVO(RolPermisos rolPermisos) {
        return new RolPermisosesVO(rolPermisos.getId(), covertPermisosToPermisosVO(rolPermisos.getPermisos()));
    }

    private PermisoVO covertPermisosToPermisosVO(Permisos permisos) {
        return new PermisoVO(permisos.getId(), permisos.getNombre());
    }

    public List<RolPermisosesVO> covertSetRolPermisosesToListaRolPermisosesVO(Set setRolPermisoses) {
        List<RolPermisosesVO> rolPermisosesVOs = new ArrayList<RolPermisosesVO>();
        for (Object setRolPermiso : setRolPermisoses) {
            RolPermisos rolPermisos = new RolPermisos();
            rolPermisos = (RolPermisos) setRolPermiso;
            rolPermisosesVOs.add(this.covertRolPermisosToRolPermisosVO(rolPermisos));
        }
        return rolPermisosesVOs;
    }

    public UsuarioSistemaVO convertUsuarioSistemaToUsuarioSistemaVO(UsuarioSys usuarioSys) {
        return new UsuarioSistemaVO(usuarioSys.getId(), usuarioSys.getNombre(), usuarioSys.getIsAdmin(), usuarioSys.getApellido());
    }
}
