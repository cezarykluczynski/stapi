package com.cezarykluczynski.stapi.etl.title.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class TitleProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private TitlePageProcessor titlePageProcessorMock

	private TitleProcessor titleProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		titlePageProcessorMock = Mock()
		titleProcessor = new TitleProcessor(pageHeaderProcessorMock, titlePageProcessorMock)
	}

	void "converts PageHeader to Title"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Title title = new Title()

		when:
		Title titleOutput = titleProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * titlePageProcessorMock.process(page) >> title

		then: 'last processor output is returned'
		titleOutput == title
	}

}
