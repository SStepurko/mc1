package com.example.mc1.restservice;

import com.example.mc1.service.ProcessController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class WebController {

	private final MessageService service;
	private final ProcessController controller;

	public WebController(MessageService service, ProcessController controller) {
		this.service = service;
		this.controller = controller;
	}

	@GetMapping(value = "/start/")
	public ResponseEntity<String> startSending() {
		try {
			controller.setRunTimerTask();
			return ResponseEntity.ok("Started");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Bad request");
		}
	}

	@GetMapping(value = "/stop/")
	public ResponseEntity<String> stopSending() {
		try {
			controller.stopRunTimerTask();
			return ResponseEntity.ok("Stopped");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Bad request");
		}
	}

	@PostMapping
	public ResponseEntity<String> receiveMessage(@RequestBody Message message) {
		service.saveMessage(message);
		return ResponseEntity.ok("Saved");
	}
}
