/**
 * 
 */
package com.fiap.leilao.domain.bean;

import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.security.Seguranca;

/**
 * @author Leandro
 *
 */
public interface SegurancaBean extends AbstractDomain<Seguranca> {

	public static final String JNDI_NAME = "managerSegurancaBean";

	/**
	 * 
	 * @param login
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public boolean disponibilidadeLogin(String login) throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param chaveServico
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public boolean validaChaveServico(String chaveServico)throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param login
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public Usuario pesquisaUsuarioLogin(String login) throws LeilaoDomainArgumentException , LeilaoDomainException;
}
