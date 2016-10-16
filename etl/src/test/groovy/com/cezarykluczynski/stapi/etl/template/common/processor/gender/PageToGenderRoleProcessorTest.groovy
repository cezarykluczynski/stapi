package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePageProcessor
import com.cezarykluczynski.stapi.wiki.api.PageApi
import com.cezarykluczynski.stapi.wiki.api.WikitextApi
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification


class PageToGenderRoleProcessorTest extends Specification {

	private static final String VALID_WIKITEXT = 'played frisbee for fun'
	private static final Gender GENDER = Gender.F

	private PageApi pageApiMock

	private WikitextApi wikitextApiMock

	private IndividualTemplatePageProcessor individualTemplatePageProcessorMock

	private PageToGenderRoleProcessor pageToGenderRoleProcessor

	def setup() {
		pageApiMock = Mock(PageApi)
		wikitextApiMock = Mock(WikitextApi)
		individualTemplatePageProcessorMock = Mock(IndividualTemplatePageProcessor)
		pageToGenderRoleProcessor = new PageToGenderRoleProcessor(pageApiMock, wikitextApiMock,
				individualTemplatePageProcessorMock)
	}

	def "returns null when wikitext does not contain 'played' word"() {
		when:
		Gender gender = pageToGenderRoleProcessor.process(new Page())

		then:
		gender == null
	}

	def "returns null when wikitext does not contain any roles"() {
		when:
		Gender gender = pageToGenderRoleProcessor.process(new Page(
				wikitext: VALID_WIKITEXT))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(VALID_WIKITEXT) >> Lists.newArrayList()
		gender == null
	}

	def "returns null when there were no pages found"() {
		given:
		List<String> titleList = Lists.newArrayList("Title")

		when:
		Gender gender = pageToGenderRoleProcessor.process(new Page(
				wikitext: VALID_WIKITEXT))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(VALID_WIKITEXT) >> titleList
		1 * pageApiMock.getPages(titleList) >> Lists.newArrayList()
		gender == null
	}

	def "returns null when individual template does not contain gender"() {
		given:
		List<String> titleList = Lists.newArrayList("Title")
		Page pageMock = Mock(Page) {
			getWikitext() >> VALID_WIKITEXT
		}
		Page subpageMock = Mock(Page)

		when:
		Gender gender = pageToGenderRoleProcessor.process(pageMock)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(VALID_WIKITEXT) >> titleList
		1 * pageApiMock.getPages(titleList) >> Lists.newArrayList(subpageMock)
		1 * individualTemplatePageProcessorMock.process(subpageMock) >> new IndividualTemplate()
		gender == null

		then: 'titles are used for logging'
		2 * pageMock.getTitle()
		2 * subpageMock.getTitle()
	}

	def "returns gender when individual template does contain it"() {
		given:
		List<String> titleList = Lists.newArrayList("Title")
		Page pageMock = Mock(Page) {
			getWikitext() >> VALID_WIKITEXT
		}
		Page subpageMock = Mock(Page)

		when:
		Gender gender = pageToGenderRoleProcessor.process(pageMock)

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(VALID_WIKITEXT) >> titleList
		1 * pageApiMock.getPages(titleList) >> Lists.newArrayList(subpageMock)
		1 * individualTemplatePageProcessorMock.process(subpageMock) >> new IndividualTemplate(gender: GENDER)
		gender == GENDER

		then: 'titles are used for logging'
		1 * pageMock.getTitle()
		1 * subpageMock.getTitle()
	}

}
