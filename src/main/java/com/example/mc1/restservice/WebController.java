package com.example.mc1.restservice;

import com.example.mc1.websocket.SendWebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class WebController {

	private final MessageService service;

	public WebController(MessageService service) {
		this.service = service;
	}

	@GetMapping(value = "/start/")
	public ResponseEntity<String> startSending() {
		try {
			MessageEntity message = service.generateMessage();
			SendWebSocketMessage.sendWebSocket(message);
//			SendWebSocketMessage.sendWebSocket(null);
			return ResponseEntity.ok("Started");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Bad request");
		}
	}

	@GetMapping(value = "/stop/")
	public ResponseEntity<String> stopSending() {
		try {
			return ResponseEntity.ok("Stopped");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Bad request");
		}
	}

	@PostMapping
	public void receiveMessage(@RequestBody MessageEntity message){
		service.saveMessage(message);
	}
}
