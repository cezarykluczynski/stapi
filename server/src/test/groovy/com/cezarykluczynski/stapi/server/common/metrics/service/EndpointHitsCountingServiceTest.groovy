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

	private EndpointHitsConsoleOutputFormatter endpointHitsPersisterMock

	private EndpointHitsCountingService endpointHitsCountingService

	void setup() {
		endpointHitsPersisterMock = Mock()
		endpointHitsCountingService = new EndpointHitsCountingService(endpointHitsPersisterMock)
	}

	void "adds statistics cumulatively"() {
		when:
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, true)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, true)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, true)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, false)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, false)
		endpointHitsCountingService.flush()

		then:
		1 * endpointHitsPersisterMock.formatForConsolePrint(_ as Map<MetricsEndpointKeyDTO, Long>, true) >> {
				Map<MetricsEndpointKeyDTO, Long> endpointsHits, boolean apiBrowser ->
			assert endpointsHits.size() == 4
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, true)) == 2
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, true)) == 1
		}
		1 * endpointHitsPersisterMock.formatForConsolePrint(_ as Map<MetricsEndpointKeyDTO, Long>, false) >> {
				Map<MetricsEndpointKeyDTO, Long> endpointsHits, boolean apiBrowser ->
			assert endpointsHits.size() == 4
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, false)) == 1
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, false)) == 1
		}

		when:
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, true)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, true)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, false)
		endpointHitsCountingService.flush()

		then:
		1 * endpointHitsPersisterMock.formatForConsolePrint(_ as Map<MetricsEndpointKeyDTO, Long>, true) >> {
				Map<MetricsEndpointKeyDTO, Long> endpointsHits, boolean apiBrowser ->
			assert endpointsHits.size() == 4
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, true)) == 3
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, true)) == 2
		}
		1 * endpointHitsPersisterMock.formatForConsolePrint(_ as Map<MetricsEndpointKeyDTO, Long>, false) >> {
				Map<MetricsEndpointKeyDTO, Long> endpointsHits, boolean apiBrowser ->
			assert endpointsHits.size() == 4
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, false)) == 2
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, false)) == 1
		}

		when:
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_3_NAME, ENDPOINT_3_METHOD_NAME, true)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME, true)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME, true)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_3_NAME, ENDPOINT_3_METHOD_NAME, false)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_3_NAME, ENDPOINT_3_METHOD_NAME, false)
		endpointHitsCountingService.recordEndpointHit(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME, false)
		endpointHitsCountingService.flush()

		then:
		1 * endpointHitsPersisterMock.formatForConsolePrint(_ as Map<MetricsEndpointKeyDTO, Long>, true) >> {
				Map<MetricsEndpointKeyDTO, Long> endpointsHits, boolean apiBrowser ->
			assert endpointsHits.size() == 8
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, true)) == 3
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, true)) == 2
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_3_NAME, ENDPOINT_3_METHOD_NAME, true)) == 1
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME, true)) == 2
		}
		1 * endpointHitsPersisterMock.formatForConsolePrint(_ as Map<MetricsEndpointKeyDTO, Long>, false) >> {
				Map<MetricsEndpointKeyDTO, Long> endpointsHits, boolean apiBrowser ->
			assert endpointsHits.size() == 8
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_1_NAME, ENDPOINT_1_METHOD_NAME, false)) == 2
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_2_NAME, ENDPOINT_2_METHOD_NAME, false)) == 1
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_3_NAME, ENDPOINT_3_METHOD_NAME, false)) == 2
			assert endpointsHits.get(MetricsEndpointKeyDTO.of(ENDPOINT_4_NAME, ENDPOINT_4_METHOD_NAME, false)) == 1
		}
	}

}
