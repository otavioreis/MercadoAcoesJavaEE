package com.javaee.otavio.mercadodeacoes.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.otavio.mercadodeacoes.config.RabbitMQConfig;
import com.javaee.otavio.mercadodeacoes.domain.Acao;
import com.javaee.otavio.mercadodeacoes.domain.Cliente;
import com.javaee.otavio.mercadodeacoes.domain.Empresa;
import com.javaee.otavio.mercadodeacoes.domain.Message;
import com.javaee.otavio.mercadodeacoes.domain.Negociacao;
import com.javaee.otavio.mercadodeacoes.domain.TipoNegociacao;
import com.javaee.otavio.mercadodeacoes.emailsender.EmailSender;
import com.javaee.otavio.mercadodeacoes.repositories.NegociacaoRepository;

@Service
public class NegociacaoServiceImpl implements NegociacaoService {

	
	@Autowired
	private NegociacaoRepository negociacaoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private AcaoService acaoService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private EmailSender emailsender;
	
	@Override
	public Set<Negociacao> getAll() {
		Set<Negociacao> negociacaos = new HashSet<>();
		negociacaoRepository.findAll().iterator().forEachRemaining(negociacaos::add);
		return negociacaos;
	}

	@Override
	public Negociacao findById(String id) {
		Optional<Negociacao> negociacaoOptional = negociacaoRepository.findById(id);

		if (!negociacaoOptional.isPresent()) {
			throw new IllegalArgumentException("Negociação não encontrada - id: " + id.toString());
		}

		return negociacaoOptional.get();
	}

	@Override
	public Negociacao createNew(Negociacao item) {
		return negociacaoRepository.save(item);			
	}

	@Override
	public Negociacao save(Negociacao item) {
		return negociacaoRepository.save(item);
	}

	@Override
	public void deleteById(String id) {
		negociacaoRepository.deleteById(id);
	}

	@Override
	public Negociacao ProcessarVendaEmpresa(Empresa empresa, Acao acao) {
		Negociacao negociacao = new Negociacao();
		negociacao.setEmpresaVendedora(empresa);
		negociacao.setAcao(acao);
		negociacao.setDataNegociacao(LocalDateTime.now());
		negociacao.setTipoNegociacao(TipoNegociacao.VendaEmpresa);
		negociacao.setValor(acao.getValorInicial());
		negociacao = this.createNew(negociacao);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Olá " + empresa.getNome() + ". Registramos o seu pedido de venda!");
		sb.append("");
		sb.append("---------------------------------------------");
		sb.append("Nome da ação:"+acao.getNome());
		sb.append("Valor: " + acao.getValorInicial());
		sb.append("---------------------------------------------");
		sb.append("");
		sb.append("Caso alguém compre a sua ação, você será notificado novamente.");

		emailsender.SendEmail(empresa.getEmail(), "Registro de venda de empresa", sb.toString());
		
		return negociacao;
	}

	@Override
	public Negociacao ProcessarVendaCliente(String idCliente, String idAcao, float valor) {
		Cliente cliente = clienteService.findById(idCliente);
		Acao acao = acaoService.findById(idAcao);
		
		Negociacao negociacao = new Negociacao();
		negociacao.setEmpresaVendedora(null);
		negociacao.setClienteVendedor(cliente);
		negociacao.setAcao(acao);
		negociacao.setDataNegociacao(LocalDateTime.now());
		negociacao.setTipoNegociacao(TipoNegociacao.VendaCliente);
		negociacao.setValor(valor);
		negociacao = this.createNew(negociacao);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Olá " + cliente.getNome() + ". Registramos o seu pedido de venda!");
		sb.append("");
		sb.append("---------------------------------------------");
		sb.append("Nome da ação:"+acao.getNome());
		sb.append("Valor: " + valor);
		sb.append("---------------------------------------------");
		sb.append("");
		sb.append("Caso alguém compre a sua ação, você será notificado novamente.");

		acao.setValorAtual(valor);
		acaoService.save(acao);
		
		emailsender.SendEmail(cliente.getEmail(), "Registro de venda de cliente", sb.toString());
		
		
		return negociacao;
	}

	@Override
	public Negociacao ProcessarCompraCliente(String idCliente, String idNegociacao) {
		Cliente cliente = clienteService.findById(idCliente);
		Negociacao negociacao = this.findById(idNegociacao);
		
		negociacao.setDataNegociacao(LocalDateTime.now());
		negociacao.setClienteComprador(cliente);
		negociacao = this.save(negociacao);
		
		//Email para o vendedor
		if(negociacao.getEmpresaVendedora() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("Olá " + negociacao.getEmpresaVendedora().getNome() + ". Um cliente comprou a sua ação!");
			sb.append("");
			sb.append("---------------------------------------------");
			sb.append("Nome da ação:"+negociacao.getAcao().getNome());
			sb.append("Valor: " + negociacao.getAcao().getValorInicial());
			sb.append("Nome do comprador:"+cliente.getNome());
			sb.append("---------------------------------------------");
	
			emailsender.SendEmail(cliente.getEmail(), "Registro de venda de cliente", sb.toString());
		}
		else if(negociacao.getClienteVendedor() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("Olá " + negociacao.getClienteVendedor().getNome() + ". Um cliente comprou a sua ação!");
			sb.append("");
			sb.append("---------------------------------------------");
			sb.append("Nome da ação:"+negociacao.getAcao().getNome());
			sb.append("Valor: " + negociacao.getAcao().getValorAtual());
			sb.append("Nome do comprador:"+cliente.getNome());
			sb.append("---------------------------------------------");
			sb.append("");
			sb.append("Caso alguém compre a sua ação, você será notificado novamente.");
	
			emailsender.SendEmail(cliente.getEmail(), "Registro de venda de cliente", sb.toString());
		}
		
		//Email para o comprador
		StringBuilder sb = new StringBuilder();
		sb.append("Olá " + cliente.getNome() + ". Registramos o seu pedido de compra!");
		sb.append("");
		sb.append("---------------------------------------------");
		sb.append("Nome do vendedor" + negociacao.getEmpresaVendedora() != null ? negociacao.getEmpresaVendedora().getNome() : negociacao.getClienteVendedor().getNome());
		sb.append("Nome da ação:"+negociacao.getAcao().getNome());
		sb.append("Valor: " + negociacao.getEmpresaVendedora() != null ? negociacao.getAcao().getValorInicial() : negociacao.getAcao().getValorAtual());
		sb.append("---------------------------------------------");

		Acao acao = negociacao.getAcao();
		acao.setCliente(cliente);
		acaoService.save(acao);
		
		emailsender.SendEmail(cliente.getEmail(), "Registro de venda de cliente", sb.toString());
		
		return negociacao;
	}

	@Override
	public String CriarVendaEmpresa(Empresa empresa, Acao acao) {
		Message message = new Message(empresa, acao);
		this.SendMessage(message);
		return "mensagem enviada - Criar Venda Empresa";
	}

	@Override
	public String CriarVendaCliente(String idCliente, String idAcao, float valor) {
		Message message = new Message(idCliente, idAcao, valor);
		this.SendMessage(message);
		return "mensagem enviada - Criar Venda Cliente";
	}

	@Override
	public String CriarCompraCliente(String idCliente, String idNegociacao) {
		Message message = new Message(idCliente, idNegociacao);
		this.SendMessage(message);
		return "mensagem enviada - Criar Compra Cliente";
	}
	
	private void SendMessage(Message message) {
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_MESSAGES, message);
	}

}
