/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mobiles;

import DAOs.FacturaDao;
import DAOs.PersonaDao;
import Utilidades.AdException;
import VO.FacturaVO;
import VO.PersonaVO;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alejandro
 */
public class NewServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final short LISTAR_PERSONA = 1;
    private static final short LISTAR_FACTURAS = 2;
    short opcion;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        try {
            String strFuncion = request.getHeader("FX");
            if (strFuncion.equals("listarPersonas")) {
                opcion = NewServlet.LISTAR_PERSONA;
            } else {
                if (strFuncion.equals("listarFacturas")) {
                    opcion = NewServlet.LISTAR_FACTURAS;
                }
            }

            switch (opcion) {
                case NewServlet.LISTAR_PERSONA:
                    List<PersonaVO> personaVOs = new ArrayList<PersonaVO>();
                    PersonaDao personaDao = new PersonaDao();
                    try {
                        personaVOs = personaDao.listar();
                        DataOutputStream dos = new DataOutputStream(response.getOutputStream());
                        dos.writeInt(personaVOs.size());

                        for (PersonaVO personaVO : personaVOs) {
                            dos.writeInt(personaVO.getId());
                            dos.writeUTF(personaVO.getNombreRs());
                            dos.writeUTF(personaVO.getCedula() + "");
                            dos.writeUTF(personaVO.getNit() + "");
                        }

                        dos.flush();
                        dos.close();

                    } catch (AdException ex) {
                        Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case NewServlet.LISTAR_FACTURAS:

                    String dia = request.getHeader("dia");
                    String mes = request.getHeader("mes");
                    String year = request.getHeader("year");

                    Calendar calendarInicio = crearCalendar(dia, mes, year);

                    String dia2 = request.getHeader("dia2");
                    String mes2 = request.getHeader("mes2");
                    String year2 = request.getHeader("year2");

                    Calendar calendarFin = crearCalendar(dia2, mes2, year2);

                    short tipoFecha = Short.parseShort(request.getHeader("tipoFecha"));
                    short tipoEstado = Short.parseShort(request.getHeader("tipoEstado"));
                    int idPersona = Integer.parseInt(request.getHeader("idPersona"));

                    List<FacturaVO> facturaVOs = new ArrayList<FacturaVO>();

                    FacturaDao facturaDao = new FacturaDao();
                    facturaVOs = facturaDao.listar(calendarInicio, calendarFin, tipoFecha, tipoEstado, idPersona);

                    DataOutputStream dos = new DataOutputStream(response.getOutputStream());

                    dos.writeInt(facturaVOs.size());

                    for (FacturaVO facturaVO : facturaVOs) {
                        dos.writeInt(facturaVO.getId());
                        dos.writeUTF(facturaVO.getPersona().getNombreRs());
                        dos.writeLong(facturaVO.getFechaFacturacion().getTime());
                        dos.writeLong(facturaVO.getFechaPactadaPago().getTime());
                        dos.writeDouble(facturaVO.getSubtutal());
                        dos.writeDouble(facturaVO.getTotal());
                        dos.writeDouble(facturaVO.getSaldo());
                        dos.writeDouble(facturaVO.getValorIva());
                        dos.writeDouble(facturaVO.getPorcentajeIva());
                        dos.writeDouble(facturaVO.getValorRetenciones());
                        dos.writeDouble(facturaVO.getTotalApagar());
                        dos.writeUTF(facturaVO.getNumeroFactura());
                    }
                    dos.flush();
                    dos.close();

                    break;
            }


        } finally {
//            out.close();
        }
    }

    private Calendar crearCalendar(String dia, String mes, String year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(mes));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
