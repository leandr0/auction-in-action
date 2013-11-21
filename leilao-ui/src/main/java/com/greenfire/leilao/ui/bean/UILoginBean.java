/**
 * 
 */
package com.greenfire.leilao.ui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.fiap.leilao.domain.bean.UsuarioBean;

/**
 * @author Leandro
 *
 */
@ManagedBean(name ="ui_login_bean")
@SessionScoped
public class UILoginBean extends UIAbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3938061356960093577L;

	@EJB
	private UsuarioBean usuarioBean;
	
	public String login(){
		usuarioBean.criarUsuarioAdmin();
		return "leilao-home";
	}
	
	public String logout(){
		session.invalidate();
		setNomeUsuario(null);
		return "home";
	}
}