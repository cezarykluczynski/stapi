package com.cezarykluczynski.stapi.etl.comic_collection.creation.processor

import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comic_collection.repository.ComicCollectionRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicCollectionWriterTest extends Specification {

	private ComicCollectionRepository comicCollectionRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private ComicCollectionWriter comicCollectionWriterMock

	void setup() {
		comicCollectionRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		comicCollectionWriterMock = new ComicCollectionWriter(comicCollectionRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		ComicCollection comicCollection = new ComicCollection()
		List<ComicCollection> comicCollectionList = Lists.newArrayList(comicCollection)

		when:
		comicCollectionWriterMock.write(comicCollectionList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, ComicCollection) >> { args ->
			assert args[0][0] == comicCollection
			comicCollectionList
		}
		1 * comicCollectionRepositoryMock.save(comicCollectionList)
		0 * _
	}

}
