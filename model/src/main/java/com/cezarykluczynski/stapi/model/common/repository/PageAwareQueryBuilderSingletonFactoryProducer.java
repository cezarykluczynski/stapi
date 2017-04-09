package com.cezarykluczynski.stapi.model.common.repository;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.google.common.collect.Maps;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

@Service
public class PageAwareQueryBuilderSingletonFactoryProducer {

	private JpaContext jpaContext;

	private CachingStrategy cachingStrategy;

	private final Map<Class<? extends PageAware>, PageAwareQueryBuilderFactory> factoryCache = Maps.newHashMap();

	@Inject
	public PageAwareQueryBuilderSingletonFactoryProducer(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		this.jpaContext = jpaContext;
		this.cachingStrategy = cachingStrategy;
	}

	public synchronized PageAwareQueryBuilderFactory create(Class<? extends PageAware> baseClass) {
		if (!factoryCache.containsKey(baseClass)) {
			factoryCache.put(baseClass, new PageAwareQueryBuilderFactory(jpaContext, cachingStrategy, baseClass));
		}

		return factoryCache.get(baseClass);
	}

}
