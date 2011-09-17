/**
 * 
 */
package com.fiap.leilao.business;

import java.io.Serializable;

import com.fiap.leilao.business.exception.LeilaoBusinessArgumentException;
import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;

/**
 * Interface que permite criar Leilões
 * 
 * @author Leandro
 * 
 */
public interface CriarLeilaoBean extends Serializable {

	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static final String JNDI_NAME = "criarLeilaoBean";

	/**
	 * Métod responsàvel por criar um novo {@link Leilao}
	 * 
	 * @param leilao
	 *            {@link Leilao}
	 * @param usuario
	 *            {@link Usuario}
	 * @return {@link Long} ID do {@link Leilao} criado
	 * @throws LeilaoBusinessException
	 */
	public Long criarLeilao(Leilao leilao, Usuario usuario)
			throws LeilaoBusinessException, LeilaoBusinessArgumentException;
}