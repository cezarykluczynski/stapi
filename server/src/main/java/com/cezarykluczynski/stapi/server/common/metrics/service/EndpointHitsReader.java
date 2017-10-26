package com.cezarykluczynski.stapi.server.common.metrics.service;

import com.cezarykluczynski.stapi.model.common.service.EntityMetadataProvider;
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.EndpointHit;
import com.cezarykluczynski.stapi.model.endpoint_hit.repository.EndpointHitRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;

@Service
@DependsOn("liquibase")
public class EndpointHitsReader {

	private static final Set<String> ENDPOINT_NAMES_EXCLUDES = Sets.newHashSet("CommonRestEndpoint");

	private Map<Class<? extends PageAware>, Long> entityToHitCountMap = Maps.newHashMap();

	private long allHitsCount;

	private final EndpointHitRepository endpointHitRepository;

	private final EntityMetadataProvider entityMetadataProvider;

	public EndpointHitsReader(EndpointHitRepository endpointHitRepository, EntityMetadataProvider entityMetadataProvider) {
		this.endpointHitRepository = endpointHitRepository;
		this.entityMetadataProvider = entityMetadataProvider;
	}

	public Long readAllHitsCount() {
		return allHitsCount;
	}

	public Map<Class<? extends PageAware>, Long> readEndpointHits() {
		return entityToHitCountMap;
	}

	@Scheduled(cron = "${statistics.read.endpointHit}")
	@PostConstruct
	public void refresh() {
		List<EndpointHit> endpointHitList = endpointHitRepository.findAll();
		Map<String, Class> classNameToMetadataMap = entityMetadataProvider.provideClassSimpleNameToClassMap();
		Map<Class<? extends PageAware>, Long> temporaryEntityToHitCountMap = Maps.newHashMap();
		LongAdder temporaryAllHitsCount = new LongAdder();
		endpointHitList.forEach(endpointHit -> {
			if (ENDPOINT_NAMES_EXCLUDES.contains(endpointHit.getEndpointName())) {
				return;
			}

			Class<? extends PageAware> entityClass = endpointNameToClass(endpointHit.getEndpointName(), classNameToMetadataMap);
			Long numberOfHits = endpointHit.getNumberOfHits();
			temporaryAllHitsCount.add(numberOfHits);
			temporaryEntityToHitCountMap.putIfAbsent(entityClass, 0L);
			temporaryEntityToHitCountMap.put(entityClass, temporaryEntityToHitCountMap.get(entityClass) + numberOfHits);
		});

		synchronized (this) {
			allHitsCount = temporaryAllHitsCount.longValue();
			entityToHitCountMap = temporaryEntityToHitCountMap;
		}
	}

	private Class<? extends PageAware> endpointNameToClass(String endpointName, Map<String, Class> classNameToMetadataMap) {
		String entityName = endpointName.replace("SoapEndpoint", "").replace("RestEndpoint", "");

		if (classNameToMetadataMap.containsKey(entityName)) {
			return (Class<? extends PageAware>) classNameToMetadataMap.get(entityName);
		}

		throw new StapiRuntimeException(String.format("Cannot map endpoint with name \"%s\" to entity class", endpointName));
	}

}
