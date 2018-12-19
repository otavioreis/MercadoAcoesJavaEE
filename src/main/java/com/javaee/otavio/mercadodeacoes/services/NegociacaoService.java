package com.javaee.otavio.mercadodeacoes.services;

import com.javaee.otavio.mercadodeacoes.domain.Acao;
import com.javaee.otavio.mercadodeacoes.domain.Empresa;
import com.javaee.otavio.mercadodeacoes.domain.Negociacao;

public interface NegociacaoService extends Crud<Negociacao>{
	Negociacao processarVendaEmpresa(Empresa empresa, Acao acao);
	Negociacao processarVendaCliente(String idCliente, String idAcao, float valor);
	Negociacao processarCompraCliente(String idCliente, String idNegociacao);
	String criarVendaEmpresa(Empresa empresa, Acao acao);
	String criarVendaCliente(String idCliente, String idAcao, float valor);
	String criarCompraCliente(String idCliente, String idNegociacao);
}
