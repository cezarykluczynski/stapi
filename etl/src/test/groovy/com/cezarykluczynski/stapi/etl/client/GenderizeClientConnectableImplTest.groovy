package com.cezarykluczynski.stapi.etl.client

import com.cezarykluczynski.stapi.etl.genderize.client.GenderizeClientConnectableImpl
import com.cezarykluczynski.stapi.etl.genderize.dto.NameGenderDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class GenderizeClientConnectableImplTest extends Specification {

	private static final String NAME = 'John'
	private static final String GENDER = 'male'
	private static final String NAME_PRODUCING_404 = 'Mark'
	private static final String NAME_PRODUCING_NULL_GENDER = 'Tom'
	private static final String URL = 'https://mock.genderize.io/'

	private RestTemplate restTemplateMock

	private GenderizeClientConnectableImpl genderizeClientConnectableImpl

	void setup() {
		restTemplateMock = Mock()
		genderizeClientConnectableImpl = new GenderizeClientConnectableImpl(URL, restTemplateMock)
	}

	void "returns NameGender when response was valid, then returns cache response"() {
		when:
		NameGenderDTO nameGender = genderizeClientConnectableImpl.getNameGender(NAME)

		then:
		1 * restTemplateMock.getForEntity("$URL?name=$NAME", NameGenderDTO) >> ResponseEntity
				.ok(new NameGenderDTO(name: NAME, gender: GENDER, probability: 1.0))
		0 * _
		nameGender.name == NAME
		nameGender.gender == 'male'

		when: 'another call is performed'
		NameGenderDTO nameGenderCached = genderizeClientConnectableImpl.getNameGender(NAME)

		then: 'the same object is returned'
		0 * _
		nameGenderCached == nameGender
	}

	void "gets null when gender was null in response"() {
		when:
		NameGenderDTO nameGender = genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_NULL_GENDER)

		then:
		1 * restTemplateMock.getForEntity("$URL?name=$NAME_PRODUCING_NULL_GENDER", NameGenderDTO) >> ResponseEntity
				.ok(new NameGenderDTO(name: NAME_PRODUCING_NULL_GENDER, gender: null, probability: 1.0))
		0 * _
		nameGender == null

		when:
		nameGender = genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_NULL_GENDER)

		then:
		0 * _
		nameGender == null
	}

	void "gets null when probability was null in response"() {
		when:
		NameGenderDTO nameGender = genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_NULL_GENDER)

		then:
		1 * restTemplateMock.getForEntity("$URL?name=$NAME_PRODUCING_NULL_GENDER", NameGenderDTO) >> ResponseEntity
				.ok(new NameGenderDTO(name: NAME_PRODUCING_NULL_GENDER, gender: GENDER, probability: null))
		0 * _
		nameGender == null

		when:
		nameGender = genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_NULL_GENDER)

		then:
		0 * _
		nameGender == null
	}

	void "gets null when API was not there"() {
		when:
		NameGenderDTO nameGender = genderizeClientConnectableImpl.getNameGender(NAME_PRODUCING_404)

		then:
		1 * restTemplateMock.getForEntity("$URL?name=$NAME_PRODUCING_404", NameGenderDTO) >> ResponseEntity.notFound()
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
		startInMilliseconds + genderizeClientConnectableImpl.MINIMAL_INTERVAL <= endInMillis
	}

}
