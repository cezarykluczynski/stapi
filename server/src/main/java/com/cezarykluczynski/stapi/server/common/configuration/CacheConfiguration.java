package com.cezarykluczynski.stapi.server.common.configuration;

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
public class CacheConfiguration {

	@Bean
	public CacheManager pagesCache() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		Cache pagesCache = new ConcurrentMapCache("pagesCache");
		Cache entitiesCache = new ConcurrentMapCache("entitiesCache");
		cacheManager.setCaches(Arrays.asList(pagesCache, entitiesCache));
		cacheManager.afterPropertiesSet();
		return cacheManager;
	}

}