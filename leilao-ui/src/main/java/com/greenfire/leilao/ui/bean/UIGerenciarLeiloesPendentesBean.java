/**
 * 
 */
package com.greenfire.leilao.ui.bean;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.jfree.util.Log;

import com.fiap.leilao.business.AutorizarLeilaoBean;
import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Leilao;

/**
 * @author Leandro
 *
 */
@ManagedBean(name = "ui_gerenciar_leiloes_pendentes_bean")
@SessionScoped
public class UIGerenciarLeiloesPendentesBean extends UIAbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6341552079491406364L;

	@EJB
	private AutorizarLeilaoBean autorizarLeilaoBean;

	private Leilao leilao;

	private List<Leilao> leiloesPendentes;

	public UIGerenciarLeiloesPendentesBean() {
		leiloesPendentes = new LinkedList<Leilao>();
	}

	public String init(){

		listarLeiloesPendentes();

		return "gerenciar-lance-leilao";
	}

	public String listarLeiloesPendentes(){

		try {
			leiloesPendentes = autorizarLeilaoBean.buscarLeiloesPendentes();
		} catch (LeilaoBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String autorizarLeilaoSelecionado(){

		try {
			autorizarLeilaoBean.atutorizarLeilao(leilao);
			showMessageInfo("Leilão autorizado com sucesso.");
		} catch (LeilaoBusinessException e) {
			Log.error(e.getMessage());
			showMessageError("Erro ao autorizar leilão.");
		}

		listarLeiloesPendentes();

		return null;
	}

	
	public String cancelarLeilaoSelecionado(){

		try {
			autorizarLeilaoBean.cancelarLeilao(leilao);
			showMessageInfo("Leilão cancelado com sucesso.");
		} catch (LeilaoBusinessException e) {
			Log.error(e.getMessage());
			showMessageError("Erro ao autorizar leilão.");
		}

		listarLeiloesPendentes();

		return null;
	}
	
	public String selecionarLeilao(){
		
		String idLeilao = FacesContext.getCurrentInstance().getExternalContext()
											.getRequestParameterMap().get("leilaoAction");

		if(idLeilao != null){
			for (Leilao ll : leiloesPendentes) {
				if(Long.valueOf(idLeilao).longValue() == ll.getId().longValue()){
					leilao = ll;
					break;
				}
			}
		}
		
		try {
			leilao.getProduto().setItens(
					autorizarLeilaoBean.buscarItensLeilao(leilao.getProduto()));
		} catch (LeilaoBusinessException e) {
			Log.error(e.getMessage());
		}
		
		return null;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public List<Leilao> getLeiloesPendentes() {
		return leiloesPendentes;
	}

	public void setLeiloesPendentes(List<Leilao> leiloesPendentes) {
		this.leiloesPendentes = leiloesPendentes;
	}
}