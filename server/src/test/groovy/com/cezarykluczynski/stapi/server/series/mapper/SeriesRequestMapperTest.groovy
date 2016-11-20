package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import org.mapstruct.factory.Mappers

class SeriesRequestMapperTest extends AbstractSeriesMapperTest {

	private SeriesRequestMapper seriesRequestMapper

	def setup() {
		seriesRequestMapper = Mappers.getMapper(SeriesRequestMapper)
	}

	def "maps SOAP SeriesRequest to SeriesRequestDTO"() {
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
						dateFrom: ORIGINAL_RUN_START_DATE_FROM_XML,
						dateTo: ORIGINAL_RUN_START_DATE_TO_XML
				),
				originalRunEndDate: new DateRange(
						dateFrom: ORIGINAL_RUN_END_DATE_FROM_XML,
						dateTo: ORIGINAL_RUN_END_DATE_TO_XML
				))

		when:
		SeriesRequestDTO seriesRequestDTO = seriesRequestMapper.map seriesRequest

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

	def "maps SeriesRestBeanParams to SeriesRequestDTO"() {
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
		SeriesRequestDTO seriesRequestDTO = seriesRequestMapper.map seriesRestBeanParams

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

}
