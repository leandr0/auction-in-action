/**
 * 
 */
package com.fiap.leilao.business;

import java.io.Serializable;

import com.fiap.leilao.business.exception.FinalizarLeilaoBusinessException;

/**
 * Interface que possibilita executar a rotina noturna de fechamento de Leilão
 * 
 * @author Leandro
 *
 */
public interface FechamentoLeilaoBean extends Serializable{

	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static final String JNDI_NAME = "fechamentoLeilaoBean";
	
	/**
	 * Método responsável por pesquisar todos os Leilões que sua data de vigência<p>
	 * expirou e finaliza-los
	 * @throws FinalizarLeilaoBusinessException
	 */
	public void finalizarLeilao() throws FinalizarLeilaoBusinessException;
}