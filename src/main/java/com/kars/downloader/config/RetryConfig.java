package com.kars.downloader.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.kars.downloader.exception.DownloadFailedException;

/**
 * 
 * RetryConfig
 * 
 * @author karthik.subbaramaiah
 *
 */
@Configuration
public class RetryConfig {

	@Value("${download.config.retry.count}")
	private int retryAttempts;

	/**
	 * @return retryTemplate
	 */
	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();

		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		fixedBackOffPolicy.setBackOffPeriod(2000l);
		retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(retryAttempts,
				Collections.singletonMap(DownloadFailedException.class, Boolean.TRUE));
		retryTemplate.setRetryPolicy(retryPolicy);

		return retryTemplate;
	}
}
