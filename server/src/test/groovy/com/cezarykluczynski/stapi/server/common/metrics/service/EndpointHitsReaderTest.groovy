package com.cezarykluczynski.stapi.server.common.metrics.service

import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.common.service.EntityMatadataProvider
import com.cezarykluczynski.stapi.model.endpointHit.entity.EndpointHit
import com.cezarykluczynski.stapi.model.endpointHit.repository.EndpointHitRepository
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.google.common.collect.Maps
import org.assertj.core.util.Lists
import spock.lang.Specification

class EndpointHitsReaderTest extends Specification {

	private static final long REST_ENDPOINT_NUMBER_OF_HITS = 2
	private static final long SOAP_ENDPOINT_NUMBER_OF_HITS = 3
	private static final String UNKNOWN_ENDPOINT_NAME = 'UNKNOWN_ENDPOINT_NAME'

	private EndpointHitRepository endpointHitRepositoryMock

	private EntityMatadataProvider entityMatadataProviderMock

	private EndpointHitsReader endpointHitsReader

	void setup() {
		endpointHitRepositoryMock = Mock()
		entityMatadataProviderMock = Mock()
		endpointHitsReader = new EndpointHitsReader(endpointHitRepositoryMock, entityMatadataProviderMock)
	}

	void "reads hits when refreshed"() {
		given:
		EndpointHit restEndpointHit = new EndpointHit(endpointName: 'AstronomicalObjectRestEndpoint', numberOfHits: REST_ENDPOINT_NUMBER_OF_HITS)
		EndpointHit soapEndpointHit = new EndpointHit(endpointName: 'BookSoapEndpoint', numberOfHits: SOAP_ENDPOINT_NUMBER_OF_HITS)
		Map<String, Class> classSimpleNameToClassMap = Maps.newHashMap()
		classSimpleNameToClassMap.put('AstronomicalObject', AstronomicalObject)
		classSimpleNameToClassMap.put('Book', Book)

		when: 'no refresh has been called'
		null

		then:
		endpointHitsReader.readAllHitsCount() == 0
		endpointHitsReader.readEndpointHits() == Maps.newHashMap()

		when: 'refresh is called'
		endpointHitsReader.refresh()

		then: 'dependencies are interacted with'
		1 * endpointHitRepositoryMock.findAll() >> Lists.newArrayList(restEndpointHit, soapEndpointHit)
		1 * entityMatadataProviderMock.provideClassSimpleNameToClassMap() >> classSimpleNameToClassMap

		then: 'correct total hit count is provided'
		endpointHitsReader.readAllHitsCount() == REST_ENDPOINT_NUMBER_OF_HITS + SOAP_ENDPOINT_NUMBER_OF_HITS

		when: 'endpoint hits are requested'
		Map<Class<? extends PageAware>, Long> endpointHits = endpointHitsReader.readEndpointHits()

		then: 'correct hit counter for endpoints is provided'
		endpointHits.size() == 2
		endpointHits.get(AstronomicalObject) == REST_ENDPOINT_NUMBER_OF_HITS
		endpointHits.get(Book) == SOAP_ENDPOINT_NUMBER_OF_HITS
	}

	void "throws on endpoint name that cannot be parsed"() {
		given:
		EndpointHit restEndpointHit = new EndpointHit(endpointName: UNKNOWN_ENDPOINT_NAME)

		when: 'refresh is called'
		endpointHitsReader.refresh()

		then: 'dependencies are interacted with'
		1 * endpointHitRepositoryMock.findAll() >> Lists.newArrayList(restEndpointHit)
		1 * entityMatadataProviderMock.provideClassSimpleNameToClassMap() >> Maps.newHashMap()

		then: 'exception is thrown'
		RuntimeException runtimeException = thrown(RuntimeException)
		runtimeException.message == "Cannot map endpoint with name \"${UNKNOWN_ENDPOINT_NAME}\" to entity class"

		then: 'correct hit counter for endpoints is provided'
		endpointHitsReader.readAllHitsCount() == 0
		endpointHitsReader.readEndpointHits().size() == 0
	}

}
