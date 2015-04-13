/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import Entidades.Liquidacion;
import Entidades.Tributaria;
import Entidades.Valores;

/**
 *
 * @author Alejandro
 */
public class Tablas {

    public static final int VALORES = 1;
    public static final int TRIBUTARIA = 2;
    public static final int LIQUIDACION = 3;

    public Class retornaClaseEntidad(int idEntidad) {
        Class clase = null;
        switch (idEntidad) {
            case VALORES:
                clase = Valores.class;
                break;
            case TRIBUTARIA:
                clase = Tributaria.class;
                break;
            case LIQUIDACION:
                clase = Liquidacion.class;
                break;
        }
        return clase;
    }
}
