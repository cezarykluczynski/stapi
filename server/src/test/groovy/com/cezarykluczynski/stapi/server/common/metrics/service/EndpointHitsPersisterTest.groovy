package com.cezarykluczynski.stapi.server.common.metrics.service

import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.EndpointHit
import com.cezarykluczynski.stapi.model.endpoint_hit.repository.EndpointHitRepository
import com.cezarykluczynski.stapi.server.common.metrics.factory.EndpointHitFactory
import com.google.common.collect.Maps
import org.assertj.core.util.Lists
import spock.lang.Specification

class EndpointHitsPersisterTest extends Specification {

	private static final String ENDPOINT_NAME_NO_HITS = 'ENDPOINT_NAME_NO_HITS'
	private static final String METHOD_NAME_NO_HITS = 'METHOD_NAME_NO_HITS'
	private static final String ENDPOINT_NAME_NOT_EXISTING = 'ENDPOINT_NAME_NOT_EXISTING'
	private static final String METHOD_NAME_NOT_EXISTING = 'METHOD_NAME_NOT_EXISTING'
	private static final String ENDPOINT_NAME_EXISTING = 'ENDPOINT_NAME_EXISTING'
	private static final String METHOD_NAME_EXISTING = 'METHOD_NAME_EXISTING'
	private static final Long NUMBER_OF_HITS_NO_HITS = 0L
	private static final Long NUMBER_OF_HITS_NOT_EXISTING = 1L
	private static final Long NUMBER_OF_HITS_EXISTING = 2L

	private EndpointHitRepository endpointHitRepositoryMock

	private EndpointHitFactory endpointHitFactoryMock

	private EndpointHitsPersister endpointHitsPersister

	void setup() {
		endpointHitRepositoryMock = Mock()
		endpointHitFactoryMock = Mock()
		endpointHitsPersister = new EndpointHitsPersister(endpointHitRepositoryMock, endpointHitFactoryMock)
	}

	void "persists hits"() {
		given:
		MetricsEndpointKeyDTO keyNotExisting = MetricsEndpointKeyDTO.of(ENDPOINT_NAME_NOT_EXISTING, METHOD_NAME_NOT_EXISTING)
		MetricsEndpointKeyDTO keyExisting = MetricsEndpointKeyDTO.of(ENDPOINT_NAME_EXISTING, METHOD_NAME_EXISTING)
		Map<MetricsEndpointKeyDTO, Long> endpointsHits = Maps.newHashMap()
		endpointsHits.put(MetricsEndpointKeyDTO.of(ENDPOINT_NAME_NO_HITS, METHOD_NAME_NO_HITS), NUMBER_OF_HITS_NO_HITS)
		endpointsHits.put(keyNotExisting, NUMBER_OF_HITS_NOT_EXISTING)
		endpointsHits.put(keyExisting, NUMBER_OF_HITS_EXISTING)
		EndpointHit endpointHitNoHits = new EndpointHit(endpointName: ENDPOINT_NAME_NO_HITS, methodName: METHOD_NAME_NO_HITS)
		EndpointHit endpointHitExisting = new EndpointHit(endpointName: ENDPOINT_NAME_EXISTING, methodName: METHOD_NAME_EXISTING)

		when:
		endpointHitsPersister.persist(endpointsHits)

		then:
		1 * endpointHitRepositoryMock.findAll() >> Lists.newArrayList(endpointHitNoHits, endpointHitExisting)
		1 * endpointHitFactoryMock.tryCreateAndPersistEmptyEntityFromKey(keyNotExisting)
		1 * endpointHitRepositoryMock.updateNumberOfHits(NUMBER_OF_HITS_NOT_EXISTING, ENDPOINT_NAME_NOT_EXISTING, METHOD_NAME_NOT_EXISTING)
		1 * endpointHitRepositoryMock.updateNumberOfHits(NUMBER_OF_HITS_EXISTING, ENDPOINT_NAME_EXISTING, METHOD_NAME_EXISTING)
		0 * _
	}

}
