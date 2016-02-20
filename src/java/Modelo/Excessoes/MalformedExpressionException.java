/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.Excessoes;

/** Exception thrown when an invalid arithmetic expression is encountered
 * by the program. The message field contains information about the
 * particular problem that was encountered.
 *
 * @see #getMessage()
 */

public class MalformedExpressionException extends Exception {

    /** Constructs a MalformedExpressionException with no message.
     */
    public MalformedExpressionException() {
        super();
    }



    /** Constructs a MalformedExpressionException with the detail message.
     */
    public MalformedExpressionException( String message ) {
        super( message );
    }
}
