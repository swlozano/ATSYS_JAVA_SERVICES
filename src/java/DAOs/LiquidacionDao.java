/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Contrato;
import Entidades.CuentaBancaria;
import Entidades.Formula;
import Entidades.Liquidacion;
import Entidades.TablaAtributo;
import Entidades.TerminacionContrato;
import Entidades.Tributaria;
import Entidades.ValorFormaPago;
import Utilidades.DAO;
import Utilidades.Mensajes;
import Utilidades.Tablas;
import Utilidades.Utilidades;
import VO.DatosLiquidacionVO;
import VO.RespuestaVO;
import antlr.collections.impl.Vector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.bcel.generic.GETFIELD;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class LiquidacionDao extends DAO {

    public static int DIAS_TRABAJADOS;
    public static double PRIMA_SERVICIOS;
    public static double VACACIONES;
    public static double CESANTIAS;
    public static double INTERES_CESANTIAS;
    public static double TOTAL_LIQUIDACION;
    public static double SUELDO_BASICO;
    public static String A_DIAS_TRABAJADOS = "DIAS_TRABAJADOS";
    public static String A_PRIMA_SERVICIOS = "PRIMA_SERVICIOS";
    public static String A_VACACIONES = "VACACIONES";
    public static String A_CESANTIAS = "CESANTIAS";
    public static String A_INTERESES_CESANTIAS = "INTERESES_CESANTIAS";
    public static String A_TOTAL_LIQUIDACION = "TOTAL_LIQUIDACION";
    public static String A_SUELDO_BASICO = "SUELDO_BASICO";
    public static final short GENERAR_PRIMA_DE_SERVICIO = 1;
    public static final short GENERAR_VACACIONES = 2;
    public static final short GENERAR_CESANTIAS = 3;
    public static final short GENERAR_INTERESES_CESANTIAS = 4;

    public RespuestaVO crear(int idContrato, int idCuentaBancaria, int idFormulaPs, int idFormulaVacaciones, int idFormulaCesantias, int idFormuInteresCesan, int diasTrabajados, double primaServicios, double vacaciones, double cesantias, double interesesCesantias, double totalLiquidacion, Date fechaLiquidacion, Date fechaPago, double sueldoBasico) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, "");
        Liquidacion liquidacion = new Liquidacion();
        Liquidacion liquidacion1 = new Liquidacion();
        TerminacionContrato terminacionContrato = new TerminacionContrato();
        CuentaPorPagarDao cuentaPorPagarDao = new CuentaPorPagarDao();
        int idRecursoHumano = 0;
        int idLiquidacion = 0;
        try {
            begin();

            Criteria criteriaTerminar = getSession().createCriteria(TerminacionContrato.class);
            criteriaTerminar.add(Restrictions.eq("idContrato", idContrato));
            criteriaTerminar.add(Restrictions.eq("respuesta", (short) 1));
            terminacionContrato = (TerminacionContrato) criteriaTerminar.uniqueResult();
            if (terminacionContrato == null) {
                return respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.DEBE_PRIMERO_ELIMINAR_CONTRATO);
            }

            Criteria criteria = getSession().createCriteria(Liquidacion.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            liquidacion1 = (Liquidacion) criteria.uniqueResult();
            if (liquidacion1 == null) {
                liquidacion = new Liquidacion(idContrato, idContrato, idCuentaBancaria, idFormulaPs, idFormulaVacaciones, idFormulaCesantias, idFormuInteresCesan, diasTrabajados, primaServicios, vacaciones, cesantias, interesesCesantias, totalLiquidacion, fechaLiquidacion, fechaPago);
                liquidacion.setSueldoBasico(sueldoBasico);
                Serializable serializable = getSession().save(liquidacion);
                idLiquidacion = Integer.parseInt(serializable.toString());

                ///////Obtener Recurso humano
                Criteria criteria1 = getSession().createCriteria(Contrato.class);
                criteria1.add(Restrictions.eq("id", idContrato));
                Contrato contrato = (Contrato) criteria1.uniqueResult();
                idRecursoHumano = contrato.getRecursoHumano().getId();

                commit();
                respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG);
            } else {
                respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.NO_INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.CONTRATO_YA_FUE_LIQUIDADO);
            }
        } catch (Exception e) {

            respuestaVO = new RespuestaVO(Mensajes.NO_INGRESO_DATOS, Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());

        } finally {
            close();
            //Crear cuenta por pagar
            if (respuestaVO.getIdRespuesta() == Mensajes.INGRESO_DATOS) {
                RespuestaVO respuestaVO1 = cuentaPorPagarDao.crear(new Date(), idRecursoHumano, -1, Mensajes.PAGO_LIQUIDACION, idCuentaBancaria, -1, -1, "", TOTAL_LIQUIDACION, (short) 2, (short) 1, idLiquidacion, null);
                if (respuestaVO1.getIdRespuesta() == Mensajes.INGRESO_DATOS) {
                    respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.LIQUIDACION_ENVIADA_CUENTAS_POR_PAGAR);
                } else {
                    respuestaVO = new RespuestaVO(Mensajes.INGRESO_DATOS, Mensajes.INGRESO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.NO_ENVIO_LIQUIDACION_CUENTAS_POR_PAGAR);
                }
            }
            return respuestaVO;
        }
    }

    public RespuestaVO actualizar(int id, int idCuentaBancaria, int idFormulaPs, int idFormulaVacaciones, int idFormulaCesantias, int idFormuInteresCesan, int diasTrabajados, double primaServicios, double vacaciones, double cesantias, double interesesCesantias, double totalLiquidacion, Date fechaLiquidacion, Date fechaPago, double sueldoBasico) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG);
        Liquidacion liquidacion = new Liquidacion();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Liquidacion.class);
            criteria.add(Restrictions.eq("id", id));
            liquidacion = (Liquidacion) criteria.uniqueResult();
            liquidacion.setCesantias(cesantias);
            liquidacion.setDiasTrabajados(diasTrabajados);
            liquidacion.setFechaLiquidacion(fechaLiquidacion);
            liquidacion.setFechaPago(fechaPago);
            liquidacion.setIdCuentaBancaria(idCuentaBancaria);
            liquidacion.setIdFormuInteresCesan(idFormuInteresCesan);
            liquidacion.setIdFormulaCesantias(idFormulaCesantias);
            liquidacion.setIdFormulaPs(idFormulaPs);
            liquidacion.setIdFormulaVacaciones(idFormulaVacaciones);
            liquidacion.setInteresesCesantias(interesesCesantias);
            liquidacion.setPrimaServicios(primaServicios);
            liquidacion.setSueldoBasico(sueldoBasico);
            liquidacion.setTotalLiquidacion(totalLiquidacion);
            liquidacion.setVacaciones(vacaciones);
            getSession().update(liquidacion);
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ACTUALIZO_DATOS, Mensajes.ACTUALIZO_DATOS_MSG);
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ACTUALIZO_DATOS, Mensajes.NO_ACTUALIZO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public Liquidacion generarLiquidacion(int idFormulaPs, int idFormulaVacaciones, int idFormulaCesantias, int idFormuInteresCesan, int diasTrabajados, double primaServicios, double vacaciones, double cesantias, double interesesCesantias, double sueldoBasico) {
        DIAS_TRABAJADOS = diasTrabajados;
        PRIMA_SERVICIOS = primaServicios;
        VACACIONES = vacaciones;
        CESANTIAS = cesantias;
        INTERES_CESANTIAS = interesesCesantias;
        //TOTAL_LIQUIDACION = totalLiquidacion;
        SUELDO_BASICO = sueldoBasico;
        Liquidacion liquidacion = new Liquidacion();
        try {
            begin();
            primaServicios = generarValoresLiquidacion(idFormulaPs);
            PRIMA_SERVICIOS = primaServicios;
            vacaciones = generarValoresLiquidacion(idFormulaVacaciones);
            VACACIONES = vacaciones;
            cesantias = generarValoresLiquidacion(idFormulaCesantias);
            CESANTIAS = cesantias;
            interesesCesantias = generarValoresLiquidacion(idFormuInteresCesan);
            INTERES_CESANTIAS = interesesCesantias;
            TOTAL_LIQUIDACION = primaServicios + interesesCesantias + cesantias + vacaciones;
            liquidacion = new Liquidacion(primaServicios, vacaciones, cesantias, interesesCesantias, TOTAL_LIQUIDACION);

        } catch (Exception e) {
            System.out.println();
            liquidacion = null;
        } finally {
            close();
            return liquidacion;
        }
    }

    public double generarValoresLiquidacion(int idFormula) {
        String nuevaFormula = "";
        double respuesta = 0;
        try {
            nuevaFormula = this.convertirFormula(idFormula);
            respuesta = getRespuestaFormula(nuevaFormula);
        } catch (Exception e) {
            respuesta = 0;
        } finally {
            return respuesta;
        }
    }

    private String suprimirEspaciosBlanco(String nuevaFormula) {
        nuevaFormula = nuevaFormula.replace(" ", "");
        return nuevaFormula;
    }

    private String convertirFormula(int idFormula) {
        Formula formula;
        String strFormula = "";
        String nuevaFormula = "";
        try {
            formula = new Formula();
            Criteria criteria = getSession().createCriteria(Formula.class);
            criteria.add(Restrictions.eq("id", idFormula));
            formula = (Formula) criteria.uniqueResult();
            strFormula = formula.getFormula();
            strFormula.toUpperCase();

            Vector indices = new Vector();
            boolean hacerCiclo = true;
            boolean findCaracter = false;
            int indexFin = 0;
            int arIndices[] = new int[2];
            String nuevaCadena = strFormula;
            int indexIni = strFormula.indexOf("C_");
            if (indexIni == -1) {
                hacerCiclo = false;
            }
            while (hacerCiclo) {

                if (indexIni != -1) {
                    for (int i = indexIni; i < strFormula.length(); i++) {

                        for (char c : Utilidades.CARACTERES_ESPECIALES) {
                            if (c == strFormula.charAt(i)) {
                                indexFin = i;
                                findCaracter = true;
                                break;
                            }
                        }
                        if (findCaracter) {
                            break;
                        }
                    }
                    arIndices = new int[2];
                    if (findCaracter) {
                        arIndices[0] = indexIni;
                        arIndices[1] = indexFin;
                        indices.appendElement(arIndices);
                        indexIni = nuevaCadena.indexOf("C_", indexFin + 1);
                    } else {
                        arIndices[0] = indexIni;
                        arIndices[1] = strFormula.length();
                        indices.appendElement(arIndices);
                        hacerCiclo = false;
                    }
                    findCaracter = false;
                } else {
                    hacerCiclo = false;
                }
            }
            nuevaFormula = this.getNuevaFormula(strFormula, indices);
        } catch (Exception e) {
            nuevaFormula = "";
        } finally {
            nuevaFormula = suprimirEspaciosBlanco(nuevaFormula);
            return nuevaFormula;
        }
    }

    private String getNuevaFormula(String cadena, Vector indices) {
        String[] subCadenas = new String[indices.size()];
        String[] segmentos = new String[indices.size()];

        int sub = 0;
        int iniSubCadena;
        int finSubCadena = 0;
        for (int i = 0; i < indices.size(); i++) {
            int index[] = new int[2];
            index = (int[]) indices.elementAt(i);
            subCadenas[i] = cadena.substring(index[0], index[1]);
            iniSubCadena = index[0];
            finSubCadena = index[1];
            segmentos[i] = cadena.substring(sub, index[0]);
            if (index[1] != cadena.length()) {
                sub = index[1];
            }
        }
        String ultimoSegemento = cadena.substring(finSubCadena, cadena.length());
        String[] nuevaSubcadena = remplazarValores(subCadenas);
        return concatenarFormula(nuevaSubcadena, segmentos, ultimoSegemento);

    }

    private String[] remplazarValores(String[] subCadenas) {
        String[] nuevaSubCadenas = new String[subCadenas.length];
        try {
            TablaAtributo tablaAtributo = new TablaAtributo();
            Criteria criteria;
            Tablas tablas = new Tablas();
            int cont = 0;
            for (String string : subCadenas) {
                criteria = getSession().createCriteria(TablaAtributo.class);
                criteria.add(Restrictions.eq("code", string));
                tablaAtributo = (TablaAtributo) criteria.uniqueResult();
                if (tablaAtributo.getIdCampo() != null) {

                    criteria = getSession().createCriteria(Entidades.Tablas.class);
                    criteria.add(Restrictions.eq("id", tablaAtributo.getIdTabla()));
                    Entidades.Tablas tablas1 = new Entidades.Tablas();
                    tablas1 = (Entidades.Tablas) criteria.uniqueResult();

                    String q = "SELECT " + tablas1.getNombreTabla() + "." + tablaAtributo.getNombreAtributo() + " FROM " + tablas1.getNombreTabla() + " where " + tablas1.getNombreTabla() + ".id" + " = " + tablaAtributo.getIdCampo();
                    SQLQuery query = getSession().createSQLQuery(q);
                    Object object = new Object();
                    object = query.uniqueResult();
                    nuevaSubCadenas[cont] = object.toString();
                } else {
                    nuevaSubCadenas[cont] = (getValoresNulos(tablaAtributo.getNombreAtributo())).toString();
                }
                cont++;
            }
        } catch (Exception e) {
            nuevaSubCadenas = null;
        } finally {
            return nuevaSubCadenas;
        }
    }

    private String concatenarFormula(String[] nuevaSubCadenas, String[] segmentos, String ultimoSegmento) {
        String nuevaFormula = "";
        for (int i = 0; i < segmentos.length; i++) {
            nuevaFormula = nuevaFormula + segmentos[i] + nuevaSubCadenas[i];
        }

        return nuevaFormula + ultimoSegmento;
    }

    private Object getValoresNulos(String nombreAtributo) {
        Object valor = null;
        if (A_CESANTIAS.equals(nombreAtributo)) {
            valor = CESANTIAS;
        } else {
            if (A_DIAS_TRABAJADOS.equals(nombreAtributo)) {
                valor = DIAS_TRABAJADOS;
            } else {
                if (A_INTERESES_CESANTIAS.equals(nombreAtributo)) {
                    valor = INTERES_CESANTIAS;
                } else {
                    if (A_PRIMA_SERVICIOS.equals(nombreAtributo)) {
                        valor = PRIMA_SERVICIOS;
                    } else {
                        if (A_TOTAL_LIQUIDACION.equals(nombreAtributo)) {
                            valor = TOTAL_LIQUIDACION;
                        } else {
                            if (A_VACACIONES.equals(nombreAtributo)) {
                                valor = VACACIONES;
                            } else {
                                if (A_SUELDO_BASICO.equals(nombreAtributo)) {
                                    valor = SUELDO_BASICO;
                                }
                            }

                        }
                    }
                }
            }
        }
        return valor;
    }

    public double getRespuestaFormula(String formula) {
        boolean ban = true;
        int lastIndex;
        String segmento = "";
        double respuesta;
        while (ban) {
            lastIndex = formula.lastIndexOf("(");
            if (lastIndex != -1) {
                for (int i = lastIndex; i < formula.length(); i++) {
                    if (formula.charAt(i) == ')') {
                        segmento = formula.substring(lastIndex + 1, i);
                        respuesta = getNumerosAndOperandos(segmento);
                        String cadToRemplazar = formula.substring(lastIndex, i + 1);
                        formula = formula.replace(cadToRemplazar, respuesta + "");
                        break;
                    }
                }
            } else {
                ban = false;
            }
        }
        return Double.parseDouble(formula);
    }

    private double getNumerosAndOperandos(String segmento) {
        double respuesta = 0;
        try {
            //segmento = "-5*453+23456";
            Vector operandos = new Vector();
            Vector numeros = new Vector();
            String nuevoSegmento = segmento.substring(1, segmento.length());
            int indexOf = nuevoSegmento.length();
            int jx = 0;
            boolean ban = true;
            String numero;
            String subNuevoSegmento = nuevoSegmento;
            for (int i = 0; i < subNuevoSegmento.length(); i++) {
                for (int j = 0; j < Utilidades.CARACTERES_ESPECIALES.length; j++) {
                    indexOf = subNuevoSegmento.indexOf(Utilidades.CARACTERES_ESPECIALES[j]);
                    if (indexOf != -1) {
                        jx = j;
                        break;
                    }
                }
                if (ban && indexOf != -1) {
                    numero = subNuevoSegmento.substring(i, indexOf);
                    numero = segmento.charAt(0) + numero;
                    operandos.appendElement(Utilidades.CARACTERES_ESPECIALES[jx]);
                    numeros.appendElement(numero);
                    ban = false;
                } else {
                    if (indexOf != -1) {
                        numero = subNuevoSegmento.substring(i - 1, indexOf);
                        operandos.appendElement(Utilidades.CARACTERES_ESPECIALES[jx]);
                        numeros.appendElement(numero);
                    }
                }
                if (indexOf != -1) {
                    subNuevoSegmento = subNuevoSegmento.substring(indexOf + 1, subNuevoSegmento.length());
                } else {
                    break;
                }
            }
            numeros.appendElement(subNuevoSegmento);
            respuesta = realizarOperacion(operandos, numeros);
            System.out.println();
        } catch (Exception e) {
            System.out.println();
        } finally {
            return respuesta;
        }
    }

    private double realizarOperacion(Vector operandos, Vector numeros) {
        double respuesta;
        int indexOperandos = 0;
        respuesta = Double.parseDouble(numeros.elementAt(0).toString());
        int nextIndex = 0;
        boolean ban = true;
        while (nextIndex < numeros.size() - 1) {
            nextIndex = nextIndex + 1;
            respuesta = operarDosNumeros(operandos.elementAt(indexOperandos).toString(), respuesta, Double.parseDouble(numeros.elementAt(nextIndex).toString()));
        }
        return respuesta;
    }

    private double operarDosNumeros(String operando, double num1, double num2) {

        double res = 0;
        if (operando.equals("*")) {
            res = num1 * num2;
        }

        if (operando.equals("/")) {
            res = num1 / num2;
        }

        if (operando.equals("+")) {
            res = num1 + num2;
        }

        if (operando.equals("-")) {
            res = num1 - num2;
        }

        if (operando.equals("^")) {
            res = Math.pow(num1, num2);
        }
        return Math.round(res);
    }

    public double getSueldoBasico(int idContrato) {
        double ibc = 0;
        try {
            ValorFormaPago valorFormaPago = new ValorFormaPago();
            begin();
            Criteria criteria = getSession().createCriteria(ValorFormaPago.class);
            criteria.add(Restrictions.eq("contrato", new Contrato(idContrato)));
            criteria.add(Restrictions.eq("esIbc", (short) 1));
            valorFormaPago = (ValorFormaPago) criteria.uniqueResult();

            Tributaria tributaria = new Tributaria();
            criteria = getSession().createCriteria(Tributaria.class);
            criteria.addOrder(Order.desc("ano"));
            tributaria = (Tributaria) criteria.list().get(0);

            if (valorFormaPago.getValorPago() <= tributaria.getSmlv()) {
                ibc = tributaria.getSmlv();
            } else {
                ibc = valorFormaPago.getValorPago();
            }

        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return ibc;
        }
    }
    Utilidades utilidades = new Utilidades();

    public int getDiasTrabajados(int idContrato) {
        int diasTrabajados = 0;
        TerminacionContrato terminacionContrato = new TerminacionContrato();
        Contrato contrato = new Contrato();
        Date dateFechaIni;
        Date dateFechaFin;
        Calendar calendarFechaIni = Calendar.getInstance();
        Calendar calendarFechaFin = Calendar.getInstance();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(TerminacionContrato.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            criteria.add(Restrictions.eq("respuesta", (short) 1));
            terminacionContrato = (TerminacionContrato) criteria.uniqueResult();

            criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("id", idContrato));

            contrato = (Contrato) criteria.uniqueResult();
            dateFechaIni = contrato.getFechaInicio();
            calendarFechaIni.setTime(dateFechaIni);
            if (terminacionContrato != null) {
                dateFechaFin = contrato.getFechaFin();
                calendarFechaFin.setTime(dateFechaFin);

            } else {
                dateFechaFin = contrato.getFechaRealTerminacion();
                calendarFechaFin.setTime(dateFechaFin);
            }
            diasTrabajados = utilidades.calcularDiasTrabajos(calendarFechaIni, calendarFechaFin);
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return diasTrabajados;
        }
    }

    public RespuestaVO eliminar(int idLiquidacion) {
        RespuestaVO respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG);
        try {
            begin();
            getSession().delete(new Liquidacion());
            commit();
            respuestaVO = new RespuestaVO(Mensajes.ELIMINO_DATOS, Mensajes.ELIMINO_DATOS_MSG);
        } catch (Exception e) {
            respuestaVO = new RespuestaVO(Mensajes.NO_ELIMINO_DATOS, Mensajes.NO_ELIMINO_DATOS_MSG + Mensajes.SALTO_LINEA + Mensajes.EXCEPCION + e.toString());
        } finally {
            close();
            return respuestaVO;
        }
    }

    public Liquidacion listar(int idContrato) {
        Liquidacion liquidacion = new Liquidacion();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Liquidacion.class);
            criteria.add(Restrictions.eq("idContrato", idContrato));
            liquidacion = (Liquidacion) criteria.uniqueResult();
            if (liquidacion != null) {
                criteria = getSession().createCriteria(CuentaBancaria.class);
                criteria.add(Restrictions.eq("id", liquidacion.getIdCuentaBancaria()));
                CuentaBancaria cuentaBancaria = new CuentaBancaria();
                cuentaBancaria = (CuentaBancaria) criteria.uniqueResult();

                liquidacion.setCuentaBancaria(cuentaBancaria.getNumero() + "");

                for (int i = 1; i <= 4; i++) {
                    criteria = getSession().createCriteria(Formula.class);
                    Formula formula = new Formula();
                    switch (i) {
                        case 1:
                            criteria.add(Restrictions.eq("id", liquidacion.getIdFormuInteresCesan()));
                            formula = (Formula) criteria.uniqueResult();
                            liquidacion.setFormuInteresCesan(formula.getFormula());
                            break;
                        case 2:
                            criteria.add(Restrictions.eq("id", liquidacion.getIdFormulaCesantias()));
                            formula = (Formula) criteria.uniqueResult();
                            liquidacion.setFormulaCesantias(formula.getFormula());
                            break;
                        case 3:
                            criteria.add(Restrictions.eq("id", liquidacion.getIdFormulaPs()));
                            formula = (Formula) criteria.uniqueResult();
                            liquidacion.setFormulaPs(formula.getFormula());
                            break;
                        case 4:
                            criteria.add(Restrictions.eq("id", liquidacion.getIdFormulaVacaciones()));
                            formula = (Formula) criteria.uniqueResult();
                            liquidacion.setFormulaVacaciones(formula.getFormula());
                            break;
                    }
                }
            } else {
                liquidacion = null;
            }
        } catch (Exception e) {
            liquidacion = null;
        } finally {
            close();
            return liquidacion;
        }
    }

    public List<Formula> listarFormulasPorTipo(int idTipoFormuala) {
        List<Formula> formulas = new ArrayList<Formula>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Formula.class);
            Criterion tipoFormula = Restrictions.eq("idTipoFormula", idTipoFormuala);
            Criterion tipoForumlaOtra = Restrictions.eq("idTipoFormula", 5);
            LogicalExpression orExp = Restrictions.or(tipoFormula, tipoForumlaOtra);
            criteria.add(orExp);
            criteria.addOrder(Order.desc("fecha"));
            formulas = criteria.list();
        } catch (Exception e) {
            formulas = null;
        } finally {
            close();
            return formulas;
        }

    }

    public DatosLiquidacionVO listarDatosLiquidacion(int idContrato) {
        DatosLiquidacionVO datosLiquidacionVO = null;
        try {
            begin();
            Criteria criteria = getSession().createCriteria(ValorFormaPago.class);
            criteria.add(Restrictions.eq("contrato", new Contrato(idContrato)));
            criteria.add(Restrictions.eq("esIbc", (short) 1));
            ValorFormaPago valorFormaPago = (ValorFormaPago) criteria.uniqueResult();
            double valorIbc = valorFormaPago.getValorPago();

            criteria = getSession().createCriteria(Tributaria.class);
            criteria.addOrder(Order.desc("ano"));
            List<Tributaria> tributarias = criteria.list();

            double valAuxTrans = tributarias.get(0).getAuxilioTrasporte();
            double valSMLV = tributarias.get(0).getSmlv();

            double suelBasico = 0;

            if (valorIbc <= valSMLV) {
                suelBasico = valSMLV;
            } else {
                suelBasico = valorIbc;
            }

            criteria = getSession().createCriteria(Contrato.class);
            criteria.add(Restrictions.eq("id", idContrato));
            Contrato contrato = (Contrato) criteria.uniqueResult();

            Date fechaTerminacion = contrato.getFechaRealTerminacion();

            datosLiquidacionVO = new DatosLiquidacionVO(suelBasico, valAuxTrans, fechaTerminacion);

        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return datosLiquidacionVO;
        }
    }
}
