package com.comtom.bc.server.config;

import javax.annotation.PostConstruct;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClient {
	
	@Autowired
	private HttpClientBuilder httpClientBuilder;
	
	private static RestTemplate restTemplate;
	

	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
//		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
//		messageConverters.addAll(converters);
		return restTemplate;
	}
	
	@PostConstruct
	private void setRestTemplate() {
		RestClient.restTemplate = restTemplate();
	}
	
	public static RestTemplate getRestClient() {
		return restTemplate;
	}
}
