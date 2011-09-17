/**
 * 
 */
package com.fiap.leilao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fiap.leilao.domain.type.StatusLance;

/**
 * @author Leandro
 *
 */
@Entity
@Table(name = "LANCE")
public class Lance implements EntityBasic<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7172842808673978566L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "VALOR", nullable = false)
	private Double valor;
	
	@Column(name = "DT_LANCE", nullable = false)
	private Date dataLance;
	
	@ManyToOne
	@JoinColumn(name = "COMPRADOR_ID", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "LEILAO_ID", nullable = false)
	private Leilao leilao;
	
	@Column(name = "STATUS" , nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusLance status = StatusLance.ATIVO;
	
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDataLance() {
		return dataLance;
	}

	public void setDataLance(Date dataLance) {
		this.dataLance = dataLance;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public StatusLance getStatus() {
		return status;
	}

	public void setStatus(StatusLance status) {
		this.status = status;
	}
}