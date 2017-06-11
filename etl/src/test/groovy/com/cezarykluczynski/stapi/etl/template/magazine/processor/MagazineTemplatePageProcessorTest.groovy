package com.cezarykluczynski.stapi.etl.template.magazine.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private MagazineTemplatePartsEnrichingProcessor magazineTemplatePartsEnrichingProcessorMock

	private MagazineTemplatePageProcessor magazineTemplatePageProcessor

	void setup() {
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		magazineTemplatePartsEnrichingProcessorMock = Mock()
		magazineTemplatePageProcessor = new MagazineTemplatePageProcessor(templateFinderMock, pageBindingServiceMock,
				magazineTemplatePartsEnrichingProcessorMock)
	}

	void "returns null when page is a product of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		MagazineTemplate magazineTemplate = magazineTemplatePageProcessor.process(page)

		then:
		0 * _
		magazineTemplate == null
	}

	void "returns null when sidebar magazine series template is not found"() {
		given:
		Page page = new Page(title: TITLE)
		Template sidebarMagazineSeriesTemplate = Mock()

		when:
		MagazineTemplate comicsTemplate = magazineTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.of(sidebarMagazineSeriesTemplate)
		0 * _
		comicsTemplate == null
	}

	void "parses page that does not have sidebar magazine template"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		MagazineTemplate comicsTemplate = magazineTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE) >> Optional.empty()
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
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE) >> Optional.of(sidebarMagazineTemplate)
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
