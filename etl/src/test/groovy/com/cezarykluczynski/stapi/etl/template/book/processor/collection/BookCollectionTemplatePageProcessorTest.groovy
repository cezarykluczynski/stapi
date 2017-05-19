package com.cezarykluczynski.stapi.etl.template.book.processor.collection

import com.cezarykluczynski.stapi.etl.template.book.dto.BookCollectionTemplate
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.template.book.processor.BookTemplatePageProcessor
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Sets
import spock.lang.Specification

class BookCollectionTemplatePageProcessorTest extends Specification {

	private BookTemplatePageProcessor bookTemplatePageProcessorMock

	private BookTemplateToBookCollectionTemplateProcessor bookTemplateToBookCollectionTemplateProcessorMock

	private BookCollectionTemplateWikitextBooksProcessor bookCollectionTemplateWikitextBooksProcessorMock

	private BookCollectionTemplatePageProcessor bookCollectionTemplatePageProcessor

	void setup() {
		bookTemplatePageProcessorMock = Mock()
		bookTemplateToBookCollectionTemplateProcessorMock = Mock()
		bookCollectionTemplateWikitextBooksProcessorMock = Mock()
		bookCollectionTemplatePageProcessor = new BookCollectionTemplatePageProcessor(bookTemplatePageProcessorMock,
				bookTemplateToBookCollectionTemplateProcessorMock, bookCollectionTemplateWikitextBooksProcessorMock)
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
		Character character1 = Mock()
		Character character2 = Mock()

		when:
		BookCollectionTemplate bookCollectionTemplateOutput = bookCollectionTemplatePageProcessor.process(page)

		then:
		1 * bookTemplatePageProcessorMock.process(page) >> bookTemplate
		1 * bookTemplateToBookCollectionTemplateProcessorMock.process(bookTemplate) >> bookCollectionTemplate
		1 * bookCollectionTemplateWikitextBooksProcessorMock.process(page) >> Sets.newHashSet(book1, book2)
		1 * book1.characters >> Sets.newHashSet(character1)
		1 * book2.characters >> Sets.newHashSet(character2)
		0 * _
		bookCollectionTemplateOutput == bookCollectionTemplate
		bookCollectionTemplateOutput.books.size() == 2
		bookCollectionTemplateOutput.books.contains book1
		bookCollectionTemplateOutput.books.contains book2
		bookCollectionTemplateOutput.characters.size() == 2
		bookCollectionTemplateOutput.characters.contains character1
		bookCollectionTemplateOutput.characters.contains character2
	}

}
