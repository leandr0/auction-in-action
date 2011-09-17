/**
 * 
 */
package com.fiap.leilao.domain.exception;

/**
 * @author Leandro
 *
 */
public class LeilaoDomainArgumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8940352792258365121L;

	/**
	 * 
	 */
	public LeilaoDomainArgumentException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public LeilaoDomainArgumentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public LeilaoDomainArgumentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LeilaoDomainArgumentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
