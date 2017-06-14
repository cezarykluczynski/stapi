package com.cezarykluczynski.stapi.server.common.metrics.factory;

import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO;
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.EndpointHit;
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.enums.EndpointType;
import com.cezarykluczynski.stapi.model.endpoint_hit.repository.EndpointHitRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class EndpointHitFactory {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(EndpointHitFactory.class);

	private final EndpointHitRepository endpointHitRepository;

	@Inject
	public EndpointHitFactory(EndpointHitRepository endpointHitRepository) {
		this.endpointHitRepository = endpointHitRepository;
	}

	public void tryCreateAndPersistEmptyEntityFromKey(MetricsEndpointKeyDTO key) {
		EndpointHit endpointHit = EndpointHit.builder()
				.endpointName(key.getEndpointName())
				.endpointType(endpointTypeFromEndpointName(key.getEndpointName()))
				.methodName(key.getMethodName())
				.numberOfHits(0L)
				.build();

		try {
			endpointHitRepository.save(endpointHit);
		} catch (DataIntegrityViolationException e) {
			LOG.info("Another instance has written endpoint key {}, continuing", key);
		}
	}

	private EndpointType endpointTypeFromEndpointName(String endpointName) {
		if (endpointName.contains("RestEndpoint")) {
			return EndpointType.REST;
		} else if (endpointName.contains("SoapEndpoint")) {
			return EndpointType.SOAP;
		}

		throw new RuntimeException(String.format("Could not determine endpoint type for endpoint named \"%s\"", endpointName));
	}

}
