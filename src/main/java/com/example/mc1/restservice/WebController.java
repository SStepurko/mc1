package com.example.mc1.restservice;

import com.example.mc1.websocket.SendWebSocketMessage;
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
			Message message = service.generateMessage();
			SendWebSocketMessage.sendWebSocket(message);
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
	public ResponseEntity<String> receiveMessage(@RequestBody Message message) {
		service.saveMessage(message);
		return ResponseEntity.ok("Saved");
	}
}
