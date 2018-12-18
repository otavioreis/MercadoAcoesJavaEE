package com.javaee.otavio.mercadodeacoes.services;

import com.javaee.otavio.mercadodeacoes.domain.Acao;
import com.javaee.otavio.mercadodeacoes.domain.Empresa;
import com.javaee.otavio.mercadodeacoes.domain.Negociacao;

public interface NegociacaoService extends Crud<Negociacao>{
	Negociacao ProcessarVendaEmpresa(Empresa empresa, Acao acao);
	Negociacao ProcessarVendaCliente(String idCliente, String idAcao, float valor);
	Negociacao ProcessarCompraCliente(String idCliente, String idNegociacao);
	String CriarVendaEmpresa(Empresa empresa, Acao acao);
	String CriarVendaCliente(String idCliente, String idAcao, float valor);
	String CriarCompraCliente(String idCliente, String idNegociacao);
}
