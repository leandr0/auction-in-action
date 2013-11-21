/**
 * 
 */
package com.greenfire.leilao.ui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.fiap.leilao.domain.Lance;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.bean.ItemBean;
import com.fiap.leilao.domain.bean.LanceBean;
import com.fiap.leilao.domain.bean.UsuarioBean;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;

/**
 * @author Leandro
 *
 */
@ManagedBean(name = "ui_leilao_usuario_bean")
@SessionScoped
public class UILeilaoUsuarioBean extends UIAbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4664472160381519370L;

	private List<Leilao> leiloesUsuario;

	private Leilao leilao;
	
	private List<Lance> lances;
	
	@EJB
	private UsuarioBean usuarioBean;
	
	//TODO
	@EJB
	private ItemBean itemBean;
	
	@EJB
	private LanceBean lanceBean;

	public String carregarLeilaoUsuario(){

		try{
			Usuario usuario = getUsuario();

			leiloesUsuario = usuarioBean.pesquisaLeiloesUsuario(usuario);
		}catch (Exception e) {
			// TODO: Tratar
		}
		return "leiloes-usuario";
	}

	public String carregarLeilaoLance(){
		try{
			Usuario usuario = getUsuario();

			leiloesUsuario = usuarioBean.pesquisaLeiloesLance(usuario);
		}catch (Exception e) {
			// TODO: Tratar
		}
		return "leiloes-usuario";
	}

	public String carregarLeilaoGanho(){
		try{
			Usuario usuario = getUsuario();

			leiloesUsuario = usuarioBean.pesquisaLeiloesGanhos(usuario);
		}catch (Exception e) {
			// TODO: Tratar
		}
		return "leiloes-usuario";
	}
	
	public String selecionarLeilao(){
		
		String idLeilao = getExternalContext().getRequestParameterMap().get("leilaoUsuario");

		if(idLeilao != null){
			for (Leilao ll : leiloesUsuario) {
				if(Long.valueOf(idLeilao).longValue() == ll.getId().longValue()){
					leilao = ll;
					break;
				}
			}
		}
		
		try {
			leilao.getProduto().setItens(
					itemBean.getItensProduto(leilao.getProduto()));
		}catch (LeilaoDomainArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LeilaoDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String listarLancesLeilaoSelecionado(){
		
		try {
			lances = lanceBean.pesquisarLancesLeilao(leilao);
		} catch (LeilaoDomainArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LeilaoDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public List<Leilao> getLeiloesUsuario() {
		return leiloesUsuario;
	}

	public void setLeiloesUsuario(List<Leilao> leiloesUsuario) {
		this.leiloesUsuario = leiloesUsuario;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public List<Lance> getLances() {
		return lances;
	}

	public void setLances(List<Lance> lances) {
		this.lances = lances;
	}
}