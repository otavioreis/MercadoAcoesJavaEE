package com.javaee.otavio.mercadodeacoes.domain;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="messages")
public class Message implements Serializable{

	//VendaEmpresa
	public Message(String idEmpresa, String idAcao, String assinatura) {
		this.setIdEmpresa(idEmpresa);
		this.setIdAcao(idAcao);
		this.setTipoNegociacao(TipoNegociacao.VendaEmpresa);
	}
	
	public Message(String idCliente, String idAcao, float valor) {
		this.setIdCliente(idCliente);
		this.setIdAcao(idAcao);
		this.setTipoNegociacao(TipoNegociacao.VendaCliente);
	}
	
	public Message(String idCliente, String idNegociacao) {
		this.setIdCliente(idCliente);
		this.setIdNegociacao(idNegociacao);
		this.setTipoNegociacao(TipoNegociacao.CompraCliente);
	}
	
	public Message() {
		
	}

	private static final long serialVersionUID = 1L;
	
	private String id;
	private TipoNegociacao tipoNegociacao;
	private String idEmpresa;
	private String idCliente;
	private String idNegociacao;
	private String idAcao;
	private float valor;
}
