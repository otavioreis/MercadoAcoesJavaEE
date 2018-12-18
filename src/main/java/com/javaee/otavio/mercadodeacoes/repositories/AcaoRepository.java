package com.javaee.otavio.mercadodeacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.otavio.mercadodeacoes.domain.Acao;

@Repository
public interface AcaoRepository extends MongoRepository<Acao, String> {
	List<Acao> findByNome(String nome);
}
