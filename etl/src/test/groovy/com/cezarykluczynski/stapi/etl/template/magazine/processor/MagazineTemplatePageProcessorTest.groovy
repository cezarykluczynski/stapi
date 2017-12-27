package com.cezarykluczynski.stapi.etl.template.magazine.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.magazine.creation.service.MagazinePageFilter
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private TemplateFinder templateFinderMock

	private MagazinePageFilter magazinePageFilterMock

	private PageBindingService pageBindingServiceMock

	private MagazineTemplatePartsEnrichingProcessor magazineTemplatePartsEnrichingProcessorMock

	private MagazineTemplatePageProcessor magazineTemplatePageProcessor

	void setup() {
		templateFinderMock = Mock()
		magazinePageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		magazineTemplatePartsEnrichingProcessorMock = Mock()
		magazineTemplatePageProcessor = new MagazineTemplatePageProcessor(templateFinderMock, magazinePageFilterMock, pageBindingServiceMock,
				magazineTemplatePartsEnrichingProcessorMock)
	}

	void "returns null when ComicsPageFilter returns true"() {
		given:
		Page page = new Page()

		when:
		MagazineTemplate magazineTemplate = magazineTemplatePageProcessor.process(page)

		then:
		1 * magazinePageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		magazineTemplate == null
	}

	void "parses page that does not have sidebar magazine template"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		MagazineTemplate comicsTemplate = magazineTemplatePageProcessor.process(page)

		then:
		1 * magazinePageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE, TemplateTitle.SIDEBAR_REFERENCE_BOOK) >> Optional.empty()
		0 * _
		comicsTemplate.title == TITLE
		comicsTemplate.page == modelPage
	}

	void "parses page with sidebar magazine template"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()
		Template.Part templatePart = Mock()
		Template sidebarMagazineTemplate = new Template(parts: Lists.newArrayList(templatePart))

		when:
		MagazineTemplate comicsTemplate = magazineTemplatePageProcessor.process(page)

		then:
		1 * magazinePageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE, TemplateTitle.SIDEBAR_REFERENCE_BOOK) >>
				Optional.of(sidebarMagazineTemplate)
		1 * magazineTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<Template.Part>, MagazineTemplate> enrichablePair ->
				assert enrichablePair.input[0] == templatePart
				assert enrichablePair.output != null
		}
		0 * _
		comicsTemplate.title == TITLE
		comicsTemplate.page == modelPage
	}

}
