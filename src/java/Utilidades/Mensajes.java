/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

/**
 *
 * @author Alejandro
 */
public class Mensajes {

    /*********************************************/
    //Generales
    public static final short NO_INGRESO_DATOS = 1;
    public static final String NO_INGRESO_DATOS_MSG = "No se ingresaron los datos.";
    public static final short LOGIN_ESTA_ASIGNADO = 2;
    public static final String LOGIN_ESTA_ASIGNADO_MSG = "El Login ingresado ya existe.";
    public static final short CEDULA_ESTA_ASIGNADO = 3;
    public static final String CEDULA_ESTA_ASIGNADO_MSG = "La cedula ingresada ya existe.";
    public static final short CORREO_ESTA_ASIGNADO = 4;
    public static final String CORREO_ESTA_ASIGNADO_MSG = "El correo electronico ingresado ya existe.";
    public static final short INGRESO_DATOS = 5;
    public static final String INGRESO_DATOS_MSG = "Se ingresaron los datos correctamente.";
    public static final short NO_ACTUALIZO_DATOS = 6;
    public static final String NO_ACTUALIZO_DATOS_MSG = "No se actualizaron los datos.";
    public static final short ACTUALIZO_DATOS = 7;
    public static final String ACTUALIZO_DATOS_MSG = "Se actualizaron los datos correctamente.";
    public static final short NO_ELIMINO_DATOS = 8;
    public static final String NO_ELIMINO_DATOS_MSG = "No se eliminaron los datos.";
    public static final short ELIMINO_DATOS = 9;
    public static final String ELIMINO_DATOS_MSG = "Se eliminaron los datos correctamente.";
    public static final short NO_OBTUVO_DATOS = 10;
    public static final String NO_OBTUVO_DATOS_MSG = "No es posible obtener  los datos  solicitados.";
    public static final short NO_ASIGNO_ROL_TO_US = 11;
    public static final String NO_ASIGNO_ROL_TO_US_MSG = "No se asignaron los roles al usuario.";
    public static final short ASIGNO_ROL_TO_US = 12;
    public static final String ASIGNO_ROL_TO_US_MSG = "Se asignaron los roles al usuario correctamente.";
    public static final short NOMBRE_ROL_ESTA_ASIGNADO = 13;
    public static final String NOMBRE_ROL_ESTA_ASIGNADO_MSG = "El nombre de rol ingresado ya existe.";
    public static final short NOMBRE_CAJA_ESTA_ASIGNADO = 14;
    public static final String NOMBRE_CAJA_ESTA_ASIGNADO_MSG = "El nombre de la caja menor ya existe.";
    public static final short NOMBRE_CENTRO_ESTA_ASIGNADO = 15;
    public static final String NOMBRE_CENTRO_ESTA_ASIGNADO_MSG = "El nombre del centro de costo ya existe.";
    public static final short NO_ASIGNO_PERMISOS_TO_ROL = 16;
    public static final String NO_ASIGNO_PERMISOS_TO_ROL_MSG = "No se asignaron los permisos al rol.";
    public static final short ASIGNO_PERMISOS_TO_ROL = 17;
    public static final String ASIGNO_PERMISOS_TO_ROL_MSG = "Se asignaron los permisos al rol correctamente.";
    public static final short NIT_PERSONA_ESTA_ASIGNADO = 18;
    public static final String NIT_PERSONA_ESTA_ASIGNADO_MSG = "El nit de la persona ingresado ya existe.";
    public static final short CEDULA_PERSONA_ESTA_ASIGNADO = 19;
    public static final String CEDULA_PERSONA_ESTA_ASIGNADO_MSG = "La cedula de la persona ya existe.";
    public static final short RAZON_SOCIAL_PERSONA_ESTA_ASIGNADO = 20;
    public static final String RAZON_SOCIAL_PERSONA_ESTA_ASIGNADO_MSG = "La razón social  de la persona ya existe.";
    public static final short NO_REALIZO_MOVIMIENTO = 21;
    public static final String NO_REALIZO_MOVIMIENTO_MSG = "No se realizo el movimiento, el egreso no debe ser superior al saldo de la caja menor.";
    public static final short DATO_NO_EXISTE = 22;
    public static final short DATO_EXISTE = 23;
    public static final short RH_NO_TIENE_CAUSAL = 24;
    public static final short RH_TIENE_CAUSAL = 25;
    public static final short HA_OCURRIDO_EXEP = 26;
    public static final short DATOS_YA_EXISTEN = 27;
    public static final short DATOS_NO_EXISTEN = 28;
    public static final short NO_EXISTE_PERSONA = 29;
    public static final short YA_EXISTE_PERSONA = 30;
    //USUARIOS
    public static final String USUARIO_TIENE_ASIGNADOS_ROLES_MSG = "El usuario tiene asignado roles.";
    public static final String ROL_TIENE_ASIGNADOS_USUARIOS_MSG = "El rol tiene asignado usuarios.";
    public static final String CAJA_TIENE_ASIGNADOS_MOVIMIENTOS_MSG = "La caja menor tiene asignado movimientos.";
    public static final String CENTRO_TIENE_ASIGNADOS_USUARIOS_MSG = "El centro de costo tiene asignado movimientos.";
    public static final String PERSONA_TIENE_ASIGNADOS_MOVIMIENTOS_MSG = "La persona tiene asignado movimientos.";
    public static final String PERSONA_NO_EXISTE_MSG = "La persona no existe.";
    public static final String RESPONSABLE_NO_EXISTE_MSG = "El responsable no existe.";
    public static final String CENTRO_COSTO_NO_EXISTE_MSG = "El centro de costo no existe.";
    public static final String CAJA_MENOR_NO_EXISTE_MSG = "La caja menor no existe.";
    public static final String ACCION_PARA_ADMIN_SISTEMA = "Esta acción es para el administrador del sistema.";
    public static final String PERSONA_NO_EXISTE = "La persona ingresada y/o seleccionada no existe.";
    public static final String TIPO_MOVIMIENTO_NO_EXISTE = "El tipo de movimiento ingresado no existe.";
    public static final String CAJA_MENOR_NO_EXISTE = "La caja menor ingresada no existe.";
    public static final String CENTRO_COSTO_NO_EXISTE = "El centro de costo ingresado no existe.";
    public static final String MOVIMIENTO_NO_EXISTE = "El movimiento no existe.";
    public static final String CAJA_SUPERADO_MONTO_NOTIFICACION = "La caja menor ha superado el valor mínimo para notificación.";
    public static final String USUARIO_TIENE_ASIGNADOS_CAJA_MENOR_MSG = "El usuario es responsable de cajas menores o cuentas de banco.";
    public static final String CENTRO_PERTECE_A_LA_EMPRESA_MSG = "El centro de costo es un dato exclusivo de la empresa.";
    public static final String PERSONA_PERTECE_A_EMPRESA_MSG = "La persona es un dato exclusivo de la empresa.";
    public static final String USUARIO_LOGEADO_NO_EXISTE_MSG = "\n El usuario logeado ha sido borrado.";
    public static final String NO_PUEDE_ELIMINAR_MOVIMIENTO_APERTURA_MSG = "\n El movimiento es un movimiento de apertura.";
    public static final String NO_PUEDE_ELIMINAR_CM_TIENE_VARIOS_MOVI = "Solo puede eliminar cajas menores que tengan un unico movimiento";
    public static final String MOVIMIENTO_DE_APERTURA = "Movimiento de apertura";
    public static final String IVA_NO_EXISTE_PART1 = "El IVA con identificador ";
    public static final String IVA_NO_EXISTE_PART2 = " no existe. Consulte al administrador del sistema.";
    public static final String RETENCION_NO_EXISTE_PART1 = "La retención con identificador ";
    public static final String RETENCION_NO_EXISTE_PART2 = " no existe. Consulte al administrador del sistema.";
    public static final String NO_GUARDO_ITEM = "No se guardo el ítem de la factura: ";
    public static final String NO_GUARDO_FACTURA_RETENCION = "No se guardaron las retenciones seleccionadas en uno de los ítems";
    public static final String EXCEPCION = " Ha ocurrido una excepción: ";
    public static final String SALTO_LINEA = " \n ";
    public static final String NO_GUARDO_IVA = "No se guardo el IVA de la factura ";
    public static final String IDENTIFICADOR = " Identificador: ";
    public static final String NO_DESASIGNO_RETENCIONES = " No se pudo des-asignar las retenciones correspondientes al detalle factura con identificador :";
    public static final String NO_DESASIGNO_DETALLE_FACTURA = " No se pudo des-asignar el detalle factura ";
    public static final String NO_DESASIGNO_IVA = " No se pudo des-asignar el IVA ";
    public static final String ABONOS_ES_MAYOR_TOTAL_FACTURA = " El valor de los abonos es mayor que el total de la factura ";
    public static final String NO_INGRESO_ABONO_MSG = " No se ingreso el abono. ";
    public static final String ABONO_MAYOR_SALDO_MSG = " El valor del abono es mayor que el saldo actual. ";
    public static final String FECHA_ABONO_MAYOR_FECHA_FACTURACION = " La fecha del abono no debe ser menor que la fecha de facturación ";
    public static final String NOMBRE_OFICIO_YA_EXISTE = " El nombre del oficio ya existe ";
    public static final String RUT_ESTA_ASIGNADO_MSG = "El RUT ingresado ya existe.";
    public static final String OFICIO_NO_EXISTE_MSG = " El oficio a desempeñar no existe. ";
    public static final String TIPO_CONTRATO_NO_EXISTE_MSG = " El tipo de contrato no existe. ";
    public static final String PAIS_LABORES_NO_EXISTE_MSG = " El país seleccionado como nación de labores no existe ";
    public static final String PAIS_CONTRATO_NO_EXISTE_MSG = " El país seleccionado como nación de contrato no existe ";
    public static final String RH_NO_EXISTE_MSG = " El recurso humano no existe ";
    public static final String NO_INGRESO_VAL_FORMA_PAGO_MSG = " No ingreso el valor/forma-pago ";
    public static final String NO_INGRESO_FECHAS_PAGO_MSG = " No ingreso las fechas de pago ";
    public static final String TIPO_PAGO_NO_EXISTE_MSG = " El tipo de pago no existe ";
    public static final String RETENCION_PAGO_NO_EXISTE_MSG = " Una de las retenciones seleccionadas no existe ";
    public static final String NO_INGRESO_ADENDO_MSG = " No se ingreso el adendo ";
    public static final String INGRESO_ADENDO_MSG = " Se ingreso el adendo correctamente ";
    public static final String FECHA_FIN_ADENDO_DEBE_SER_MAYOR_MSG = " La fecha fin del adendo debe ser mayor a la fecha de terminación del contrato ";
    public static final String SELECCIONE_IBC = "Debe seleccionar un valor/forma pago como IBC";
    public static final String SELECCIONE_SOLO_UN_IBC = "Seleccione solo un valor/forma pago como IBC";
    public static final String CONTRATO_TERMINADO = " Contrato terminado ";
    public static final String NO_ACEPTO_TERMINAR_CONTRATO = " No se acepto la terminación del contrato ";
    public static final String NO_PUEDE_TERMINAR_CONTRATO = " No se puede terminar un contrato que ya ha sido finalizado ";
    public static final String FICHA_TIENE_ESTADO_PENDIENTE = " La ficha tiene un estado pendiente de pago o devolución ";
    public static final String CONTRATO_YA_FUE_LIQUIDADO = "El contrato ya fue liquidado";
    public static final String RH_NO_TIENE_CAUSAL_MSG = " El recurso humano no tiene ningún memorando por mal comportamiento ";
    public static final String DESEA_CONTINUAR_MSG = " ¿Esta seguro que desea continuar? ";
    public static final String DEBE_PRIMERO_ELIMINAR_CONTRATO = " Para poder liquidar el contrato primero debe terminarlo ";
    public static final String ENVIO_PETICION_DE_CC = " Se envió la petición de cuenta de cobro con éxito ";
    public static final String NO_ENVIO_PETICION_DE_CC = " No envió la petición de cuenta de cobro ";
    public static final String INTENTELO_DE_NUEVO = " Inténtelo de nuevo ";
    public static final String NUMERO_FACTURA_YA_INGRESADO = " El número de la factura ya fue ingresado ";
    public static final String CUENTA_BANCO_YA_EXISTE = " Ya ha sido creada una cuenta bancaria con los mismos datos ";
    public static final String NO_CREO_ADENDO = " No se creo el adendo ";
    public static final String SE_CREO_ADENDO = " El adendo se creado correctamente ";
    public static final String CUENTA_BANCO_ESTA_ASOCIADA_PROCESOS = " La cuenta bancaria está asociada a otros procesos ";
    public static final String FECHA_A_CAUSAR_YA_CAUSADA = " La fecha seleccionada ya fue causada. ";
    public static final String ACTUALIZO_ESTADO_CUENTA_PAGAR_CORECTAMENTE = " Se actualizo el estado de la cuenta por pagar correctamente. ";
    public static final String PAGO_LIQUIDACION = " Pago de liquidación ";
    public static final String LIQUIDACION_ENVIADA_CUENTAS_POR_PAGAR = " La liquidación ha sido enviada al modulo de cuentas por pagar. ";
    public static final String NO_ENVIO_LIQUIDACION_CUENTAS_POR_PAGAR = "No  ha sido enviada la liquidación al modulo de cuentas por pagar";
    public static final String OFICIO_DESEMPENAR_ES_UTILIZADO = " El oficio a desempeñar está asignado a un contrato ";
}
