/**
 * 
 */
package com.fiap.leilao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Leandro
 *
 */
@Entity
@Table(name = "ENDERECO")
public class Endereco implements EntityBasic<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9069126964673722148L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ENDERECO" , nullable = false)
	private String endereco;
	
	@Column(name = "CEP" , nullable = false)
	private String cep;
	
	@Column(name = "NUMERO" , nullable = false)
	private String numero;
	
	@Column(name = "BAIRRO" , nullable = false)
	private String bairro;
	
	@Column(name = "MUNICIPIO" , nullable = false)
	private String municipio;
	
	@Column(name = "ESTADO" , nullable = false)
	private String estado;
	
	@OneToOne(mappedBy = "endereco")
	private Usuario usuario;
	
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}