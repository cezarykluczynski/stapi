package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBase
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeriesBaseSoapMapperTest extends AbstractSeriesMapperTest {

	private SeriesBaseSoapMapper seriesBaseSoapMapper

	void setup() {
		seriesBaseSoapMapper = Mappers.getMapper(SeriesBaseSoapMapper)
	}

	void "maps SOAP SeriesBaseRequest to SeriesBaseRequestDTO"() {
		given:
		SeriesBaseRequest seriesBaseRequest = new SeriesBaseRequest(
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYear: new IntegerRange(
						from: PRODUCTION_START_YEAR_FROM,
						to: PRODUCTION_START_YEAR_TO,
				),
				productionEndYear: new IntegerRange(
						from: PRODUCTION_END_YEAR_FROM,
						to: PRODUCTION_END_YEAR_TO,
				),
				originalRunStartDate: new DateRange(
						from: ORIGINAL_RUN_START_DATE_FROM_XML,
						to: ORIGINAL_RUN_START_DATE_TO_XML
				),
				originalRunEndDate: new DateRange(
						from: ORIGINAL_RUN_END_DATE_FROM_XML,
						to: ORIGINAL_RUN_END_DATE_TO_XML
				))

		when:
		SeriesRequestDTO seriesRequestDTO = seriesBaseSoapMapper.mapBase seriesBaseRequest

		then:
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

	void "maps DB entity to base SOAP entity"() {
		given:
		Series series = createSeries()

		when:
		SeriesBase seriesBase = seriesBaseSoapMapper.mapBase(Lists.newArrayList(series))[0]

		then:
		seriesBase.uid == UID
		seriesBase.title == TITLE
		seriesBase.originalBroadcaster != null
		seriesBase.productionCompany != null
		seriesBase.abbreviation == ABBREVIATION
		seriesBase.productionStartYear == PRODUCTION_START_YEAR
		seriesBase.productionEndYear == PRODUCTION_END_YEAR
		seriesBase.originalRunStartDate == ORIGINAL_RUN_START_DATE_XML
		seriesBase.originalRunEndDate == ORIGINAL_RUN_END_DATE_XML
		seriesBase.seasonsCount == SEASONS_COUNT
		seriesBase.episodesCount == EPISODES_COUNT
		seriesBase.featureLengthEpisodesCount == FEATURE_LENGTH_EPISODES_COUNT
	}

}
