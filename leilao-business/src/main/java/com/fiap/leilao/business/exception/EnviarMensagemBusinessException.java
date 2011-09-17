/**
 * 
 */
package com.fiap.leilao.business.exception;

/**
 * @author Leandro
 *
 */
public class EnviarMensagemBusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2444432499644548271L;

	/**
	 * 
	 */
	public EnviarMensagemBusinessException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public EnviarMensagemBusinessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public EnviarMensagemBusinessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EnviarMensagemBusinessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
