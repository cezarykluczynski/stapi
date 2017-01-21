package com.cezarykluczynski.stapi.sources.genderize.client

import com.cezarykluczynski.stapi.sources.genderize.dto.NameGenderDTO
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.matchers.MatchType
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.JsonBody
import spock.lang.Shared
import spock.lang.Specification

class GenderizeClientConnectableImplTest extends Specification {

	private static final String NAME = 'John'
	private static final String NAME_PRODUCING_404 = 'Mark'
	private static final String NAME_PRODUCING_NULL_GENDER = 'Tom'
	private static final String URL = 'http://localhost:9761/'

	private GenderizeClientConnectableImpl genderizeClientConnectableImpl

	@Shared
	private ClientAndServer mockServer

	private MockServerClient mockServerClient

	void setupSpec() {
		mockServer = ClientAndServer.startClientAndServer(9761)
	}

	void cleanupSpec() {
		mockServer.stop()
	}

	void setup() {
		genderizeClientConnectableImpl = new GenderizeClientConnectableImpl(URL)
		mockServerClient = new MockServerClient('localhost', 9761)
	}

	void "returns NameGender when response was valid, then returns cache response"() {
		given:
		mockServerClient
				.when(HttpRequest.request().withMethod('GET').withQueryStringParameter('name', NAME))
				.respond(HttpResponse.response().withStatusCode(200).withBody(
				JsonBody.json('{"name":"' + NAME + '","gender":"male","probability":0.98,"count":1000}', MatchType.STRICT)))

		when:
		NameGenderDTO nameGender = genderizeClientConnectableImpl.getNameGender(NAME)

		then:
		nameGender.name == NAME
		nameGender.gender == 'male'

		when: 'another call is performed'
		NameGenderDTO nameGenderCached = genderizeClientConnectableImpl.getNameGender(NAME)

		then: 'the same object is returned'
		nameGenderCached == nameGender
	}

	void "gets null when gender was null in response"() {
		given:
		mockServerClient
				.when(HttpRequest.request().withMethod('GET').withQueryStringParameter('name', NAME_PRODUCING_NULL_GENDER))
				.respond(HttpResponse.response().withStatusCode(202).withBody(
				JsonBody.json('{"name":"' + NAME_PRODUCING_NULL_GENDER + '","gender":null}', MatchType.STRICT)))

		when:
		NameGenderDTO nameGender = genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_NULL_GENDER)

		then:
		nameGender == null
	}

	void "gets null when probability was null in response"() {
		given:
		mockServerClient
				.when(HttpRequest.request().withMethod('GET').withQueryStringParameter('name', NAME_PRODUCING_NULL_GENDER))
				.respond(HttpResponse.response().withStatusCode(202).withBody(
				JsonBody.json('{"name":"' + NAME_PRODUCING_NULL_GENDER + '","gender":"male", "probability": null}', MatchType.STRICT)))

		when:
		NameGenderDTO nameGender = genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_NULL_GENDER)

		then:
		nameGender == null
	}

	void "gets null when API was not there"() {
		given:
		mockServerClient
				.when(HttpRequest.request().withMethod('GET').withQueryStringParameter('name', NAME_PRODUCING_404))
				.respond(HttpResponse.response().withStatusCode(404))

		when:
		NameGenderDTO nameGender = genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_404)

		then:
		nameGender == null
	}

	void "another call to API is postponed"() {
		given:
		long startInMilliseconds = System.currentTimeMillis()

		when:
		genderizeClientConnectableImpl.getNameGender(NAME)
		genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_NULL_GENDER)
		long endInMillis = System.currentTimeMillis()

		then:
		startInMilliseconds + genderizeClientConnectableImpl.minimalInterval <= endInMillis
	}

}
