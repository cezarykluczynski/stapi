package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class SeasonSeriesProcessorTest extends Specification {

	private static final String PAGE_TITLE = 'DS9 Season 4'
	private static final String ABBREVIATION = 'DS9'

	private SeriesRepository seriesRepositoryMock

	private SeasonSeriesProcessor seasonSeriesProcessor

	void setup() {
		seriesRepositoryMock = Mock()
		seasonSeriesProcessor = new SeasonSeriesProcessor(seriesRepositoryMock)
	}

	void "throws exception when series cannot be found by abbreviation"() {
		given:

		when:
		seasonSeriesProcessor.process(PAGE_TITLE)

		then:
		1 * seriesRepositoryMock.findByAbbreviation(ABBREVIATION) >> Optional.empty()
		0 * _
		thrown(StapiRuntimeException)
	}

	void "returns series when it can be found by abbreviation"() {
		given:
		Series series = Mock()

		when:
		Series seriesOutput = seasonSeriesProcessor.process(PAGE_TITLE)

		then:
		1 * seriesRepositoryMock.findByAbbreviation(ABBREVIATION) >> Optional.of(series)
		0 * _
		seriesOutput == series
	}

}
