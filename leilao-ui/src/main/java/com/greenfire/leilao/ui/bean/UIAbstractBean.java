/**
 * 
 */
package com.greenfire.leilao.ui.bean;

import java.io.Serializable;
import java.security.Principal;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.bean.SegurancaBean;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;

/**
 * @author Leandro
 *
 */
public abstract class UIAbstractBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2478948320073700578L;
	
	protected HttpSession session;
	
	protected String nomeUsuario;
	
	protected String chaveServico;
	
	@EJB
	protected SegurancaBean segurancaBean;
	
	public UIAbstractBean() {
		createNewSession();
	}

	private void createNewSession(){
		session = (HttpSession) 
		getExternalContext().getSession(true);
	}

	public Usuario getUsuario() throws LeilaoDomainArgumentException, LeilaoDomainException{

		HttpServletRequest request =  (HttpServletRequest) getExternalContext().getRequest();
		Principal principal = request.getUserPrincipal();
	
		return segurancaBean.pesquisaUsuarioLogin(principal.getName());
	}

	protected void setAttributeInSession(String attributeName, Object value){
		session.setAttribute(attributeName, value);
	}

	/*protected Object getAttributeInSession(String attributeName){
		return session.getAttribute(attributeName);
	}*/

	protected String getParameterInRequest(String name){
		return getExternalContext().getRequestParameterMap().get(name);
	}
	
	protected ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance()
		.getExternalContext();
	}

	protected Object getAttributeInContext(String attributeName){
		return getExternalContext().getRequestMap().get(attributeName);
	}

	public HttpSession getSession() {

		if(session == null)
			createNewSession();

		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	protected void showMessageWarn(String message){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_WARN,"Warn",message));
	}

	protected void showMessageInfo(String message){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO,"Info",message));
	}

	protected void showMessageError(String message){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",message));
	}
	
	protected void showMessageFatal(String message){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal",message));
	}

	public String getNomeUsuario() {
		
		try{
			nomeUsuario = getUsuario().getNome();
		}catch (Exception e) {
			System.err.println("Erro ao recuperar usuario");
		}	
		
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getChaveServico() {
		try{
			chaveServico = getUsuario().getSeguranca().getChaveServico();
		}catch (Exception e) {
			System.err.println("Erro ao recuperar usuario");
		}
		return chaveServico;
	}

	public void setChaveServico(String chaveServico) {
		this.chaveServico = chaveServico;
	}
}