package com.cezarykluczynski.stapi.etl.comic_series.creation.processor

import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicSeriesWriterTest extends Specification {

	private ComicSeriesRepository comicSeriesRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private ComicSeriesWriter comicSeriesWriterMock

	void setup() {
		comicSeriesRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		comicSeriesWriterMock = new ComicSeriesWriter(comicSeriesRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		ComicSeries comicSeries = new ComicSeries()
		List<ComicSeries> comicSeriesList = Lists.newArrayList(comicSeries)

		when:
		comicSeriesWriterMock.write(comicSeriesList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, ComicSeries) >> { args ->
			assert args[0][0] == comicSeries
			comicSeriesList
		}
		1 * comicSeriesRepositoryMock.save(comicSeriesList)
		0 * _
	}

}
