package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor

import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class MagazineSeriesWriterTest extends Specification {

	private MagazineSeriesRepository magazineSeriesRepositoryMock

	private MagazineSeriesWriter magazineSeriesWriter

	void setup() {
		magazineSeriesRepositoryMock = Mock()
		magazineSeriesWriter = new MagazineSeriesWriter(magazineSeriesRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		MagazineSeries magazine = new MagazineSeries()
		List<MagazineSeries> magazineSeriesList = Lists.newArrayList(magazine)

		when:
		magazineSeriesWriter.write(new Chunk(magazineSeriesList))

		then:
		1 * magazineSeriesRepositoryMock.saveAll(magazineSeriesList)
		0 * _
	}

}
