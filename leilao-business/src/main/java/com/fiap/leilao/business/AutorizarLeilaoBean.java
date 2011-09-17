/**
 * 
 */
package com.fiap.leilao.business;

import java.io.Serializable;
import java.util.List;

import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Produto;

/**
 * Interface responsável por permitir que um usuário com o perfil ADMINISTRADOR<p>
 * tenha as possibilidades de autorizar e rejeitar um leilão
 * @author Leandro
 *
 */
public interface AutorizarLeilaoBean extends Serializable{

	
	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static final String JNDI_NAME = "autorizarLeilaoBean";
	
	/**
	 * Método que autoriza um {@link Leilao}
	 * @param leilao {@link Leilao}
	 * @throws LeilaoBusinessException
	 */
	public void atutorizarLeilao(Leilao leilao) throws LeilaoBusinessException;
	
	
	/**
	 * Método que busca todos os Leilões que foram criados , e ainda o Moderador (usuário com perfil ADMINISTRADOR)<p>
	 * não tenha  autorizado {@link #atutorizarLeilao(Leilao)} e nem cancelado {@link #cancelarLeilao(Leilao)} o {@link Leilao}
	 * @return {@link List}{@code <}{@link Leilao}{@code >}
	 * @throws LeilaoBusinessException
	 */
	public List<Leilao> buscarLeiloesPendentes() throws LeilaoBusinessException;
	
	/**
	 * Busca na base de dados todos os Itens vinculados a um produto , que está vinculado a um {@link Leilao}
	 * @param produto {@link Produto}
	 * @return {@link List}{@code <}{@link Item}{@code >}
	 * @throws LeilaoBusinessException
	 */
	public List<Item> buscarItensLeilao(Produto produto) throws LeilaoBusinessException;
	
	/**
	 * Método que permite ao cancelar um {@link Leilao}
	 * @param leilao {@link Leilao}
	 * @throws LeilaoBusinessException
	 */
	public void cancelarLeilao(Leilao leilao) throws LeilaoBusinessException;
}