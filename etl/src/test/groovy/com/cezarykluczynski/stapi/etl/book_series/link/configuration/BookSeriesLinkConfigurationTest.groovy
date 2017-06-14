package com.cezarykluczynski.stapi.etl.book_series.link.configuration

import com.cezarykluczynski.stapi.etl.book_series.link.processor.BookSeriesLinkReader
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository
import spock.lang.Specification

class BookSeriesLinkConfigurationTest extends Specification {

	private static final Integer COMMIT_INTERVAL = 1

	private BookSeriesRepository bookSeriesRepositoryMock

	private StepsProperties stepsPropertiesMock

	private BookSeriesLinkConfiguration bookSeriesLinkConfiguration

	void setup() {
		bookSeriesRepositoryMock = Mock()
		stepsPropertiesMock = Mock()
		bookSeriesLinkConfiguration = new BookSeriesLinkConfiguration(
				bookSeriesRepository: bookSeriesRepositoryMock,
				stepsProperties: stepsPropertiesMock)
	}

	void "BookSeriesLinkReader is created"() {
		given:
		StepProperties linkBookSeriesStepProperties = Mock()

		when:
		BookSeriesLinkReader bookSeriesLinkReader = bookSeriesLinkConfiguration.bookSeriesLinkReader()

		then:
		1 * stepsPropertiesMock.linkBookSeries >> linkBookSeriesStepProperties
		1 * linkBookSeriesStepProperties.commitInterval >> COMMIT_INTERVAL
		0 * _
		bookSeriesLinkReader != null
	}

}
