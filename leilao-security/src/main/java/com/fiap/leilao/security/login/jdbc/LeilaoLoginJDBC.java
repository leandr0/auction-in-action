/**
 * 
 */
package com.fiap.leilao.security.login.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.security.login.model.LoginModel;

/**
 * @author leandro.goncalves
 *
 */
public class LeilaoLoginJDBC implements LoginJDBC{

	private static final Log LOG = LogFactory.getLog(LeilaoLoginJDBC.class);

	private Connection connection;

	private String dataSoureJndi;

	private Context 	context;
	
	private DataSource 	dataSource;
	
	public LeilaoLoginJDBC(String dataSoureJndi) {
		this.dataSoureJndi = dataSoureJndi;
	}

	private LoginModel executeQuery(String username) throws SQLException, NamingException {

		LOG.info("Executando query");

		LoginModel model = null;

		String sql = "SELECT SEG.SENHA , USU.PERFIL FROM SEGURANCA SEG " +
					" INNER JOIN USUARIO USU "+
					" ON SEG.ID = USU.SEGURANCA_ID "+
					" WHERE SEG.LOGIN = ?";

		
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1 , username);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			LOG.info("Obtendo resultado da query");
			model = new LoginModel(resultSet.getString(1),
					resultSet.getString(2));
		}

		LOG.info("Papel : [ "+model.getRole()+" ]");

		resultSet.close();
		preparedStatement.close();

		return model;
	}
	
	public LoginModel executeLogin(String username){
		
		try{
			
			openConnection();

			return executeQuery(username);
			
		}catch (Exception e) {
			LOG.error("ERRO ao executar login : " +e.getMessage());
		}finally {
			
			LOG.info("Finalizando Conexao e Contexto ");
			
			try{
				if(connection != null && !connection.isClosed())
					connection.close();
			}catch (Exception e) {
				LOG.error("ERRO ao fechar conexao");
			}
			dataSource = null;

			if(context!=null) {
				try {
					context.close();
				} catch(Exception e) {
					LOG.error("ERRO ao finalizar contexto");
				} 
			}
		}
		
		return null;
	}
	
	/**
	 
	 * @throws NamingException 
	 * @throws SQLException 
	 */
	private void openConnection() throws NamingException, SQLException{

		LOG.info("Fazendo lookup do dataSource");
		
		/**
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.provider.url", "localhost:1099");
		**/
		
		context = new InitialContext();
		dataSource = (javax.sql.DataSource)context.lookup(dataSoureJndi);

		LOG.info("Obtendo conexao");
		connection = dataSource.getConnection();

	}
}
