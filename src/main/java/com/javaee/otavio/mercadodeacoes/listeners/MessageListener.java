package com.javaee.otavio.mercadodeacoes.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaee.otavio.mercadodeacoes.config.RabbitMQConfig;
import com.javaee.otavio.mercadodeacoes.domain.Message;
import com.javaee.otavio.mercadodeacoes.services.NegociacaoService;

@Component
public class MessageListener {
	static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
	
	@Autowired
	private NegociacaoService negociacaoService;
	
	@RabbitListener(queues = RabbitMQConfig.QUEUE_MESSAGES)
	public void processMessage(Message message) {
		
		switch (message.getTipoNegociacao()) {
		case CompraCliente:
			negociacaoService.ProcessarCompraCliente(message.getIdCliente(), message.getIdNegociacao());
			break;

		case VendaCliente:
			negociacaoService.ProcessarVendaCliente(message.getIdCliente(), message.getIdAcao(), message.getValor());
			break;
			
		case VendaEmpresa:
			negociacaoService.ProcessarVendaEmpresa(message.getEmpresa(), message.getAcao());

		default:
			logger.info("Tipo de negociação inválido.");
			break;
		}
	}
}
