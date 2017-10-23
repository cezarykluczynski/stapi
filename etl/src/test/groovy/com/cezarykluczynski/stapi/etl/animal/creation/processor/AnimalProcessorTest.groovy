package com.cezarykluczynski.stapi.etl.animal.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.animal.entity.Animal
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class AnimalProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private AnimalPageProcessor animalPageProcessorMock

	private AnimalProcessor animalProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		animalPageProcessorMock = Mock()
		animalProcessor = new AnimalProcessor(pageHeaderProcessorMock, animalPageProcessorMock)
	}

	void "converts PageHeader to Animal"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Animal animal = new Animal()

		when:
		Animal animalOutput = animalProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * animalPageProcessorMock.process(page) >> animal

		then: 'last processor output is returned'
		animalOutput == animal
	}

}
