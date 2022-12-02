package com.example.mc1.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Store results of sending process and print it
 */
public class Results {
	private static Date startDate;
	private static long messageCount;

	/**
	 * Starts counting and reset the message counter
	 */
	public static void startCount() {
		System.out.println("Process started");
		messageCount = 0;
		startDate = new Date(System.currentTimeMillis());
	}

	/**
	 * Increments the static message counter
	 */
	public static void countMessage() {
		messageCount++;
	}

	/**
	 * Stops counting and prints the message counter and duration of process.
	 */
	public static void stopCount() {
		if (startDate == null) {
			System.out.println("Nothing was sent");
			return;
		}
		Date stopDate = new Date(System.currentTimeMillis());
		long interactionTime = TimeUnit.MILLISECONDS.toSeconds(stopDate.getTime() - startDate.getTime());
		System.out.printf("Interaction time %s \n%d Messages sent\n", interactionTime, messageCount);
		startDate = null;
	}

}
