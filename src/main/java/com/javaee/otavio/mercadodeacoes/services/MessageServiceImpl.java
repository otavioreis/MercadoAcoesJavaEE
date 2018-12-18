package com.javaee.otavio.mercadodeacoes.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.otavio.mercadodeacoes.domain.Message;
import com.javaee.otavio.mercadodeacoes.repositories.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public Set<Message> getAll() {
		Set<Message> messages = new HashSet<>();
		messageRepository.findAll().iterator().forEachRemaining(messages::add);
		return messages;
	}

	@Override
	public Message findById(String id) {
		Optional<Message> messageOptional = messageRepository.findById(id);

		if (!messageOptional.isPresent()) {
			throw new IllegalArgumentException("Mensagem não encontrada - id: " + id.toString());
		}

		return messageOptional.get();
	}

	@Override
	public Message createNew(Message item) {
		return messageRepository.save(item);			
	}

	@Override
	public Message save(Message item) {
		return messageRepository.save(item);
	}

	@Override
	public void deleteById(String id) {
		messageRepository.deleteById(id);
	}

}
