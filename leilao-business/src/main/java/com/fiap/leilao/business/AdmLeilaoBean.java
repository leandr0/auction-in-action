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
 * Interface respons�vel em permitir que um usu�rio 
 * gerencie os leil�es 
 * @author Leandro
 *
 */
public interface AdmLeilaoBean extends Serializable{

	/**
	 * Mapeamento JNDI para o EJB Bean que implementar� esta interface
	 */
	public static String JNDI_NAME = "admLeilaoBean";
	
	/**
	 * M�todo que executa a atualiza��o da entidade {@link Leilao}
	 * 
	 * @param leilao {@link Leilao}
	 * @return {@link Leilao}
	 * @throws LeilaoBusinessException
	 */
	public Leilao atualizarLeilao(Leilao leilao) throws LeilaoBusinessException;
	
	/**
	 * M�todo que atualiza o {@link StatusLeilao} para a entidade {@link Leilao}
	 * 
	 * @param leilao {@link Leilao}
	 * @return {@link Leilao}
	 * @throws LeilaoBusinessException
	 */
	public Leilao atualizarStatusLeilao(Leilao leilao) throws LeilaoBusinessException;
	
	/**
	 * M�todo que verifica se o {@link Leilao} em quest�o pode ser editado. <p>
	 * O {@link Leilao} somente pode ser editado casa n�o tenha recebido nenhum {@link Lance}
	 * @param leilao {@link Leilao}
	 * @return boolean {@code true} or {@code false}
	 * @throws LeilaoBusinessException
	 */
	public boolean isPermitidoEditarLeilao(Leilao leilao)throws LeilaoBusinessException;
}
