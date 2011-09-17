/**
 * 
 */
package com.fiap.leilao.business;

import java.io.Serializable;

import com.fiap.leilao.domain.Leilao;

/**
 * Interface que possibilita o envio de relatórios por e-mail
 * 
 * @author Leandro
 *
 */
public interface EnviarRelatorioBean extends Serializable{
	
	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static final String JNDI_NAME = "enviarRelatorioBean";
	
	/**
	 * Método responsável por requisitar a construção de um relatório e envia-lo por e-mail
	 * @param idLeilao {@link Long} do {@link Leilao} 
	 */
	public void enviarRelatorio(Long idLeilao);
}
