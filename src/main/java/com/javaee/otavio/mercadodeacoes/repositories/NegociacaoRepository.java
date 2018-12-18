package com.javaee.otavio.mercadodeacoes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.otavio.mercadodeacoes.domain.Negociacao;

@Repository
public interface NegociacaoRepository extends MongoRepository<Negociacao, String> {

}
