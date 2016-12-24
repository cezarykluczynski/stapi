package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePageProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryNames
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
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

	def "logs that no roles were found when page is a performer page"() {
		given:
		Page page = Mock(Page) {
			getCategories() >> Lists.newArrayList(new CategoryHeader(title: CategoryNames.PERFORMER.get(0)))
		}

		when:
		Gender gender = pageToGenderRoleProcessor.process(page)

		then:
		1 * page.getTitle()
		gender == null
	}

	def "does not log that no roles were found when page is not a performer page"() {
		given:
		Page page = Mock(Page) {
			getCategories() >> Lists.newArrayList(new CategoryHeader(title: CategoryNames.STAFF.get(0)))
		}

		when:
		Gender gender = pageToGenderRoleProcessor.process(page)

		then:
		0 * page.getTitle()
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
		1 * pageApiMock.getPages(titleList, MediaWikiSource.MEMORY_ALPHA_EN) >> Lists.newArrayList()
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
		1 * pageApiMock.getPages(titleList, MediaWikiSource.MEMORY_ALPHA_EN) >> Lists.newArrayList(subpageMock)
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
		1 * pageApiMock.getPages(titleList, MediaWikiSource.MEMORY_ALPHA_EN) >> Lists.newArrayList(subpageMock)
		1 * individualTemplatePageProcessorMock.process(subpageMock) >> new IndividualTemplate(gender: GENDER)
		gender == GENDER

		then: 'titles are used for logging'
		1 * pageMock.getTitle()
		1 * subpageMock.getTitle()
	}

}
