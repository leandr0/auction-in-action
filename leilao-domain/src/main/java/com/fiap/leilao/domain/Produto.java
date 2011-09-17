/**
 * 
 */
package com.fiap.leilao.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

/**
 * @author Leandro
 *
 */
@Entity
@Table(name = "PRODUTO")
public class Produto implements EntityBasic<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7534417245600812692L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DESCRICAO", nullable = false)
	@Size(min = 5 , message = "A descrição do leilão deve ter no mínimo 5 caracteres")
	private String descricao;
	
	@OneToOne
	@JoinColumn(name = "LEILAO_ID", nullable = false)
	private Leilao leilao;
	
	
	@SuppressWarnings("deprecation")
	@OneToMany(mappedBy = "produto",cascade = CascadeType.ALL )
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Size(min = 1 , message = "O leilão deve ter no mínimo 1 ítem")
	private List<Item> itens;
	
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

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
}