package com.javaee.otavio.mercadodeacoes.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.otavio.mercadodeacoes.domain.Cliente;
import com.javaee.otavio.mercadodeacoes.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public Set<Cliente> getAll() {
		Set<Cliente> clientes = new HashSet<>();
		clienteRepository.findAll().iterator().forEachRemaining(clientes::add);
		return clientes;
	}

	@Override
	public Cliente findById(String id) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);

		if (!clienteOptional.isPresent()) {
			throw new IllegalArgumentException("Cliente não encontrado - id: " + id.toString());
		}

		return clienteOptional.get();
	}

	@Override
	public Cliente createNew(Cliente item) {
		Cliente cliente;
		try {
			cliente = clienteRepository.findByNome(item.getNome()).get(0);
		} catch (Exception e) {
			return clienteRepository.save(item);			
		}
		
		throw new IllegalArgumentException("Cliente já existe no banco - id: " + cliente.getId());
	}

	@Override
	public Cliente save(Cliente item) {
		return clienteRepository.save(item);
	}

	@Override
	public void deleteById(String id) {
		clienteRepository.deleteById(id);
	}

}
