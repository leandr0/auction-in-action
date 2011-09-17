/**
 * 
 */
package com.fiap.leilao.business.message;

import java.io.Serializable;

import com.fiap.leilao.business.exception.EnviarMensagemBusinessException;

/**
 * Interface que permite o envio de mensagens JMS para a fila de lances
 * 
 * @author Leandro
 *
 */
public interface EnviarMessageLanceBean extends ResourceAdapterMessage {
	
	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static final String JNDI_NAME = "enviarMessageLanceBean";
	
	/**
	 * Envia mensagem JMS 
	 * @param message {@link String}
	 * @throws EnviarMensagemBusinessException
	 */
	public void enviarTextMessage(String message)throws EnviarMensagemBusinessException;
	
	
	/**
	 * Envia um objeto via JMS
	 * @param <M> {@code extends }{@link Serializable}
	 * @param message {@code extends }{@link Serializable}
	 * @throws EnviarMensagemBusinessException
	 */
	public < M extends Serializable> void enviarObjectMessage( M message)throws EnviarMensagemBusinessException;
	
	/**
	 * Envia mensagem JMS com Seletor de mensagem
	 * @param message {@link String}
	 * @param messageSelect {@link MessageSelect}
	 * @throws EnviarMensagemBusinessException
	 */
	public void enviarTextMessageQuerySelector(String message, MessageSelect messageSelect)throws EnviarMensagemBusinessException;
	
	/**
	 * Envia um objeto via JMS com Seletor de mensagem
	 * @param <M> {@code extends }{@link Serializable}
	 * @param message {@code extends }{@link Serializable}
	 * @param messageSelect {@link MessageSelect}
	 * @throws EnviarMensagemBusinessException
	 */
	public < M extends Serializable>  void enviarObjectMessageQuerySeletor(M message, MessageSelect messageSelect)throws EnviarMensagemBusinessException;
	
	/**
	 * Inner Class para criar Seletores de mensagens JMS
	 * @author Leandro
	 *
	 */
	public class MessageSelect implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -3157294419634368142L;

		public final String QUERYSELECTOR;
		
		public final String VALUESELECTOR;
		
		public MessageSelect(String querySelector, String valueSelector) {
			this.QUERYSELECTOR = querySelector;
			this.VALUESELECTOR = valueSelector;
		}
	}
}