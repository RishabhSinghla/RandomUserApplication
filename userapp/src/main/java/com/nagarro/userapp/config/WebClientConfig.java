package com.nagarro.userapp.config;

/**
 * @author rishabhsinghla
 * Web Client configuration beans
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient api1Client() {
		return WebClient.builder().baseUrl("https://randomuser.me/api/")
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
	}

	@Bean
	public WebClient api2Client() {
		return WebClient.builder().baseUrl("https://api.nationalize.io/")
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
	}

	@Bean
	public WebClient api3Client() {
		return WebClient.builder().baseUrl("https://api.genderize.io/")
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
	}
}
