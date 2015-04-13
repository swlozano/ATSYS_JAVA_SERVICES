/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Movimientos;
import Entidades.Persona;
import Utilidades.AdException;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.PersonaVO;
import VO.RespuestaVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class PersonaDao extends DAO {

    private VO.RespuestaVO respuesta;
    private Utilidades utilidades = new Utilidades();
    private Persona persona;

    public VO.RespuestaVO create(BigDecimal nit, Integer cedula, String telefonoStr, String nombreRs, short es_usuario, String cual, String numeroCual) throws AdException {
        int telefono = 0;
        if (!telefonoStr.equals("")) {
            float tel = Float.parseFloat(telefonoStr);
            telefono = Math.round(tel);
        }

        String nitStr = nit + "";

        if (nitStr.equals("0.0")) {
            nitStr = "0";
        }

        try {
            respuesta = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
            begin();
            if (utilidades.buscarPropiedad(Persona.class, "nit", nit, Utilidades.BIGDECIMAL) || nitStr.equals("0")) {
                if (utilidades.buscarPropiedad(Persona.class, "cedula", cedula, Utilidades.INT) || cedula == 0) {
                    if (utilidades.buscarPropiedad(Persona.class, "nombreRs", nombreRs, Utilidades.STRING)) {
                        persona = new Persona();
                        persona.setCedula(cedula);
                        persona.setNit(nit);
                        persona.setNombreRs(nombreRs);
                        persona.setTelefono(telefono);
                        persona.setEsUsuario(es_usuario);
                        persona.setCual(cual);
                        persona.setNumeroCual(numeroCual);
                        getSession().save(persona);
                        commit();
                        respuesta = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);

                    } else {
                        respuesta = new RespuestaVO(Mensajes.RAZON_SOCIAL_PERSONA_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.RAZON_SOCIAL_PERSONA_ESTA_ASIGNADO_MSG);
                    }
                } else {
                    respuesta = new RespuestaVO(Mensajes.CEDULA_PERSONA_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.CEDULA_PERSONA_ESTA_ASIGNADO_MSG);
                }

            } else {
                respuesta = new RespuestaVO(Mensajes.NIT_PERSONA_ESTA_ASIGNADO, Mensajes.NO_INGRESO_DATOS_MSG + " - " + Mensajes.NIT_PERSONA_ESTA_ASIGNADO_MSG);
            }
        } catch (Exception e) {
            System.out.print(e);
        } finally {
            close();
            return respuesta;
        }
    }

    public RespuestaVO actualizar(int id, BigDecimal nit, Integer cedula, String telefonoStr, String nombreRs, String cual, String numeroCual) throws AdException {
        String msgAlterno = "";
        int telefono = 0;
        if (!telefonoStr.equals("")) {
            float tel = Float.parseFloat(telefonoStr);
            telefono = Math.round(tel);
        }

        String nitStr = nit + "";

        if (nitStr.equals("0.0")) {
            nitStr = "0";
        }
        try {
            respuesta = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
            begin();
            persona = (Persona) getSession().get(Persona.class, id);

            if (utilidades.buscarPropiedad(Persona.class, "nit", nit, Utilidades.BIGDECIMAL) || persona.getNit().equals(nit) || nitStr.equals("0")) {

                if (utilidades.buscarPropiedad(Persona.class, "cedula", cedula, Utilidades.INT) || persona.getCedula().equals(cedula) || cedula == 0) {
                    if (utilidades.buscarPropiedad(Persona.class, "nombreRs", nombreRs, Utilidades.STRING) || persona.getNombreRs().equals(nombreRs)) {
                        Criteria criteria = getSession().createCriteria(Movimientos.class);
                        criteria.add(Restrictions.eq("persona", persona));
                        List<Movimientos> list = criteria.list();
                        if (list.isEmpty()) {
                            persona.setNit(nit);
                        } else {
                            msgAlterno = "No actualizo el NIT, debido a que la persona está asociada a un movimiento";
                        }
                        persona.setCedula(cedula);
                        persona.setNombreRs(nombreRs);
                        persona.setTelefono(telefono);
                        persona.setCual(cual);
                        persona.setNumeroCual(numeroCual);
                        getSession().update(persona);
                        commit();
                        respuesta = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG + " \n " + msgAlterno);
                    } else {
                        respuesta = new RespuestaVO(Mensajes.RAZON_SOCIAL_PERSONA_ESTA_ASIGNADO, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.RAZON_SOCIAL_PERSONA_ESTA_ASIGNADO_MSG);
                    }
                } else {
                    respuesta = new RespuestaVO(Mensajes.CEDULA_PERSONA_ESTA_ASIGNADO, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.CEDULA_PERSONA_ESTA_ASIGNADO_MSG);
                }
            } else {
                respuesta = new RespuestaVO(Mensajes.NIT_PERSONA_ESTA_ASIGNADO, Mensajes.NO_ACTUALIZO_DATOS_MSG + " - " + Mensajes.NIT_PERSONA_ESTA_ASIGNADO_MSG);
            }
        } catch (Exception e) {
        } finally {
            close();
            return respuesta;
        }
    }

    public VO.RespuestaVO eliminar(int id) throws AdException {
        respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        try {
            begin();
            persona = new Persona();
            persona = (Persona) getSession().get(Persona.class, id);

            if (utilidades.buscarPropiedad(Movimientos.class, "persona", persona, Utilidades.OBJECT)) {
                CajaMenorDao cajaMenorDao = new CajaMenorDao();
                int[] ids = new int[2];
                ids = cajaMenorDao.getCentroCostoYPersonaEmpresa();
                begin();

                if (ids == null || id != ids[0]) {
                    getSession().delete(persona);
                    commit();
                    respuesta = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
                } else {
                    respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.PERSONA_PERTECE_A_EMPRESA_MSG);
                }

            } else {
                respuesta = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + " - " + Mensajes.PERSONA_TIENE_ASIGNADOS_MOVIMIENTOS_MSG);
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return respuesta;
        }
    }

    public List listar() throws AdException {
        List<Persona> personas = new ArrayList<Persona>();
        List<PersonaVO> personaVOs = new ArrayList<PersonaVO>();
        try {
            if (getSession().isOpen()) {
                close();
            }

            begin();
            Criteria criteria = getSession().createCriteria(Persona.class);
            personas = criteria.list();
            commit();
            personaVOs = convertPersonasToPersonasVO(personas);
        } catch (Exception e) {
            personas = null;
            rollback();
            throw new AdException(" No se obtuvieron la lista de personas " + e);

        } finally {
            if (personas.isEmpty()) {
                personaVOs = null;
            }

            return personaVOs;
        }
    }

    private List<PersonaVO> convertPersonasToPersonasVO(List<Persona> personas) {
        List<PersonaVO> personaVOs = new ArrayList<PersonaVO>();
        for (Persona personax : personas) {
            personaVOs.add(convertPersonaToPersonaVO(personax));
        }
        return personaVOs;
    }

    protected PersonaVO convertPersonaToPersonaVO(Persona persona) {
        return new PersonaVO(persona.getId(), persona.getNit(), persona.getCedula(), persona.getTelefono(), persona.getNombreRs(), persona.getEsEmpresa(),persona.getCual(),persona.getNumeroCual());
    }

    public RespuestaVO comprobarExistenciaPersona(int cedula) {
        RespuestaVO respuestaVO = new RespuestaVO();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Persona.class);
            criteria.add(Restrictions.eq("cedula", cedula));
            Persona persona = (Persona) criteria.uniqueResult();
            if (persona != null) {
                respuestaVO = new RespuestaVO(Mensajes.YA_EXISTE_PERSONA, "");
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_EXISTE_PERSONA, Mensajes.PERSONA_NO_EXISTE_MSG);
            }

        } catch (Exception e) {
            System.out.println();
            respuestaVO = new RespuestaVO(Mensajes.HA_OCURRIDO_EXEP, Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }
}
