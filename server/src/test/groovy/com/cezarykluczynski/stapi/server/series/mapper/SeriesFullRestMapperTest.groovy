package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFull
import com.cezarykluczynski.stapi.model.series.entity.Series
import org.mapstruct.factory.Mappers

class SeriesFullRestMapperTest extends AbstractSeriesMapperTest {

	private SeriesFullRestMapper seriesFullRestMapper

	void setup() {
		seriesFullRestMapper = Mappers.getMapper(SeriesFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Series series = createSeries()

		when:
		SeriesFull seriesFull = seriesFullRestMapper.mapFull(series)

		then:
		seriesFull.uid == UID
		seriesFull.title == TITLE
		seriesFull.abbreviation == ABBREVIATION
		seriesFull.productionStartYear == PRODUCTION_START_YEAR
		seriesFull.productionEndYear == PRODUCTION_END_YEAR
		seriesFull.originalRunStartDate == ORIGINAL_RUN_START_DATE
		seriesFull.originalRunEndDate == ORIGINAL_RUN_END_DATE
		seriesFull.seasonsCount == SEASONS_COUNT
		seriesFull.episodesCount == EPISODES_COUNT
		seriesFull.featureLengthEpisodesCount == FEATURE_LENGTH_EPISODES_COUNT
		seriesFull.originalBroadcaster != null
		seriesFull.productionCompany != null
		seriesFull.episodes.size() == series.episodes.size()
		seriesFull.seasons.size() == series.seasons.size()
	}

}
