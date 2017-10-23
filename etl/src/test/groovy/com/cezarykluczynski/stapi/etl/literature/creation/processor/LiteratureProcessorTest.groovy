package com.cezarykluczynski.stapi.etl.literature.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class LiteratureProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private LiteraturePageProcessor literaturePageProcessorMock

	private LiteratureProcessor literatureProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		literaturePageProcessorMock = Mock()
		literatureProcessor = new LiteratureProcessor(pageHeaderProcessorMock, literaturePageProcessorMock)
	}

	void "converts PageHeader to Literature"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Literature literature = new Literature()

		when:
		Literature literatureOutput = literatureProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * literaturePageProcessorMock.process(page) >> literature

		then: 'last processor output is returned'
		literatureOutput == literature
	}

}
