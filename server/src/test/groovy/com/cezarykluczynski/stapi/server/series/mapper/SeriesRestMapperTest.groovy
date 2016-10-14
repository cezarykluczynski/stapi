package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers
import com.cezarykluczynski.stapi.client.rest.model.Series as RESTSeries

class SeriesRestMapperTest extends AbstractSeriesMapperTest {

	private SeriesRestMapper seriesRestMapper

	def setup() {
		seriesRestMapper = Mappers.getMapper(SeriesRestMapper.class)
	}

	def "maps DB entity to SOAP entity"() {
		given:
		Series dBSeries = new Series(
				id: ID,
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYear: PRODUCTION_START_YEAR,
				productionEndYear: PRODUCTION_END_YEAR,
				originalRunStartDate: ORIGINAL_RUN_START_DATE,
				originalRunEndDate: ORIGINAL_RUN_END_DATE)

		when:
		RESTSeries restSeries = seriesRestMapper.map(Lists.newArrayList(dBSeries))[0]

		then:
		restSeries.id == ID
		restSeries.title == TITLE
		restSeries.abbreviation == ABBREVIATION
		restSeries.productionStartYear == PRODUCTION_START_YEAR
		restSeries.productionEndYear == PRODUCTION_END_YEAR
		restSeries.originalRunStartDate == ORIGINAL_RUN_START_DATE
		restSeries.originalRunEndDate == ORIGINAL_RUN_END_DATE
	}

}
