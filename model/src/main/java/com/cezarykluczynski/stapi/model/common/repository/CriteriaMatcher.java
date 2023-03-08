package com.cezarykluczynski.stapi.model.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CriteriaMatcher<C, E> {

	@SuppressWarnings("SpringCacheAnnotationsOnInterfaceInspection")
	@Cacheable(cacheNames = "entitiesCache", condition = "@entitiesCacheService.isCacheable(#p0)", key = "@entitiesCacheService.resolveKey(#p0)")
	Page<E> findMatching(C criteria, Pageable pageable);

}
