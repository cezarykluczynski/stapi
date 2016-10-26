package com.cezarykluczynski.stapi.sources.configuration;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class SourcesConfiguration {

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		Cache pagesCache = new ConcurrentMapCache("pagesCache");
		cacheManager.setCaches(Arrays.asList(pagesCache));
		cacheManager.afterPropertiesSet();
		return cacheManager;
	}

}
