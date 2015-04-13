/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilidades;

/**
 *
 * @author Alejandro
 */
public class AdException extends Exception {
    public AdException(String message)
    {
        super(message);
    }
    public AdException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
