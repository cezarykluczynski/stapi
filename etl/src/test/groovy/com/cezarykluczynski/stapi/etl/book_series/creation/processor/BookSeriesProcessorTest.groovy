package com.cezarykluczynski.stapi.etl.book_series.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.book_series.processor.BookSeriesTemplatePageProcessor
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class BookSeriesProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private BookSeriesTemplatePageProcessor bookSeriesTemplatePageProcessorMock

	private BookSeriesTemplateProcessor bookSeriesTemplateProcessorMock

	private BookSeriesProcessor bookSeriesProcessor

	void setup() {
		pageHeaderProcessorMock =
				Mock()
		bookSeriesTemplatePageProcessorMock = Mock()
		bookSeriesTemplateProcessorMock = Mock()
		bookSeriesProcessor = new BookSeriesProcessor(pageHeaderProcessorMock, bookSeriesTemplatePageProcessorMock, bookSeriesTemplateProcessorMock)
	}

	void "converts PageHeader to BookSeries"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()
		BookSeries bookSeries = new BookSeries()

		when:
		BookSeries bookSeriesOutput = bookSeriesProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * bookSeriesTemplatePageProcessorMock.process(page) >> bookSeriesTemplate

		and:
		1 * bookSeriesTemplateProcessorMock.process(bookSeriesTemplate) >> bookSeries

		then: 'last processor output is returned'
		bookSeriesOutput == bookSeries
	}

}
