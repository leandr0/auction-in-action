/**
 * 
 */
package com.fiap.leilao.service.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamInclude;

/**
 * @author Leandro
 *
 */
@XStreamInclude(value = ItemModel.class)
public class LeilaoModel implements ServiceModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3709682815064077756L;

	private final Long codLeilao;
	
	private final String descricao;
	
	private final double lanceMinimo;
	
	@XStreamImplicit(itemFieldName = "itens")
	private final List<ItemModel> itens;

	public LeilaoModel(Long codLeilao, String descricao ,double lanceMinimo, List<ItemModel> itens) {
		this.codLeilao 		= codLeilao;
		this.descricao 		= descricao;
		this.lanceMinimo 	= lanceMinimo;
		this.itens 			= itens;
	}

	public Long getCodLeilao() {
		return codLeilao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public double getLanceMinimo() {
		return lanceMinimo;
	}

	public List<ItemModel> getItens() {
		return itens;
	}
}