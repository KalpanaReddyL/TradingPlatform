package com.company.project.trading.exceptions;

/**
 * Validation exception for instruction level validations
 * @author kalpana
 *
 */
public class ValidationException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -377201993027300623L;

	public ValidationException(String message){
		super(message);
	}
	
}
