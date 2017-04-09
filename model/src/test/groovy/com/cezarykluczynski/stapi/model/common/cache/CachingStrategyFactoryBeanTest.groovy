package com.cezarykluczynski.stapi.model.common.cache

import spock.lang.Specification

class CachingStrategyFactoryBeanTest extends Specification {

	private CacheProperties cachePropertiesMock

	private CachingStrategyFactoryBean cachingStrategyFactoryBean

	void setup() {
		cachePropertiesMock = Mock()
		cachingStrategyFactoryBean = new CachingStrategyFactoryBean(cachePropertiesMock)
	}

	void "returns NoCacheCachingStrategy for NO_CACHE CachingStrategyType"() {
		when:
		CachingStrategy cachingStrategy = cachingStrategyFactoryBean.object

		then:
		cachePropertiesMock.cachingStrategyType >> CachingStrategyType.NO_CACHE
		cachingStrategy instanceof NoCacheCachingStrategy

		then:
		cachingStrategyFactoryBean.objectType == CachingStrategy
		cachingStrategyFactoryBean.singleton
	}

	void "returns FullEntityCachingStrategy for CACHE_FULL_ENTITIES CachingStrategyType"() {
		when:
		CachingStrategy cachingStrategy = cachingStrategyFactoryBean.object

		then:
		cachePropertiesMock.cachingStrategyType >> CachingStrategyType.CACHE_FULL_ENTITIES
		cachingStrategy instanceof FullEntityCachingStrategy

		then:
		cachingStrategyFactoryBean.objectType == CachingStrategy
		cachingStrategyFactoryBean.singleton
	}

	void "returns CacheAllCachingStrategy for CACHE_ALL CachingStrategyType"() {
		when:
		CachingStrategy cachingStrategy = cachingStrategyFactoryBean.object

		then:
		cachePropertiesMock.cachingStrategyType >> CachingStrategyType.CACHE_ALL
		cachingStrategy instanceof CacheAllCachingStrategy

		then:
		cachingStrategyFactoryBean.objectType == CachingStrategy
		cachingStrategyFactoryBean.singleton
	}

}
