package com.cezarykluczynski.stapi.etl.book_collection.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate
import com.cezarykluczynski.stapi.etl.template.book.processor.collection.BookCollectionTemplatePageProcessor
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class BookCollectionProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private BookCollectionTemplatePageProcessor bookCollectionTemplatePageProcessorMock

	private BookCollectionTemplateProcessor bookCollectionTemplateProcessorMock

	private BookCollectionProcessor bookCollectionProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		bookCollectionTemplatePageProcessorMock = Mock()
		bookCollectionTemplateProcessorMock = Mock()
		bookCollectionProcessor = new BookCollectionProcessor(pageHeaderProcessorMock, bookCollectionTemplatePageProcessorMock,
				bookCollectionTemplateProcessorMock)
	}

	void "converts PageHeader to BookCollection"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		BookCollectionTemplate bookCollectionTemplate = new BookCollectionTemplate()
		BookCollection bookCollection = new BookCollection()

		when:
		BookCollection bookCollectionOutput = bookCollectionProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * bookCollectionTemplatePageProcessorMock.process(page) >> bookCollectionTemplate

		and:
		1 * bookCollectionTemplateProcessorMock.process(bookCollectionTemplate) >> bookCollection

		then: 'last processor output is returned'
		bookCollectionOutput == bookCollection
	}

}
