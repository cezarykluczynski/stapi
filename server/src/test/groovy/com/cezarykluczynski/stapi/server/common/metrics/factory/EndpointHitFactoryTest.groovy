package com.cezarykluczynski.stapi.server.common.metrics.factory

import com.cezarykluczynski.stapi.model.endpoint_hit.dto.MetricsEndpointKeyDTO
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.EndpointHit
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.enums.EndpointType
import com.cezarykluczynski.stapi.model.endpoint_hit.repository.EndpointHitRepository
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification

class EndpointHitFactoryTest extends Specification {

	private static final String ENDPOINT_NAME_REST = 'ENDPOINT_NAME_RestEndpoint'
	private static final String ENDPOINT_NAME_SOAP = 'ENDPOINT_NAME_SoapEndpoint'
	private static final String ENDPOINT_NAME_UNKNOWN = 'ENDPOINT_NAME_UNKNOWN'
	private static final String METHOD_NAME = 'METHOD_NAME'

	private EndpointHitRepository endpointHitRepositoryMock

	private EndpointHitFactory endpointHitFactory

	void setup() {
		endpointHitRepositoryMock = Mock()
		endpointHitFactory = new EndpointHitFactory(endpointHitRepositoryMock)
	}

	void "creates REST EndpointHit"() {
		given:
		MetricsEndpointKeyDTO metricsEndpointKeyDTO = MetricsEndpointKeyDTO.of(ENDPOINT_NAME_REST, METHOD_NAME)

		when:
		endpointHitFactory.tryCreateAndPersistEmptyEntityFromKey(metricsEndpointKeyDTO)

		then:
		1 * endpointHitRepositoryMock.save(_ as EndpointHit) >> { EndpointHit endpointHit ->
			assert endpointHit.endpointName == ENDPOINT_NAME_REST
			assert endpointHit.methodName == METHOD_NAME
			assert endpointHit.endpointType == EndpointType.REST
			assert endpointHit.numberOfHits == 0
		}
		0 * _
	}

	void "creates SOAP EndpointHit"() {
		given:
		MetricsEndpointKeyDTO metricsEndpointKeyDTO = MetricsEndpointKeyDTO.of(ENDPOINT_NAME_SOAP, METHOD_NAME)

		when:
		endpointHitFactory.tryCreateAndPersistEmptyEntityFromKey(metricsEndpointKeyDTO)

		then:
		1 * endpointHitRepositoryMock.save(_ as EndpointHit) >> { EndpointHit endpointHit ->
			assert endpointHit.endpointName == ENDPOINT_NAME_SOAP
			assert endpointHit.methodName == METHOD_NAME
			assert endpointHit.endpointType == EndpointType.SOAP
			assert endpointHit.numberOfHits == 0
		}
		0 * _
	}

	void "does not create endpoint that could not be classified as neither SOAP endpoint nor REST endpoint"() {
		given:
		MetricsEndpointKeyDTO metricsEndpointKeyDTO = MetricsEndpointKeyDTO.of(ENDPOINT_NAME_UNKNOWN, METHOD_NAME)

		when:
		endpointHitFactory.tryCreateAndPersistEmptyEntityFromKey(metricsEndpointKeyDTO)

		then:
		0 * _
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message == "Could not determine endpoint type for endpoint named \"${ENDPOINT_NAME_UNKNOWN}\""
	}

	void "tolerates DataIntegrityViolationException when saving"() {
		given:
		MetricsEndpointKeyDTO metricsEndpointKeyDTO = MetricsEndpointKeyDTO.of(ENDPOINT_NAME_REST, METHOD_NAME)

		when:
		endpointHitFactory.tryCreateAndPersistEmptyEntityFromKey(metricsEndpointKeyDTO)

		then:
		1 * endpointHitRepositoryMock.save(_ as EndpointHit) >> { EndpointHit endpointHit ->
			throw new DataIntegrityViolationException('')
		}
		0 * _
		notThrown(Exception)
	}

}
