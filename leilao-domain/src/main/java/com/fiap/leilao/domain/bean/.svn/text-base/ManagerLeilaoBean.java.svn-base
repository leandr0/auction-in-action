/**
 * 
 */
package com.fiap.leilao.domain.bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.NotYetImplementedException;

import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Produto;
import static com.fiap.leilao.domain.QueryLeilaoType.*;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.type.StatusLeilao;

/**
 * Bean que permite iterar com a camada de domínio <p>
 * para a entidade {@link Leilao}
 * @author Leandro
 *
 */
@Stateless(mappedName = LeilaoBean.JNDI_NAME)
@Remote(LeilaoBean.class)
public class ManagerLeilaoBean extends AbstractDomainBean<Leilao> implements LeilaoBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5714324918414293503L;

	
	private static final Log LOG = LogFactory.getLog(ManagerLeilaoBean.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.LeilaoBean#pesquisaLeilaoStatus(com.fiap.leilao.domain.type.StatusLeilao)
	 */
	@Override
	public List<Leilao> pesquisaLeilaoStatus(StatusLeilao statusLeilao) throws LeilaoDomainArgumentException, LeilaoDomainException {

		if(statusLeilao == null)
			throw new IllegalArgumentException("Status inválido para pesquisa.");

		try{
			CriteriaBuilder 		criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Leilao> 	criteriaQuery 	= criteriaBuilder.createQuery(Leilao.class);
			Root<Leilao> 			root			= criteriaQuery.from(Leilao.class);
			Predicate				condition		= criteriaBuilder.equal(root.get("status"),statusLeilao);

			criteriaQuery.where(condition);
			TypedQuery<Leilao> typedQuery = entityManager.createQuery(criteriaQuery);

			return typedQuery.getResultList();
		}catch (Exception e) {
			LOG.error("Erro ao pesquisar leilao por status",e);
			throw new LeilaoDomainException(e);
		}
	}

	/**
	 * A decisão arquitetural de utilizar as queries nativas <p>
	 * diretamente com um {@link DataSource} é devido a exceção {@link NotYetImplementedException}<p>
	 * ser lançada pelo perovider hibernate quando executada utilizando um arquivo orm.xml<p>
	 * @see com.fiap.leilao.domain.bean.LeilaoBean#pesquisaFinalizarLeilao()
	 */
	@Override
	public List<Long> pesquisaFinalizarLeilao() throws LeilaoDomainException {

		Connection conn = null;

		try{

			conn = dataSource.getConnection();

			LOG.info("Iniciando pesquisa para finalizar leilao");
			
			PreparedStatement querySearch = conn.prepareStatement(PESQUISA_FINALIZAR_LEILAO.nativeQuery);

			querySearch.setDate(1, getSqlDate());

			ResultSet resultSet = querySearch.executeQuery();

			List<Long> result = new LinkedList<Long>();

			while(resultSet.next()){
				result.add(resultSet.getLong(1));
			}

			return result;

		}catch (Exception e) {
			LOG.error("Erro ao excutar pesquiza de finalizar leilao ",e);
			throw new LeilaoDomainException(e);
		}finally{

			try{
				conn.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao");
			}
		}
	}

	/**
	 * Cria uma data com o horário igual à 00:00
	 * @return {@link Date}
	 */
	private Date getSqlDate(){

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.HOUR,0);
		calendar.set(Calendar.HOUR_OF_DAY,0);

		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * A decisão arquitetural de utilizar as queries nativas <p>
	 * diretamente com um {@link DataSource} é devido a exceção {@link NotYetImplementedException}<p>
	 * ser lançada pelo perovider hibernate quando executada utilizando um arquivo orm.xml<p>
	 * @see com.fiap.leilao.domain.bean.LeilaoBean#updateLeiloesFinalizados(java.util.List)
	 */
	@Override
	public void updateLeiloesFinalizados(List<Long> leiloesFinalizados) throws LeilaoDomainException {

		Connection conn = null;

		try{

			conn = dataSource.getConnection();

			PreparedStatement queryUpdate = conn.prepareStatement(UPDATE_LISTA_FINALIZAR_LEILAO.nativeQuery);

			int batch = 0;

			for (Long idLeilao : leiloesFinalizados) {

				queryUpdate.setInt(1, StatusLeilao.FINALIZADO.ordinal());
				queryUpdate.setLong(2, idLeilao);

				queryUpdate.addBatch();

				if(batch % 10 == 0)
					queryUpdate.executeBatch();

				batch++;
			}

			queryUpdate.executeBatch();
			
		}catch (Exception e) {
			LOG.error("Erro ao atualizar leiloes ",e);
			throw new LeilaoDomainException(e);
		}finally{

			try{
				conn.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao");
			}
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.LeilaoBean#pesquisaLeilaoEnviarLance(com.fiap.leilao.domain.Usuario)
	 */
	@Override
	public List<Leilao> pesquisaLeilaoEnviarLance(Usuario usuario) throws LeilaoDomainArgumentException,LeilaoDomainException {

		if(usuario == null || usuario.getId() == null || usuario.getId().longValue() <= 0L)
			throw new LeilaoDomainArgumentException("Usuário inválido para pesquisa");
		try{	

			CriteriaBuilder 		criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Leilao> 	criteriaQuery 	= criteriaBuilder.createQuery(Leilao.class);
			Root<Leilao> 			root			= criteriaQuery.from(Leilao.class);
			Predicate				condition		= criteriaBuilder.and( 
					criteriaBuilder.equal(root.get("status"),StatusLeilao.INICADO),
					criteriaBuilder.notEqual(root.get("vendedor"),usuario));

			criteriaQuery.where(condition);
			TypedQuery<Leilao> typedQuery = entityManager.createQuery(criteriaQuery);

			return typedQuery.getResultList();
	
		}catch (Exception e) {
			throw new LeilaoDomainException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fiap.leilao.domain.bean.LeilaoBean#pesquisaLeilaoStatusUsuario(com.fiap.leilao.domain.Usuario)
	 */
	@Override
	public List<Leilao> pesquisaLeilaoStatusUsuario(Usuario usuario)throws LeilaoDomainArgumentException, LeilaoDomainException {

		if(usuario == null || usuario.getId() == null || usuario.getId().longValue() <= 0L)
			throw new LeilaoDomainArgumentException("Usuário inválido para pesquisa");

		try{
			CriteriaBuilder 		criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Leilao> 	criteriaQuery 	= criteriaBuilder.createQuery(Leilao.class);
			Root<Leilao> 			root			= criteriaQuery.from(Leilao.class);
			Predicate				condition		= criteriaBuilder.and(
					criteriaBuilder.or( criteriaBuilder.equal(root.get("status"),StatusLeilao.AGUARDANDO_AUTORIZACAO),
							criteriaBuilder.equal(root.get("status"),StatusLeilao.INICADO))
							,
							criteriaBuilder.equal(root.get("vendedor"),usuario));

			criteriaQuery.where(condition);
			TypedQuery<Leilao> typedQuery = entityManager.createQuery(criteriaQuery);

			return typedQuery.getResultList();
		}catch (Exception e) {
			LOG.error("Erro ao pesquisar leilao por status",e);
			throw new LeilaoDomainException(e);
		}
	}

	/**
	 * A decisão arquitetural de utilizar as queries nativas <p>
	 * diretamente com um {@link DataSource} é devido a exceção {@link NotYetImplementedException}<p>
	 * ser lançada pelo perovider hibernate quando executada utilizando um arquivo orm.xml<p>
	 * @see com.fiap.leilao.domain.bean.LeilaoBean#pesquisarLeilaoGanhador(java.lang.Long)
	 */
	@Override
	public Leilao pesquisarLeilaoGanhador(Long idLeilao)throws LeilaoDomainArgumentException, LeilaoDomainException {

		Connection conn = null;

		try{

			LOG.info("Pesquisando ganhador dor leilao , id : "+idLeilao);
			
			conn = dataSource.getConnection();

			PreparedStatement querySelect = conn.prepareStatement(PESQUISA_GANHADOR_LEILAO.nativeQuery);

			
			querySelect.setLong(1, idLeilao);
			
			ResultSet resultSet = querySelect.executeQuery();
			
			Leilao leilao = null;
			
			while(resultSet.next()){
				
				leilao = new Leilao();
				leilao.setId(resultSet.getLong(1));
				
				Produto produto = new Produto();
				produto.setDescricao(resultSet.getString(2));
				
				Usuario usuario = new Usuario();
				usuario.setEmail(resultSet.getString(4));
				usuario.setNome(resultSet.getString(3));
				
				leilao.setComprador(usuario);
				leilao.setProduto(produto);
				
			}
			
			return leilao;

		}catch (Exception e) {
			LOG.error("Erro ao pesquisar ganhador leilao ",e);
			throw new LeilaoDomainException(e);
		}finally{

			try{
				conn.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao");
			}
		}		
	}
}