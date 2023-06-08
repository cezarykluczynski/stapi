package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

class SeasonSeriesProcessorTest extends Specification {

	private static final String DS9_SEASON_PAGE_TITLE = 'DS9 Season 4'
	private static final String DS9_ABBREVIATION = 'DS9'
	private static final String TRR_SEASON_PAGE_TITLE = 'The Ready Room Season 2'
	private static final String TRR_SERIES_TITLE = 'The Ready Room'

	private SeriesRepository seriesRepositoryMock

	private SeasonSeriesProcessor seasonSeriesProcessor

	void setup() {
		seriesRepositoryMock = Mock()
		seasonSeriesProcessor = new SeasonSeriesProcessor(seriesRepositoryMock)
	}

	void "throws exception when series cannot be found by abbreviation and by prefix"() {
		given:
		Series differentSeries = new Series(title: TRR_SERIES_TITLE)

		when:
		seasonSeriesProcessor.process(DS9_SEASON_PAGE_TITLE)

		then:
		1 * seriesRepositoryMock.findByAbbreviation(DS9_ABBREVIATION) >> Optional.empty()
		1 * seriesRepositoryMock.findAll() >> [differentSeries]
		0 * _
		thrown(StapiRuntimeException)
	}

	void "returns series when it can be found by abbreviation"() {
		given:
		Series series = Mock()

		when:
		Series seriesOutput = seasonSeriesProcessor.process(DS9_SEASON_PAGE_TITLE)

		then:
		1 * seriesRepositoryMock.findByAbbreviation(DS9_ABBREVIATION) >> Optional.of(series)
		0 * _
		seriesOutput == series
	}

	void "returns series when it can be found by prefix"() {
		given:
		Series series = new Series(title: TRR_SERIES_TITLE)

		when:
		Series seriesOutput = seasonSeriesProcessor.process(TRR_SEASON_PAGE_TITLE)

		then:
		1 * seriesRepositoryMock.findByAbbreviation('The') >> Optional.empty()
		1 * seriesRepositoryMock.findAll() >> [series]
		0 * _
		seriesOutput == series
	}

}
