package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.characterbox.processor.CharacterboxIndividualTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.individual.service.IndividualTemplateFilter
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class IndividualTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Long PAGE_ID = 11L
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private IndividualTemplateFilter individualTemplateFilterMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private IndividualTemplateCompositeEnrichingProcessor invidiualTemplateCompositeEnrichingProcessorMock

	private CharacterboxIndividualTemplateEnrichingProcessor characterboxIndividualTemplateEnrichingProcessorMock

	private IndividualTemplatePageProcessor individualTemplatePageProcessor

	void setup() {
		individualTemplateFilterMock = Mock(IndividualTemplateFilter)
		pageBindingServiceMock = Mock(PageBindingService)
		templateFinderMock = Mock(TemplateFinder)
		invidiualTemplateCompositeEnrichingProcessorMock = Mock(IndividualTemplateCompositeEnrichingProcessor)
		characterboxIndividualTemplateEnrichingProcessorMock = Mock(CharacterboxIndividualTemplateEnrichingProcessor)
		individualTemplatePageProcessor = new IndividualTemplatePageProcessor(individualTemplateFilterMock,
				pageBindingServiceMock, templateFinderMock, invidiualTemplateCompositeEnrichingProcessorMock,
				characterboxIndividualTemplateEnrichingProcessorMock)
	}

	void "returns null when IndividualTemplateFilter returns true"() {
		given:
		Page page = new Page()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * individualTemplateFilterMock.shouldBeFilteredOut(page) >> true
		individualTemplate == null
	}

	void "missing template results IndividualTemplate with only the name and page"() {
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
		1 * individualTemplateFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		0 * _
		individualTemplate.name == TITLE
		individualTemplate.page == pageEntity
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 4
	}

	void "sets name from page title, and cuts brackets when they are present"() {
		given:
		Page page = new Page(
				title: TITLE + ' (civilian)',
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * individualTemplateFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> new PageEntity()
		individualTemplate.name == TITLE
		ReflectionTestUtils.getNumberOfNotNullFields(individualTemplate) == 4
	}

	void "sets productOfRedirect flag to true"() {
		given:
		Page page = new Page(
				title: TITLE,
				redirectPath: Lists.newArrayList(Mock(PageHeader))
		)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * individualTemplateFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		individualTemplate.productOfRedirect
	}

	void "sets productOfRedirect flag to false"() {
		given:
		Page page = new Page(
				title: TITLE
		)

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * individualTemplateFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		!individualTemplate.productOfRedirect
	}

	void "when sidebar individual is found, enriching processors are called, but not the characterbox processor, when there is no mbeta template"() {
		given:
		List<Template.Part> templatePartList = Lists.newArrayList(Mock(Template.Part))
		Page page = new Page(
				title: TITLE
		)
		Template sidebarIndividualTemplate = new Template(parts: templatePartList)

		when:
		individualTemplatePageProcessor.process(page)

		then:
		1 * individualTemplateFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.of(sidebarIndividualTemplate)
		1 * invidiualTemplateCompositeEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Page, IndividualTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.MBETA) >> Optional.empty()
		0 * _
	}

	void "when sidebar individual is found, enriching processors are called, including characterbox processor, when mbeta template is found"() {
		given:
		List<Template.Part> templatePartList = Lists.newArrayList(Mock(Template.Part))
		Page page = new Page(
				title: TITLE
		)
		Template sidebarIndividualTemplate = new Template(parts: templatePartList)
		Template mbetaTemplate = Mock(Template)

		when:
		individualTemplatePageProcessor.process(page)

		then:
		1 * individualTemplateFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.of(sidebarIndividualTemplate)
		1 * invidiualTemplateCompositeEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Page, IndividualTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.MBETA) >> Optional.of(mbetaTemplate)
		1 * characterboxIndividualTemplateEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Template, IndividualTemplate> enrichablePair ->
			assert enrichablePair.input == mbetaTemplate
			assert enrichablePair.output != null
		}
		0 * _
	}

}
