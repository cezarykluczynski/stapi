package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor

import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineSeriesWriterTest extends Specification {

	private MagazineSeriesRepository magazineSeriesRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private MagazineSeriesWriter magazineSeriesWriter

	void setup() {
		magazineSeriesRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		magazineSeriesWriter = new MagazineSeriesWriter(magazineSeriesRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		MagazineSeries magazine = new MagazineSeries()
		List<MagazineSeries> magazineSeriesList = Lists.newArrayList(magazine)

		when:
		magazineSeriesWriter.write(magazineSeriesList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, MagazineSeries) >> { args ->
			assert args[0][0] == magazine
			magazineSeriesList
		}
		1 * magazineSeriesRepositoryMock.save(magazineSeriesList)
		0 * _
	}

}
