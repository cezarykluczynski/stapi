package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.wiki.dto.Page
import spock.lang.Specification

class PageToGenderPronounProcessorTest extends Specification {

	private static final Gender FEMALE = Gender.F
	private static final Gender MALE = Gender.M

	PageToGenderPronounProcessor pageToGenderPronounProcessor

	def setup() {
		pageToGenderPronounProcessor = new PageToGenderPronounProcessor()
	}

	def "returns F is there is more female than male pronouns"() {
		when:
		Gender gender = pageToGenderPronounProcessor.process(new Page(
				wikitext: 'Is she a real she or is she a he?'))

		then:
		gender == FEMALE
	}

	def "returns F is there is more female than male pronouns, but logs the fact that difference was not too big"() {
		given:
		Page pageMock = Mock(Page) {
			getWikitext() >> 'Is she a real she or is she a he - and how about him then?'
		}

		when:
		Gender gender = pageToGenderPronounProcessor.process(pageMock)

		then:
		gender == FEMALE

		then: 'title is used for logging'
		1 * pageMock.getTitle()
	}

	def "returns M is there is more male than female pronouns"() {
		when:
		Gender gender = pageToGenderPronounProcessor.process(new Page(
				wikitext: 'Is he a real he or is he a she?'))

		then:
		gender == MALE
	}

	def "returns M is there is more male than female pronouns, but logs the fact that difference was not too big"() {
		given:
		Page pageMock = Mock(Page) {
			getWikitext() >> 'Is he a real he or is he a she - and how about her then?'
		}

		when:
		Gender gender = pageToGenderPronounProcessor.process(pageMock)

		then:
		gender == MALE

		then: 'title is used for logging'
		1 * pageMock.getTitle()
	}

	def "returns null when there was equal number of findings"() {
		given:
		Page pageMock = Mock(Page) {
			getWikitext() >> 'Is her problem his problem?'
		}

		when:
		Gender gender = pageToGenderPronounProcessor.process(pageMock)

		then:
		gender == null

		then: 'title is used for logging'
		1 * pageMock.getTitle()
	}

	def "returns null when there was no findinds"() {
		when:
		Gender gender = pageToGenderPronounProcessor.process(new Page(wikitext: ''))

		then:
		gender == null
	}

}
