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

	private Page page

	void setup() {
		pageToGenderPronounProcessorMock = Mock()
		pageToGenderRoleProcessorMock = Mock()
		genderFixedValueProvider = Mock()
		pageToGenderNameProcessorMock = Mock()
		pageToGenderProcessor = new PageToGenderProcessor(pageToGenderPronounProcessorMock, pageToGenderRoleProcessorMock, genderFixedValueProvider,
				pageToGenderNameProcessorMock)
		page = Mock()
		page.title >> TITLE
	}

	void "gets gender from PageToGenderSupplementaryProcessor when it is found"() {
		when:
		Gender gender = pageToGenderProcessor.process(page)

		then:
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.of(true, GENDER)
		gender == GENDER
	}

	void "skips PageToGenderPronounProcessor and PageToGenderRoleProcessor when wikitext is null"() {
		when:
		pageToGenderProcessor.process(page)

		then:
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		0 * pageToGenderPronounProcessorMock._
		0 * pageToGenderRoleProcessorMock._
		1 * pageToGenderNameProcessorMock._
	}

	void "gets gender from PageToGenderPronounProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(page)

		then:
		1 * page.wikitext >> ''
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * pageToGenderPronounProcessorMock.process(page) >> GENDER
		gender == GENDER
	}

	void "gets gender from PageToGenderRoleProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(page)

		then:
		1 * page.wikitext >> ''
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * pageToGenderPronounProcessorMock.process(page) >> null
		1 * pageToGenderRoleProcessorMock.process(page) >> GENDER
		gender == GENDER
	}

	void "gets gender from PageToGenderNameProcessor when it is not null"() {
		when:
		Gender gender = pageToGenderProcessor.process(page)

		then:
		1 * page.wikitext >> ''
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * pageToGenderPronounProcessorMock.process(page) >> null
		1 * pageToGenderRoleProcessorMock.process(page) >> null
		1 * pageToGenderNameProcessorMock.process(page) >> GENDER
		gender == GENDER
	}

	void "returns null when all processor returned null"() {
		when:
		Gender gender = pageToGenderProcessor.process(page)

		then:
		1 * page.wikitext >> ''
		1 * genderFixedValueProvider.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * pageToGenderPronounProcessorMock.process(page) >> null
		1 * pageToGenderRoleProcessorMock.process(page) >> null
		1 * pageToGenderNameProcessorMock.process(page) >> null
		gender == null
	}
}
