/**
 * 
 */
package com.fiap.leilao.web.application;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fiap.leilao.business.AdmLeilaoBean;
import com.fiap.leilao.business.exception.LeilaoBusinessException;
import com.fiap.leilao.domain.Item;
import com.fiap.leilao.domain.Leilao;
import com.fiap.leilao.domain.Usuario;
import com.fiap.leilao.domain.bean.ItemBean;
import com.fiap.leilao.domain.bean.LeilaoBean;
import com.fiap.leilao.domain.exception.LeilaoDomainArgumentException;
import com.fiap.leilao.domain.exception.LeilaoDomainException;
import com.fiap.leilao.domain.type.StatusLeilao;

/**
 * @author Leandro
 *
 */
@ManagedBean(name = "ui_gerenciarleilao_bean")
@SessionScoped
public class UIGerenciarLeilaoBean extends UIAbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5809926423081393770L;

	private static final Log LOG = LogFactory.getLog(UIGerenciarLeilaoBean.class);

	private List<Leilao> leiloes;

	private Leilao leilao;

	private Item 	item;

	@EJB//TODO
	private LeilaoBean leilaoBean;

	@EJB//TODO
	private ItemBean itemBean;

	@EJB//TODO
	private AdmLeilaoBean admLeilaoBean;

	private boolean permitidoEditar;

	public String init(){

		try {
			leiloes = leilaoBean.pesquisaLeilaoStatusUsuario(getUsuario());
			item = new Item();
		} catch (LeilaoDomainArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LeilaoDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "leiloes-usuario-adm";
	}

	public String selecionarLeilao(){

		String idLeilao = getExternalContext().getRequestParameterMap().get("leilaoUsuario");

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

			permitidoEditar  = admLeilaoBean.isPermitidoEditarLeilao(leilao);

			if(!permitidoEditar)
				showMessageWarn("O leilão não poderá ser editado pois já recebeu lances.");

		}catch (LeilaoDomainArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LeilaoDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LeilaoBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return null;
	}

	public String cancelarLeilao(){

		String idLeilao = getExternalContext().getRequestParameterMap().get("leilaoUsuarioCancelar");

		if(idLeilao != null){
			for (Leilao ll : leiloes) {
				if(Long.valueOf(idLeilao).longValue() == ll.getId().longValue()){
					leilao = ll;
					break;
				}
			}
		}

		
		try {
			
			permitidoEditar  = admLeilaoBean.isPermitidoEditarLeilao(leilao);
			
			if(!permitidoEditar){
				showMessageWarn("O leilão não poderá ser cancelado pois já recebeu lances.");
				return null;
			}		
			
			leilao.setStatus(StatusLeilao.CANCELADO);
			
			leilao = admLeilaoBean.atualizarStatusLeilao(leilao);
			
			showMessageInfo("Leilão cancelado com sucesso.");
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LeilaoBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		init();

		return null;
	}


	public String removerItem(){

		List<Integer> index = new LinkedList<Integer>();

		for(Item item :leilao.getProduto().getItens()){

			if(item.isSelect())
				index.add(leilao.getProduto().getItens().indexOf(item));

		}

		if(leilao.getProduto().getItens().size() == index.size()){
			showMessageWarn("O sistema não pode apagar todos os ítens , um leilão deve ter no mínimo 1 ítem");
			return null;
		}

		for(Integer idx : index){
			leilao.getProduto().getItens().remove(idx.intValue());
		}


		try {
			admLeilaoBean.atualizarLeilao(leilao);

			showMessageInfo("Ítem(ns) removido(s) com sucesso");
		} catch (LeilaoBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String editarItem(){

		String idLeilao = getExternalContext().getRequestParameterMap().get("itemLeilao");

		if(idLeilao != null){
			long itemLeilao = Long.valueOf(idLeilao);
			for(Item item :leilao.getProduto().getItens()){
				if(itemLeilao == item.getId().longValue()){
					this.item = item;
					break;
				}	
			}
		}
		
		return null;
	}

	public String atualizarItem(){

		if(item.getId() != null && item.getId().longValue() > 0){

			int index = 0;

			for(Item itm : leilao.getProduto().getItens()){
				if(itm.getId().longValue()== item.getId().longValue()){
					index = leilao.getProduto().getItens().indexOf(itm);			
					break;
				}
			}

			leilao.getProduto().getItens().get(index).setDescricao(item.getDescricao());
			leilao.getProduto().getItens().get(index).setQuantidade(item.getQuantidade());

		}else{
			leilao.getProduto().getItens().remove(item);
			leilao.getProduto().getItens().add(item);
		}

		return null;
	}

	public String atualizarLeilao(){

		try {
			admLeilaoBean.atualizarLeilao(leilao);
			showMessageInfo("Leilão atualizado com sucesso");
		} catch (IllegalArgumentException e) {
			showMessageError(e.getMessage());
		} catch (LeilaoBusinessException e) {
			e.printStackTrace();
			showMessageFatal("Ocorreu um erro grave.Por favor reinicie a operação");
		}

		init();

		return null;
	}

	public String adicionarItem(){

		try {

			Item tmp =  (Item) BeanUtils.cloneBean(item);

			for(Item itm : leilao.getProduto().getItens()){
				if(tmp.getDescricao().trim().equalsIgnoreCase(itm.getDescricao().trim())){
					showMessageWarn("Já existem um ítem com a mesma descrição : "+itm.getDescricao()+".");
					return null;
				}
			}

			if(StringUtils.isBlank(tmp.getDescricao())){
				showMessageWarn("Item não pode ser adicionado.Descrição inválida.");
				return null;
			}
			
			if(tmp.getQuantidade() == null || tmp.getQuantidade().intValue() <= 0 ){
				showMessageWarn("Item não pode ser adicionado.Quantidade inválida.");
				return null;
			}
			
			leilao.getProduto().getItens().add(tmp);

			item = new Item();

		} catch (Throwable e) {
			e.printStackTrace();
		}

		return null;
	}

	public String clear(){
		
		item = new Item();
		
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isPermitidoEditar() {
		return permitidoEditar;
	}

	public void setPermitidoEditar(boolean permitidoEditar) {
		this.permitidoEditar = permitidoEditar;
	}
}