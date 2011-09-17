/**
 * 
 */
package com.fiap.leilao.domain.bean;

import java.util.List;

import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.type.StatusLeilao;

/**
 * Interface que permite iterar com a camada de domínio <p>
 * para a entidade {@link Leilao}
 * @author Leandro
 *
 */
public interface LeilaoBean extends AbstractDomain<Leilao> {
	
	/**
	 * Mapeamento JNDI para o EJB Bean que implementará esta interface
	 */
	public static final String JNDI_NAME = "leilaoBean";
	
	/**
	 * Pesquisa leilões com o status informado
	 * @param statusLeilao {@link StatusLeilao}
	 * @return {@link List}{@code <}{@link Leilao}{@code>}
	 * @throws IllegalArgumentException
	 * @throws LeilaoDomainException
	 */
	public List<Leilao> pesquisaLeilaoStatus(StatusLeilao statusLeilao)throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * Busca leiloes que o usuário pode enviar lance
	 * @param usuario {@link Usuario}
	 * @return  {@link List}{@code <}{@link Leilao}{@code>}
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public List<Leilao> pesquisaLeilaoEnviarLance(Usuario usuario)throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * Pesquisa leilões a serem finalizados
	 * @return {@link List}{@code <}{@link Long}{@code >}
	 * @throws LeilaoDomainException
	 */
	public List<Long> pesquisaFinalizarLeilao() throws  LeilaoDomainException;
	
	/**
	 * Atualiza lista de leilões para status de finalizado
	 * @param leiloesFinalizados
	 * @throws LeilaoDomainException
	 */
	public void updateLeiloesFinalizados(List<Long> leiloesFinalizados)throws  LeilaoDomainException;
	
	/**
	 * Pesquisa leiões que o usuário informado pode enviar lances
	 * @param ususUsuario
	 * @return  {@link List}{@code <}{@link Leilao}{@code>}
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public List<Leilao> pesquisaLeilaoStatusUsuario(Usuario usuario)throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * Pesquisa leilão por id
	 * @param idLeilao
	 * @return {@link Leilao}
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public Leilao pesquisarLeilaoGanhador(Long idLeilao)throws LeilaoDomainArgumentException , LeilaoDomainException;
}
