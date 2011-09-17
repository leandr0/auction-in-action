/**
 * 
 */
package com.fiap.leilao.business;

import java.io.Serializable;

import com.fiap.leilao.business.exception.FinalizarLeilaoBusinessException;

/**
 * Interface que possibilita executar a rotina noturna de fechamento de Leil�o
 * 
 * @author Leandro
 *
 */
public interface FechamentoLeilaoBean extends Serializable{

	/**
	 * Mapeamento JNDI para o EJB Bean que implementar� esta interface
	 */
	public static final String JNDI_NAME = "fechamentoLeilaoBean";
	
	/**
	 * M�todo respons�vel por pesquisar todos os Leil�es que sua data de vig�ncia<p>
	 * expirou e finaliza-los
	 * @throws FinalizarLeilaoBusinessException
	 */
	public void finalizarLeilao() throws FinalizarLeilaoBusinessException;
}