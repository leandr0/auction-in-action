/**
 * 
 */
package com.fiap.leilao.business;

import java.io.Serializable;
import java.util.List;

import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Produto;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;

/**
 * Interface que permite enviar lances aos leilões
 * @author Leandro
 *
 */
public interface EnviarLanceBean extends Serializable{

	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static String JNDI_NAME = "enviarLanceBean";
	
	/**
	 * Método que permite enviar um {@link Lance} a um {@link Leilao}
	 * @param lance {@link Lance}
	 * @param usuario {@link Usuario}
	 * @throws LeilaoBusinessException
	 * @throws LeilaoDomainArgumentException
	 */
	public void enviarLance(Lance lance , Usuario usuario) throws LeilaoBusinessException , LeilaoDomainArgumentException;

	/**
	 * Método retorna todos os leilões ativos que não pertencem ao {@link Usuario} informado como <p>
	 * parâmetro , ou seja , retorna os Leiões que o Usuário pode enviar lances
	 * @param usuario {@link Usuario}
	 * @return {@link List}{@code <}{@link Leilao}{@code>}
	 * @throws LeilaoBusinessException
	 * @throws LeilaoDomainArgumentException
	 */
	public List<Leilao> buscarLeiloesAtivos(Usuario usuario) throws LeilaoBusinessException , LeilaoDomainArgumentException;
	

	/**
	 * Busca na base de dados todos os Itens vinculados a um produto , que está vinculado a um {@link Leilao}
	 * @param produto {@link Produto}
	 * @return {@link List}{@code <}{@link Item}{@code >}
	 * @throws LeilaoBusinessException
	 */
	public List<Item> buscarItensProduto(Produto produto) throws LeilaoBusinessException , LeilaoDomainArgumentException;
}
