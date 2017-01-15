package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.characterbox.processor.CharacterboxIndividualTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.PageName
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class IndividualTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String WIKITEXT = 'WIKITEXT'
	private static final Long PAGE_ID = 11L
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private IndividualDateOfDeathEnrichingProcessor individualDateOfDeathEnrichingProcessorMock

	private WikitextApi wikitextApiMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessorMock

	private CharacterboxIndividualTemplateEnrichingProcessor characterboxIndividualTemplateEnrichingProcessorMock

	private IndividualTemplatePageProcessor individualTemplatePageProcessor

	def setup() {
		individualDateOfDeathEnrichingProcessorMock = Mock(IndividualDateOfDeathEnrichingProcessor)
		wikitextApiMock = Mock(WikitextApi)
		pageBindingServiceMock = Mock(PageBindingService)
		templateFinderMock = Mock(TemplateFinder)
		individualTemplatePartsEnrichingProcessorMock = Mock(IndividualTemplatePartsEnrichingProcessor)
		characterboxIndividualTemplateEnrichingProcessorMock = Mock(CharacterboxIndividualTemplateEnrichingProcessor)
		individualTemplatePageProcessor = new IndividualTemplatePageProcessor(individualDateOfDeathEnrichingProcessorMock,
				wikitextApiMock, pageBindingServiceMock, templateFinderMock, individualTemplatePartsEnrichingProcessorMock,
				characterboxIndividualTemplateEnrichingProcessorMock)
	}

	def "returns null when page name starts with 'Unnamed '"() {
		given:
		Page page = new Page(
				title: 'Unnamed humanoids',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when page name starts with 'List of '"() {
		given:
		Page page = new Page(
				title: 'List of some people',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when page name starts with 'Memory Alpha images '"() {
		given:
		Page page = new Page(
				title: 'Memory Alpha images (Greek gods)',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when page name contains 'personnel'"() {
		given:
		Page page = new Page(
				title: PageName.MEMORY_ALPHA_PERSONNEL,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when category list contains Production lists"() {
		given:
		Page page = new Page(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.PRODUCTION_LISTS)
				),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when category list contains Families"() {
		given:
		Page page = new Page(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.FAMILIES)
				),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when category list contains Personnel lists"() {
		given:
		Page page = new Page(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.PERSONNEL_LISTS)
				),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when category list contains Lists"() {
		given:
		Page page = new Page(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.LISTS)
				),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when category list contains category that start with 'Unnamed'"() {
		given:
		Page page = new Page(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: 'Unnamed Klingons')
				),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "returns null when page is sorted on top of any category"() {
		given:
		Page page = new Page(
				title: TITLE,
				wikitext: WIKITEXT,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(
				new PageLink(
						title: 'Category:Some page'
				),
				new PageLink(
						title: 'category:Some other page',
						description: StringUtils.EMPTY
				),
				new PageLink(
						title: 'category:Yet another page',
						description: 'Page, yet another'
				)
		)
		individualTemplate == null
	}

	def "missing template results IndividualTemplate with only the name and page"() {
		given:
		Page page = new Page(
				title: TITLE,
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())
		PageEntity pageEntity = new PageEntity()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * wikitextApiMock.getPageLinksFromWikitext(null) >> Lists.newArrayList()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		0 * _
		individualTemplate.name == TITLE
		individualTemplate.page == pageEntity
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 4
	}

	def "sets name from page title, and cuts brackets when they are present"() {
		given:
		Page page = new Page(
				title: TITLE + ' (civilian)',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())
		wikitextApiMock.getPageLinksFromWikitext(*_) >> Lists.newArrayList()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		individualTemplate.name == TITLE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 4
	}

	def "sets productOfRedirect flag to true"() {
		given:
		Page page = new Page(
				title: TITLE,
				redirectPath: Lists.newArrayList(Mock(PageHeader))
		)
		wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		individualTemplate.productOfRedirect
	}

	def "sets productOfRedirect flag to false"() {
		given:
		Page page = new Page(
				title: TITLE
		)
		wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		!individualTemplate.productOfRedirect
	}

}
