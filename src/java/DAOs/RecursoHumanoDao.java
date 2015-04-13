/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Afp;
import Entidades.Arp;
import Entidades.Banco;
import Entidades.City;
import Entidades.Country;
import Entidades.CuentaBancaria;
import Entidades.CuentaDeCobro;
import Entidades.Eps;
import Entidades.Liquidacion;
import Entidades.RecursoHumano;
import Entidades.TipoCuentaBancaria;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Utilidades;
import VO.CuentaBancariaVO;
import VO.RecursoHumanoVO;
import VO.RespuestaVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class RecursoHumanoDao extends DAO {

    Utilidades utilidades = new Utilidades();

    public List<Arp> listarArp() {
        List<Arp> arps = new ArrayList<Arp>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Arp.class);
            arps = criteria.list();
            arps = normalizarListaArp(arps);
        } catch (Exception e) {
            arps = null;
        } finally {
            close();
            return arps;
        }
    }

    private List<Arp> normalizarListaArp(List<Arp> arps) {
        List<Arp> arps1 = new ArrayList<Arp>();
        for (Arp arp : arps) {
            Arp arp1 = new Arp(arp.getId(), arp.getNombre(), arp.getEsEmpresa());
            arps1.add(arp1);
        }
        return arps1;
    }

    public List<Eps> listarEps() {
        List<Eps> epses = new ArrayList<Eps>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Eps.class);
            epses = criteria.list();
            epses = normalizarListaEps(epses);
        } catch (Exception e) {
            epses = null;
        } finally {
            close();
            return epses;
        }
    }

    private List<Eps> normalizarListaEps(List<Eps> epses) {
        List<Eps> epses1 = new ArrayList<Eps>();
        for (Eps eps : epses) {
            Eps eps1 = new Eps(eps.getId(), eps.getNombre(), eps.getEsEmpresa());
            epses1.add(eps1);
        }
        return epses1;
    }

    public List<Afp> listarAfp() {
        List<Afp> afps = new ArrayList<Afp>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Afp.class);
            afps = criteria.list();
            afps = normalizarListaAfp(afps);
        } catch (Exception e) {
            afps = null;
        } finally {
            close();
            return afps;
        }
    }

    private List<Afp> normalizarListaAfp(List<Afp> Afps) {
        List<Afp> afps1 = new ArrayList<Afp>();
        for (Afp afp : Afps) {
            Afp afp1 = new Afp(afp.getId(), afp.getNombre(), afp.getEsEmpresa());
            afps1.add(afp1);
        }
        return afps1;
    }

    public List<Country> listarPaises(String continente) {
        List<Country> countrys = new ArrayList<Country>();
        Criteria criteria;
        try {
            begin();
            criteria = getSession().createCriteria(Country.class);
            criteria.add(Restrictions.eq("continent", continente));
            countrys = criteria.list();
            countrys = this.normalizarListaPaises(countrys);
        } catch (Exception e) {
            countrys = null;
        } finally {
            close();
            return countrys;
        }
    }

    private List<Country> normalizarListaPaises(List<Country> countrys) {
        List<Country> countrys1 = new ArrayList<Country>();
        for (Country country : countrys) {
            Country country1 = new Country(country.getCode(), country.getName());
            countrys1.add(country1);
        }
        return countrys1;
    }

    public List<City> ciudades(String idPais) {
        List<City> ciudades = new ArrayList<City>();
        Criteria criteria;
        try {
            begin();
            criteria = getSession().createCriteria(City.class);
            criteria.add(Restrictions.eq("country", new Country(idPais)));
            ciudades = criteria.list();
            ciudades = this.normalizarListaCiudades(ciudades);
        } catch (Exception e) {
            ciudades = null;
        } finally {
            close();
            return ciudades;
        }
    }

    private List<City> normalizarListaCiudades(List<City> ciudades) {
        List<City> ciudades1 = new ArrayList<City>();
        for (City ciudad : ciudades) {
            City ciudad1 = new City(ciudad.getId(), new Country(ciudad.getCountry().getCode(), ciudad.getCountry().getName()), ciudad.getName());
            ciudades1.add(ciudad1);
        }
        return ciudades1;
    }

    public List<Banco> listarBancos() {
        List<Banco> bancos = new ArrayList<Banco>();
        Criteria criteria;
        try {
            begin();
            criteria = getSession().createCriteria(Banco.class);
            bancos = criteria.list();
            bancos = this.normalizarListaBancos(bancos);
        } catch (Exception e) {
            bancos = null;
        } finally {
            close();
            return bancos;
        }
    }

    private List<Banco> normalizarListaBancos(List<Banco> bancos) {
        List<Banco> bancos1 = new ArrayList<Banco>();
        for (Banco banco : bancos) {
            Banco banco1 = new Banco(banco.getId(), banco.getNombre(), banco.getCodigo());
            bancos1.add(banco1);
        }
        return bancos1;
    }

    public List<TipoCuentaBancaria> listarTipoCuentaBancaria() {
        List<TipoCuentaBancaria> tipoCuentaBancarias = new ArrayList<TipoCuentaBancaria>();
        Criteria criteria;
        try {
            begin();
            criteria = getSession().createCriteria(TipoCuentaBancaria.class);
            tipoCuentaBancarias = criteria.list();
            List<TipoCuentaBancaria> tipoCuentaBancarias1 = new ArrayList<TipoCuentaBancaria>();
            for (TipoCuentaBancaria tipoCuentaBancaria : tipoCuentaBancarias) {
                TipoCuentaBancaria tipoCuentaBancaria1 = new TipoCuentaBancaria(tipoCuentaBancaria.getId(), tipoCuentaBancaria.getNombre());
                tipoCuentaBancarias1.add(tipoCuentaBancaria1);
            }
            tipoCuentaBancarias = tipoCuentaBancarias1;
        } catch (Exception e) {
            tipoCuentaBancarias = null;
        } finally {
            close();
            return tipoCuentaBancarias;
        }
    }

    public RespuestaVO crear(
            String nombre,
            String apellido,
            int cedula,
            String correoElectronico,
            String telefono,
            String direccionDomicilio,
            String celular,
            Date fechaNacimiento,
            String rut,
            short presentoRut,
            short presentoCedula,
            short presntoHv,
            short presentoEntrevista,
            int idLugarNacimiento, int idLugarExpedicion, int idArp, int idEps, int idAfp) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG);
        try {
            begin();
            RespuestaVO respuestaVOFiltroDatos = this.filtrarDatos(cedula, correoElectronico, rut);
            if (respuestaVOFiltroDatos.getIdRespuesta() == 1) {
                City cityNacimiento = new City(idLugarNacimiento, "");
                City cityExpedicion = new City(idLugarExpedicion, "");
                getSession().save(this.crearInstanciaRecursoHumano(nombre, apellido, cedula, correoElectronico, telefono, direccionDomicilio, celular, fechaNacimiento, rut, presentoRut, presentoCedula, presntoHv, presentoEntrevista, cityNacimiento, cityExpedicion, idArp, idEps, idAfp));
                commit();
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOFiltroDatos.getMensajeRespuesta());
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private RecursoHumano crearInstanciaRecursoHumano(
            String nombre,
            String apellido,
            int cedula,
            String correoElectronico,
            String telefono,
            String direccionDomicilio,
            String celular,
            Date fechaNacimiento,
            String rut,
            short presentoRut,
            short presentoCedula,
            short presntoHv,
            short presentoEntrevista,
            //int idLugarNacimiento,
            //int idLugarExpedicion,
            City lugarNacimiento,
            City lugarExpedicion,
            int idArp,
            int idEps,
            int idAfp) {
        RecursoHumano recursoHumano = new RecursoHumano(nombre, apellido, cedula, correoElectronico, telefono, direccionDomicilio, celular, fechaNacimiento, rut, presentoRut, presentoCedula, presntoHv, presentoEntrevista);
        recursoHumano.setAfp(new Afp(idAfp));
        recursoHumano.setArp(new Arp(idArp));
        recursoHumano.setCityByIdLugarExpedicion(new City(lugarExpedicion.getId(), lugarExpedicion.getName()));
        recursoHumano.setCityByIdLugarNacimiento(new City(lugarNacimiento.getId(), lugarNacimiento.getName()));
        recursoHumano.setContrasena("" + cedula);
        recursoHumano.setEps(new Eps(idEps));
        recursoHumano.setLogin("" + cedula);
        return recursoHumano;
    }

    private RespuestaVO filtrarDatos(int cedula, String correoElectronico, String rut) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            if (!utilidades.buscarPropiedad(RecursoHumano.class, "cedula", cedula, Utilidades.INT)) {
                respuestaVO = new RespuestaVO((short) -1, Mensajes.CEDULA_ESTA_ASIGNADO_MSG);
            } else {
                if (!utilidades.buscarPropiedad(RecursoHumano.class, "correoElectronico", correoElectronico, Utilidades.STRING)) {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.CORREO_ESTA_ASIGNADO_MSG);
                } else {
                    if (!utilidades.buscarPropiedad(RecursoHumano.class, "rut", rut, Utilidades.STRING)) {
                        respuestaVO = new RespuestaVO((short) -1, Mensajes.RUT_ESTA_ASIGNADO_MSG);
                    }
                }
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }

    }

    public RespuestaVO actualizar(int id, String nombre, String apellido, int cedula,
            String correoElectronico, String telefono, String direccionDomicilio,
            String celular, Date fechaNacimiento, String rut,
            short presentoRut, short presentoCedula, short presntoHv,
            short presentoEntrevista, int idLugarNacimiento, int idLugarExpedicion, int idArp, int idEps, int idAfp) {

        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
        try {
            begin();
            RecursoHumano recursoHumano = new RecursoHumano();
            recursoHumano = (RecursoHumano) utilidades.getObjetoEspecifico(RecursoHumano.class, "id", id, Utilidades.INT);
            RespuestaVO respuestaVOFiltroDatos = this.filtrarDatosActualizar(cedula, correoElectronico, rut, recursoHumano);
            if (respuestaVOFiltroDatos.getIdRespuesta() == 1) {
                recursoHumano.setNombre(nombre);
                recursoHumano.setApellido(apellido);
                recursoHumano.setCedula(cedula);
                recursoHumano.setCorreoElectronico(correoElectronico);
                recursoHumano.setTelefono(telefono);
                recursoHumano.setDireccionDomicilio(direccionDomicilio);
                recursoHumano.setCelular(celular);
                recursoHumano.setFechaNacimiento(fechaNacimiento);
                recursoHumano.setRut(rut);
                recursoHumano.setPresentoRut(presentoRut);
                recursoHumano.setPresentoCedula(presentoCedula);
                recursoHumano.setPresntoHv(presntoHv);
                recursoHumano.setPresentoEntrevista(presentoEntrevista);
                recursoHumano.setCityByIdLugarNacimiento(new City(idLugarNacimiento, ""));
                recursoHumano.setCityByIdLugarExpedicion(new City(idLugarExpedicion, ""));
                recursoHumano.setAfp(new Afp(idAfp));
                recursoHumano.setArp(new Arp(idArp));
                recursoHumano.setEps(new Eps(idEps));

                getSession().update(recursoHumano);
                commit();
                respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOFiltroDatos.getMensajeRespuesta());
            }

        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private RespuestaVO filtrarDatosActualizar(int cedula, String correoElectronico, String rut, RecursoHumano recursoHumano) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            if (utilidades.buscarPropiedad(RecursoHumano.class, "cedula", cedula, Utilidades.INT) || recursoHumano.getCedula() == cedula) {
                if (utilidades.buscarPropiedad(RecursoHumano.class, "correoElectronico", correoElectronico, Utilidades.STRING) || recursoHumano.getCorreoElectronico().equals(correoElectronico)) {
                    if (utilidades.buscarPropiedad(RecursoHumano.class, "rut", rut, Utilidades.STRING) || recursoHumano.getRut().equals(rut)) {
                        respuestaVO = new RespuestaVO((short) 1, "");
                    } else {
                        respuestaVO = new RespuestaVO((short) -1, Mensajes.RUT_ESTA_ASIGNADO_MSG);
                    }
                } else {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.CORREO_ESTA_ASIGNADO_MSG);
                }
            } else {
                respuestaVO = new RespuestaVO((short) -1, Mensajes.CEDULA_ESTA_ASIGNADO_MSG);
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO((short) -1, Mensajes.EXCEPCION + e.toString());
        } finally {
            return respuestaVO;
        }

    }

    public List<RecursoHumano> listar() {
        List<RecursoHumano> recursoHumanos = new ArrayList<RecursoHumano>();
        Criteria criteria;
        try {
            begin();
            criteria = getSession().createCriteria(RecursoHumano.class);
            recursoHumanos = criteria.list();
            List<RecursoHumano> recursoHumanos1 = new ArrayList<RecursoHumano>();
            for (RecursoHumano recursoHumano : recursoHumanos) {
                RecursoHumano recursoHumano1 = new RecursoHumano();
                recursoHumano1 = this.crearInstanciaRecursoHumano(
                        recursoHumano.getNombre(),
                        recursoHumano.getApellido(),
                        recursoHumano.getCedula(),
                        recursoHumano.getCorreoElectronico(),
                        recursoHumano.getTelefono(),
                        recursoHumano.getDireccionDomicilio(),
                        recursoHumano.getCelular(),
                        recursoHumano.getFechaNacimiento(),
                        recursoHumano.getRut(),
                        recursoHumano.getPresentoRut(),
                        recursoHumano.getPresentoCedula(),
                        recursoHumano.getPresntoHv(),
                        recursoHumano.getPresentoEntrevista(),
                        recursoHumano.getCityByIdLugarNacimiento(),
                        recursoHumano.getCityByIdLugarExpedicion(),
                        recursoHumano.getArp().getId(),
                        recursoHumano.getEps().getId(),
                        recursoHumano.getAfp().getId());
                recursoHumano1.setId(recursoHumano.getId());
                recursoHumanos1.add(recursoHumano1);
            }
            recursoHumanos = recursoHumanos1;
        } catch (Exception e) {
            recursoHumanos = null;
        } finally {
            close();
            return recursoHumanos;
        }
    }

    public RespuestaVO modificarLoginAndPwd(int idRecursoHumano, String login, String contrasena) {
        RecursoHumano recursoHumano = new RecursoHumano();
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
        try {
            begin();
            Criteria criteria = getSession().createCriteria(RecursoHumano.class);
            criteria.add(Restrictions.eq("login", login));
            //criteria.add(Restrictions.eq("contrasena",idRecursoHumano));
            recursoHumano = (RecursoHumano) criteria.uniqueResult();
            if (recursoHumano == null || idRecursoHumano == recursoHumano.getId()) {
                criteria = getSession().createCriteria(RecursoHumano.class);
                criteria.add(Restrictions.eq("id", idRecursoHumano));
                recursoHumano = new RecursoHumano();
                recursoHumano = (RecursoHumano) criteria.uniqueResult();
                recursoHumano.setContrasena(contrasena);
                recursoHumano.setLogin(login);
                getSession().update(recursoHumano);
                commit();
                respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.LOGIN_ESTA_ASIGNADO_MSG);
            }
            //criteria.add(Restrictions.eq("id",idRecursoHumano));
            } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    //Lista las cuentas de bancaria de los recursos humenos llamada desde la calse de cuentaCobroDao
    public List<CuentaBancariaVO> getCuentasBancariasRh(int idRh) {
        List<CuentaBancaria> cuentaBancarias = new ArrayList<CuentaBancaria>();
        List<CuentaBancariaVO> cuentaBancariaVOs = new ArrayList<CuentaBancariaVO>();
        try {
            Criteria criteria = getSession().createCriteria(CuentaBancaria.class);
            if (idRh != -1) {
                criteria.add(Restrictions.eq("recursoHumano", new RecursoHumano(idRh)));
            }
            cuentaBancarias = criteria.list();
            for (CuentaBancaria cuentaBancaria : cuentaBancarias) {
                cuentaBancariaVOs.add(cuentasBankToCuentaBankVO(cuentaBancaria));
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return cuentaBancariaVOs;
        }
    }

    private CuentaBancariaVO cuentasBankToCuentaBankVO(CuentaBancaria cuentaBancaria) {
        CuentaBancariaVO cuentaBancariaVO = null;
        try {
            RecursoHumanoVO recursoHumanoVO = new RecursoHumanoVO(cuentaBancaria.getRecursoHumano().getId(), cuentaBancaria.getRecursoHumano().getNombre(), cuentaBancaria.getRecursoHumano().getApellido(), cuentaBancaria.getRecursoHumano().getCedula());
            cuentaBancariaVO = new CuentaBancariaVO(cuentaBancaria.getId(), cuentaBancaria.getBanco().getNombre(), cuentaBancaria.getBanco().getId(), cuentaBancaria.getTipoCuentaBancaria().getNombre(), cuentaBancaria.getTipoCuentaBancaria().getId(), cuentaBancaria.getNumero(), cuentaBancaria.getEsActual(), recursoHumanoVO);
        } catch (Exception e) {
            System.out.println();
        } finally {
            return cuentaBancariaVO;
        }
    }

    public RecursoHumanoVO recursoHumanoToRecursoHumanoVO(RecursoHumano recursoHumano) {
        RecursoHumanoVO recursoHumanoVO = null;
        try {
            RecursoHumanoDao recursoHumanoDao = new RecursoHumanoDao();
            recursoHumanoVO = new RecursoHumanoVO(recursoHumano.getId(), recursoHumano.getCityByIdLugarNacimiento().getName(), recursoHumano.getCityByIdLugarExpedicion().getName(), recursoHumano.getArp().getNombre(), recursoHumano.getAfp().getNombre(), recursoHumano.getEps().getNombre(), recursoHumano.getNombre(), recursoHumano.getApellido(), recursoHumano.getCedula(), recursoHumano.getCorreoElectronico(), recursoHumano.getTelefono(), recursoHumano.getDireccionDomicilio(), recursoHumano.getCelular(), recursoHumano.getFechaNacimiento(), recursoHumano.getRut(), recursoHumano.getPresentoRut(), recursoHumano.getPresentoCedula(), recursoHumano.getPresntoHv(), recursoHumano.getPresentoEntrevista(), recursoHumano.getLogin(), recursoHumano.getContrasena(), recursoHumanoDao.getCuentasBancariasRh(recursoHumano.getId()));
        } catch (Exception e) {
            System.out.println();
        } finally {
            return recursoHumanoVO;
        }

    }

    public RespuestaVO actualizarCuentaBancaria(int idCuentaBanco, int idBanco, short idTipocuenta, String numero) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
        try {
            begin();
            CuentaBancaria cuentaBancaria = (CuentaBancaria) utilidades.getObjetoEspecifico(CuentaBancaria.class, "id", idCuentaBanco, Utilidades.INT);
            if (cuentaBancaria.getBanco().getId() != idBanco || cuentaBancaria.getNumero().equals(numero) == false) {
                RespuestaVO respuestaVOExiteCuenta = comprobarExistenciaCuentaBanco(idBanco, numero);
                if (respuestaVOExiteCuenta.getIdRespuesta() == 1) {

                    cuentaBancaria.setBanco(new Banco(idBanco));
                    cuentaBancaria.setNumero(numero);
                    cuentaBancaria.setTipoCuentaBancaria(new TipoCuentaBancaria(idTipocuenta));
                    getSession().update(cuentaBancaria);
                    commit();
                    respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
                } else {
                    respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + respuestaVOExiteCuenta.getMensajeRespuesta());
                }

            } else {
                cuentaBancaria.setTipoCuentaBancaria(new TipoCuentaBancaria(idTipocuenta));
            }
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO crearCuentaBancaria(int idRh, int idBanco, short idTipocuenta, String numero) {
        boolean existeCx = false;
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, "");
        try {
            existeCx = getSession().isConnected();
            begin();
            RespuestaVO exiteCuenta = comprobarExistenciaCuentaBanco(idBanco, numero);
            if (exiteCuenta.getIdRespuesta() == (short) 1) {
                CuentaBancaria cuentaBancaria = new CuentaBancaria();
                cuentaBancaria.setBanco(new Banco(idBanco));
                cuentaBancaria.setEsActual((short) 0);
                cuentaBancaria.setNumero(numero);
                cuentaBancaria.setRecursoHumano(new RecursoHumano(idRh));
                cuentaBancaria.setTipoCuentaBancaria(new TipoCuentaBancaria(idTipocuenta));
                getSession().save(cuentaBancaria);
                commit();
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + exiteCuenta.getMensajeRespuesta());
            }
        } catch (Exception e) {
            System.out.println();
            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public RespuestaVO eliminarCuentaBanco(int idCuentaBanco) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Liquidacion.class);
            criteria.add(Restrictions.eq("idCuentaBancaria", idCuentaBanco));
            List<Liquidacion> liquidacions = new ArrayList<Liquidacion>();
            liquidacions = criteria.list();

            if (liquidacions.size() > 0) {
                respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.CUENTA_BANCO_ESTA_ASOCIADA_PROCESOS);
                return respuestaVO;
            } else {
                criteria = getSession().createCriteria(CuentaDeCobro.class);
                criteria.add(Restrictions.eq("idCuentaBancaria", idCuentaBanco));
                List<CuentaDeCobro> cuentaDeCobros = new ArrayList<CuentaDeCobro>();
                cuentaDeCobros = criteria.list();
                if (cuentaDeCobros.size() > 0) {
                    respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.CUENTA_BANCO_ESTA_ASOCIADA_PROCESOS);
                    return respuestaVO;
                }
            }

            CuentaBancaria cuentaBancaria = (CuentaBancaria) utilidades.getObjetoEspecifico(CuentaBancaria.class, "id", idCuentaBanco, Utilidades.INT);
            getSession().delete(cuentaBancaria);
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.HA_OCURRIDO_EXEP + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    private RespuestaVO comprobarExistenciaCuentaBanco(int idBanco, String numero) {
        RespuestaVO respuestaVO = new RespuestaVO((short) 1, "");
        try {
            Criteria criteria = getSession().createCriteria(CuentaBancaria.class);
            criteria.add(Restrictions.eq("numero", numero));
            List<CuentaBancaria> cuentaBancarias = new ArrayList<CuentaBancaria>();
            cuentaBancarias = criteria.list();
            for (CuentaBancaria cuentaBancaria : cuentaBancarias) {
                if (cuentaBancaria.getBanco().getId() == idBanco && cuentaBancaria.getNumero().equals(numero)) {
                    respuestaVO = new RespuestaVO((short) -1, Mensajes.CUENTA_BANCO_YA_EXISTE);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return respuestaVO;
        }
    }

    protected RecursoHumanoVO getRecursoHumanoById(int idRh) {
        RecursoHumanoVO recursoHumanoVO = new RecursoHumanoVO();
        try {
           RecursoHumano recursoHumano =  (RecursoHumano) utilidades.getObjetoEspecifico(RecursoHumano.class, "id", idRh, Utilidades.INT);
           recursoHumanoVO = recursoHumanoToRecursoHumanoVO(recursoHumano);
        } catch (Exception e) {
            System.out.println();
        } finally {
            return recursoHumanoVO;
        }
    }
}
