package com.javaee.otavio.mercadodeacoes.services;

import com.javaee.otavio.mercadodeacoes.domain.Negociacao;

public interface NegociacaoService extends Crud<Negociacao>{
	Negociacao processarVendaEmpresa(String idEmpresa, String idAcao);
	Negociacao processarVendaCliente(String idCliente, String idAcao, float valor);
	Negociacao processarCompraCliente(String idCliente, String idNegociacao);
	String criarVendaEmpresa(String idEmpresa, String idAcao);
	String criarVendaCliente(String idCliente, String idAcao, float valor);
	String criarCompraCliente(String idCliente, String idNegociacao);
}
