package com.cezarykluczynski.stapi.etl.bookCollection.creation.processor

import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection
import com.cezarykluczynski.stapi.model.bookCollection.repository.BookCollectionRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class BookCollectionWriterTest extends Specification {

	private BookCollectionRepository bookCollectionRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private BookCollectionWriter bookCollectionWriterMock

	void setup() {
		bookCollectionRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		bookCollectionWriterMock = new BookCollectionWriter(bookCollectionRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		BookCollection bookCollection = new BookCollection()
		List<BookCollection> bookCollectionList = Lists.newArrayList(bookCollection)

		when:
		bookCollectionWriterMock.write(bookCollectionList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, BookCollection) >> { args ->
			assert args[0][0] == bookCollection
			bookCollectionList
		}
		1 * bookCollectionRepositoryMock.save(bookCollectionList)
		0 * _
	}

}
