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

	private static boolean isRunning;
	private final MessageService service;
	private final ConfigReader config;
	Timer timer = new Timer();

	MyThread runThread = new MyThread();
	TimerTask stopTimerTask = new TimerTask() {
		@Override
		public void run() {
			isRunning = false;
			Results.stopCount();
			runThread.pause();
			timer.cancel();
			timer.purge();
		}
	};

	public ProcessController(MessageService service, ConfigReader config) {
		this.service = service;
		this.config = config;
		isRunning = false;
	}

	private void startRunTimerTask(long delay, long period) {
		Results.startCount();
		if (runThread.isWasStarted()) {
			runThread.resumeIt();
		} else {
			runThread.start();
		}

		Timer timer = new Timer();
		timer.schedule(stopTimerTask, delay);
	}

	public void setRunTimerTask() {
//		do nothing if running
		if (isRunning) {
			return;
		}
//		get from config
		Map<String, Long> confMap = new HashMap<>();
		confMap = config.getProperties();
		startRunTimerTask(confMap.get(config.getRuntimeKey()), confMap.get(config.getPeriodKey()));
	}

	public void stopRunTimerTask() {
		if (isRunning) {
			timer.cancel();
			timer.purge();
			timer = null;
			runThread.pause();
			Results.stopCount();
			isRunning = false;
		}
	}


	/**
	 * thread with pause and stop
	 */
	class MyThread extends Thread {
		private boolean run;
		private boolean wasStarted;

		public MyThread() {
		}

		@Override
		public void run() {
			this.run = true;
			while (run) {
				isRunning = true;
				Message message = service.generateMessage();
				SendWebSocketMessage.sendWebSocket(message);
				Results.countMessage();
			}
		}

		@Override
		public synchronized void start() {
			this.setWasStarted(true);
			super.start();
		}

		public void pause() {
			this.run = false;
		}

		public void resumeIt() {
			this.run = true;
		}

		public boolean isWasStarted() {
			return wasStarted;
		}

		public void setWasStarted(boolean wasStarted) {
			this.wasStarted = wasStarted;
		}
	}
}
