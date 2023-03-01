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

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(EndpointHitsCountingService.class);

	private final ConcurrentMap<MetricsEndpointKeyDTO, LongAdder> endpointsHits = new ConcurrentHashMap<>();

	private final EndpointHitsConsoleOutputFormatter endpointHitsConsoleOutputFormatter;

	public EndpointHitsCountingService(EndpointHitsConsoleOutputFormatter endpointHitsConsoleOutputFormatter) {
		this.endpointHitsConsoleOutputFormatter = endpointHitsConsoleOutputFormatter;
	}

	public void recordEndpointHit(String endpointName, String methodName, boolean apiBrowser) {
		MetricsEndpointKeyDTO key = MetricsEndpointKeyDTO.of(endpointName, methodName, apiBrowser);
		endpointsHits.putIfAbsent(key, new LongAdder());
		endpointsHits.get(key).increment();
	}

	@Scheduled(cron = "${statistics.endpointHitsConsolePrintCron}")
	public void flush() {
		LOG.info("{}", endpointHitsConsoleOutputFormatter.formatForConsolePrint(endpointsHits.entrySet()
				.stream()
					.collect(Collectors.toMap(Map.Entry::getKey, value -> value.getValue().sum()))));
	}

}
