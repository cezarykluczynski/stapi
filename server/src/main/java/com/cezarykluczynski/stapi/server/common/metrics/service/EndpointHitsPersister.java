package com.cezarykluczynski.stapi.server.common.metrics.service;

import com.cezarykluczynski.stapi.model.endpointHit.dto.MetricsEndpointKeyDTO;
import com.cezarykluczynski.stapi.model.endpointHit.entity.EndpointHit;
import com.cezarykluczynski.stapi.model.endpointHit.repository.EndpointHitRepository;
import com.cezarykluczynski.stapi.server.common.metrics.factory.EndpointHitFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EndpointHitsPersister {

	private final EndpointHitRepository endpointHitRepository;

	private final EndpointHitFactory endpointHitFactory;

	@Inject
	public EndpointHitsPersister(EndpointHitRepository endpointHitRepository, EndpointHitFactory endpointHitFactory) {
		this.endpointHitRepository = endpointHitRepository;
		this.endpointHitFactory = endpointHitFactory;
	}

	public void persist(Map<MetricsEndpointKeyDTO, Long> endpointsHits) {
		List<EndpointHit> endpointHitList = endpointHitRepository.findAll();
		Map<MetricsEndpointKeyDTO, EndpointHit> endpointHitMap = toEndpointHitMap(endpointHitList);

		endpointsHits.forEach((key, numberOfHits) -> {
			if (numberOfHits == 0) {
				return;
			}

			if (!endpointHitMap.containsKey(key)) {
				endpointHitFactory.tryCreateAndPersistEmptyEntityFromKey(key);
			}
			endpointHitRepository.updateNumberOfHits(numberOfHits, key.getEndpointName(), key.getMethodName());
		});
	}

	private Map<MetricsEndpointKeyDTO, EndpointHit> toEndpointHitMap(List<EndpointHit> endpointHitList) {
		return endpointHitList.stream()
				.collect(Collectors.toMap(this::createKey, Function.identity()));
	}

	private MetricsEndpointKeyDTO createKey(EndpointHit endpointHit) {
		return MetricsEndpointKeyDTO.of(endpointHit.getEndpointName(), endpointHit.getMethodName());
	}

}
