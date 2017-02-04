package com.cezarykluczynski.stapi.etl.comicSeries.link.configuration

import com.cezarykluczynski.stapi.etl.comicSeries.link.processor.ComicSeriesLinkReader
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.model.comicSeries.repository.ComicSeriesRepository
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class ComicSeriesLinkConfigurationTest extends Specification {

	private static final Integer COMMIT_INTERVAL = 1

	private ApplicationContext applicationContextMock

	private StepCompletenessDecider stepCompletenessDeciderMock

	private ComicSeriesRepository comicSeriesRepositoryMock

	private StepsProperties stepsPropertiesMock

	private ComicSeriesLinkConfiguration comicSeriesLinkConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		stepCompletenessDeciderMock = Mock(StepCompletenessDecider)
		comicSeriesRepositoryMock = Mock(ComicSeriesRepository)
		stepsPropertiesMock = Mock(StepsProperties)
		comicSeriesLinkConfiguration = new ComicSeriesLinkConfiguration(
				applicationContext: applicationContextMock,
				stepCompletenessDecider: stepCompletenessDeciderMock,
				comicSeriesRepository: comicSeriesRepositoryMock,
				stepsProperties: stepsPropertiesMock)
	}

	void "ComicSeriesLinkReader is created"() {
		given:
		StepProperties linkComicSeriesStepProperties = Mock(StepProperties)

		when:
		ComicSeriesLinkReader comicSeriesLinkReader = comicSeriesLinkConfiguration.comicSeriesLinkReader()

		then:
		1 * stepsPropertiesMock.linkComicSeries >> linkComicSeriesStepProperties
		1 * linkComicSeriesStepProperties.commitInterval >> COMMIT_INTERVAL
		0 * _
		comicSeriesLinkReader != null
	}

}
