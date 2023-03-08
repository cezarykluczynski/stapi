package com.cezarykluczynski.stapi.server.common.cache;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider;
import com.google.common.base.Stopwatch;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EagerCachingExecutor {

	@SuppressWarnings("ConstantName")
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EagerCachingExecutor.class);

	private final EagerCachingDecider eagerCachingDecider;

	private final CacheableClassForFullEntitiesSearchProvider cacheableClassForFullEntitiesSearchProvider;

	private final RepositoryProvider repositoryProvider;

	private final EagerCacheReflectionHelper eagerCacheReflectionHelper;

	public EagerCachingExecutor(EagerCachingDecider eagerCachingDecider,
			CacheableClassForFullEntitiesSearchProvider cacheableClassForFullEntitiesSearchProvider, RepositoryProvider repositoryProvider,
			EagerCacheReflectionHelper eagerCacheReflectionHelper) {
		this.eagerCachingDecider = eagerCachingDecider;
		this.cacheableClassForFullEntitiesSearchProvider = cacheableClassForFullEntitiesSearchProvider;
		this.repositoryProvider = repositoryProvider;
		this.eagerCacheReflectionHelper = eagerCacheReflectionHelper;
	}

	@EventListener
	public void onApplicationReadyEvent(ApplicationReadyEvent event) {
		if (!eagerCachingDecider.isEagerCachingEnabled()) {
			return;
		}

		try {
			loadCaches();
		} catch (Exception e) {
			log.error("Eager loading caches failed, but the application will continue running.", e);
		}
	}

	private void loadCaches() {
		Stopwatch stopwatch = Stopwatch.createStarted();
		final List<Class> cacheableEntities = cacheableClassForFullEntitiesSearchProvider.getListOfEntities();
		final Map<Class, CrudRepository> classCrudRepositoryMap = repositoryProvider.provide();
		final AtomicInteger loadedEntities = new AtomicInteger();
		for (Class cacheableEntity : cacheableEntities) {
			String cacheableEntitySimpleName = cacheableEntity.getSimpleName();
			Stopwatch stopwatchEntity = Stopwatch.createStarted();
			final CrudRepository crudRepository = classCrudRepositoryMap.get(cacheableEntity);
			if (crudRepository instanceof CriteriaMatcher) {
				final Iterable allEntities = crudRepository.findAll();
				CriteriaMatcher criteriaMatcher = (CriteriaMatcher<?, ?>) crudRepository;
				Class criteriaClass = eagerCacheReflectionHelper.getCriteriaClass(criteriaMatcher);
				for (Object entity : allEntities) {
					Object criteria = eagerCacheReflectionHelper.createCriteria(criteriaClass, entity);
					try {
						criteriaMatcher.findMatching(criteria, Pageable.ofSize(1));
						loadedEntities.incrementAndGet();
					} catch (Exception e) {
						log.warn("Eager cache loading for criteria {} failed, but process will continue.", criteria);
					}
				}
			} else {
				log.error("Repository {} not an instance of CriteriaMatcher, skipping cache loading.", crudRepository);
			}
			stopwatchEntity.stop();
			log.info("Loading cache for type {} took {} seconds.", cacheableEntitySimpleName, stopwatchEntity.elapsed(TimeUnit.SECONDS));
		}
		stopwatch.stop();
		log.info("Loading all {} entities for {} types took {} minutes.",
				loadedEntities.get(), cacheableEntities.size(), stopwatch.elapsed(TimeUnit.SECONDS) / 60);
	}

}
