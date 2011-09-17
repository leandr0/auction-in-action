/**
 * 
 */
package com.fiap.leilao.domain.exception;

/**
 * @author Leandro
 *
 */
public class LeilaoDomainException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2433528550777225763L;

	/**
	 * 
	 */
	public LeilaoDomainException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public LeilaoDomainException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public LeilaoDomainException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LeilaoDomainException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
