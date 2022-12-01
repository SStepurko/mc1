package com.example.mc1.service;

import com.example.mc1.restservice.Message;
import com.example.mc1.restservice.MessageService;
import com.example.mc1.websocket.SendWebSocketMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class ProcessController {

	private final MessageService service;
	private final ConfigReader config;

	public ProcessController(MessageService service, ConfigReader config) {
		this.service = service;
		this.config = config;
	}

	Timer timer = new Timer();
	TimerTask runTimerTask = new TimerTask() {
		@Override
		public void run() {
			Message message = service.generateMessage();
			SendWebSocketMessage.sendWebSocket(message);
		}
	};
	TimerTask stopTimerTask = new TimerTask() {
		@Override
		public void run() {
			runTimerTask.cancel();
			timer.cancel();
		}
	};

	private void setRunTimerTask(long delay, long period) {
		timer.schedule(runTimerTask, 0, period);
		timer.schedule(stopTimerTask, delay);
	}

	public void setRunTimerTask() {
		Map<String, Integer> confMap = new HashMap<>();
		confMap = config.getProperties();

		setRunTimerTask(confMap.get(config.getRuntimeKey()), confMap.get(config.getPeriodKey()));
	}

	public void stopRunTimerTask() {
		timer.schedule(stopTimerTask, 0);
	}

}
