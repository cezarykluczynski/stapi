package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.series.creation.dto.SeriesEpisodeStatisticsDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.series.entity.Series
import org.apache.commons.lang3.RandomUtils
import spock.lang.Specification

import java.time.LocalDate

class SeriesTemplateProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'ABBREVIATION'
	private static final Page PAGE = new Page(id: 1L)
	private static final String UID = 'UID'
	private static final Integer START_YEAR = 1995
	private static final Integer END_YEAR = 2000
	private static final LocalDate START_DATE = LocalDate.of(1996, 1, 1)
	private static final LocalDate END_DATE = LocalDate.of(1999, 2, 2)
	private static final Integer SEASONS_COUNT = 1
	private static final Integer EPISODES_COUNT = 2
	private static final Integer FEATURE_LENGTH_EPISODES_COUNT = 3
	private static final Boolean COMPANION_SERIES = RandomUtils.nextBoolean()

	private UidGenerator uidGeneratorMock

	private SeriesEpisodeStatisticsFixedValueProvider seriesEpisodeStatisticsFixedValueProviderMock

	private SeriesTemplateProcessor seriesTemplateProcessor

	void setup() {
		uidGeneratorMock = Mock()
		seriesEpisodeStatisticsFixedValueProviderMock = Mock()
		seriesTemplateProcessor = new SeriesTemplateProcessor(uidGeneratorMock, seriesEpisodeStatisticsFixedValueProviderMock)
	}

	void "converts SeriesTemplate to Series"() {
		given:
		Company productionCompany = Mock()
		Company originalBroadcaster = Mock()
		SeriesTemplate seriesTemplate = new SeriesTemplate(
				title: TITLE,
				page: PAGE,
				abbreviation: ABBREVIATION,
				productionYearRange: new YearRange(yearFrom: START_YEAR, yearTo: END_YEAR),
				originalRunDateRange: new DateRange(startDate: START_DATE, endDate: END_DATE),
			productionCompany: productionCompany,
			originalBroadcaster: originalBroadcaster,
			companionSeries: COMPANION_SERIES)
		SeriesEpisodeStatisticsDTO seriesEpisodeStatisticsDTO = SeriesEpisodeStatisticsDTO
				.of(SEASONS_COUNT, EPISODES_COUNT, FEATURE_LENGTH_EPISODES_COUNT)
		FixedValueHolder<SeriesEpisodeStatisticsDTO> seriesEpisodeStatisticsDTOFixedValueHolder = FixedValueHolder.found(seriesEpisodeStatisticsDTO)

		when:
		Series series = seriesTemplateProcessor.process(seriesTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(PAGE, Series) >> UID
		1 * seriesEpisodeStatisticsFixedValueProviderMock.getSearchedValue(ABBREVIATION) >> seriesEpisodeStatisticsDTOFixedValueHolder
		0 * _
		series.uid == UID
		series.title == TITLE
		series.page == PAGE
		series.abbreviation == ABBREVIATION
		series.productionStartYear == START_YEAR
		series.productionEndYear == END_YEAR
		series.originalRunStartDate == START_DATE
		series.originalRunEndDate == END_DATE
		series.seasonsCount == SEASONS_COUNT
		series.episodesCount == EPISODES_COUNT
		series.featureLengthEpisodesCount == FEATURE_LENGTH_EPISODES_COUNT
		series.companionSeries == COMPANION_SERIES
		series.productionCompany == productionCompany
		series.originalBroadcaster == originalBroadcaster
	}

	void "null values are tolerated"() {
		given:
		SeriesTemplate seriesTemplate = new SeriesTemplate(
				productionYearRange: new YearRange(),
				originalRunDateRange: new DateRange(),
				page: PAGE)
		FixedValueHolder<SeriesEpisodeStatisticsDTO> seriesEpisodeStatisticsDTOFixedValueHolder = FixedValueHolder.notFound()

		when:
		Series series = seriesTemplateProcessor.process(seriesTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(PAGE, Series) >> null
		1 * seriesEpisodeStatisticsFixedValueProviderMock.getSearchedValue(null) >> seriesEpisodeStatisticsDTOFixedValueHolder
		0 * _
		series.uid == null
		series.title == null
		series.page == PAGE
		series.abbreviation == null
		series.productionStartYear == null
		series.productionEndYear == null
		series.originalRunStartDate == null
		series.originalRunEndDate == null
		series.seasonsCount == null
		series.episodesCount == null
		series.featureLengthEpisodesCount == null
		series.companionSeries == null
		series.productionCompany == null
		series.originalBroadcaster == null
	}

}
