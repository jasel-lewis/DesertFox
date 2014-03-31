/**
 * 
 */
package com.jasel.classes.msim795.exception;

/**
 * @author Jasel
 *
 */
public class MissingAuthParameterException extends Exception {
	private static final long serialVersionUID = -3201646919556825551L;

	public MissingAuthParameterException(String message) {
		super(message);
	}
}