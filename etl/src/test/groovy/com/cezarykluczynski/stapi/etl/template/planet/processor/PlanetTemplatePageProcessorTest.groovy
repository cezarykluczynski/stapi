package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.astronomical_object.creation.service.AstronomicalObjectPageFilter
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplateParameter
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class PlanetTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String CLASS = 'CLASS'
	private static final String TYPE = 'TYPE'
	private static final AstronomicalObjectType GALAXY = AstronomicalObjectType.GALAXY

	private AstronomicalObjectPageFilter astronomicalObjectPageFilterMock

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessorMock

	private AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessorMock

	private AstronomicalObjectBestPickEnrichingProcessor astronomicalObjectBestPickEnrichingProcessorMock

	private PlanetTemplateWikitextEnrichingProcessor planetTemplateWikitextEnrichingProcessorMock

	private PlanetTemplatePageProcessor planetTemplatePageProcessor

	void setup() {
		astronomicalObjectPageFilterMock = Mock()
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		astronomicalObjectTypeProcessorMock = Mock()
		astronomicalObjectTypeEnrichingProcessorMock = Mock()
		astronomicalObjectBestPickEnrichingProcessorMock = Mock()
		planetTemplateWikitextEnrichingProcessorMock = Mock()
		planetTemplatePageProcessor = new PlanetTemplatePageProcessor(astronomicalObjectPageFilterMock, templateFinderMock, pageBindingServiceMock,
				astronomicalObjectTypeProcessorMock,
				astronomicalObjectTypeEnrichingProcessorMock,
				astronomicalObjectBestPickEnrichingProcessorMock, planetTemplateWikitextEnrichingProcessorMock)
	}

	void "returns null when AstronomicalObjectPageFilter returns true"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		PlanetTemplate planetTemplate = planetTemplatePageProcessor.process(page)

		then:
		1 * astronomicalObjectPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		planetTemplate == null
	}

	void "calls PageBindingService and AstronomicalObjectTypeEnrichingProcessor, then returns when sidebar planet template is not found"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = Mock()

		when:
		PlanetTemplate planetTemplate = planetTemplatePageProcessor.process(page)

		then:
		1 * astronomicalObjectPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * astronomicalObjectTypeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<Page, PlanetTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_PLANET) >> Optional.empty()
		1 * planetTemplateWikitextEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Page, PlanetTemplate> enrichablePair ->
				assert enrichablePair.input == page
				assert enrichablePair.output != null
		}
		0 * _
		planetTemplate.page == modelPage
	}

	void "calls all dependencies when page with planet template is passed with Template.Part key class"() {
		given:
		Template.Part classTemplatePart = new Template.Part(key: PlanetTemplateParameter.CLASS, value: CLASS)
		Template template = new Template(
				parts: Lists.newArrayList(classTemplatePart)
		)
		Page page = new Page(
				title: TITLE,
				templates: Lists.newArrayList(template))
		ModelPage modelPage = Mock()

		when:
		PlanetTemplate planetTemplate = planetTemplatePageProcessor.process(page)

		then:
		1 * astronomicalObjectPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * astronomicalObjectTypeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<Page, PlanetTemplate> enrichablePair ->
			enrichablePair.input == page
			enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_PLANET) >> Optional.of(template)
		1 * astronomicalObjectTypeProcessorMock.process(CLASS) >> GALAXY
		1 * astronomicalObjectBestPickEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<AstronomicalObjectType, AstronomicalObjectType>, PlanetTemplate> enrichablePair ->
				assert enrichablePair.input.left == null
				assert enrichablePair.input.right == GALAXY
				assert enrichablePair.output != null
		}
		1 * planetTemplateWikitextEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Page, PlanetTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		0 * _
		planetTemplate.page == modelPage
	}

	void "calls all dependencies when page with planet template is passed with Template.Part key type"() {
		given:
		Template.Part classTemplatePart = new Template.Part(key: PlanetTemplateParameter.TYPE, value: TYPE)
		Template template = new Template(
				parts: Lists.newArrayList(classTemplatePart)
		)
		Page page = new Page(
				title: TITLE,
				templates: Lists.newArrayList(template))
		ModelPage modelPage = Mock()

		when:
		PlanetTemplate planetTemplate = planetTemplatePageProcessor.process(page)

		then:
		1 * astronomicalObjectPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * astronomicalObjectTypeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<Page, PlanetTemplate> enrichablePair ->
			enrichablePair.input == page
			enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_PLANET) >> Optional.of(template)
		1 * astronomicalObjectTypeProcessorMock.process(TYPE) >> GALAXY
		1 * astronomicalObjectBestPickEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<AstronomicalObjectType, AstronomicalObjectType>, PlanetTemplate> enrichablePair ->
				assert enrichablePair.input.left == null
				assert enrichablePair.input.right == GALAXY
				assert enrichablePair.output != null
		}
		1 * planetTemplateWikitextEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Page, PlanetTemplate> enrichablePair ->
				assert enrichablePair.input == page
				assert enrichablePair.output != null
		}
		0 * _
		planetTemplate.page == modelPage
	}

}
