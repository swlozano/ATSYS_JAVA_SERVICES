/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import DAOs.NotificacionFacturaDao;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro
 */
public class NotificacionFactura extends Thread {

    Calendar fechaActual;
    boolean bandera;

    @Override
    public void run() {
        bandera = true;
        while (true) {
            try {
                fechaActual = Calendar.getInstance();
                if (fechaActual.get(Calendar.DAY_OF_MONTH) == 0 || fechaActual.get(Calendar.DAY_OF_MONTH) == 15 || bandera) {
                    bandera = false;
                    Calendar calendarFechaFin = sumarFechas(fechaActual);
                    NotificacionFacturaDao notificacionFacturaDao = new NotificacionFacturaDao();
                    notificacionFacturaDao.consultarFactProxToPagar(fechaActual, calendarFechaFin);
                    System.out.println("BUSCAR LAS FACTURAS QUE SE ENCUENTREN EN EL RANGO");
                }
                NotificacionFactura.sleep(1000);
                //NotificacionFactura.sleep(86400000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NotificacionFactura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Calendar sumarFechas(Calendar calendar) {

        int year1 = calendar.get(Calendar.YEAR);
        int mes1 = calendar.get(Calendar.MONTH);
        int dia1 = calendar.get(Calendar.DAY_OF_MONTH);

        Fechas fechas = new Fechas();
        int numeroMaxDiasMes = fechas.getNumeroDiasMes(mes1, year1);
        
        if(dia1==0)
        {
            calendar.set(Calendar.DAY_OF_MONTH, 14);
        }
        else
        {
            calendar.set(Calendar.DAY_OF_MONTH, numeroMaxDiasMes);
        }

        return  calendar;
    }
}
