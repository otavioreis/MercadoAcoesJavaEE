package com.javaee.otavio.mercadodeacoes.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.otavio.mercadodeacoes.domain.Acao;
import com.javaee.otavio.mercadodeacoes.domain.Empresa;
import com.javaee.otavio.mercadodeacoes.repositories.EmpresaRepository;


@Service
public class EmpresaServiceImpl implements EmpresaService{

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private NegociacaoService negociacaoService;
	
	@Autowired
	private AcaoService acaoService;
	
	@Override
	public Set<Empresa> getAll() {
		Set<Empresa> empresas = new HashSet<>();
		empresaRepository.findAll().iterator().forEachRemaining(empresas::add);
		return empresas;
	}

	@Override
	public Empresa findById(String id) {
		Optional<Empresa> empresaOptional = empresaRepository.findById(id);

		if (!empresaOptional.isPresent()) {
			throw new IllegalArgumentException("Empresa não encontrado - id: " + id.toString());
		}

		return empresaOptional.get();
	}

	@Override
	public Empresa createNew(Empresa item) {
		Empresa empresa;
		try {
			empresa = empresaRepository.findByNome(item.getNome()).get(0);
		} catch (Exception e) {
			item.setDataCriacao(LocalDateTime.now());
			return empresaRepository.save(item);			
		}
		
		throw new IllegalArgumentException("Empresa já existe no banco - id: " + empresa.getId());
	}

	@Override
	public Empresa update(String id, Empresa item) {
		item.setId(id);
		return empresaRepository.save(item);
	}

	@Override
	public void deleteById(String id) {
		empresaRepository.deleteById(id);
	}

	@Override
	public Empresa criarAcao(String idEmpresa, Acao acao) {
		Acao regAcao = acaoService.createNew(acao);
		
		Empresa empresa = this.findById(idEmpresa);
		Set<Acao> acoes = empresa.getAcoes();
		acoes.add(regAcao);
		empresa.setAcoes(acoes);
		Empresa empresaSaved = this.update(idEmpresa, empresa);
		negociacaoService.criarVendaEmpresa(empresaSaved.getId(), acao.getId());
		
		return empresaSaved;
	}

}
