package com.cezarykluczynski.stapi.etl.technology.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class TechnologyProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private TechnologyPageProcessor technologyPageProcessorMock

	private TechnologyProcessor technologyProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		technologyPageProcessorMock = Mock()
		technologyProcessor = new TechnologyProcessor(pageHeaderProcessorMock, technologyPageProcessorMock)
	}

	void "converts PageHeader to Technology"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Technology technology = new Technology()

		when:
		Technology technologyOutput = technologyProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * technologyPageProcessorMock.process(page) >> technology

		then: 'last processor output is returned'
		technologyOutput == technology
	}

}
