package com.javaee.otavio.mercadodeacoes.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Cliente {
	@Id
	private String id;
	private String nome;
	private String email;
	private LocalDateTime dataCriacao;
	
	@DBRef
	private Set<Acao> acoes = new HashSet<>();
}
