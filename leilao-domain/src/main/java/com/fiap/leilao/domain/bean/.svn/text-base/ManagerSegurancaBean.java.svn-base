/**
 * 
 */
package com.fiap.leilao.domain.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.security.Seguranca;

/**
 * @author Leandro
 *
 */
@Stateless(mappedName = SegurancaBean.JNDI_NAME)
@Local(SegurancaBean.class)
public class ManagerSegurancaBean extends AbstractDomainBean<Seguranca> implements SegurancaBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738466916904306158L;

	/* (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.SegurancaBean#disponibilidadeLogin(java.lang.String)
	 */
	@Override
	public boolean disponibilidadeLogin(String login) throws LeilaoDomainArgumentException, LeilaoDomainException {

		Connection conn = null; 

		
		if(StringUtils.isBlank(login) || login.length() < 3)
			throw new LeilaoDomainArgumentException("Dados inválidos para pesquisa");
		
		
		try{

			conn = dataSource.getConnection();

			PreparedStatement  querySeguranca = conn.prepareStatement("SELECT SEG.LOGIN FROM SEGURANCA SEG WHERE SEG.LOGIN = ?");

			querySeguranca.setString(1, login);

			ResultSet resultSet = querySeguranca.executeQuery();

			while(resultSet.next()){
				return false;
			}

			return true;

		}catch (Throwable e) {
			throw new LeilaoDomainException(e);
		}finally{

			try{
				conn.close();
			}catch (Throwable e) {
				System.err.println(e.getMessage());
			}
		}	
	}

	@Override
	public boolean validaChaveServico(String chaveServico)throws LeilaoDomainArgumentException, LeilaoDomainException {
		
		Connection conn = null; 

		
		if(StringUtils.isBlank(chaveServico) || chaveServico.length() < 10)
			throw new LeilaoDomainArgumentException("Dados inválidos para pesquisa");
		
		
		try{

			conn = dataSource.getConnection();

			PreparedStatement  querySeguranca = conn.prepareStatement("SELECT SEG.CHAVE_SERVICO FROM SEGURANCA SEG WHERE SEG.CHAVE_SERVICO = ?");

			querySeguranca.setString(1, chaveServico);
			querySeguranca.setMaxRows(1);
			
			ResultSet resultSet = querySeguranca.executeQuery();

			while(resultSet.next()){
				return true;
			}

			return true;

		}catch (Throwable e) {
			throw new LeilaoDomainException(e);
		}finally{

			try{
				conn.close();
			}catch (Throwable e) {
				System.err.println(e.getMessage());
			}
		}
	}

	@Override
	public Usuario pesquisaUsuarioLogin(String login)throws LeilaoDomainArgumentException, LeilaoDomainException {
		
		if(StringUtils.isBlank(login))
			throw new LeilaoDomainArgumentException("Login inválido");

		try{
			CriteriaBuilder 			criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Seguranca> 	criteriaQuery 	= criteriaBuilder.createQuery(Seguranca.class);
			Root<Seguranca> 			root			= criteriaQuery.from(Seguranca.class);
			Predicate					condition		= criteriaBuilder.equal(root.get("login"),login);

			criteriaQuery.where(condition);
			TypedQuery<Seguranca> typedQuery = entityManager.createQuery(criteriaQuery);

			Seguranca seguranca = typedQuery.getSingleResult();
			
			if(seguranca == null)
				throw new LeilaoDomainArgumentException("Registro não encontrado");
			
			return seguranca.getUsuario();
			
		}catch (LeilaoDomainArgumentException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LeilaoDomainException(e);
		}
	}

}
