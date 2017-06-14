package com.cezarykluczynski.stapi.etl.comic_strip.creation.processor

import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.comic_strip.repository.ComicStripRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicStripWriterTest extends Specification {

	private ComicStripRepository comicStripRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private ComicStripWriter comicStripWriterMock

	void setup() {
		comicStripRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		comicStripWriterMock = new ComicStripWriter(comicStripRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		ComicStrip comicStrip = new ComicStrip()
		List<ComicStrip> comicStripList = Lists.newArrayList(comicStrip)

		when:
		comicStripWriterMock.write(comicStripList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, ComicStrip) >> { args ->
			assert args[0][0] == comicStrip
			comicStripList
		}
		1 * comicStripRepositoryMock.save(comicStripList)
		0 * _
	}

}
