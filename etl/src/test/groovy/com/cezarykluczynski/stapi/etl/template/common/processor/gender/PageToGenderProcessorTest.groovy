package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class PageToGenderProcessorTest extends Specification {

	private static final Gender GENDER = Gender.F

	private PageToGenderPronounProcessor pageToGenderPronounProcessorMock

	private PageToGenderRoleProcessor pageToGenderRoleProcessorMock

	private PageToGenderSupplementaryProcessor pageToGenderSupplementaryProcessorMock

	private PageToGenderNameProcessor pageToGenderNameProcessorMock

	private PageToGenderProcessor pageToGenderProcessor

	private Page pageMock

	def setup() {
		pageToGenderPronounProcessorMock = Mock(PageToGenderPronounProcessor)
		pageToGenderRoleProcessorMock = Mock(PageToGenderRoleProcessor)
		pageToGenderSupplementaryProcessorMock = Mock(PageToGenderSupplementaryProcessor)
		pageToGenderNameProcessorMock = Mock(PageToGenderNameProcessor)
		pageToGenderProcessor = new PageToGenderProcessor(pageToGenderPronounProcessorMock,
				pageToGenderRoleProcessorMock, pageToGenderSupplementaryProcessorMock, pageToGenderNameProcessorMock)
		pageMock = Mock(Page)
	}

	def "gets gender from PageToGenderSupplementaryProcessor when it is found"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * pageToGenderSupplementaryProcessorMock.process(pageMock) >> new PageToGenderSupplementaryProcessor.Finding(
				gender: GENDER,
				found: true)
		gender == GENDER
	}

	def "skips PageToGenderPronounProcessor and PageToGenderRoleProcessor when wikitext is null"() {
		when:
		pageToGenderProcessor.process(pageMock)

		then:
		1 * pageToGenderSupplementaryProcessorMock.process(pageMock) >> new PageToGenderSupplementaryProcessor.Finding()
		0 * pageToGenderPronounProcessorMock._
		0 * pageToGenderRoleProcessorMock._
		1 * pageToGenderNameProcessorMock._
	}

	def "gets gender from PageToGenderPronounProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * pageMock.getWikitext() >> ""
		1 * pageToGenderSupplementaryProcessorMock.process(pageMock) >> new PageToGenderSupplementaryProcessor.Finding()
		1 * pageToGenderPronounProcessorMock.process(pageMock) >> GENDER
		gender == GENDER
	}

	def "gets gender from PageToGenderRoleProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * pageMock.getWikitext() >> ""
		1 * pageToGenderSupplementaryProcessorMock.process(pageMock) >> new PageToGenderSupplementaryProcessor.Finding()
		1 * pageToGenderPronounProcessorMock.process(pageMock) >> null
		1 * pageToGenderRoleProcessorMock.process(pageMock) >> GENDER
		gender == GENDER
	}

	def "gets gender from PageToGenderNameProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * pageMock.getWikitext() >> ""
		1 * pageToGenderSupplementaryProcessorMock.process(pageMock) >> new PageToGenderSupplementaryProcessor.Finding()
		1 * pageToGenderPronounProcessorMock.process(pageMock) >> null
		1 * pageToGenderRoleProcessorMock.process(pageMock) >> null
		1 * pageToGenderNameProcessorMock.process(pageMock) >> GENDER
		gender == GENDER
	}

	def "returns null when all processor returned null"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * pageMock.getWikitext() >> ""
		1 * pageToGenderSupplementaryProcessorMock.process(pageMock) >> new PageToGenderSupplementaryProcessor.Finding()
		1 * pageToGenderPronounProcessorMock.process(pageMock) >> null
		1 * pageToGenderRoleProcessorMock.process(pageMock) >> null
		1 * pageToGenderNameProcessorMock.process(pageMock) >> null
		gender == null
	}
}
