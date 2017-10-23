package com.cezarykluczynski.stapi.etl.species.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate
import com.cezarykluczynski.stapi.etl.template.species.processor.SpeciesTemplatePageProcessor
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class SpeciesProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private SpeciesTemplatePageProcessor pageProcessorMock

	private SpeciesTemplateProcessor speciesTemplateProcessorMock

	private SpeciesProcessor speciesProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		pageProcessorMock = Mock()
		speciesTemplateProcessorMock = Mock()
		speciesProcessor = new SpeciesProcessor(pageHeaderProcessorMock, pageProcessorMock, speciesTemplateProcessorMock)
	}

	void "converts PageHeader to Species"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		SpeciesTemplate speciesTemplate = new SpeciesTemplate()
		Species species = new Species()

		when:
		Species speciesOutput = speciesProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * pageProcessorMock.process(page) >> speciesTemplate

		and:
		1 * speciesTemplateProcessorMock.process(speciesTemplate) >> species

		then: 'last processor output is returned'
		speciesOutput == species
	}

}
