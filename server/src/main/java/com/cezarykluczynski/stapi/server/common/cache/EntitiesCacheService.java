package com.cezarykluczynski.stapi.server.common.cache;

import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntitiesCacheService {

	private final CacheUidExtractor cacheUidExtractor;
	private final UidGenerator uidGenerator;
	private final CacheableClassForFullEntitiesSearchProvider cacheableClassForFullEntitiesSearchProvider;

	public EntitiesCacheService(CacheUidExtractor cacheUidExtractor, UidGenerator uidGenerator,
			CacheableClassForFullEntitiesSearchProvider cacheableClassForFullEntitiesSearchProvider) {
		this.cacheUidExtractor = cacheUidExtractor;
		this.uidGenerator = uidGenerator;
		this.cacheableClassForFullEntitiesSearchProvider = cacheableClassForFullEntitiesSearchProvider;
	}

	public boolean isCacheable(Object criteria) {
		final Optional<String> uidOptional = cacheUidExtractor.extractUid(criteria);
		if (uidOptional.isEmpty()) {
			return false;
		}

		final Class entityClass = uidGenerator.retrieveEntityClassFromUid(uidOptional.get());
		return cacheableClassForFullEntitiesSearchProvider.isClassCacheable(entityClass);
	}

	public String resolveKey(Object criteria) {
		return cacheUidExtractor.extractUid(criteria).orElse(null);
	}

}
