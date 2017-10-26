package com.cezarykluczynski.stapi.server.common.metrics.service;

import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

@Service
public class EndpointHitsCountingService {

	private final ConcurrentMap<MetricsEndpointKeyDTO, LongAdder> endpointsHits = new ConcurrentHashMap<>();

	private final EndpointHitsPersister endpointHitsPersister;

	public EndpointHitsCountingService(EndpointHitsPersister endpointHitsPersister) {
		this.endpointHitsPersister = endpointHitsPersister;
	}

	public void recordEndpointHit(String endpointName, String methodName) {
		MetricsEndpointKeyDTO key = MetricsEndpointKeyDTO.of(endpointName, methodName);
		endpointsHits.putIfAbsent(key, new LongAdder());
		endpointsHits.get(key).increment();
	}

	@Scheduled(cron = "${statistics.persist.endpointHit}")
	public void flush() {
		Map<MetricsEndpointKeyDTO, Long> statisticsToPersist;

		synchronized (this) {
			statisticsToPersist = endpointsHits.entrySet()
					.stream()
					.collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().sumThenReset()));
		}

		endpointHitsPersister.persist(statisticsToPersist);
	}

}
