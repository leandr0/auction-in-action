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
import com.fiap.leilao.domain.bean.LeilaoBean;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.type.StatusLance;
import com.fiap.leilao.domain.type.StatusLeilao;

/**
 * @author Leandro
 *
 */
@ManagedBean(name = "ui_lance_usuario_bean")
@SessionScoped
public class UILanceUsuarioBean extends UIAbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -281574589318549369L;

	private List<Lance> lances;
	
	private Leilao leilao;
	
	private Lance lance;

	@EJB
	private LanceBean lanceBean;
	
	@EJB
	private LeilaoBean leilaoBean;
	
	//TODO
	@EJB
	private ItemBean itemBean;
	
	public String carregarLanceUsuario(){

		try{
			Usuario usuario = getUsuario();

			lances = lanceBean.pesquisarLancesUsuario(usuario);
		}catch (Exception e) {
			// TODO: Tratar
		}
		return "lances-usuario";
	}

	public String selecionarLance(){
		
		String idLance = getExternalContext().getRequestParameterMap().get("lanceUsuario");

		if(idLance != null){
			
			for (Lance lnc : lances) {
				if(Long.valueOf(idLance).longValue() == lnc.getId().longValue()){
					leilao = lnc.getLeilao();
					break;
				}
			}
		}
		
		try {
			leilao = leilaoBean.find(leilao);
		}catch (LeilaoDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public String cancelarLance(){
		
		String idLance = getExternalContext().getRequestParameterMap().get("lanceUsuarioCancelar");

		if(idLance != null){
			
			for (Lance lnc : lances) {
				if(Long.valueOf(idLance).longValue() == lnc.getId().longValue()){
					lance = lnc;
					break;
				}
			}
		}
		
		try {
			
			if(lance.getLeilao().getStatus() != StatusLeilao.INICADO ){
				showMessageWarn("O lance não pode ser cancelado , pois o leilao não está ativo.");
				return null;
			}
			
			lance.setStatus(StatusLance.CANCELADO);
			lanceBean.update(lance);
			
			showMessageInfo("Lance cancelado com sucesso.");
			
		} catch (IllegalArgumentException e) {
			showMessageError(e.getMessage());
		} catch (LeilaoDomainException e) {
			showMessageError("Erro ao atualizar Status do Lance.");
		}
		
		return null;
	}
	
	public List<Lance> getLances() {
		return lances;
	}

	public void setLances(List<Lance> lances) {
		this.lances = lances;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public Lance getLance() {
		return lance;
	}

	public void setLance(Lance lance) {
		this.lance = lance;
	}
}