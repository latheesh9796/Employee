package com.ideas2it.application.exception;

/**
 * <p>
 * ApplicationException class extends the exception class which is 
 * used to define user defined exceptions for better user understanding
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class ApplicationException extends Exception {
    String exceptionMessage;

    public ApplicationException(String exceptionMessage) {
	    this.exceptionMessage=exceptionMessage;
    }

    public String toString(){ 
	    return (exceptionMessage) ;
    }
}
