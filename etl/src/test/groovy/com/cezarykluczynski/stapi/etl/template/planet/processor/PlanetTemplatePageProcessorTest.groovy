package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplateParameter
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as EtlPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class PlanetTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String CLASS = 'CLASS'
	private static final AstronomicalObjectType GALAXY = AstronomicalObjectType.GALAXY
	private static final AstronomicalObjectType NEBULA = AstronomicalObjectType.NEBULA

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessorMock

	private AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessorMock

	private AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessorMock

	private AstronomicalObjectCompositeEnrichingProcessor astronomicalObjectCompositeEnrichingProcessorMock

	private PlanetTemplatePageProcessor planetTemplatePageProcessor

	void setup() {
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		astronomicalObjectTypeProcessorMock = Mock()
		astronomicalObjectTypeEnrichingProcessorMock = Mock()
		astronomicalObjectWikitextProcessorMock = Mock()
		astronomicalObjectCompositeEnrichingProcessorMock = Mock()
		planetTemplatePageProcessor = new PlanetTemplatePageProcessor(templateFinderMock, pageBindingServiceMock, astronomicalObjectTypeProcessorMock,
				astronomicalObjectTypeEnrichingProcessorMock, astronomicalObjectWikitextProcessorMock,
				astronomicalObjectCompositeEnrichingProcessorMock)
	}

	void "filters out unnamed planets"() {
		when:
		PlanetTemplate planetTemplate = planetTemplatePageProcessor.process(new EtlPage(title: 'Unnamed planets'))

		then:
		planetTemplate == null
	}

	void "filters out planet lists"() {
		when:
		PlanetTemplate planetTemplate = planetTemplatePageProcessor.process(new EtlPage(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryTitle.PLANET_LISTS)
				)
		))

		then:
		planetTemplate == null
	}

	void "calls PageBindingService and AstronomicalObjectTypeEnrichingProcessor, then returns when sidebar planet template is not found"() {
		given:
		EtlPage page = new EtlPage(title: TITLE)
		ModelPage modelPage = Mock()

		when:
		PlanetTemplate planetTemplate = planetTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * astronomicalObjectTypeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<EtlPage, PlanetTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_PLANET) >> Optional.empty()
		1 * astronomicalObjectWikitextProcessorMock.process(_) >> null
		0 * _
		planetTemplate.page == modelPage
		!planetTemplate.productOfRedirect
	}

	void "calls all dependencies when page with planet template is passed"() {
		given:
		Template.Part classTemplatePart = new Template.Part(key: PlanetTemplateParameter.CLASS, value: CLASS)
		Template template = new Template(
				parts: Lists.newArrayList(classTemplatePart)
		)
		PageHeader pageHeader = Mock()
		EtlPage page = new EtlPage(
				title: TITLE,
				templates: Lists.newArrayList(template),
				redirectPath: Lists.newArrayList(pageHeader))
		ModelPage modelPage = Mock()

		when:
		PlanetTemplate planetTemplate = planetTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * astronomicalObjectTypeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<EtlPage, PlanetTemplate> enrichablePair ->
			enrichablePair.input == page
			enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_PLANET) >> Optional.of(template)
		1 * astronomicalObjectWikitextProcessorMock.process(_) >> NEBULA
		1 * astronomicalObjectTypeProcessorMock.process(CLASS) >> GALAXY
		1 * astronomicalObjectCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<AstronomicalObjectType, AstronomicalObjectType>, PlanetTemplate> enrichablePair ->
				assert enrichablePair.input.left == null
				assert enrichablePair.input.right == GALAXY
				assert enrichablePair.output != null
		}
		0 * _
		planetTemplate.page == modelPage
		planetTemplate.astronomicalObjectType == NEBULA
		planetTemplate.productOfRedirect
	}

}
