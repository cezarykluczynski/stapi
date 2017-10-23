package com.cezarykluczynski.stapi.etl.book.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.template.book.processor.BookTemplatePageProcessor
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class BookProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private BookTemplatePageProcessor bookTemplatePageProcessorMock

	private BookTemplateProcessor bookTemplateProcessorMock

	private BookProcessor bookProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		bookTemplatePageProcessorMock = Mock()
		bookTemplateProcessorMock = Mock()
		bookProcessor = new BookProcessor(pageHeaderProcessorMock, bookTemplatePageProcessorMock, bookTemplateProcessorMock)
	}

	void "converts PageHeader to Book"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		BookTemplate bookTemplate = new BookTemplate()
		Book book = new Book()

		when:
		Book bookOutput = bookProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * bookTemplatePageProcessorMock.process(page) >> bookTemplate

		and:
		1 * bookTemplateProcessorMock.process(bookTemplate) >> book

		then: 'last processor output is returned'
		bookOutput == book
	}

}
