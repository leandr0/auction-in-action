/**
 * 
 */
package com.fiap.leilao.business;

import java.io.Serializable;

import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.type.StatusLeilao;

/**
 * Interface responsável em permitir que um usuário 
 * gerencie os leilões 
 * @author Leandro
 *
 */
public interface AdmLeilaoBean extends Serializable{

	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static String JNDI_NAME = "admLeilaoBean";
	
	/**
	 * Método que executa a atualização da entidade {@link Leilao}
	 * 
	 * @param leilao {@link Leilao}
	 * @return {@link Leilao}
	 * @throws LeilaoBusinessException
	 */
	public Leilao atualizarLeilao(Leilao leilao) throws LeilaoBusinessException;
	
	/**
	 * Método que atualiza o {@link StatusLeilao} para a entidade {@link Leilao}
	 * 
	 * @param leilao {@link Leilao}
	 * @return {@link Leilao}
	 * @throws LeilaoBusinessException
	 */
	public Leilao atualizarStatusLeilao(Leilao leilao) throws LeilaoBusinessException;
	
	/**
	 * Método que verifica se o {@link Leilao} em questão pode ser editado. <p>
	 * O {@link Leilao} somente pode ser editado casa não tenha recebido nenhum {@link Lance}
	 * @param leilao {@link Leilao}
	 * @return boolean {@code true} or {@code false}
	 * @throws LeilaoBusinessException
	 */
	public boolean isPermitidoEditarLeilao(Leilao leilao)throws LeilaoBusinessException;
}
