package com.kars.downloader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Threads configuration
 * @author karthik.subbaramaiah
 *
 */
@Configuration
public class ThreadConfig {

	/**
	 * thread pool configurations and creation
	 * @return TaskExecutor
	 */
	@Bean("file_download_task_executor")
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("file_download_task_executor_thread");
        executor.initialize();
        return executor;
    }
}
