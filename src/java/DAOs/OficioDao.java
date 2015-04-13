/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.OficioDesempenar;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.RespuestaVO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class OficioDao extends DAO {

    Utilidades utilidades = new Utilidades();

    public RespuestaVO crear(String nombre, String objeto) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
        try {
            begin();
            if (!utilidades.buscarPropiedad(OficioDesempenar.class, "nombre", nombre, Utilidades.STRING)) {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.NOMBRE_OFICIO_YA_EXISTE);
            } else {
                getSession().save(new OficioDesempenar(nombre, objeto));
                commit();
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_ABONO_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO actualizar(int id, String nombre, String objeto) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
        try {
            begin();
            OficioDesempenar oficioDesempenar = new OficioDesempenar();
            oficioDesempenar = (OficioDesempenar) utilidades.getObjetoEspecifico(OficioDesempenar.class, "id", id, Utilidades.INT);

            if (utilidades.buscarPropiedad(OficioDesempenar.class, "nombre", nombre, Utilidades.STRING) || oficioDesempenar.getNombre().equals(nombre)) {
                oficioDesempenar.setNombre(nombre);
                oficioDesempenar.setObjeto(objeto);
                getSession().update(oficioDesempenar);
                commit();
                respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.NOMBRE_OFICIO_YA_EXISTE);
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public List<OficioDesempenar> listar() {
        List<OficioDesempenar> oficioDesempenars = new ArrayList<OficioDesempenar>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(OficioDesempenar.class);
            oficioDesempenars = criteria.list();
            oficioDesempenars = this.normalizarListaOficios(oficioDesempenars);
        } catch (Exception e) {
            oficioDesempenars = null;
        } finally {
            close();
            return oficioDesempenars;
        }
    }

    private List<OficioDesempenar> normalizarListaOficios(List<OficioDesempenar> oficioDesempenars) {
        List<OficioDesempenar> oficioDesempenars1 = new ArrayList<OficioDesempenar>();

        for (OficioDesempenar oficioDesempenar : oficioDesempenars) {
            OficioDesempenar od = new OficioDesempenar(oficioDesempenar.getId(), oficioDesempenar.getNombre(), oficioDesempenar.getObjeto());
            oficioDesempenars1.add(od);
        }
        return oficioDesempenars1;
    }

    public RespuestaVO eliminar(int idOficio) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Entidades.Contrato.class);
            criteria.add(Restrictions.eq("oficioDesempenar", new OficioDesempenar(idOficio)));
            List listaContratos = criteria.list();
            if (listaContratos.isEmpty()) {
                criteria = getSession().createCriteria(OficioDesempenar.class);
                criteria.add(Restrictions.eq("id", idOficio));
                OficioDesempenar oficioDesempenar = (OficioDesempenar) criteria.uniqueResult();
                getSession().delete(oficioDesempenar);
                commit();
                respuestaVO = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.OFICIO_DESEMPENAR_ES_UTILIZADO);
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA +  Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }
}
