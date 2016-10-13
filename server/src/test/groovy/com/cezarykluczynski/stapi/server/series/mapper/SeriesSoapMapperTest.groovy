package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.soap.Series as SOAPSeries
import com.cezarykluczynski.stapi.model.series.entity.Series as DBSeries
import com.google.common.collect.Lists
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.XMLGregorianCalendar
import java.time.LocalDate

class SeriesSoapMapperTest extends Specification {

	private static final Long ID = 1L
	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'ABBREVIATION'
	private static final Integer PRODUCTION_START_YEAR = 1998
	private static final Integer PRODUCTION_END_YEAR = 2002
	private static final LocalDate ORIGINAL_RUN_START_DATE = LocalDate.of(1999, 5, 3)
	private static final LocalDate ORIGINAL_RUN_END_DATE = LocalDate.of(2001, 2, 18)
	private static final XMLGregorianCalendar ORIGINAL_RUN_START_DATE_XML = XMLGregorianCalendarImpl
			.createDate(1999, 5, 3, DatatypeConstants.FIELD_UNDEFINED)
	private static final XMLGregorianCalendar ORIGINAL_RUN_END_DATE_XML = XMLGregorianCalendarImpl
			.createDate(2001, 2, 18, DatatypeConstants.FIELD_UNDEFINED)

	private SeriesSoapMapper seriesSoapMapper

	def setup() {
		seriesSoapMapper = Mappers.getMapper(SeriesSoapMapper.class)
	}

	def "maps DB entity to SOAP entity"() {
		given:
		DBSeries dBSeries = new DBSeries(
				id: ID,
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYear: PRODUCTION_START_YEAR,
				productionEndYear: PRODUCTION_END_YEAR,
				originalRunStartDate: ORIGINAL_RUN_START_DATE,
				originalRunEndDate: ORIGINAL_RUN_END_DATE)

		when:
		SOAPSeries soapSeries = seriesSoapMapper.map(Lists.newArrayList(dBSeries))[0]

		then:
		soapSeries.id == ID
		soapSeries.title == TITLE
		soapSeries.abbreviation == ABBREVIATION
		soapSeries.productionStartYear == PRODUCTION_START_YEAR
		soapSeries.productionEndYear == PRODUCTION_END_YEAR
		soapSeries.originalRunStartDate == ORIGINAL_RUN_START_DATE_XML
		soapSeries.originalRunEndDate == ORIGINAL_RUN_END_DATE_XML
	}

}
