package com.cezarykluczynski.stapi.server.common.metrics.service

import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO
import spock.lang.Specification

class EndpointHitsCountingServiceTest extends Specification {

	private static final String ENDPOINT_1_NAME = 'ENDPOINT_1_NAME'
	private static final String ENDPOINT_1_METHOD_NAME = 'ENDPOINT_1_METHOD_NAME'
	private static final String ENDPOINT_2_NAME = 'ENDPOINT_2_NAME'
	private static final String ENDPOINT_2_METHOD_NAME = 'ENDPOINT_2_METHOD_NAME'
	private static final String ENDPOINT_3_NAME = 'ENDPOINT_3_NAME'
	private static final String ENDPOINT_3_METHOD_NAME = 'ENDPOINT_3_METHOD_NAME'
	private static final String ENDPOINT_4_NAME = 'ENDPOINT_4_NAME'
	private static final String ENDPOINT_4_METHOD_NAME = 'ENDPOINT_4_METHOD_NAME'

	private EndpointHitsPersister endpointHitsPersisterMock

	private EndpointHitsCountingService endpointHitsCountingService

	void setup() {
		endpointHitsPersisterMock = Mock()
		endpointHitsCountingService = new EndpointHitsCountingService(endpointHitsPersisterMock)
	}

	void "adds statistics, then send them to persister, then does it again"() {
		when:
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME)
		endpointHitsCountingService.flush()

		then: 'entries has the right number of hits'
		1 * endpointHitsPersisterMock.persist(_) >> { Map<MetricsEndpointKeyDTO, Long> endpointsHits ->
			assert endpointsHits.size() == 2
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME)) == 2
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME)) == 1
		}

		when:
		endpointHitsCountingService.flush()

		then: 'entries are empty'
		1 * endpointHitsPersisterMock.persist(_) >> { Map<MetricsEndpointKeyDTO, Long> endpointsHits ->
			assert endpointsHits.size() == 2
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME)) == 0
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME)) == 0
		}

		when:
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_3_NAME, ENDPOINT_3_METHOD_NAME)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME)
		endpointHitsCountingService.flush()

		then: 'old entries are empty, new entries has the right number of hits'
		1 * endpointHitsPersisterMock.persist(_) >> { Map<MetricsEndpointKeyDTO, Long> endpointsHits ->
			assert endpointsHits.size() == 4
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME)) == 0
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME)) == 0
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_3_NAME, ENDPOINT_3_METHOD_NAME)) == 1
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME)) == 2
		}

		when:
		endpointHitsCountingService.flush()

		then: 'all entries are empty'
		1 * endpointHitsPersisterMock.persist(_) >> { Map<MetricsEndpointKeyDTO, Long> endpointsHits ->
			assert endpointsHits.size() == 4
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME)) == 0
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME)) == 0
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_3_NAME, ENDPOINT_3_METHOD_NAME)) == 0
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME)) == 0
		}
	}

}
