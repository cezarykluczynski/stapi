package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBase
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFull
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeriesRestMapperTest extends AbstractSeriesMapperTest {

	private SeriesRestMapper seriesRestMapper

	void setup() {
		seriesRestMapper = Mappers.getMapper(SeriesRestMapper)
	}

	void "maps SeriesRestBeanParams to SeriesRequestDTO"() {
		given:
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams(
				guid: GUID,
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYearFrom: PRODUCTION_START_YEAR_FROM,
				productionStartYearTo: PRODUCTION_START_YEAR_TO,
				productionEndYearFrom: PRODUCTION_END_YEAR_FROM,
				productionEndYearTo: PRODUCTION_END_YEAR_TO,
				originalRunStartDateFrom: ORIGINAL_RUN_START_DATE_FROM_DB,
				originalRunStartDateTo: ORIGINAL_RUN_START_DATE_TO_DB,
				originalRunEndDateFrom: ORIGINAL_RUN_END_DATE_FROM_DB,
				originalRunEndDateTo: ORIGINAL_RUN_END_DATE_TO_DB)

		when:
		SeriesRequestDTO seriesRequestDTO = seriesRestMapper.mapBase seriesRestBeanParams

		then:
		seriesRequestDTO.guid == GUID
		seriesRequestDTO.title == TITLE
		seriesRequestDTO.abbreviation == ABBREVIATION
		seriesRequestDTO.productionStartYearFrom == PRODUCTION_START_YEAR_FROM
		seriesRequestDTO.productionStartYearTo == PRODUCTION_START_YEAR_TO
		seriesRequestDTO.productionEndYearFrom == PRODUCTION_END_YEAR_FROM
		seriesRequestDTO.productionEndYearTo == PRODUCTION_END_YEAR_TO
		seriesRequestDTO.originalRunStartDateFrom == ORIGINAL_RUN_START_DATE_FROM_DB
		seriesRequestDTO.originalRunStartDateTo == ORIGINAL_RUN_START_DATE_TO_DB
		seriesRequestDTO.originalRunEndDateFrom == ORIGINAL_RUN_END_DATE_FROM_DB
		seriesRequestDTO.originalRunEndDateTo == ORIGINAL_RUN_END_DATE_TO_DB
	}

	void "maps DB entity to base REST entity"() {
		given:
		Series dBSeries = createSeries()

		when:
		SeriesBase seriesBase = seriesRestMapper.mapBase(Lists.newArrayList(dBSeries))[0]

		then:
		seriesBase.guid == GUID
		seriesBase.title == TITLE
		seriesBase.abbreviation == ABBREVIATION
		seriesBase.productionStartYear == PRODUCTION_START_YEAR
		seriesBase.productionEndYear == PRODUCTION_END_YEAR
		seriesBase.originalRunStartDate == ORIGINAL_RUN_START_DATE
		seriesBase.originalRunEndDate == ORIGINAL_RUN_END_DATE
		seriesBase.seasonsCount == SEASONS_COUNT
		seriesBase.episodesCount == EPISODES_COUNT
		seriesBase.originalBroadcaster != null
		seriesBase.productionCompany != null
		seriesBase.featureLengthEpisodesCount == FEATURE_LENGTH_EPISODES_COUNT
	}

	void "maps DB entity to full REST entity"() {
		given:
		Series dBSeries = createSeries()

		when:
		SeriesFull seriesFull = seriesRestMapper.mapFull(dBSeries)

		then:
		seriesFull.guid == GUID
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
		seriesFull.episodeHeaders.size() == dBSeries.episodes.size()
	}

}
