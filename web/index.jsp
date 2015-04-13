<%-- 
    Document   : index
    Created on : 5/06/2009, 04:52:33 PM
    Author     : Alejandro
--%>


<%@page contentType="text/html" pageEncoding="UTF-8" import="DAOs.*"  %>
<%@page import="DAOs.*" %>
<%@page import="java.util.*" %>
<%@page import="java.util.*" %>
<%@page import="Utilidades.NotificacionFactura" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>.:ATSYS-SERATIC:.</title>
    </head>
    <body>

        <h1 >ATSYS - AUXILIAR DE TESORERIA</h1>

        <%
           NotificacionFactura notificacionFactura = new NotificacionFactura();
           notificacionFactura.start();

           PersonaDao personaDao = new PersonaDao();
           personaDao.listar();
        %>

    </body>
</html>
















