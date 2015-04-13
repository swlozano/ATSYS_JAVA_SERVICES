/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Persona;
import Utilidades.DAO;
import Utilidades.Fechas;
import VO.FacturaReportN1VO;
import VO.FacturaReportN2VO;
import VO.FacturaVO;
import VO.FechaIniFinVO;
import VO.PersonaVO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class ReporteFacturaDao extends DAO {

    private static final int ANUAL = 1;
    private static final int SEMESTRAL = 2;
    private static final int BIMENSUAL = 3;
    private static final int MENSUAL = 4;
    private static final int RANGO_FECHAS = 5;

    public List<FacturaReportN1VO> generarReporteFactura(int tipoPeriodo, Calendar fechaInicio, Calendar fechaFin, short tipoFecha, short tipoEstado, int[] idsPersonas) {
        List<FacturaReportN1VO> facturaReportN1VOs = new ArrayList<FacturaReportN1VO>();
        try {
            begin();
            switch (tipoPeriodo) {
                case ANUAL:
                    facturaReportN1VOs = reporteAnual(fechaInicio, fechaFin, tipoFecha, tipoEstado, idsPersonas);
                    break;
                case SEMESTRAL:
                    facturaReportN1VOs = reporteSemestrar(fechaInicio, fechaFin, tipoFecha, tipoEstado, idsPersonas);
                    break;
                case BIMENSUAL:
                    facturaReportN1VOs = reporteBimensual(fechaInicio, fechaFin, tipoFecha, tipoEstado, idsPersonas);
                    break;
                case MENSUAL:
                    facturaReportN1VOs = reporteMensual(fechaInicio, fechaFin, tipoFecha, tipoEstado, idsPersonas);
                    break;
                case RANGO_FECHAS:
                    facturaReportN1VOs = reporteRangoFechas(fechaInicio, fechaFin, tipoFecha, tipoEstado, idsPersonas);
                    break;
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return facturaReportN1VOs;
        }
    }

    private List<FacturaReportN1VO> reporteAnual(Calendar fechaInicio, Calendar fechaFin, short tipoFecha, short tipoEstado, int[] idsPersonas) {
        List<FacturaReportN1VO> facturaReportN1VOs = new ArrayList<FacturaReportN1VO>();
        try {
            Calendar fecha1 = Calendar.getInstance();
            Calendar fecha2 = Calendar.getInstance();

            fecha1.set(fechaInicio.get(Calendar.YEAR), fechaInicio.get(Calendar.MONTH), fechaInicio.get(Calendar.DATE));
            fecha2.set(fechaInicio.get(Calendar.YEAR), fechaInicio.get(Calendar.MONTH), fechaInicio.get(Calendar.DATE));

            fecha2.set(Calendar.MONTH, 11);
            fecha2.set(Calendar.DAY_OF_MONTH, 31);

            for (int year = fechaInicio.get(Calendar.YEAR); year <= fechaFin.get(Calendar.YEAR); year++) {

                fecha1.set(Calendar.YEAR, year);
                fecha2.set(Calendar.YEAR, year);

                List<FacturaReportN2VO> facturaReportN2VOs = new ArrayList<FacturaReportN2VO>();
                facturaReportN2VOs = generarListaReporteFacturaN2(fecha1, fecha2, tipoFecha, tipoEstado, idsPersonas);
                FacturaReportN1VO facturaReportN1VO = new FacturaReportN1VO();
                facturaReportN1VO = generarFacturaReportN1VO(facturaReportN2VOs, fecha1, fecha2);
                facturaReportN1VOs.add(facturaReportN1VO);

            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            System.out.println();
            return facturaReportN1VOs;
        }
    }

    private List<FacturaReportN1VO> reporteSemestrar(Calendar fechaInicio, Calendar fechaFin, short tipoFecha, short tipoEstado, int[] idsPersonas) {
        List<FacturaReportN1VO> facturaReportN1VOs = new ArrayList<FacturaReportN1VO>();
        try {

            Calendar fecha1;
            Calendar fecha2;

            Vector<FechaIniFinVO> fechaIniFinVOs = new Vector<FechaIniFinVO>();
            fechaIniFinVOs = getFechasSemestre(fechaInicio, fechaFin);

            for (int i = 0; i < fechaIniFinVOs.size(); i++) {
                FechaIniFinVO fechaIniFinVO = fechaIniFinVOs.elementAt(i);
                fecha1 = Calendar.getInstance();
                fecha2 = Calendar.getInstance();

                fecha1.set(fechaIniFinVO.getFechaInicio().get(Calendar.YEAR), fechaIniFinVO.getFechaInicio().get(Calendar.MONTH), fechaIniFinVO.getFechaInicio().get(Calendar.DATE));
                fecha2.set(fechaIniFinVO.getFechaFin().get(Calendar.YEAR), fechaIniFinVO.getFechaFin().get(Calendar.MONTH), fechaIniFinVO.getFechaFin().get(Calendar.DATE));

                List<FacturaReportN2VO> facturaReportN2VOs = new ArrayList<FacturaReportN2VO>();
                facturaReportN2VOs = generarListaReporteFacturaN2(fecha1, fecha2, tipoFecha, tipoEstado, idsPersonas);
                FacturaReportN1VO facturaReportN1VO = new FacturaReportN1VO();
                facturaReportN1VO = generarFacturaReportN1VO(facturaReportN2VOs, fecha1, fecha2);
                facturaReportN1VOs.add(facturaReportN1VO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return facturaReportN1VOs;
        }
    }

    private List<FacturaReportN1VO> reporteBimensual(Calendar fechaInicio, Calendar fechaFin, short tipoFecha, short tipoEstado, int[] idsPersonas) {
        List<FacturaReportN1VO> facturaReportN1VOs = new ArrayList<FacturaReportN1VO>();
        try {

            Calendar fecha1;
            Calendar fecha2;

            Vector<FechaIniFinVO> fechaIniFinVOs = new Vector<FechaIniFinVO>();
            fechaIniFinVOs = getFechasBimensual(fechaInicio, fechaFin);

            for (int i = 0; i < fechaIniFinVOs.size(); i++) {
                FechaIniFinVO fechaIniFinVO = fechaIniFinVOs.elementAt(i);
                fecha1 = Calendar.getInstance();
                fecha2 = Calendar.getInstance();

                fecha1.set(fechaIniFinVO.getFechaInicio().get(Calendar.YEAR), fechaIniFinVO.getFechaInicio().get(Calendar.MONTH), fechaIniFinVO.getFechaInicio().get(Calendar.DATE));
                fecha2.set(fechaIniFinVO.getFechaFin().get(Calendar.YEAR), fechaIniFinVO.getFechaFin().get(Calendar.MONTH), fechaIniFinVO.getFechaFin().get(Calendar.DATE));

                List<FacturaReportN2VO> facturaReportN2VOs = new ArrayList<FacturaReportN2VO>();
                facturaReportN2VOs = generarListaReporteFacturaN2(fecha1, fecha2, tipoFecha, tipoEstado, idsPersonas);
                FacturaReportN1VO facturaReportN1VO = new FacturaReportN1VO();
                facturaReportN1VO = generarFacturaReportN1VO(facturaReportN2VOs, fecha1, fecha2);
                facturaReportN1VOs.add(facturaReportN1VO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return facturaReportN1VOs;
        }
    }

    private List<FacturaReportN1VO> reporteMensual(Calendar fechaInicio, Calendar fechaFin, short tipoFecha, short tipoEstado, int[] idsPersonas) {
        List<FacturaReportN1VO> facturaReportN1VOs = new ArrayList<FacturaReportN1VO>();
        try {

            Calendar fecha1;
            Calendar fecha2;

            Vector<FechaIniFinVO> fechaIniFinVOs = new Vector<FechaIniFinVO>();
            fechaIniFinVOs = getFechasMensual(fechaInicio, fechaFin);

            for (int i = 0; i < fechaIniFinVOs.size(); i++) {
                FechaIniFinVO fechaIniFinVO = fechaIniFinVOs.elementAt(i);
                fecha1 = Calendar.getInstance();
                fecha2 = Calendar.getInstance();

                fecha1.set(fechaIniFinVO.getFechaInicio().get(Calendar.YEAR), fechaIniFinVO.getFechaInicio().get(Calendar.MONTH), fechaIniFinVO.getFechaInicio().get(Calendar.DATE));
                fecha2.set(fechaIniFinVO.getFechaFin().get(Calendar.YEAR), fechaIniFinVO.getFechaFin().get(Calendar.MONTH), fechaIniFinVO.getFechaFin().get(Calendar.DATE));

                List<FacturaReportN2VO> facturaReportN2VOs = new ArrayList<FacturaReportN2VO>();
                facturaReportN2VOs = generarListaReporteFacturaN2(fecha1, fecha2, tipoFecha, tipoEstado, idsPersonas);
                FacturaReportN1VO facturaReportN1VO = new FacturaReportN1VO();
                facturaReportN1VO = generarFacturaReportN1VO(facturaReportN2VOs, fecha1, fecha2);
                facturaReportN1VOs.add(facturaReportN1VO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return facturaReportN1VOs;
        }
    }

    private List<FacturaReportN1VO> reporteRangoFechas(Calendar fechaInicio, Calendar fechaFin, short tipoFecha, short tipoEstado, int[] idsPersonas) {
        List<FacturaReportN1VO> facturaReportN1VOs = new ArrayList<FacturaReportN1VO>();
        try {

            Calendar fecha1;
            Calendar fecha2;

            Vector<FechaIniFinVO> fechaIniFinVOs = new Vector<FechaIniFinVO>();
            fechaIniFinVOs.add(new FechaIniFinVO(fechaInicio, fechaFin));

            for (int i = 0; i < fechaIniFinVOs.size(); i++) {
                FechaIniFinVO fechaIniFinVO = fechaIniFinVOs.elementAt(i);
                fecha1 = Calendar.getInstance();
                fecha2 = Calendar.getInstance();
                fecha1.set(fechaIniFinVO.getFechaInicio().get(Calendar.YEAR), fechaIniFinVO.getFechaInicio().get(Calendar.MONTH), fechaIniFinVO.getFechaInicio().get(Calendar.DATE));
                fecha2.set(fechaIniFinVO.getFechaFin().get(Calendar.YEAR), fechaIniFinVO.getFechaFin().get(Calendar.MONTH), fechaIniFinVO.getFechaFin().get(Calendar.DATE));
                List<FacturaReportN2VO> facturaReportN2VOs = new ArrayList<FacturaReportN2VO>();
                facturaReportN2VOs = generarListaReporteFacturaN2(fecha1, fecha2, tipoFecha, tipoEstado, idsPersonas);
                FacturaReportN1VO facturaReportN1VO = new FacturaReportN1VO();
                facturaReportN1VO = generarFacturaReportN1VO(facturaReportN2VOs, fecha1, fecha2);
                facturaReportN1VOs.add(facturaReportN1VO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return facturaReportN1VOs;
        }
    }

    private double[] getTotalesN2(List<FacturaVO> facturaVOs) {
        double totalesAPagar = 0;
        double totalesPagados = 0;
        double totalesSubtotales = 0;
        double totalesNumeroVentas = 0;
        double[] retorno = new double[4];
        try {
            totalesNumeroVentas = facturaVOs.size();
            for (FacturaVO facturaVO : facturaVOs) {
                totalesAPagar = totalesAPagar + facturaVO.getTotalApagar();
                totalesSubtotales = totalesSubtotales + facturaVO.getSubtutal();
                double aux = facturaVO.getTotalApagar() - facturaVO.getSaldo();
                totalesPagados = totalesPagados + aux;
            }
            retorno[0] = totalesAPagar;
            retorno[1] = totalesPagados;
            retorno[2] = totalesSubtotales;
            retorno[3] = totalesNumeroVentas;
        } catch (Exception e) {
            System.out.println();
        } finally {
            return retorno;
        }
    }

    private PersonaVO getPersonaVO(int idPersona) {
        PersonaVO personaVO = null;
        try {
            Criteria criteria = getSession().createCriteria(Persona.class);
            criteria.add(Restrictions.eq("id", idPersona));
            Persona persona = (Persona) criteria.uniqueResult();
            PersonaDao personaDao = new PersonaDao();
            personaVO = new PersonaVO();
            personaVO = personaDao.convertPersonaToPersonaVO(persona);
        } catch (Exception e) {
            System.out.println();
        } finally {
            return personaVO;
        }
    }

    private FacturaReportN1VO getTotalesN1(List<FacturaReportN2VO> facturaReportN2VOs) {
        double totalesAPagar = 0;
        double totalesPagados = 0;
        double totalesSubtotales = 0;
        double totalesNumeroVentas = 0;
        FacturaReportN1VO facturaReportN1VO = new FacturaReportN1VO();
        try {
            for (FacturaReportN2VO facturaReportN2VO : facturaReportN2VOs) {
                totalesAPagar = totalesAPagar + facturaReportN2VO.getTotalesAPagar();
                totalesPagados = totalesPagados + facturaReportN2VO.getTotalesPagados();
                totalesSubtotales = totalesSubtotales + facturaReportN2VO.getTotalesSubtotales();
                totalesNumeroVentas = totalesNumeroVentas + facturaReportN2VO.getTotalesNumeroVentas();
            }
            facturaReportN1VO = new FacturaReportN1VO(totalesAPagar, totalesPagados, totalesSubtotales, totalesNumeroVentas);

        } catch (Exception e) {
            System.out.println();
        } finally {
            return facturaReportN1VO;
        }
    }

    private List<FacturaReportN2VO> generarListaReporteFacturaN2(Calendar fecha1, Calendar fecha2, short tipoFecha, short tipoEstado, int[] idsPersonas) {
        List<FacturaReportN2VO> facturaReportN2VOs = new ArrayList<FacturaReportN2VO>();
        try {
            FacturaDao facturaDao = new FacturaDao();
            for (int i : idsPersonas) {
                List<FacturaVO> facturaVOs = new ArrayList<FacturaVO>();
                facturaVOs = facturaDao.listar(fecha1, fecha2, tipoFecha, tipoEstado, i);
                double[] totales = getTotalesN2(facturaVOs);
                PersonaVO personaVO = new PersonaVO();
                personaVO = getPersonaVO(i);
                FacturaReportN2VO facturaReportN2VO = new FacturaReportN2VO(personaVO, totales[0], totales[1], totales[2], totales[3], facturaVOs);
                facturaReportN2VOs.add(facturaReportN2VO);
            }
        } catch (Exception e) {
            System.out.println();
        } finally {
            return facturaReportN2VOs;
        }
    }

    private FacturaReportN1VO generarFacturaReportN1VO(List<FacturaReportN2VO> facturaReportN2VOs, Calendar fecha1, Calendar fecha2) {
        FacturaReportN1VO facturaReportN1VO = new FacturaReportN1VO();
        try {
            facturaReportN1VO = getTotalesN1(facturaReportN2VOs);
            facturaReportN1VO.setFacturaReportN2VOs(facturaReportN2VOs);
            facturaReportN1VO.setFechaInicio(fecha1.getTime());
            facturaReportN1VO.setFechaFin(fecha2.getTime());
        } catch (Exception e) {
            System.out.println();
        } finally {
            return facturaReportN1VO;
        }
    }

    private Vector<FechaIniFinVO> getFechasSemestre(Calendar fechaInicio, Calendar fechaFin) {
        Vector<FechaIniFinVO> fechaIniFinVOs = new Vector<FechaIniFinVO>();
        int yearInicio = fechaInicio.get(Calendar.YEAR);
        int yearFin = fechaFin.get(Calendar.YEAR);

        int mesInicio = fechaInicio.get(Calendar.MONTH);
        int mesFin = fechaFin.get(Calendar.MONTH);
        FechaIniFinVO fechaIniFinVO;

        Calendar fecha1;
        Calendar fecha2;


        for (int iyear = yearInicio; iyear <= yearFin; iyear++) {
            fecha1 = Calendar.getInstance();
            fecha1.set(Calendar.YEAR, iyear);
            fecha1.set(Calendar.MONTH, 0);
            fecha1.set(Calendar.DAY_OF_MONTH, 1);

            fecha2 = Calendar.getInstance();
            fecha2.set(Calendar.YEAR, iyear);
            fecha2.set(Calendar.MONTH, 5);
            fecha2.set(Calendar.DAY_OF_MONTH, 30);

            fechaIniFinVO = new FechaIniFinVO(fecha1 , fecha2);
            fechaIniFinVOs.add(fechaIniFinVO);

            fecha1 = Calendar.getInstance();
            fecha1.set(Calendar.YEAR, iyear);
            fecha1.set(Calendar.MONTH, 6);
            fecha1.set(Calendar.DAY_OF_MONTH, 1);

            fecha2 = Calendar.getInstance();
            fecha2.set(Calendar.YEAR, iyear);
            fecha2.set(Calendar.MONTH, 11);
            fecha2.set(Calendar.DAY_OF_MONTH, 31);

            fechaIniFinVO = new FechaIniFinVO(fecha1, fecha2);
            fechaIniFinVOs.add(fechaIniFinVO);
        }

        if (mesInicio == 6) {
            fechaIniFinVOs.remove(0);
        }
        if (mesFin == 5) {
            fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
        }

        return fechaIniFinVOs;
    }

    private Vector<FechaIniFinVO> getFechasBimensual(Calendar fechaInicio, Calendar fechaFin) {
        Vector<FechaIniFinVO> fechaIniFinVOs = new Vector<FechaIniFinVO>();
        int yearInicio = fechaInicio.get(Calendar.YEAR);
        int yearFin = fechaFin.get(Calendar.YEAR);

        int mesInicio = fechaInicio.get(Calendar.MONTH);
        int mesFin = fechaFin.get(Calendar.MONTH);
        FechaIniFinVO fechaIniFinVO;

        Calendar fecha1;
        Calendar fecha2;

        for (int iyear = yearInicio; iyear <= yearFin; iyear++) {

            fecha1 = Calendar.getInstance();
            fecha1.set(Calendar.YEAR, iyear);
            fecha1.set(Calendar.MONTH, 0);
            fecha1.set(Calendar.DAY_OF_MONTH, 1);

            fecha2 = Calendar.getInstance();
            fecha2.set(Calendar.YEAR, iyear);
            fecha2.set(Calendar.MONTH, 1);
            int diaMes = 28;
            if (fecha2.get(Calendar.YEAR) % 4 == 0) {
                diaMes = 29;
            }
            fecha2.set(Calendar.DAY_OF_MONTH, diaMes);
            fechaIniFinVO = new FechaIniFinVO(fecha1, fecha2);
            fechaIniFinVOs.add(fechaIniFinVO);

            ////////////////////////////////////////////////////////////

            fecha1 = Calendar.getInstance();
            fecha1.set(Calendar.YEAR, iyear);
            fecha1.set(Calendar.MONTH, 2);
            fecha1.set(Calendar.DAY_OF_MONTH, 1);

            fecha2 = Calendar.getInstance();
            fecha2.set(Calendar.YEAR, iyear);
            fecha2.set(Calendar.MONTH, 3);
            fecha2.set(Calendar.DAY_OF_MONTH, 30);

            fechaIniFinVO = new FechaIniFinVO(fecha1, fecha2);
            fechaIniFinVOs.add(fechaIniFinVO);

            ////////////////////////////////////////////////////////////

            fecha1 = Calendar.getInstance();
            fecha1.set(Calendar.YEAR, iyear);
            fecha1.set(Calendar.MONTH, 4);
            fecha1.set(Calendar.DAY_OF_MONTH, 1);

            fecha2 = Calendar.getInstance();
            fecha2.set(Calendar.YEAR, iyear);
            fecha2.set(Calendar.MONTH, 5);
            fecha2.set(Calendar.DAY_OF_MONTH, 30);

            fechaIniFinVO = new FechaIniFinVO(fecha1, fecha2);
            fechaIniFinVOs.add(fechaIniFinVO);

            ////////////////////////////////////////////////////////////

            fecha1 = Calendar.getInstance();
            fecha1.set(Calendar.YEAR, iyear);
            fecha1.set(Calendar.MONTH, 6);
            fecha1.set(Calendar.DAY_OF_MONTH, 1);

            fecha2 = Calendar.getInstance();
            fecha2.set(Calendar.YEAR, iyear);
            fecha2.set(Calendar.MONTH, 7);
            fecha2.set(Calendar.DAY_OF_MONTH, 31);

            fechaIniFinVO = new FechaIniFinVO(fecha1, fecha2);
            fechaIniFinVOs.add(fechaIniFinVO);

            ////////////////////////////////////////////////////////////

            fecha1 = Calendar.getInstance();
            fecha1.set(Calendar.YEAR, iyear);
            fecha1.set(Calendar.MONTH, 8);
            fecha1.set(Calendar.DAY_OF_MONTH, 1);

            fecha2 = Calendar.getInstance();
            fecha2.set(Calendar.YEAR, iyear);
            fecha2.set(Calendar.MONTH, 9);
            fecha2.set(Calendar.DAY_OF_MONTH, 31);

            fechaIniFinVO = new FechaIniFinVO(fecha1, fecha2);
            fechaIniFinVOs.add(fechaIniFinVO);

            ////////////////////////////////////////////////////////////

            fecha1 = Calendar.getInstance();
            fecha1.set(Calendar.YEAR, iyear);
            fecha1.set(Calendar.MONTH, 10);
            fecha1.set(Calendar.DAY_OF_MONTH, 1);

            fecha2 = Calendar.getInstance();
            fecha2.set(Calendar.YEAR, iyear);
            fecha2.set(Calendar.MONTH, 11);
            fecha2.set(Calendar.DAY_OF_MONTH, 31);

            fechaIniFinVO = new FechaIniFinVO(fecha1, fecha2);
            fechaIniFinVOs.add(fechaIniFinVO);
        }

        switch (mesInicio) {
            case 2:
                fechaIniFinVOs.remove(0);
                break;
            case 4:
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                break;
            case 6:
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                break;
            case 8:
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                break;
            case 10:
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                fechaIniFinVOs.remove(0);
                break;
        }

        switch (mesFin) {
            case 1:
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                break;
            case 3:
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                break;
            case 5:
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                break;
            case 7:
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                break;
            case 9:
                fechaIniFinVOs.remove(fechaIniFinVOs.size() - 1);
                break;
        }

        return fechaIniFinVOs;
    }

    private Vector<FechaIniFinVO> getFechasMensual(Calendar fechaInicio, Calendar fechaFin) {
        Vector<FechaIniFinVO> fechaIniFinVOs = new Vector<FechaIniFinVO>();
        int yearInicio = fechaInicio.get(Calendar.YEAR);
        int yearFin = fechaFin.get(Calendar.YEAR);

        int mesInicio = fechaInicio.get(Calendar.MONTH);
        int mesFin = fechaFin.get(Calendar.MONTH);
        FechaIniFinVO fechaIniFinVO;

        Calendar fecha1;
        Calendar fecha2;

        Fechas fechas = new Fechas();

        for (int iyear = yearInicio; iyear <= yearFin; iyear++) {
            int mesFinal = 11;
            if (iyear == yearFin) {
                mesFinal = mesFin;
            }
            for (int imes = mesInicio; imes <= mesFinal; imes++) {
                fecha1 = Calendar.getInstance();
                fecha1.set(Calendar.YEAR, iyear);
                fecha1.set(Calendar.MONTH, imes);
                fecha1.set(Calendar.DAY_OF_MONTH, 1);

                fecha2 = Calendar.getInstance();
                fecha2.set(Calendar.YEAR, iyear);
                fecha2.set(Calendar.MONTH, imes);
                fecha2.set(Calendar.DAY_OF_MONTH, fechas.getNumeroDiasMes(imes + 1, iyear));

                fechaIniFinVO = new FechaIniFinVO(fecha1, fecha2);
                fechaIniFinVOs.add(fechaIniFinVO);
            }
            mesInicio = 0;

        }

        return fechaIniFinVOs;
    }
}
