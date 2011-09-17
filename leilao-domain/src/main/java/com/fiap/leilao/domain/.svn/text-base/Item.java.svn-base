/**
 * 
 */
package com.fiap.leilao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Leandro
 *
 */
@Entity
@Table(name = "ITEM")
public class Item implements EntityBasic<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2763999461281718470L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DESCRICAO", nullable = false)
	//@Size(min = 5 , message = "A descrição do ítem deve ter no mínimo 5 caracteres")
	private String descricao;
	
	@Column(name = "QUANTIDADE" , nullable = false)
	private Integer quantidade = 1;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "PRODUTO_ID", nullable = false)
	private Produto produto;
	
	@Transient
	private boolean select;
	
	/* (non-Javadoc)
	 * @see com.fiap.leilao.domain.EntityBasic#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.fiap.leilao.domain.EntityBasic#setId(java.lang.Object)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}