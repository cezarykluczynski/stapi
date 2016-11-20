package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Series as SOAPSeries
import com.cezarykluczynski.stapi.model.series.entity.Series as DBSeries
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class SeriesSoapMapperTest extends AbstractSeriesMapperTest {

	private SeriesSoapMapper seriesSoapMapper

	def setup() {
		seriesSoapMapper = Mappers.getMapper(SeriesSoapMapper)
	}

	def "maps DB entity to SOAP entity"() {
		given:
		DBSeries dBSeries = new DBSeries(
				guid: GUID,
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYear: PRODUCTION_START_YEAR,
				productionEndYear: PRODUCTION_END_YEAR,
				originalRunStartDate: ORIGINAL_RUN_START_DATE,
				originalRunEndDate: ORIGINAL_RUN_END_DATE)

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
	}

}
