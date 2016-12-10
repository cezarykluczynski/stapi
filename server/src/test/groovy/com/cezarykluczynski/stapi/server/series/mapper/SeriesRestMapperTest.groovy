package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers
import com.cezarykluczynski.stapi.client.v1.rest.model.Series as RESTSeries

class SeriesRestMapperTest extends AbstractSeriesMapperTest {

	private SeriesRestMapper seriesRestMapper

	def setup() {
		seriesRestMapper = Mappers.getMapper(SeriesRestMapper)
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
		SeriesRequestDTO seriesRequestDTO = seriesRestMapper.map seriesRestBeanParams

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

	def "maps DB entity to SOAP entity"() {
		given:
		Series dBSeries = new Series(
				guid: GUID,
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYear: PRODUCTION_START_YEAR,
				productionEndYear: PRODUCTION_END_YEAR,
				originalRunStartDate: ORIGINAL_RUN_START_DATE,
				originalRunEndDate: ORIGINAL_RUN_END_DATE)

		when:
		RESTSeries restSeries = seriesRestMapper.map(Lists.newArrayList(dBSeries))[0]

		then:
		restSeries.guid == GUID
		restSeries.title == TITLE
		restSeries.abbreviation == ABBREVIATION
		restSeries.productionStartYear == PRODUCTION_START_YEAR
		restSeries.productionEndYear == PRODUCTION_END_YEAR
		restSeries.originalRunStartDate == ORIGINAL_RUN_START_DATE
		restSeries.originalRunEndDate == ORIGINAL_RUN_END_DATE
	}

}
