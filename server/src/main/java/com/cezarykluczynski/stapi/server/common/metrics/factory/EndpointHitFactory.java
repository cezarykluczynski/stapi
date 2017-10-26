package com.cezarykluczynski.stapi.server.common.metrics.factory;

import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO;
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.EndpointHit;
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.enums.EndpointType;
import com.cezarykluczynski.stapi.model.endpoint_hit.repository.EndpointHitRepository;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class EndpointHitFactory {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(EndpointHitFactory.class);

	private final EndpointHitRepository endpointHitRepository;

	public EndpointHitFactory(EndpointHitRepository endpointHitRepository) {
		this.endpointHitRepository = endpointHitRepository;
	}

	public void tryCreateAndPersistEmptyEntityFromKey(MetricsEndpointKeyDTO key) {
		EndpointHit endpointHit = new EndpointHit();
		endpointHit.setEndpointName(key.getEndpointName());
		endpointHit.setEndpointType(endpointTypeFromEndpointName(key.getEndpointName()));
		endpointHit.setMethodName(key.getMethodName());
		endpointHit.setNumberOfHits(0L);

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

		throw new StapiRuntimeException(String.format("Could not determine endpoint type for endpoint named \"%s\"", endpointName));
	}

}
