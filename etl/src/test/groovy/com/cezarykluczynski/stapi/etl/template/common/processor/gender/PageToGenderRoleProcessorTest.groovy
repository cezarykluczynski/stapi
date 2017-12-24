package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePartsGenderProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class PageToGenderRoleProcessorTest extends Specification {

	private static final String TITLE = 'Title'
	private static final String VALID_WIKITEXT = 'played frisbee for fun'
	private static final Gender GENDER = Gender.F

	private PageApi pageApiMock

	private WikitextApi wikitextApiMock

	private TemplateFinder templateFinderMock

	private IndividualTemplatePartsGenderProcessor individualTemplatePartsGenderProcessorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private PageToGenderRoleProcessor pageToGenderRoleProcessor

	void setup() {
		pageApiMock = Mock()
		wikitextApiMock = Mock()
		templateFinderMock = Mock()
		individualTemplatePartsGenderProcessorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		pageToGenderRoleProcessor = new PageToGenderRoleProcessor(pageApiMock, wikitextApiMock, templateFinderMock,
				individualTemplatePartsGenderProcessorMock, categoryTitlesExtractingProcessorMock)
	}

	void "returns null when wikitext does not contain 'played' word"() {
		when:
		Gender gender = pageToGenderRoleProcessor.process(new Page())

		then:
		0 * _
		gender == null
	}

	void "logs that no roles were found when page is a performer page"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: CategoryTitles.PERFORMERS.get(0)))
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)

		when:
		Gender gender = pageToGenderRoleProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitles.PERFORMERS.get(0))
		0 * _
		gender == null
	}

	void "does not log that no roles were found when page is not a performer page"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: CategoryTitles.STAFF.get(0)))
		Page page = new Page(categories: categoryHeaderList)

		when:
		Gender gender = pageToGenderRoleProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitles.STAFF.get(0))
		0 * _
		gender == null
	}

	void "returns null when wikitext does not contain any roles"() {
		when:
		Gender gender = pageToGenderRoleProcessor.process(new Page(
				wikitext: VALID_WIKITEXT))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(VALID_WIKITEXT) >> Lists.newArrayList()
		0 * _
		gender == null
	}

	void "returns null when there were no pages found"() {
		given:
		List<String> titleList = Lists.newArrayList(TITLE)

		when:
		Gender gender = pageToGenderRoleProcessor.process(new Page(
				wikitext: VALID_WIKITEXT))

		then:
		1 * wikitextApiMock.getPageTitlesFromWikitext(VALID_WIKITEXT) >> titleList
		1 * pageApiMock.getPages(titleList, MediaWikiSource.MEMORY_ALPHA_EN) >> Lists.newArrayList()
		0 * _
		gender == null
	}

	void "returns null when individual template does not contain gender"() {
		given:
		List<String> titleList = Lists.newArrayList(TITLE)
		Page page = Mock()
		Page subpage = Mock()

		when:
		Gender gender = pageToGenderRoleProcessor.process(page)

		then:
		1 * page.wikitext >> VALID_WIKITEXT
		1 * wikitextApiMock.getPageTitlesFromWikitext(VALID_WIKITEXT) >> titleList
		1 * pageApiMock.getPages(titleList, MediaWikiSource.MEMORY_ALPHA_EN) >> Lists.newArrayList(subpage)
		1 * templateFinderMock.findTemplate(subpage, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		0 * _
		gender == null

		then: 'titles are used for logging'
		2 * page.title
		2 * subpage.title
	}

	void "returns gender when individual template does contain it"() {
		given:
		List<String> titleList = Lists.newArrayList(TITLE)
		Page page = Mock()
		Page subpage = Mock()
		Template.Part templatePart = Mock()
		Template sidebarIndividualTemplate = new Template(parts: Lists.newArrayList(templatePart))

		when:
		Gender gender = pageToGenderRoleProcessor.process(page)

		then:
		1 * page.wikitext >> VALID_WIKITEXT
		1 * wikitextApiMock.getPageTitlesFromWikitext(VALID_WIKITEXT) >> titleList
		1 * pageApiMock.getPages(titleList, MediaWikiSource.MEMORY_ALPHA_EN) >> Lists.newArrayList(subpage)
		1 * templateFinderMock.findTemplate(subpage, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.of(sidebarIndividualTemplate)
		1 * individualTemplatePartsGenderProcessorMock.process(Lists.newArrayList(templatePart)) >> GENDER
		0 * _
		gender == GENDER

		then: 'titles are used for logging'
		1 * page.title
		1 * subpage.title
	}

}
