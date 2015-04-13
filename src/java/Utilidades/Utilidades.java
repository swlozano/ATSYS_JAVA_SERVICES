/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import Entidades.FechasPago;
import Entidades.Movimientos;
import Entidades.UsuarioSys;
import VO.FechaPagoVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class Utilidades extends DAO {

    public static final short SHORT = 1;
    public static final short STRING = 2;
    public static final short INT = 3;
    public static final short OBJECT = 4;
    public static final short BIGDECIMAL = 5;
    public static final char[] CARACTERES_ESPECIALES = {'%', '/', '(', ')', '*', '+', '-', '^'};
    public static final String[] NUMEROS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."};

    public Utilidades() {
    }

    public boolean buscarPropiedad(Class clase, String propiedad, Object valor, short tipoDato) throws AdException {
        boolean retorno = false;
        try {
            Criteria crit = getSession().createCriteria(clase);
            crit.add(Restrictions.eq(propiedad, this.getTipoDato(tipoDato, valor)));
            List results = crit.list();
            retorno = results.isEmpty();
        } catch (Exception e) {
            throw new AdException(" No se pudo establecer si existe un elemento en la propiedad especificada " + e);
        } finally {
            return retorno;//si retorna true es que el elemento buscado no existe si retorna false es
            //que hay un elemento con las caracteristicas buscadas.
        }
    }

    public Object getObjetoEspecifico(Class clase, String propiedad, Object valor, short tipoDato) throws AdException {
        Criteria crit = null;
        try {
            crit = getSession().createCriteria(clase);
            crit.add(Restrictions.eq(propiedad, this.getTipoDato(tipoDato, valor)));

        } catch (Exception e) {
            throw new AdException(" No se pudo establecer si existe un elemento en la propiedad especificada " + e);
        } finally {
            return crit.uniqueResult();
        }
    }

    private Object getTipoDato(short op, Object val) {
        switch (op) {
            case SHORT:
                return (short) Integer.parseInt(val + "");

            case STRING:
                return val + "";

            case INT:
                return Integer.parseInt(val + "");
            case OBJECT:
                return val;
            case BIGDECIMAL:
                return new BigDecimal(val.toString());
                
        }

        return null;
    }

    /*public List<UsuarioSys> listar(Class entidad) throws AdException
    {
    List<UsuarioSys> lista = null;
    try
    {
    begin();
    lista = getSession().createSQLQuery("SELECT * FROM USUARIO_SYS").list();
    commit();
    }
    catch (Exception e)
    {
    rollback();
    }
    finally
    {
    close();
    return  lista;
    }
    }*/
    public List listarObjetos(Class clase) {
        Criteria criteria = null;
        try {
            criteria = getSession().createCriteria(clase);
        } catch (Exception e) {
            criteria = null;
        } finally {
            return criteria.list();
        }
    }

    public String formaterTexto(String texto) {
        texto = texto.toLowerCase();
        String primeraLetra = texto.substring(0, 1);
        texto = primeraLetra.toUpperCase() + texto.substring(1);
        return texto;
    }

    public List<Object> getListaObjetos(Class clase, String propiedad, Object valor, short tipoDato) throws AdException {
        Criteria crit = null;
        try {
            crit = getSession().createCriteria(clase);
            crit.add(Restrictions.eq(propiedad, this.getTipoDato(tipoDato, valor)));

        } catch (Exception e) {
            throw new AdException(" No se pudo establecer si existe un elemento en la propiedad especificada " + e);
        } finally {
            return crit.list();
        }
    }
    public static int mesFinCausacion;
    public static int diaFinCausacion;

    public java.sql.Date[] normalizarFechasBusqueda(int mes, int year) {

        Fechas fechas = new Fechas();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mes /*- 1*/);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date fechaInicio = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, fechas.getNumeroDiasMes(mes + 1, year));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        Date fechaFin = calendar.getTime();
        mesFinCausacion = -1;
        mesFinCausacion = calendar.get(Calendar.MONTH);
        diaFinCausacion = -1;
        diaFinCausacion = calendar.get(Calendar.DAY_OF_MONTH);

        long time1 = fechaInicio.getTime();
        java.sql.Date dateIni = new java.sql.Date(time1);

        long time2 = fechaFin.getTime();
        java.sql.Date dateFin = new java.sql.Date(time2);

        java.sql.Date retorno[] = {dateIni, dateFin};

        return retorno;

    }

    public Date[] formatearCalendar(Calendar fecha, Calendar fechaFin) {

        fecha.set(Calendar.HOUR_OF_DAY, 0);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);

        fechaFin.set(Calendar.HOUR_OF_DAY, 23);
        fechaFin.set(Calendar.MINUTE, 59);
        fechaFin.set(Calendar.SECOND, 59);
        fechaFin.set(Calendar.MILLISECOND, 59);

        Date[] date = {fecha.getTime(), fechaFin.getTime()};
        return date;
    }

    public short calcularDiasTrabajados(Date fechaInicio, Date fechaFin) {

        return (short) ((fechaFin.getDate() - fechaInicio.getDate()) + 1);
    }

    public void convertirDateToCalendar(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.get(Calendar.YEAR);
    }

    public int calcularDiasTrabajos(Calendar fechaIni, Calendar fechaFin) {

        int yearInicio = fechaIni.get(Calendar.YEAR);
        int yearFin = fechaFin.get(Calendar.YEAR);
        int mesIni = fechaIni.get(Calendar.MONTH);
        int mesFin = 0;
        int diasTrabajados = 0;
        int primerosDias = 0;
        int ultimosDias = 0;
        if (yearInicio != yearFin) {
            mesFin = 12;
        } else {
            mesFin = fechaFin.get(Calendar.MONTH);
        }

        for (int i = yearInicio; i <= yearFin; i++) {
            for (int j = mesIni; j <= mesFin; j++) {
                diasTrabajados = diasTrabajados + 30;
            }
            if (yearFin == yearInicio) {
                mesFin = fechaFin.get(Calendar.MONTH);
            }
            mesIni = 1;
        }

        primerosDias = (30 - (30 - fechaIni.get(Calendar.DATE))) - 1;
        ultimosDias = (30 - fechaFin.get(Calendar.DATE));

        diasTrabajados = (diasTrabajados - primerosDias) - ultimosDias;
        return diasTrabajados;
    }
}

