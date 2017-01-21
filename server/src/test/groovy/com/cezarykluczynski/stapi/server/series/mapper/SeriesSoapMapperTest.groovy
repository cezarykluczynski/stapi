package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.Series as SOAPSeries
import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.entity.Series as DBSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeriesSoapMapperTest extends AbstractSeriesMapperTest {

	private SeriesSoapMapper seriesSoapMapper

	void setup() {
		seriesSoapMapper = Mappers.getMapper(SeriesSoapMapper)
	}

	void "maps SOAP SeriesRequest to SeriesRequestDTO"() {
		given:
		SeriesRequest seriesRequest = new SeriesRequest(
				guid: GUID,
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
		SeriesRequestDTO seriesRequestDTO = seriesSoapMapper.map seriesRequest

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

	void "maps DB entity to SOAP entity"() {
		given:
		DBSeries dBSeries = createSeries()

		when:
		SOAPSeries soapSeries = seriesSoapMapper.map(Lists.newArrayList(dBSeries))[0]

		then:
		soapSeries.guid == GUID
		soapSeries.title == TITLE
		soapSeries.abbreviation == ABBREVIATION
		soapSeries.productionStartYear == PRODUCTION_START_YEAR
		soapSeries.productionEndYear == PRODUCTION_END_YEAR
		soapSeries.originalRunStartDate == ORIGINAL_RUN_START_DATE_XML
		soapSeries.originalRunEndDate == ORIGINAL_RUN_END_DATE_XML
		soapSeries.seasonsCount == SEASONS_COUNT
		soapSeries.episodesCount == EPISODES_COUNT
		soapSeries.featureLengthEpisodesCount == FEATURE_LENGTH_EPISODES_COUNT
		soapSeries.episodeHeaders.size() == dBSeries.episodes.size()
	}

}
