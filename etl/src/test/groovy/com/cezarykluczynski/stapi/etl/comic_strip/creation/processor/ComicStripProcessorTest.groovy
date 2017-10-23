package com.cezarykluczynski.stapi.etl.comic_strip.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplate
import com.cezarykluczynski.stapi.etl.template.comic_strip.processor.ComicStripTemplatePageProcessor
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class ComicStripProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ComicStripTemplatePageProcessor comicStripTemplatePageProcessorMock

	private ComicStripTemplateProcessor comicStripTemplateProcessorMock

	private ComicStripProcessor comicStripProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		comicStripTemplatePageProcessorMock = Mock()
		comicStripTemplateProcessorMock = Mock()
		comicStripProcessor = new ComicStripProcessor(pageHeaderProcessorMock, comicStripTemplatePageProcessorMock, comicStripTemplateProcessorMock)
	}

	void "converts PageHeader to ComicStrip"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()
		ComicStrip comicStrip = new ComicStrip()

		when:
		ComicStrip comicStripOutput = comicStripProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * comicStripTemplatePageProcessorMock.process(page) >> comicStripTemplate

		and:
		1 * comicStripTemplateProcessorMock.process(comicStripTemplate) >> comicStrip

		then: 'last processor output is returned'
		comicStripOutput == comicStrip
	}

}
