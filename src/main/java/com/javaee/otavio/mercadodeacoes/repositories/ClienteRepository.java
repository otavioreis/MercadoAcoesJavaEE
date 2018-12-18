package com.javaee.otavio.mercadodeacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.otavio.mercadodeacoes.domain.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String>{
	List<Cliente> findByNome(String nome);
	List<Cliente> findByEmail(String email);
}
