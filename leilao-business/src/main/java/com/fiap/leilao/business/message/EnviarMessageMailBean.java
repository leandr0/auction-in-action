/**
 * 
 */
package com.fiap.leilao.business.message;

import com.fiap.leilao.business.exception.EnviarMensagemBusinessException;
import com.fiap.leilao.domain.Leilao;

/**
 * @author leandro
 *
 */
public interface EnviarMessageMailBean extends ResourceAdapterMessage {

	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static final String JNDI_NAME = "enviarMessageMailBean";
	
	/**
	 * 
	 * @param leilao
	 */
	public abstract void sendMailApproved(Leilao leilao)throws EnviarMensagemBusinessException;
	
	/**
	 * 
	 * @param leilao
	 */
	public abstract void sendMailUnapprived(Leilao leilao)throws EnviarMensagemBusinessException;
}
