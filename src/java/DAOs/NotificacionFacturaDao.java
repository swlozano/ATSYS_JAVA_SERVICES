/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.UsuarioSys;
import Utilidades.Mail;
import VO.FacturaVO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Alejandro
 */
public class NotificacionFacturaDao extends Utilidades.DAO {

    public void consultarFactProxToPagar(Calendar fechaInicio, Calendar fechaFin) {
        List<VO.FacturaVO> facturaVOs = new ArrayList<FacturaVO>();
        try {
            FacturaDao facturaDao = new FacturaDao();
            facturaVOs = facturaDao.listar(fechaInicio, fechaFin, (short) 2, (short) 2, -1);
            sendNotificacion(facturaVOs);
        } catch (Exception e) {
            System.out.println();
        } finally {
            System.out.println();
        }
    }

    private void sendNotificacion(List<VO.FacturaVO> facturaVOs) {
        try {
          String  from = getFrom();
          String to = from;
          String mensaje = crearMnesaje(facturaVOs);
          String[] tos  = {to};
          Mail mail = new Mail();
          mail.sendMail(from, tos,"ATSYS-Facturas próximas a pagar", mensaje);
        } catch (Exception e) {
            System.out.println();
        } finally {
            System.out.println();
        }

    }

    private String crearMnesaje(List<VO.FacturaVO> facturaVOs) {
        String mensaje = "";
        for (FacturaVO facturaVO : facturaVOs) {
            mensaje = mensaje + Utilidades.Mail.IDENTIFICADOR_FACTURA + facturaVO.getId();
            mensaje = mensaje + Utilidades.Mail.NUMERO_FACTURA + facturaVO.getNumeroFactura();
            mensaje = mensaje + Utilidades.Mail.CLIENTE + facturaVO.getPersona().getNombreRs();
            mensaje = mensaje + Utilidades.Mail.FECHA_FACTURACION + facturaVO.getFechaFacturacion();
            mensaje = mensaje + Utilidades.Mail.FECHA_PACTADA + facturaVO.getFechaPactadaPago();
            mensaje = mensaje + Utilidades.Mail.TOTAL_A_PAGAR + facturaVO.getTotalApagar();
            mensaje = mensaje + Utilidades.Mail.SALDO + facturaVO.getSaldo();
            mensaje = mensaje + Utilidades.EtiquetasHtml.BR_C;
        }
        return mensaje;
    }

    private String getFrom() {
        String from = null;
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Entidades.UsuarioSys.class);
            criteria.add(Restrictions.eq("isAdmin", (short) 1));
            Entidades.UsuarioSys usuarioSys = (UsuarioSys) criteria.uniqueResult();
            from = usuarioSys.getCorreoElectronico();
        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
            return from;
        }
    }

    private void getTo() {
        String correoPara;
        try {
            begin();
            Criteria criteria = getSession().createCriteria(UsuarioSys.class);
            criteria.add(Restrictions.eq("isAdmin", (short) 1));
            UsuarioSys usuarioSys = (UsuarioSys) criteria.uniqueResult();
            correoPara = usuarioSys.getCorreoElectronico();

        } catch (Exception e) {
            System.out.println();
        } finally {
            close();
        }
    }
}
