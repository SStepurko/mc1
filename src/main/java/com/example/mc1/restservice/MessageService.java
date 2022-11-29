package com.example.mc1.restservice;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageService {

	private final MessageRepository repository;
	private Long sessionNumber = 0L;

	public MessageService(MessageRepository repository) {
		this.repository = repository;
	}

	public void saveMessage(MessageEntity message) {
		repository.save(message);
	}

	public MessageEntity generateMessage() {
		this.sessionNumber ++;
		return new MessageEntity(sessionNumber, new Date(System.currentTimeMillis()));
	}
}
