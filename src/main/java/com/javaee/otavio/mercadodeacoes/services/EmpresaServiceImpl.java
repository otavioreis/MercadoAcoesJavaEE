package com.javaee.otavio.mercadodeacoes.services;

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
			throw new IllegalArgumentException("Cliente não encontrado - id: " + id.toString());
		}

		return empresaOptional.get();
	}

	@Override
	public Empresa createNew(Empresa item) {
		Empresa empresa;
		try {
			empresa = empresaRepository.findByNome(item.getNome()).get(0);
		} catch (Exception e) {
			return empresaRepository.save(item);			
		}
		
		throw new IllegalArgumentException("Cliente já existe no banco - id: " + empresa.getId());
	}

	@Override
	public Empresa save(Empresa item) {
		return empresaRepository.save(item);
	}

	@Override
	public void deleteById(String id) {
		empresaRepository.deleteById(id);
	}

	@Override
	public Empresa CriarAcao(String idEmpresa, Acao acao) {
		Acao regAcao = acaoService.createNew(acao);
		
		Empresa empresa = this.findById(idEmpresa);
		Set<Acao> acoes = empresa.getAcoes();
		acoes.add(regAcao);
		empresa.setAcoes(acoes);
		Empresa empresaSaved = this.save(empresa);
		negociacaoService.CriarVendaEmpresa(empresaSaved, acao);
		
		return empresaSaved;
	}

}
