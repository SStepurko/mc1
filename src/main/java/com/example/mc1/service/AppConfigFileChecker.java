package com.example.mc1.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class AppConfigFileChecker {


	/**
	 * Check if app.config file exists and create new one if it doesn't
	 *
	 * @param configReader class with config properties
	 */
	@Bean
	CommandLineRunner runner(ConfigReader configReader) {
		return args -> {
			String fileName = configReader.getFileName();
			Path path = Path.of(fileName);
			if (Files.notExists(path)) {
				Files.createFile(path);
				FileWriter fileWriter = new FileWriter(fileName);
				fileWriter.write("""
						# Длительность интервала взаимодействия задается в секундах
						runtime = 30
						# Периодичность отправки сообщений задается в милисекундах
						period = 1000""");
				fileWriter.close();
			}
		};
	}

}
