/**
 * 
 */
package com.fiap.leilao.security.login;

import java.security.acl.Group;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import com.fiap.leilao.security.login.criptografia.CriptografiaUtil;
import com.fiap.leilao.security.login.jdbc.LeilaoLoginJDBC;
import com.fiap.leilao.security.login.jdbc.LoginJDBC;
import com.fiap.leilao.security.login.model.LoginModel;

/**
 * @author leandro.goncalves
 *
 */
public class LeilaoDataSourceLoginModule extends UsernamePasswordLoginModule {

	private static final Log LOG = LogFactory.getLog(LeilaoDataSourceLoginModule.class);
	
	/**
	 * nome do datasource
	 */
	private String dataSoureJndi; 

	private LoginModel loginModel;
	
	private LoginJDBC loginJDBC;
	
	/*
	 * (non-Javadoc)
	 * @see org.jboss.security.auth.spi.UsernamePasswordLoginModule#initialize(javax.security.auth.Subject, javax.security.auth.callback.CallbackHandler, java.util.Map, java.util.Map)
	 */
	
	@SuppressWarnings({"rawtypes","unchecked"})
	@Override
	public void initialize(Subject subject, CallbackHandler cbh,Map sharedState, Map options) {

		LOG.info("Inicializando JAAS customizado");
		
		super.initialize(subject, cbh, sharedState, options);
		
		LOG.info("Recuperando JNDI DataSource");
		dataSoureJndi = (String) options.get("dsJndiName");
		
		loginJDBC = new LeilaoLoginJDBC(dataSoureJndi);
		
		LOG.info("Datasource  : [ "+ dataSoureJndi+" ]");
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.security.auth.spi.UsernamePasswordLoginModule#validatePassword(java.lang.String, java.lang.String)
	 */
	@Override
	protected boolean validatePassword(String inputPassword,String expectedPassword) {
		LOG.info("Validando Senha");
		try{
			inputPassword = CriptografiaUtil.criptografar(inputPassword);
		}catch (Exception e) {
			LOG.error("Erro ao criptografar senha");
		}
		return super.validatePassword(inputPassword, expectedPassword);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.security.auth.spi.AbstractServerLoginModule#getRoleSets()
	 */
	@Override
	protected Group[] getRoleSets() throws LoginException {

		LOG.info("Obtendo papeis");
		
		if(loginModel != null){

			SimpleGroup simpleGroup = new SimpleGroup("Roles");

			SimplePrincipal simplePrincipal = new SimplePrincipal(loginModel.getRole());

			simpleGroup.addMember(simplePrincipal);

			return new Group[] { simpleGroup };
		}

		return new Group[0];
	}

	/* (non-Javadoc)
	 * @see org.jboss.security.auth.spi.UsernamePasswordLoginModule#getUsersPassword()
	 */
	@Override
	protected String getUsersPassword() throws LoginException {
		try{

			LOG.info("Obtendo password");

			LOG.info("Login : [ " + this.getUsername()+" ]");
			
			executeLogin();

			return loginModel.getSenha();
			
		}catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
	}

	private void executeLogin()throws Exception{
		LOG.info("Executando Login");
		loginModel = loginJDBC.executeLogin(this.getUsername());
	}

}