package com.kerr.interpreter.words;

/**
 * An exception that is thrown when a leave statement is encountered. This exception is always
 * caught by the loop-word that is executing the body.
 * 
 * @author allankerr
 *
 */
@SuppressWarnings("serial")
class LeaveLoopException extends RuntimeException {

}
