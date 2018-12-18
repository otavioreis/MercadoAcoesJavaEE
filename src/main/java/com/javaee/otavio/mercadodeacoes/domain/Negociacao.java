package com.javaee.otavio.mercadodeacoes.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Negociacao {
	@Id
	private String id;
	private float valor;
	private LocalDateTime dataNegociacao;
	private TipoNegociacao tipoNegociacao; //Compra ou Venda
	
	@DBRef
	private Acao acao;
	
	@DBRef
	private Empresa empresaVendedora;
	@DBRef
	private Cliente clienteVendedor;
	@DBRef
	private Cliente clienteComprador;
}
