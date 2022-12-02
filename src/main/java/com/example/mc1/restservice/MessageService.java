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

	/**
	 * Sets end time stamp for the message and saves it to DB
	 */
	public void saveMessage(Message message) {
		message.setEndTimestamp(new Date(System.currentTimeMillis()));
		repository.save(message);
	}

	/**
	 * Created the new message and set ime stamp mc1
	 * @return new message with current time stamp
	 */
	public Message generateMessage() {
		Message message = new Message();
		this.sessionNumber++;
		message.setMc1Timestamp(new Date(System.currentTimeMillis()));
		message.setSessionId(sessionNumber);
		return message;
	}
}
