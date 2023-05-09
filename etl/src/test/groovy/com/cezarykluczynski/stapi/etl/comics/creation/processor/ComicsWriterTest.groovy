package com.cezarykluczynski.stapi.etl.comics.creation.processor

import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class ComicsWriterTest extends Specification {

	private ComicsRepository comicsRepositoryMock

	private ComicsWriter comicsWriterMock

	void setup() {
		comicsRepositoryMock = Mock()
		comicsWriterMock = new ComicsWriter(comicsRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Comics comics = new Comics()
		List<Comics> comicsList = Lists.newArrayList(comics)

		when:
		comicsWriterMock.write(new Chunk(comicsList))

		then:
		1 * comicsRepositoryMock.saveAll(comicsList)
		0 * _
	}

}
