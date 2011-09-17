/**
 * 
 */
package com.fiap.leilao.business.message;

import com.fiap.leilao.domain.Leilao;

/**
 * Interface que permite Enviar um relatorio por e-mail
 * @author Leandro
 *
 */
public interface EnviarSolicitacaoRelatorioBean extends ResourceAdapterMessage {

	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static final String JNDI_NAME = "enviarSolicitacaoRelatorioBean";
	
	/**
	 * Envia relatorio gerado a partir do ID do {@link Leilao} informado
	 * @param idLeilao {@link Long}
	 */
	public void enviarSolicitacaoRelatorioEmail(Long idLeilao);
}
