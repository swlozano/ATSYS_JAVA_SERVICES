/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 *
 * @author Alejandro
 */
public class Mail {

    private String to;
    private String from;
    private String message;
    private String subject;
    private String smtpServ;
    public static final String ASUNTO_MOVIMIENTO = "..:Movimiento en ATSYS..:";
    public static final String ASUNTO_MODIFICACION_MOVIMIENTO = "..:Modificación de movimiento en ATSYS..:";
    public static final String ID_MOVIMIENTO = EtiquetasHtml.B + "Identificador del movimiento: " + EtiquetasHtml.B_C;
    public static final String TIPO_MOVIMIENTO = EtiquetasHtml.B + "Tipo de movimiento: " + EtiquetasHtml.B_C;
    public static final String CONCEPTO_MOVIMIENTO = EtiquetasHtml.B + "Concepto de movimiento: " + EtiquetasHtml.B_C;
    public static final String VALOR_MOVIMIENTO = EtiquetasHtml.B + "Valor de movimiento: " + EtiquetasHtml.B_C;
    public static final String CAJA_MENOR = EtiquetasHtml.B + "Caja Menor: " + EtiquetasHtml.B_C;
    /***************************************************************************************************************/
    public static final String ASUNTO_CUENTA_DE_COBRO = "Nueva cuenta de cobro en Atsys";
    public static final String ID_CC = EtiquetasHtml.B + "Identificador de la cuenta de cobro: " + EtiquetasHtml.B_C;
    public static final String ID_CONTRATO = EtiquetasHtml.B + "Identificador del contrato: " + EtiquetasHtml.B_C;
    public static final String RECURSO_HUMANO = EtiquetasHtml.B + "Recurso Humano: " + EtiquetasHtml.B_C;
    public static final String NUMERO_CUENTA_BANK = EtiquetasHtml.B + "Número de cuenta bancaria: " + EtiquetasHtml.B_C;
    public static final String FECHA_DE_SOLICITUD = EtiquetasHtml.B + "Fecha de solicitud: " + EtiquetasHtml.B_C;
    public static final String CONCEPTO = EtiquetasHtml.B + "Concepto: " + EtiquetasHtml.B_C;
    public static final String OBSERVACION = EtiquetasHtml.B + "Observación: " + EtiquetasHtml.B_C;
    public static final String VALOR = EtiquetasHtml.B + "Valor: " + EtiquetasHtml.B_C;

    public static final String IDENTIFICADOR_FACTURA = EtiquetasHtml.B + "Identificador: " + EtiquetasHtml.B_C;
    public static final String NUMERO_FACTURA = EtiquetasHtml.B + "Número de factura: " + EtiquetasHtml.B_C;
    public static final String CLIENTE = EtiquetasHtml.B + "Cliente: " + EtiquetasHtml.B_C;
    public static final String FECHA_FACTURACION = EtiquetasHtml.B + "Fecha de facturación: " + EtiquetasHtml.B_C;
    public static final String FECHA_PACTADA = EtiquetasHtml.B + "Fecha pactada para el pago: " + EtiquetasHtml.B_C;
    public static final String TOTAL_A_PAGAR = EtiquetasHtml.B + "Total a pagar: " + EtiquetasHtml.B_C;
    public static final String SALDO = EtiquetasHtml.B + "Saldo: " + EtiquetasHtml.B_C;
    
    public String sendMail(String from, String[] to, String subject, String message) throws MessagingException {
        try {
            Properties props = System.getProperties();
            // -- Attaching to default Session, or we could start a new one --
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");

            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);

            // -- Create a new message --
            MimeMessage msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(from));

            InternetAddress internetAddress[] = new InternetAddress[to.length];

            for (int i = 0; i < internetAddress.length; i++) {
                InternetAddress[] ia = InternetAddress.parse(to[i], false);
                internetAddress[i] = ia[0];

            }

            //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para, false));
            msg.setRecipients(Message.RecipientType.TO, internetAddress);
            msg.setSubject(subject);

            //msg.setText(message);
            msg.setText(message, "ISO-8859-1", "html");
            // -- Set some other header information --
            msg.setHeader("MyMail", "Mr. XYZ");
            msg.setSentDate(new Date());
            // -- Send the message --
            Transport.send(msg);
            System.out.println("Message sent to" + to + " OK.");
            return " - Se envió el correo  de notificación ";
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception " + ex);
            return " - No se envió el correo  de notificación ";
        }
    }

// Also include an inner class that is used for authentication purposes
    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "atsys2009@gmail.com";           // specify your email id here (sender's email id)
            String password = "atsis2009";                                      // specify your password here
            return new PasswordAuthentication(username, password);
        }
    }
}
