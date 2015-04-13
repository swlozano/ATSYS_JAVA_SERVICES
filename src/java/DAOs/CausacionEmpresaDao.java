/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.CausacionEmpresa;
import Utilidades.DAO;

/**
 *
 * @author Alejandro
 */
public class CausacionEmpresaDao extends DAO {

    protected CausacionEmpresa crearCausacionEmpresa(double ibc, short diasTrabajados, double salario, double auxilioTransporte) {
        CausacionEmpresa causacionEmpresa = null;
        try {
            int diasCotizados = 30;
            double ibc_empresa = getIbcEmpres(ibc, diasCotizados);
            double salud = getSalud(ibc_empresa);
            double pension = getPension(ibc_empresa);
            double ibcRiesgos = getIbcRiesgos(ibc);
            double riesgos = getRiesgos(ibcRiesgos, diasTrabajados);
            double cesantias = getCesantias(salario, auxilioTransporte, diasCotizados);
            double interesesCesantias = getIteresesCesantias(salario, diasCotizados);
            double ccf = getCCF(ibc_empresa);
            double sena = getSena(ibc_empresa);
            double icbf = getIcbf(ibc_empresa);
            double prima = getPrima(salario, auxilioTransporte, diasCotizados);
            double vacaciones = getVacaciones(salario, diasCotizados);
            double total = salud + pension + riesgos + cesantias + interesesCesantias + ccf + sena + icbf + prima + vacaciones;

             causacionEmpresa = new CausacionEmpresa(diasCotizados, salud, pension, ibcRiesgos, riesgos, cesantias, interesesCesantias, ccf, sena, icbf, prima, vacaciones, total,ibc_empresa);
        } catch (Exception e) {
            System.out.println();
        } finally {
            return causacionEmpresa;
        }
    }

    private double getIbcEmpres(double ibc, int diasCotizados) {
        double ibc_empresa = (ibc * diasCotizados) / 30;
        return ibc_empresa;
    }

    private double getSalud(double ibc_empresa) {
        return (ibc_empresa * (0.125));
    }

    private double getPension(double ibc_empresa) {
        return (ibc_empresa * (0.16));
    }

    private double getIbcRiesgos(double ibc) {
        return ibc;
    }

    private double getRiesgos(double ibcRiesgos, int diasTrabajados) {
        return (((ibcRiesgos * 0.00522) * diasTrabajados) / 30);
    }

    private double getCesantias(double salario, double auxilioTransporte, int diasCotizados) {
        return (((salario + auxilioTransporte) / 360) * diasCotizados);
    }

    private double getIteresesCesantias(double salario, int diasCotizados) {
        return ((salario * (0.12)) / 360) * diasCotizados;
    }

    private double getCCF(double ibc_empresa) {
        return ibc_empresa * 0.04;
    }

    private double getSena(double ibc_empresa) {
        return ibc_empresa * 0.02;
    }

    private double getIcbf(double ibc_empresa) {
        return ibc_empresa * 0.03;
    }

    private double getPrima(double salario, double auxilioTransporte, int diasCotizados) {
        return ((salario + auxilioTransporte) / 360) * diasCotizados;
    }

    private double getVacaciones(double salario, int diasCotizados) {
        return ((salario / 2) / 360) * diasCotizados;
    }
}
