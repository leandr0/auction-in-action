/**
 * 
 */
package com.fiap.leilao.domain.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Produto;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.type.StatusLance;

/**
 * @author Leandro
 *
 */
@Stateless(mappedName = LanceBean.JNDI_NAME)
@Remote(LanceBean.class)
public class ManagerLanceBean extends AbstractDomainBean<Lance>implements LanceBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 243692927104355027L;
	
	private static final Log LOG = LogFactory.getLog(ManagerLanceBean.class);

	@Override
	public Long pesquisarMaiorLanceLeilao(Long idLeilao) throws LeilaoDomainArgumentException, LeilaoDomainException {

		Connection conn = null;

		try{

			conn = dataSource.getConnection();

			PreparedStatement  queryLance = conn.prepareStatement("SELECT LC.ID  " +
																" FROM LANCE LC " +
																" WHERE LEILAO_ID = ? " +
																" AND LC.STATUS = ? "+
																" ORDER BY  LC.VALOR DESC , " +
																" LC.DT_LANCE ASC");
			queryLance.setMaxRows(1);

			queryLance.setLong(1, idLeilao);
			queryLance.setString(2, StatusLance.ATIVO.toString());

			ResultSet resultSet = queryLance.executeQuery();

			Long result = null;

			if(resultSet.next()){
				result = resultSet.getLong(1);
			}

			return result;

		}catch (Exception e) {
			LOG.error("Erro ao consultar maior lance do leilao",e);
			throw new LeilaoDomainException(e);
		}finally{

			try{
				conn.close();
			}catch (Exception e) {
				LOG.error("Erro ao fechar conexao",e);
			}
		}	
	}

	@Override
	public List<Lance> pesquisarLancesLeilao(Leilao leilao)throws LeilaoDomainArgumentException, LeilaoDomainException {

		Connection conn = null;

		try{

			conn = dataSource.getConnection();

			PreparedStatement  queryLance = conn.prepareStatement("SELECT USU.NOME , LNC.DT_LANCE , LNC.VALOR  FROM " +
					" LEILAO LL INNER JOIN  LANCE LNC " +
					" ON LL.ID = LNC.LEILAO_ID  "+
					" INNER JOIN USUARIO USU "+
					" ON USU.ID = LNC.COMPRADOR_ID "+
			"WHERE LL.ID = ? ORDER BY  LNC.VALOR DESC , LNC.DT_LANCE ASC");

			queryLance.setLong(1, leilao.getId());

			ResultSet resultSet = queryLance.executeQuery();

			List<Lance> result = new LinkedList<Lance>();

			while(resultSet.next()){

				Lance lance = new Lance();
				lance.setDataLance(resultSet.getDate(2));
				lance.setValor(resultSet.getDouble(3));
				Usuario usuario = new Usuario();
				usuario.setNome(resultSet.getString(1));

				lance.setUsuario(usuario);

				result.add(lance);

			}

			return result;

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
	public List<Lance> pesquisarLancesUsuario(Usuario usuario) throws LeilaoDomainArgumentException, LeilaoDomainException {

		Connection conn = null;

		try{

			conn = dataSource.getConnection();

			PreparedStatement  queryLance = conn.prepareStatement

			(" SELECT PROD.DESCRICAO , LNC.DT_LANCE , LNC.VALOR , LNC.ID , LL.ID" +
					" FROM LANCE LNC " +
					" INNER JOIN USUARIO USU " +
					" ON LNC.COMPRADOR_ID = USU.ID " +
					" INNER JOIN LEILAO LL " +
					" ON LNC.LEILAO_ID = LL.ID " +
					" INNER JOIN PRODUTO PROD " +
					" ON PROD.LEILAO_ID = LL.ID " +
			" WHERE USU.ID = ? ");

			queryLance.setLong(1, usuario.getId());

			ResultSet resultSet = queryLance.executeQuery();

			List<Lance> result = new LinkedList<Lance>();

			while(resultSet.next()){

				Lance lance = new Lance();
				lance.setId(resultSet.getLong(4));
				lance.setDataLance(resultSet.getDate(2));
				lance.setValor(resultSet.getDouble(3));
				Produto produto = new Produto();
				produto.setDescricao(resultSet.getString(1));

				Leilao leilao = new Leilao();
				leilao.setId(resultSet.getLong(5));
				leilao.setProduto(produto);

				lance.setLeilao(leilao);

				result.add(lance);

			}

			return result;

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
	public boolean contemLanceLeilao(Leilao leilao) throws LeilaoDomainArgumentException, LeilaoDomainException {
		Connection conn = null;

		try{

			conn = dataSource.getConnection();

			PreparedStatement  queryLance = conn.prepareStatement("SELECT  LNC.VALOR  FROM " +
																		" LEILAO LL INNER JOIN  LANCE LNC " +
																		" ON LL.ID = LNC.LEILAO_ID  "+
																"WHERE LL.ID = ? ");

			queryLance.setLong(1, leilao.getId());
			queryLance.setMaxRows(1);

			ResultSet resultSet = queryLance.executeQuery();


			if(resultSet.next())
				return true;

			return false;

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
}