package com.cezarykluczynski.stapi.etl.comics.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.etl.template.comics.processor.ComicsTemplatePageProcessor
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class ComicsProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ComicsTemplatePageProcessor comicsTemplatePageProcessorMock

	private ComicsTemplateProcessor comicsTemplateProcessorMock

	private ComicsProcessor comicsProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		comicsTemplatePageProcessorMock = Mock()
		comicsTemplateProcessorMock = Mock()
		comicsProcessor = new ComicsProcessor(pageHeaderProcessorMock, comicsTemplatePageProcessorMock, comicsTemplateProcessorMock)
	}

	void "converts PageHeader to Comics"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Comics comics = new Comics()

		when:
		Comics comicsOutput = comicsProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * comicsTemplatePageProcessorMock.process(page) >> comicsTemplate

		and:
		1 * comicsTemplateProcessorMock.process(comicsTemplate) >> comics

		then: 'last processor output is returned'
		comicsOutput == comics
	}

}
