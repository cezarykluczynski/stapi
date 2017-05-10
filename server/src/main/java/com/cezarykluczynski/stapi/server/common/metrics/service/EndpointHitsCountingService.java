package com.cezarykluczynski.stapi.server.common.metrics.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

@Service
public class EndpointHitsCountingService {

	private final ConcurrentMap<Pair<String, String>, LongAdder> endpointsHits = new ConcurrentHashMap<>();

	private final EndpointHitsPersister endpointHitsPersister;

	@Inject
	public EndpointHitsCountingService(EndpointHitsPersister endpointHitsPersister) {
		this.endpointHitsPersister = endpointHitsPersister;
	}

	public void recordEndpointHit(String endpointName, String methodName) {
		Pair<String, String> key = Pair.of(endpointName, methodName);
		endpointsHits.putIfAbsent(key, new LongAdder());
		endpointsHits.get(key).increment();
	}

	public synchronized void flush() {
		endpointHitsPersister.persist(endpointsHits.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().sumThenReset())));
	}

}
