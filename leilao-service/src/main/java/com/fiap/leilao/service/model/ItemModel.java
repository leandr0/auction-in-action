/**
 * 
 */
package com.fiap.leilao.service.model;


/**
 * @author Leandro
 *
 */
public class ItemModel implements ServiceModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3806263969563701934L;

	private final String descricao;

	private final Integer quantidade;

	public ItemModel(String descricao, Integer quantidade) {
		this.descricao = descricao;
		this.quantidade = quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}
}