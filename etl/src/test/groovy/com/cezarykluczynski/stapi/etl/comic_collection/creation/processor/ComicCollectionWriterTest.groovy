package com.cezarykluczynski.stapi.etl.comic_collection.creation.processor

import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comic_collection.repository.ComicCollectionRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class ComicCollectionWriterTest extends Specification {

	private ComicCollectionRepository comicCollectionRepositoryMock

	private ComicCollectionWriter comicCollectionWriterMock

	void setup() {
		comicCollectionRepositoryMock = Mock()
		comicCollectionWriterMock = new ComicCollectionWriter(comicCollectionRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		ComicCollection comicCollection = new ComicCollection()
		List<ComicCollection> comicCollectionList = Lists.newArrayList(comicCollection)

		when:
		comicCollectionWriterMock.write(new Chunk(comicCollectionList))

		then:
		1 * comicCollectionRepositoryMock.saveAll(comicCollectionList)
		0 * _
	}

}
