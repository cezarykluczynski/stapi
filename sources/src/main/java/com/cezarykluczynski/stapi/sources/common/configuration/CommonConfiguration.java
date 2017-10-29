package com.cezarykluczynski.stapi.sources.common.configuration;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("sourcesCommonConfiguration")
public class CommonConfiguration {

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}

}
