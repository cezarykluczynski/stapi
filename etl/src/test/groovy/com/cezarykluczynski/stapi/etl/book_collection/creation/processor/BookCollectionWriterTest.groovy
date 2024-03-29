package com.cezarykluczynski.stapi.etl.book_collection.creation.processor

import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection
import com.cezarykluczynski.stapi.model.book_collection.repository.BookCollectionRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class BookCollectionWriterTest extends Specification {

	private BookCollectionRepository bookCollectionRepositoryMock

	private BookCollectionWriter bookCollectionWriterMock

	void setup() {
		bookCollectionRepositoryMock = Mock()
		bookCollectionWriterMock = new BookCollectionWriter(bookCollectionRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		BookCollection bookCollection = new BookCollection()
		List<BookCollection> bookCollectionList = Lists.newArrayList(bookCollection)

		when:
		bookCollectionWriterMock.write(new Chunk(bookCollectionList))

		then:
		1 * bookCollectionRepositoryMock.saveAll(bookCollectionList)
		0 * _
	}

}
