package com.cezarykluczynski.stapi.etl.template.book.processor.collection

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.template.book.processor.BookTemplatePageProcessor
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Sets
import spock.lang.Specification

class BookCollectionTemplatePageProcessorTest extends Specification {

	private BookTemplatePageProcessor bookTemplatePageProcessorMock

	private BookTemplateToBookCollectionTemplateProcessor bookTemplateToBookCollectionTemplateProcessorMock

	private BookCollectionTemplateWikitextBookProcessor bookCollectionTemplateWikitextBookProcessorMock

	private BookCollectionTemplatePageProcessor bookCollectionTemplatePageProcessor

	void setup() {
		bookTemplatePageProcessorMock = Mock()
		bookTemplateToBookCollectionTemplateProcessorMock = Mock()
		bookCollectionTemplateWikitextBookProcessorMock = Mock()
		bookCollectionTemplatePageProcessor = new BookCollectionTemplatePageProcessor(bookTemplatePageProcessorMock,
				bookTemplateToBookCollectionTemplateProcessorMock, bookCollectionTemplateWikitextBookProcessorMock)
	}

	void "returns null when BookTemplatePageProcessor returns null"() {
		given:
		Page page = new Page()

		when:
		BookCollectionTemplate bookCollectionTemplate = bookCollectionTemplatePageProcessor.process(page)

		then:
		1 * bookTemplatePageProcessorMock.process(page) >> null
		0 * _
		bookCollectionTemplate == null
	}

	void "maps not null BookTemplate to BookCollectionTemplate, then adds book from BookCollectionTemplateWikitextBookProcessor"() {
		given:
		Page page = new Page()
		BookTemplate bookTemplate = new BookTemplate()
		BookCollectionTemplate bookCollectionTemplate = new BookCollectionTemplate()
		Book book1 = Mock()
		Book book2 = Mock()

		when:
		BookCollectionTemplate bookCollectionTemplateOutput = bookCollectionTemplatePageProcessor.process(page)

		then:
		1 * bookTemplatePageProcessorMock.process(page) >> bookTemplate
		1 * bookTemplateToBookCollectionTemplateProcessorMock.process(bookTemplate) >> bookCollectionTemplate
		1 * bookCollectionTemplateWikitextBookProcessorMock.process(page) >> Sets.newHashSet(book1, book2)
		0 * _
		bookCollectionTemplateOutput == bookCollectionTemplate
		bookCollectionTemplateOutput.books.size() == 2
		bookCollectionTemplateOutput.books.contains book1
		bookCollectionTemplateOutput.books.contains book2
	}

}
