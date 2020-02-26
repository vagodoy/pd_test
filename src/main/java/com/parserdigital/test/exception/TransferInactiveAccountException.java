package com.parserdigital.test.exception;

/**
 * @author Alex
 *
 */
public class TransferInactiveAccountException extends Exception {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new Resource not found exception.
	 *
	 * @param message the message
	 */
	public TransferInactiveAccountException(String message) {
		super(message);
	}
}
