/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import VO.FechaPagoVO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class Fechas {

    public List<FechaPagoVO> getFechasPago(Calendar fInipago, Calendar fIni, Calendar fFin, int periodoPago) {
        List<Date> dateFechasPagos = new ArrayList<Date>();
        List<Date> dateIntervalo1 = new ArrayList<Date>();
        List<Date> dateIntervalo2 = new ArrayList<Date>();
        int contadorDias = getTotalDias(fInipago, fIni, fFin, periodoPago);
        dateFechasPagos = generarFechasPago(fInipago, periodoPago, contadorDias);
        dateIntervalo1 = generarIntervalo1Fp(fIni, periodoPago, contadorDias);
        dateIntervalo2 = generarIntervalo2Fp(periodoPago, contadorDias, fFin.get(Calendar.DATE), dateIntervalo1);
        return generarListaFpVos(dateFechasPagos, dateIntervalo1, dateIntervalo2);
    }

    private List<FechaPagoVO> generarListaFpVos(List<Date> dateFechasPagos, List<Date> dateIntervalo1, List<Date> dateIntervalo2) {
        List<FechaPagoVO> fechaPagoVOs = new ArrayList<FechaPagoVO>();
        FechaPagoVO fechaPagoVO;
        for (int i = 0; i < dateFechasPagos.size(); i++) {
            fechaPagoVO = new FechaPagoVO(dateFechasPagos.get(i), dateIntervalo1.get(i), dateIntervalo2.get(i));
            fechaPagoVOs.add(fechaPagoVO);
        }
        return fechaPagoVOs;
    }

    private int getTotalDias(Calendar fInipago, Calendar fIni, Calendar fFin, int periodoPago) {

        int mesFinLocal = 0;
        int mesInicioLocal = fIni.get(Calendar.MONTH);
        int contadorDias = /*(fIni.get(Calendar.DATE) - 30) + 1;*/ 0;
        boolean ban = true;
        for (int i = fIni.get(Calendar.YEAR); i <= fFin.get(Calendar.YEAR); i++) {
            if (fFin.get(Calendar.YEAR) == i) {
                mesFinLocal = fFin.get(Calendar.MONTH);
            } else {
                mesFinLocal = 11;
            }
            for (int j = mesInicioLocal; j <= mesFinLocal; j++) {
                if (ban) {
                    contadorDias = (30 - fIni.get(Calendar.DATE)) + 1;
                    ban = false;
                } else {
                    if (fFin.get(Calendar.YEAR) == i) {
                        if (j == mesFinLocal) {
                            contadorDias = contadorDias + getDiaUtimoMes(periodoPago, fFin.get(Calendar.DATE));
                        } else {
                            contadorDias = contadorDias + 30;
                        }
                    } else {
                        contadorDias = contadorDias + 30;
                    }
                }
            }
            mesInicioLocal = 0;
        }

        return contadorDias;
    }

    private int getDiaUtimoMes(int periodoPago, int diaUltimoFecha) {
        int numeroParticion = 30 / periodoPago;
        int intervalo1 = 1;
        int intervalo2 = periodoPago;
        if (diaUltimoFecha == 31) {
            diaUltimoFecha = 30;
        }
        for (int i = 0; i < numeroParticion; i++) {
            if (diaUltimoFecha >= intervalo1 && diaUltimoFecha <= intervalo2) {
                break;
            }
            intervalo1 = intervalo1 + periodoPago;
            intervalo2 = intervalo2 + periodoPago;

        }
        return intervalo2;
    }

    private List<Date> generarFechasPago(Calendar fechaInicioPago, int periodo, int totalDias) {

        List<Date> dateFechasPagos = new ArrayList<Date>();
        FechaPagoVO fechaPagoVO;
        int yearInicioPago = fechaInicioPago.get(Calendar.YEAR);
        int mesInicioPago = fechaInicioPago.get(Calendar.MONTH);
        int diaInicioPago = fechaInicioPago.get(Calendar.DATE);

        int yearFinPago = yearInicioPago;
        int mesFinPago = mesInicioPago;
        int diaFinPago = diaInicioPago;
        while (totalDias > periodo) {
            System.out.println(" ********** fechaPago ******* " + yearFinPago + "/" + mesFinPago + "/" + diaFinPago);
            Calendar fecha = Calendar.getInstance();
            fecha.set(yearFinPago, mesFinPago, diaFinPago);
            dateFechasPagos.add(fecha.getTime());
            diaFinPago = diaFinPago + periodo;
            if (diaFinPago >= 30) {
                diaFinPago = 1;
                if ((mesFinPago + 1) == 12) {
                    mesFinPago = 0;
                    yearFinPago++;
                } else {
                    mesFinPago++;
                }
                /*if (mesFinPago == 11) {
                 }*/
            }
            totalDias = totalDias - periodo;
        }
        System.out.println(" ********** fechaPago ******* " + yearFinPago + "/" + mesFinPago + "/" + diaFinPago);
        Calendar fecha = Calendar.getInstance();
        fecha.set(yearFinPago, mesFinPago, diaFinPago);
        dateFechasPagos.add(fecha.getTime());
        return dateFechasPagos;
    }

    private List<Date> generarIntervalo1Fp(Calendar fechaInicio, int periodo, int totalDias) {

        List<Date> dateIntervalo1 = new ArrayList<Date>();
        int yearInicio = fechaInicio.get(Calendar.YEAR);
        int mesInicio = fechaInicio.get(Calendar.MONTH);
        int diaInicio = fechaInicio.get(Calendar.DATE);

        int yearInicioInter = yearInicio;
        int mesIniIntervalo = mesInicio;
        int diaIniIntervalo = diaInicio;
        while (totalDias > periodo) {
            System.out.println(" ********** fechaPago inervalo 1 ******* " + yearInicioInter + "/" + mesIniIntervalo + "/" + diaIniIntervalo);
            /************************************************************/
            Calendar fecha = Calendar.getInstance();
            fecha.set(yearInicioInter, mesIniIntervalo, diaIniIntervalo);
            dateIntervalo1.add(fecha.getTime());
            /************************************************************/
            diaIniIntervalo = peiriodoDiaIntervalo1(periodo, diaIniIntervalo); /*diaIniIntervalo + periodo;*/
            if (diaIniIntervalo >= 30) {
                diaIniIntervalo = 1;
                if ((mesIniIntervalo + 1) == 12) {
                    yearInicioInter++;
                    mesIniIntervalo = 0;
                } else {
                    mesIniIntervalo++;
                }
                /*if (mesIniIntervalo == 11) {
                    yearInicioInter++;
                }*/
            } 
            totalDias = totalDias - periodo;
        }
        System.out.println(" ********** fechaPago inervalo 1 ******* " + yearInicioInter + "/" + mesIniIntervalo + "/" + diaIniIntervalo);
        /************************************************************/
        Calendar fecha = Calendar.getInstance();
        fecha.set(yearInicioInter, mesIniIntervalo, diaIniIntervalo);
        dateIntervalo1.add(fecha.getTime());
        /************************************************************/
        return dateIntervalo1;
    }

    private int peiriodoDiaIntervalo1(int periodoPago, int dia) {
        int numeroParticion = 30 / periodoPago;
        int intervalo1 = 1;
        int intervalo2 = periodoPago;
        if (dia == 31) {
            dia = 30;
        }
        for (int i = 0; i < numeroParticion; i++) {
            if (dia >= intervalo1 && dia <= intervalo2) {
                break;
            }
            intervalo1 = intervalo1 + periodoPago;
            intervalo2 = intervalo2 + periodoPago;
        }
        return intervalo1+periodoPago;
    }

    private List<Date> generarIntervalo2Fp(int periodo, int totalDias, int diaFin, List<Date> dateIntervalo1) {
        List<Date> dateIntervalo2 = new ArrayList<Date>();
        int diaInicio;
        Calendar calDiaIntervalo1;
        int cont = 1;
        for (Date date : dateIntervalo1) {
            calDiaIntervalo1 = Calendar.getInstance();
            calDiaIntervalo1.setTime(date);
            if (cont < dateIntervalo1.size()) {
                diaInicio = getInicioDiaIntervalo2(periodo, calDiaIntervalo1.get(Calendar.DATE));
            } else {
                diaInicio = diaFin;
            }
            if (diaInicio >= 30) {
                diaInicio = getNumeroDiasMes(calDiaIntervalo1.get(Calendar.MONTH) + 1, calDiaIntervalo1.get(Calendar.YEAR));
            }
            calDiaIntervalo1.set(Calendar.DATE, diaInicio);
            dateIntervalo2.add(calDiaIntervalo1.getTime());
            cont++;
        }

        for (Date date : dateIntervalo2) {

            System.out.println(" ************ intervalo2 **************** " + date);

        }
        return dateIntervalo2;
    }

    private int getInicioDiaIntervalo2(int periodoPago, int diaInicioIntervalo1) {

        int numeroParticion = 30 / periodoPago;
        int intervalo1 = 1;
        int intervalo2 = periodoPago;
        if (diaInicioIntervalo1 == 31) {
            diaInicioIntervalo1 = 30;
        }
        for (int i = 0; i < numeroParticion; i++) {
            if (diaInicioIntervalo1 >= intervalo1 && diaInicioIntervalo1 <= intervalo2) {
                break;
            }
            intervalo1 = intervalo1 + periodoPago;
            intervalo2 = intervalo2 + periodoPago;

        }
        return intervalo2;
    }
    private static final int ENERO_NDIAS = 31;
    //private static int FEB_NDIAS = 30;
    private static final int MARZO_NDIAS = 31;
    private static final int ABRIL_NDIAS = 30;
    private static final int MAYO_NDIAS = 31;
    private static final int JUNIO_NDIAS = 30;
    private static final int JULIO_NDIAS = 31;
    private static final int AGOSTO_NDIAS = 31;
    private static final int SEP_NDIAS = 30;
    private static final int OCTUBRE_NDIAS = 31;
    private static final int NOVIEMBRE_NDIAS = 30;
    private static final int DIC_NDIAS = 31;

    public  int getNumeroDiasMes(int mes, int year) {
        int ndias = 0;
        switch (mes) {
            case 1:
                ndias = ENERO_NDIAS;
                break;
            case 2:
                if (year % 4 == 0) {
                    ndias = 29;
                } else {
                    ndias = 28;
                }
                break;
            case 3:
                ndias = MARZO_NDIAS;
                break;
            case 4:
                ndias = ABRIL_NDIAS;
                break;
            case 5:
                ndias = MAYO_NDIAS;
                break;
            case 6:
                ndias = JUNIO_NDIAS;
                break;
            case 7:
                ndias = JULIO_NDIAS;
                break;
            case 8:
                ndias = AGOSTO_NDIAS;
                break;
            case 9:
                ndias = SEP_NDIAS;
                break;
            case 10:
                ndias = OCTUBRE_NDIAS;
                break;
            case 11:
                ndias = NOVIEMBRE_NDIAS;
                break;
            case 12:
                ndias = DIC_NDIAS;
                break;
        }
        return ndias;
    }
}


