package com.cezarykluczynski.stapi.etl.occupation.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class OccupationProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private OccupationPageProcessor occupationPageProcessorMock

	private OccupationProcessor occupationProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		occupationPageProcessorMock = Mock()
		occupationProcessor = new OccupationProcessor(pageHeaderProcessorMock, occupationPageProcessorMock)
	}

	void "converts PageHeader to Occupation"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Occupation occupation = new Occupation()

		when:
		Occupation occupationOutput = occupationProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * occupationPageProcessorMock.process(page) >> occupation

		then: 'last processor output is returned'
		occupationOutput == occupation
	}

}
