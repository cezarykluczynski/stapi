package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class PageToGenderPronounProcessorTest extends Specification {

	private static final Gender FEMALE = Gender.F
	private static final Gender MALE = Gender.M

	private ParagraphExtractor paragraphExtractorMock

	private PageToGenderPronounProcessor pageToGenderPronounProcessor

	void setup() {
		paragraphExtractorMock = Mock()
		pageToGenderPronounProcessor = new PageToGenderPronounProcessor(paragraphExtractorMock)
	}

	void "returns F is there is more female than male pronouns"() {
		when:
		Gender gender = pageToGenderPronounProcessor.process(new Page(
				wikitext: 'Is she a real she or is she a he?'))

		then:
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		gender == FEMALE
	}

	void "returns F is there is more female than male pronouns, but logs the fact that difference was not too big"() {
		given:
		Page page = Mock()
		page.wikitext >> 'Is she a real she or is she a he - and how about him then?'

		when:
		Gender gender = pageToGenderPronounProcessor.process(page)

		then:
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		gender == FEMALE

		then: 'title is used for logging'
		1 * page.title
	}

	void "returns M is there is more male than female pronouns"() {
		when:
		Gender gender = pageToGenderPronounProcessor.process(new Page(
				wikitext: 'Is he a real he or is he a she?'))

		then:
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		gender == MALE
	}

	void "returns M is there is more male than female pronouns, but logs the fact that difference was not too big"() {
		given:
		Page page = Mock()
		page.wikitext >> 'Is he a real he or is he a she - and how about her then?'

		when:
		Gender gender = pageToGenderPronounProcessor.process(page)

		then:
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		gender == MALE

		then: 'title is used for logging'
		1 * page.title
	}

	void "returns null when there was equal number of findings"() {
		given:
		Page page = Mock()
		page.wikitext >> 'Is her problem his problem?'

		when:
		Gender gender = pageToGenderPronounProcessor.process(page)

		then:
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		gender == null

		then: 'title is used for logging'
		1 * page.title
	}

	void "returns null when there was no findinds"() {
		when:
		Gender gender = pageToGenderPronounProcessor.process(new Page(wikitext: ''))

		then:
		1 * paragraphExtractorMock.extractParagraphs(_) >> { String wikitext -> Lists.newArrayList(wikitext) }
		gender == null
	}

}
