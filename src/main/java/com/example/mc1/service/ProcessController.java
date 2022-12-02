package com.example.mc1.service;

import com.example.mc1.restservice.Message;
import com.example.mc1.restservice.MessageService;
import com.example.mc1.websocket.SendWebSocketMessage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Starts and stops process of sending.
 * New start command will stop execution of old one.
 * Prints result at the end or when stopped
 */
@Component
public class ProcessController {

	//	Services for stop and start sending
	static ExecutorService service;
	static ScheduledExecutorService timerService;
	static Future<?> currentJob;
	static ScheduledFuture<?> timerJob;

	//	injections
	private final MessageService messageService;
	private final ConfigReader configReader;

	public ProcessController(MessageService messageService, ConfigReader configReader) {
		this.messageService = messageService;
		this.configReader = configReader;
	}

	/**
	 * Stops all current processes and cancel scheduled.
	 * Prints results
	 */
	private static void stopALL() {
		Results.stopCount();
		if (currentJob != null && !currentJob.isDone()) currentJob.cancel(true);
		if (timerJob != null && !timerJob.isDone()) timerJob.cancel(true);
	}

	/**
	 * Starts sending process and schedule stop. New call of method will end running old one.
	 * Starts counting messages and time for Result class.
	 * Prints results.
	 *
	 * @param periodMs frequency of sending messages in milliseconds
	 * @param delayMs  period of sending messages in milliseconds
	 */
	private void startTreadAndTimer(long periodMs, long delayMs) {
// check if executors exist and create new if don't
		if (service == null) service = Executors.newFixedThreadPool(1);
		if (timerService == null) timerService = Executors.newScheduledThreadPool(1);

//		check if process was running - stop it and print results
		if (currentJob != null && !currentJob.isDone()) {
			Results.stopCount();
			currentJob.cancel(true);
		}
//		start sending process
		currentJob = service.submit(() -> {
			Results.startCount();
			while (true) {
//				Message message = messageService.generateMessage();
//				SendWebSocketMessage.sendWebSocket(message);
				Results.countMessage();
				Thread.sleep(periodMs);
			}
		});

//		check if there was schedule and cancel it
		if (timerJob != null && !timerJob.isDone()) timerJob.cancel(true);
//		set new schedule
		timerJob = timerService.schedule(() -> {
			Results.stopCount();
			if (currentJob != null && !currentJob.isDone()) currentJob.cancel(true);
		}, delayMs, TimeUnit.SECONDS);
	}

	/**
	 * Starts private method for sending messages. Gets parameters from config file.
	 * New call of method will end running old one and print results
	 * Starts counting messages and time for Result class.
	 */
	public void setRunTimerTask() {
//		get from config
		Map<String, Long> confMap;
		confMap = configReader.getProperties();
		long periodMs = confMap.get(configReader.getPeriodKey());
		long delayMs = confMap.get(configReader.getRuntimeKey());
//		start process
		this.startTreadAndTimer(periodMs, delayMs);
	}

	/**
	 * Stop sending processes and print results.
	 */
	public void stopRunTimerTask() {
		stopALL();
	}
}
