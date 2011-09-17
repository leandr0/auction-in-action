/**
 * 
 */
package com.fiap.leilao.domain.bean;

import java.util.List;

import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;

/**
 * @author Leandro
 *
 */
public interface LanceBean extends AbstractDomain<Lance> {

	public static final String JNDI_NAME = "managerLanceBean";
	
	/**
	 * 
	 * @param idLeilao
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public Long pesquisarMaiorLanceLeilao(Long idLeilao) throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param leilao
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public List<Lance> pesquisarLancesLeilao(Leilao leilao) throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public List<Lance> pesquisarLancesUsuario(Usuario usuario) throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param leilao
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public boolean contemLanceLeilao(Leilao leilao) throws LeilaoDomainArgumentException , LeilaoDomainException;
}
