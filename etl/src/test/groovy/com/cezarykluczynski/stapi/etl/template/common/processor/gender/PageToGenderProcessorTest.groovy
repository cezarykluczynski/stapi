package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class PageToGenderProcessorTest extends Specification {

	private static final Gender GENDER = Gender.F
	private static final String TITLE = 'TITLE'

	private PageToGenderPronounProcessor pageToGenderPronounProcessorMock

	private PageToGenderRoleProcessor pageToGenderRoleProcessorMock

	private GenderFixedValueProvider genderFixedValueProvider

	private PageToGenderNameProcessor pageToGenderNameProcessorMock

	private PageToGenderProcessor pageToGenderProcessor

	private Page pageMock

	def setup() {
		pageToGenderPronounProcessorMock = Mock(PageToGenderPronounProcessor)
		pageToGenderRoleProcessorMock = Mock(PageToGenderRoleProcessor)
		genderFixedValueProvider = Mock(GenderFixedValueProvider)
		pageToGenderNameProcessorMock = Mock(PageToGenderNameProcessor)
		pageToGenderProcessor = new PageToGenderProcessor(pageToGenderPronounProcessorMock,
				pageToGenderRoleProcessorMock, genderFixedValueProvider, pageToGenderNameProcessorMock)
		pageMock = Mock(Page) {
			getTitle() >> TITLE
		}
	}

	def "gets gender from PageToGenderSupplementaryProcessor when it is found"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.of(true, GENDER)
		gender == GENDER
	}

	def "skips PageToGenderPronounProcessor and PageToGenderRoleProcessor when wikitext is null"() {
		when:
		pageToGenderProcessor.process(pageMock)

		then:
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		0 * pageToGenderPronounProcessorMock._
		0 * pageToGenderRoleProcessorMock._
		1 * pageToGenderNameProcessorMock._
	}

	def "gets gender from PageToGenderPronounProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * pageMock.getWikitext() >> ""
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * pageToGenderPronounProcessorMock.process(pageMock) >> GENDER
		gender == GENDER
	}

	def "gets gender from PageToGenderRoleProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * pageMock.getWikitext() >> ""
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * pageToGenderPronounProcessorMock.process(pageMock) >> null
		1 * pageToGenderRoleProcessorMock.process(pageMock) >> GENDER
		gender == GENDER
	}

	def "gets gender from PageToGenderNameProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(pageMock)

		then:
		1 * pageMock.getWikitext() >> ""
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
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
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * pageToGenderPronounProcessorMock.process(pageMock) >> null
		1 * pageToGenderRoleProcessorMock.process(pageMock) >> null
		1 * pageToGenderNameProcessorMock.process(pageMock) >> null
		gender == null
	}
}
