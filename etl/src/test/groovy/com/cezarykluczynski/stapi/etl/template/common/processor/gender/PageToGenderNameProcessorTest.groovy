package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.FullNameToFirstNameProcessor
import com.cezarykluczynski.stapi.etl.vendor.genderize.client.GenderizeClient
import com.cezarykluczynski.stapi.etl.vendor.genderize.dto.NameGender
import com.cezarykluczynski.stapi.wiki.dto.Page
import spock.lang.Specification

class PageToGenderNameProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String FIRST_NAME = 'FIRST_NAME'
	private static final Gender GENDER = Gender.F

	private FullNameToFirstNameProcessor fullNameToFirstNameProcessor

	private GenderizeClient genderizeClientMock

	private PageToGenderNameProcessor pageToGenderNameProcessor

	def setup() {
		fullNameToFirstNameProcessor = Mock(FullNameToFirstNameProcessor)
		genderizeClientMock = Mock(GenderizeClient)
		pageToGenderNameProcessor = new PageToGenderNameProcessor(fullNameToFirstNameProcessor, genderizeClientMock)
	}

	def "returns null when genderize client returns null"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		Gender gender = pageToGenderNameProcessor.process(page)

		then:
		1 * fullNameToFirstNameProcessor.process(TITLE) >> FIRST_NAME
		1 * genderizeClientMock.getNameGender(FIRST_NAME) >> null
		gender == null
	}

	def "returns null when genderize client returns entity with null gender"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		Gender gender = pageToGenderNameProcessor.process(page)

		then:
		1 * fullNameToFirstNameProcessor.process(TITLE) >> FIRST_NAME
		1 * genderizeClientMock.getNameGender(FIRST_NAME) >> new NameGender()
		gender == null
	}

	def "returns gender when probability is higher than required"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		Gender gender = pageToGenderNameProcessor.process(page)

		then:
		1 * fullNameToFirstNameProcessor.process(TITLE) >> FIRST_NAME
		1 * genderizeClientMock.getNameGender(FIRST_NAME) >> new NameGender(
				probability: PageToGenderNameProcessor.MINIMAL_PROBABILITY + 0.01,
				gender: GENDER)
		gender == GENDER
	}

	def "returns null when probability is lower than required"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		Gender gender = pageToGenderNameProcessor.process(page)

		then:
		1 * fullNameToFirstNameProcessor.process(TITLE) >> FIRST_NAME
		1 * genderizeClientMock.getNameGender(FIRST_NAME) >> new NameGender(
				probability: PageToGenderNameProcessor.MINIMAL_PROBABILITY - 0.01,
				gender: GENDER)
		gender == null
	}

}
