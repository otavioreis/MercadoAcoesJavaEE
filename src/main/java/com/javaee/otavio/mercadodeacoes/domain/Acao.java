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
public class Acao {
	@Id
	private String id;
	private String nome;
	private float valorInicial;
	private float valorAtual;
	private LocalDateTime dataCriacao;
	
	@DBRef
	private Empresa empresa;
	@DBRef
	private Cliente cliente;
}
