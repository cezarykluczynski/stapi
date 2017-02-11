package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.comicStrip.creation.service.ComicStripCandidatePageGatheringService
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicsTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private ComicStripCandidatePageGatheringService comicStripCandidatePageGatheringService

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private ComicsTemplateFixedValuesEnrichingProcessor comicsTemplateFixedValuesEnrichingProcessorMock

	private ComicsTemplatePartsEnrichingProcessor comicsTemplatePartsEnrichingProcessorMock

	private ComicsTemplatePageProcessor comicsTemplatePageProcessor

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock(CategoryTitlesExtractingProcessor)
		comicStripCandidatePageGatheringService = Mock(ComicStripCandidatePageGatheringService)
		pageBindingServiceMock = Mock(PageBindingService)
		templateFinderMock = Mock(TemplateFinder)
		comicsTemplateFixedValuesEnrichingProcessorMock = Mock(ComicsTemplateFixedValuesEnrichingProcessor)
		comicsTemplatePartsEnrichingProcessorMock = Mock(ComicsTemplatePartsEnrichingProcessor)
		comicsTemplatePageProcessor = new ComicsTemplatePageProcessor(categoryTitlesExtractingProcessorMock, comicStripCandidatePageGatheringService,
				pageBindingServiceMock, templateFinderMock, comicsTemplateFixedValuesEnrichingProcessorMock,
				comicsTemplatePartsEnrichingProcessorMock)
	}

	void "returns null when page title is among invalid page titles"() {
		given:
		Page page = new Page(title: ComicsTemplatePageProcessor.INVALID_TITLES[0])

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		0 * _
		comicsTemplate == null
	}

	void "returns null when 'Star_Trek_series_magazines' is among page categories"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock(List)
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryName.STAR_TREK_SERIES_MAGAZINES)
		0 * _
		comicsTemplate == null
	}

	void "sets photonovel flag when photonovels category is found"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock(List)
		Page page = new Page(
				title: TITLE,
				categories: categoryHeaderList)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		2 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryName.PHOTONOVELS)
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * comicsTemplateFixedValuesEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC) >> Optional.empty()
		0 * _
		comicsTemplate.photonovel
	}

	void "returns null when sidebar comic strip template is found, and adds page to ComicStripCandidatePageGatheringService"() {
		given:
		Page page = new Page(title: TITLE)
		Template comisStripTemplate = Mock(Template)

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_STRIP) >> Optional.of(comisStripTemplate)
		1 * comicStripCandidatePageGatheringService.addCandidate(page)
		0 * _
		comicsTemplate == null
	}

	void "parses page that does not have sidebar comics template"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		2 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * comicsTemplateFixedValuesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			assert enrichablePair.input != null
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC) >> Optional.empty()
		0 * _
		comicsTemplate.title == TITLE
		comicsTemplate.page == modelPage
		!comicsTemplate.productOfRedirect
	}

	void "parses page with sidebar comics template"() {
		given:
		Page page = new Page(
				title: TITLE,
				redirectPath: Lists.newArrayList(Mock(PageHeader)))
		ModelPage modelPage = new ModelPage()
		Template.Part templatePart = Mock(Template.Part)
		Template template = new Template(parts: Lists.newArrayList(templatePart))

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		2 * categoryTitlesExtractingProcessorMock.process(_) >> Lists.newArrayList()
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_STRIP) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * comicsTemplateFixedValuesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			assert enrichablePair.input != null
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC) >> Optional.of(template)
		1 * comicsTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<Template.Part>, ComicsTemplate> enrichablePair ->
			assert enrichablePair.input[0] == templatePart
			assert enrichablePair.output != null
		}
		0 * _
		comicsTemplate.title == TITLE
		comicsTemplate.page == modelPage
		comicsTemplate.productOfRedirect
	}

}
