/**
 * 
 */
package com.fiap.leilao.web.application;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.bean.ItemBean;
import com.fiap.leilao.domain.bean.UsuarioBean;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;

/**
 * @author Leandro
 *
 */
@ManagedBean(name = "ui_leilaoganhousuario_bean")
@SessionScoped
public class UILeilaoGanhoUsuario extends UIAbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1007995227737936713L;

	@EJB
	private UsuarioBean usuarioBean;

	@EJB//TODO
	private ItemBean itemBean;
	
	private List<Leilao> leiloes;
	
	private Leilao leilao;

	public String init(){

		try{
			leiloes = usuarioBean.pesquisaLeiloesGanhos(getUsuario());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return "leiloes-ganhos-usuario";
	}

	public String selecionarLeilao(){

		String idLeilao = getExternalContext().getRequestParameterMap().get("leilaoGanho");

		if(idLeilao != null){
			for (Leilao ll : leiloes) {
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

	public List<Leilao> getLeiloes() {
		return leiloes;
	}

	public void setLeiloes(List<Leilao> leiloes) {
		this.leiloes = leiloes;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}
}