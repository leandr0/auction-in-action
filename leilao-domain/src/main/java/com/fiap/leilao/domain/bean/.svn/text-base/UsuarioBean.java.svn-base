/**
 * 
 */
package com.fiap.leilao.domain.bean;

import java.util.List;

import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;

/**
 * @author Leandro
 *
 */
public interface UsuarioBean extends AbstractDomain<Usuario>{

	/**
	 * 
	 */
	public static final String JNDI_NAME = "managerUsuarioBean";
	
	/**
	 * 
	 * @param login
	 * @param senha
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public Usuario pesquisaLoginSenha(String login , String senha) throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public List<Leilao> pesquisaLeiloesUsuario(Usuario usuario) throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public List<Leilao> pesquisaLeiloesLance(Usuario usuario) throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public List<Leilao> pesquisaLeiloesGanhos(Usuario usuario) throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param chaveSeguranca
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public Usuario pesquisaChaveSeguranca(String chaveSeguranca)throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws LeilaoDomainArgumentException
	 * @throws LeilaoDomainException
	 */
	public Usuario salvarUsuario(Usuario usuario)throws LeilaoDomainArgumentException , LeilaoDomainException;
	
	/**
	 * Cria o usuario Admin do sistema 
	 * Utilizado para testes do sistema
	 */
	public void criarUsuarioAdmin();
}
