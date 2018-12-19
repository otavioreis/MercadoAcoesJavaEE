package com.javaee.otavio.mercadodeacoes.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.otavio.mercadodeacoes.domain.Acao;
import com.javaee.otavio.mercadodeacoes.repositories.AcaoRepository;

@Service
public class AcaoServiceImpl implements AcaoService{

	@Autowired
	private AcaoRepository acaoRepository;
	
	@Override
	public Set<Acao> getAll() {
		Set<Acao> acoes = new HashSet<>();
		acaoRepository.findAll().iterator().forEachRemaining(acoes::add);
		return acoes;
	}

	@Override
	public Acao findById(String id) {
		Optional<Acao> acaoOptional = acaoRepository.findById(id);

		if (!acaoOptional.isPresent()) {
			throw new IllegalArgumentException("Ação não encontrada - id: " + id.toString());
		}

		return acaoOptional.get();
	}

	@Override
	public Acao createNew(Acao item) {
		Acao acao;
		try {
			acao = acaoRepository.findByNome(item.getNome()).get(0);
		} catch (Exception e) {
			item.setDataCriacao(LocalDateTime.now());
			return acaoRepository.save(item);			
		}
		
		throw new IllegalArgumentException("Ação já existe no banco - id: " + acao.getId());
	}

	@Override
	public Acao update(String id, Acao item) {
		item.setId(id);
		return acaoRepository.save(item);
	}

	@Override
	public void deleteById(String id) {
		acaoRepository.deleteById(id);
	}
}
