package com.cezarykluczynski.stapi.etl.comics.creation.processor

import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicsWriterTest extends Specification {

	private ComicsRepository comicsRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private ComicsWriter comicsWriterMock

	void setup() {
		comicsRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		comicsWriterMock = new ComicsWriter(comicsRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Comics comics = new Comics()
		List<Comics> comicsList = Lists.newArrayList(comics)

		when:
		comicsWriterMock.write(comicsList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Comics) >> { args ->
			assert args[0][0] == comics
			comicsList
		}
		1 * comicsRepositoryMock.save(comicsList)
		0 * _
	}

}
