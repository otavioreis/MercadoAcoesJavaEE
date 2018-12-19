package com.javaee.otavio.mercadodeacoes.services;

import com.javaee.otavio.mercadodeacoes.domain.Acao;
import com.javaee.otavio.mercadodeacoes.domain.Empresa;

public interface EmpresaService extends Crud<Empresa> {
	Empresa criarAcao(String idEmpresa, Acao acao);
}
