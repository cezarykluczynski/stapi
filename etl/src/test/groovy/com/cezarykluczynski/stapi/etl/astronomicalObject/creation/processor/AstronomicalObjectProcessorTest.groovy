package com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.processor.PlanetTemplatePageProcessor
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class AstronomicalObjectProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private PlanetTemplatePageProcessor planetTemplatePageProcessorMock

	private PlanetTemplateProcessor planetTemplateProcessorMock

	private AstronomicalObjectProcessor astronomicalObjectProcessor

	void setup() {
		pageHeaderProcessorMock = Mock(PageHeaderProcessor)
		planetTemplatePageProcessorMock = Mock(PlanetTemplatePageProcessor)
		planetTemplateProcessorMock = Mock(PlanetTemplateProcessor)
		astronomicalObjectProcessor = new AstronomicalObjectProcessor(pageHeaderProcessorMock, planetTemplatePageProcessorMock,
				planetTemplateProcessorMock)
	}

	void "converts PageHeader to AstronomicalObject"() {
		given:
		PageHeader pageHeader = PageHeader.builder().build()
		Page page = new Page()
		PlanetTemplate planetTemplate = new PlanetTemplate()
		AstronomicalObject astronomicalObject = new AstronomicalObject()

		when:
		AstronomicalObject outputAstronomicalObject = astronomicalObjectProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page
		1 * planetTemplatePageProcessorMock.process(page) >> planetTemplate
		1 * planetTemplateProcessorMock.process(planetTemplate) >> astronomicalObject

		then: 'last processor output is returned'
		outputAstronomicalObject == astronomicalObject
	}

}
