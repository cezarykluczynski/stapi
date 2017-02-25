package com.cezarykluczynski.stapi.etl.comicCollection.creation.processor

import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comicCollection.repository.ComicCollectionRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicCollectionWriterTest extends Specification {

	private ComicCollectionRepository comicCollectionRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private ComicCollectionWriter comicCollectionWriterMock

	void setup() {
		comicCollectionRepositoryMock = Mock(ComicCollectionRepository)
		duplicateFilteringPreSavePageAwareProcessorMock = Mock(DuplicateFilteringPreSavePageAwareFilter)
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
