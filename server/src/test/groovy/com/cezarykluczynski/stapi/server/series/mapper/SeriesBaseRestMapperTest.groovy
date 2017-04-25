package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBase
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeriesBaseRestMapperTest extends AbstractSeriesMapperTest {

	private SeriesBaseRestMapper seriesBaseRestMapper

	void setup() {
		seriesBaseRestMapper = Mappers.getMapper(SeriesBaseRestMapper)
	}

	void "maps SeriesRestBeanParams to SeriesRequestDTO"() {
		given:
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams(
				uid: UID,
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
		SeriesRequestDTO seriesRequestDTO = seriesBaseRestMapper.mapBase seriesRestBeanParams

		then:
		seriesRequestDTO.uid == UID
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
		Series series = createSeries()

		when:
		SeriesBase seriesBase = seriesBaseRestMapper.mapBase(Lists.newArrayList(series))[0]

		then:
		seriesBase.uid == UID
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

}
