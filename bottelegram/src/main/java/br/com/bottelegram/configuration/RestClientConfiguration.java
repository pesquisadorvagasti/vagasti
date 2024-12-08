package br.com.bottelegram.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

	@Bean
	RestClient restClient(RestTemplateBuilder restTemplateBuilder) {
		return RestClient.builder(restTemplateBuilder.build()).build();
	}
}