/**
 * 
 */
package com.fiap.leilao.web.application;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.FlowEvent;

import com.fiap.leilao.domain.Endereco;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.bean.SegurancaBean;
import com.fiap.leilao.domain.bean.UsuarioBean;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.security.Seguranca;

/**
 * @author Leandro
 *
 */
@ManagedBean(name = "cadastrarUsuarioBean")
@SessionScoped
public class UICadastrarUsuarioBean extends UIAbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8096694653534413514L;

	private final Log LOG = LogFactory.getLog(UICadastrarUsuarioBean.class);
	
	private Usuario usuario;

	private String confirmSenha;
	
	@EJB
	private UsuarioBean usuarioBean;
	
	@EJB
	private SegurancaBean segurancaBean;
	
	public UICadastrarUsuarioBean() {
		init();
	}
	
	private void init(){
		usuario = new Usuario();
		usuario.setSeguranca(new Seguranca());
		usuario.setEndereco(new Endereco());
	}
	
	public String cadastrarNovoUsuario(){
		
		init();
		
		return "cadastrar-usuario";
	}
	
	
	public String salvarUsuario(){
		try{
			
			if(usuario.getSeguranca().getSenha().equals(confirmSenha)){
				usuarioBean.salvarUsuario(usuario);
				init();
				showMessageInfo("Usuário salvo com sucesso! Efetue o login.");
			}else
				showMessageError("Os campos com os dados de senha estão divergentes");
			
		}catch (LeilaoDomainArgumentException e) {
			showMessageError(e.getMessage());
		}catch (Exception e) {
			LOG.error("Erro ao salvar usuario", e);
			showMessageError("Erro ao salvar usuário.");
		}
		
		return null;
	}
	
	
	public String verificarDisponibilidadeLogin(){
		
		try{
			if(!segurancaBean.disponibilidadeLogin(usuario.getSeguranca().getLogin()))
				showMessageError("Login indisponível");
			else
				showMessageInfo("Login disponível");
		}catch (LeilaoDomainArgumentException e) {
			showMessageError(e.getMessage());
		}catch (Exception e) {
			LOG.error("Erro ao buscar disponibilidade do login",e);
		}
		
		return null;
	}
	
	 public String onFlowProcess(FlowEvent event) {   
		             return event.getNewStep();  

   }  
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmSenha() {
		return confirmSenha;
	}

	public void setConfirmSenha(String confirmSenha) {
		this.confirmSenha = confirmSenha;
	}
}