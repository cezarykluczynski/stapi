package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.etl.template.starship.processor.StarshipTemplatePageProcessor
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class SpacecraftProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private StarshipTemplatePageProcessor starshipTemplatePageProcessorMock

	private StarshipTemplateProcessor starshipTemplateProcessorMock

	private SpacecraftProcessor spacecraftProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		starshipTemplatePageProcessorMock = Mock()
		starshipTemplateProcessorMock = Mock()
		spacecraftProcessor = new SpacecraftProcessor(pageHeaderProcessorMock, starshipTemplatePageProcessorMock, starshipTemplateProcessorMock)
	}

	void "converts PageHeader to Spacecraft"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		StarshipTemplate starshipClassTemplate = new StarshipTemplate()
		Spacecraft spacecraft = new Spacecraft()

		when:
		Spacecraft outputSpacecraft = spacecraftProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * starshipTemplatePageProcessorMock.process(page) >> starshipClassTemplate

		and:
		1 * starshipTemplateProcessorMock.process(starshipClassTemplate) >> spacecraft

		then: 'last processor output is returned'
		outputSpacecraft == spacecraft
	}

}
