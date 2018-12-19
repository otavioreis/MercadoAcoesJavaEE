package com.javaee.otavio.mercadodeacoes.controllers.v1;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.otavio.mercadodeacoes.domain.Negociacao;
import com.javaee.otavio.mercadodeacoes.services.NegociacaoService;

@RestController
@RequestMapping("/api/v1/negociacoes")
public class NegociacaoController {

	@Autowired
	private NegociacaoService negociacaoService;
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<Negociacao> getAll(){
		return negociacaoService.getAll();
	}
	
	@GetMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public Negociacao findById(@PathVariable String id) {
		return negociacaoService.findById(id);
	}
	
	@PostMapping({"/cliente/{idCliente}/negociacao/{idNegociacao}"})
	@ResponseStatus(HttpStatus.OK)
	public String criarCompraCliente(@PathVariable String idCliente, @PathVariable String idNegociacao) {
		return negociacaoService.criarCompraCliente(idCliente, idNegociacao);
	}
	
	@PostMapping({"/cliente/{idCliente}/acao/{idAcao}/valor/{valor}"})
	@ResponseStatus(HttpStatus.OK)
	public String criarVendaCliente(@PathVariable String idCliente, @PathVariable String idAcao, @PathVariable float valor) {
		return negociacaoService.criarVendaCliente(idCliente, idAcao, valor);
	}

}
