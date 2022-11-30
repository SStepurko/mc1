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

	public void saveMessage(Message message) {
		message.setEndTimestamp(new Date(System.currentTimeMillis()));
		repository.save(message);
	}

	public Message generateMessage() {
		Message message = new Message();
		this.sessionNumber++;
		message.setMc1Timestamp(new Date(System.currentTimeMillis()));
		message.setSessionId(sessionNumber);
		return message;
	}
}
