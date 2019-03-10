package com.kars.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.kars.downloader" })
public class DownloaderApplication {

	private static Logger logger = LoggerFactory.getLogger(DownloaderApplication.class);

	public static void main(String[] args) {
		logger.info("Starting the File Downloader application");
		SpringApplication.run(DownloaderApplication.class, args);
		logger.info("###################################################");
		logger.info("\n___________.__.__               .___                  .__                    .___            \n"
				+ "\\_   _____/|__|  |   ____     __| _/______  _  ______ |  |   _________     __| _/___________ \n"
				+ " |    __)  |  |  | _/ __ \\   / __ |/  _ \\ \\/ \\/ /    \\|  |  /  _ \\__  \\   / __ |/ __ \\_  __ \\\n"
				+ " |     \\   |  |  |_\\  ___/  / /_/ (  <_> )     /   |  \\  |_(  <_> ) __ \\_/ /_/ \\  ___/|  | \\/\n"
				+ " \\___  /   |__|____/\\___  > \\____ |\\____/ \\/\\_/|___|  /____/\\____(____  /\\____ |\\___  >__|   \n"
				+ "     \\/                 \\/       \\/                 \\/                \\/      \\/    \\/       ");
		logger.info("###################################################");
		logger.info("File Downloader application started successfully");
	}

}
