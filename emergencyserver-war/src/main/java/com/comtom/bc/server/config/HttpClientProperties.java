package com.comtom.bc.server.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "httpClient")
public class HttpClientProperties {
	@NotNull
	private Pool pool;
	@NotNull
	private Builder builder;

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public Builder getBuilder() {
		return builder;
	}

	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	public static class Pool{
		private Integer validateAfterInactivity;

	    private Integer maxTotal;

	    private Integer defaultMaxPerRoute;

		public Integer getValidateAfterInactivity() {
			return validateAfterInactivity;
		}

		public void setValidateAfterInactivity(Integer validateAfterInactivity) {
			this.validateAfterInactivity = validateAfterInactivity;
		}

		public Integer getMaxTotal() {
			return maxTotal;
		}

		public void setMaxTotal(Integer maxTotal) {
			this.maxTotal = maxTotal;
		}

		public Integer getDefaultMaxPerRoute() {
			return defaultMaxPerRoute;
		}

		public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
			this.defaultMaxPerRoute = defaultMaxPerRoute;
		}
	    
	}
	
	public static class Builder{
	    private Integer connectTimeout;

	    private Integer connectionRequestTimeout;

	    private Integer socketTimeout;

		public Integer getConnectTimeout() {
			return connectTimeout;
		}

		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}

		public Integer getConnectionRequestTimeout() {
			return connectionRequestTimeout;
		}

		public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
			this.connectionRequestTimeout = connectionRequestTimeout;
		}

		public Integer getSocketTimeout() {
			return socketTimeout;
		}

		public void setSocketTimeout(Integer socketTimeout) {
			this.socketTimeout = socketTimeout;
		}
	    
	    
	}
}
