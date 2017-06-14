package com.cezarykluczynski.stapi.etl.comic_series.link.configuration

import com.cezarykluczynski.stapi.etl.comic_series.link.processor.ComicSeriesLinkReader
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository
import spock.lang.Specification

class ComicSeriesLinkConfigurationTest extends Specification {

	private static final Integer COMMIT_INTERVAL = 1

	private ComicSeriesRepository comicSeriesRepositoryMock

	private StepsProperties stepsPropertiesMock

	private ComicSeriesLinkConfiguration comicSeriesLinkConfiguration

	void setup() {
		comicSeriesRepositoryMock = Mock()
		stepsPropertiesMock = Mock()
		comicSeriesLinkConfiguration = new ComicSeriesLinkConfiguration(
				comicSeriesRepository: comicSeriesRepositoryMock,
				stepsProperties: stepsPropertiesMock)
	}

	void "ComicSeriesLinkReader is created"() {
		given:
		StepProperties linkComicSeriesStepProperties = Mock()

		when:
		ComicSeriesLinkReader comicSeriesLinkReader = comicSeriesLinkConfiguration.comicSeriesLinkReader()

		then:
		1 * stepsPropertiesMock.linkComicSeries >> linkComicSeriesStepProperties
		1 * linkComicSeriesStepProperties.commitInterval >> COMMIT_INTERVAL
		0 * _
		comicSeriesLinkReader != null
	}

}
