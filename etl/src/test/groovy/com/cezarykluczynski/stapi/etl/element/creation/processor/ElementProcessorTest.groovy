package com.cezarykluczynski.stapi.etl.element.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class ElementProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ElementPageProcessor elementPageProcessorMock

	private ElementProcessor elementProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		elementPageProcessorMock = Mock()
		elementProcessor = new ElementProcessor(pageHeaderProcessorMock, elementPageProcessorMock)
	}

	void "converts PageHeader to Element"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Element element = new Element()

		when:
		Element elementOutput = elementProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * elementPageProcessorMock.process(page) >> element

		then: 'last processor output is returned'
		elementOutput == element
	}

}
