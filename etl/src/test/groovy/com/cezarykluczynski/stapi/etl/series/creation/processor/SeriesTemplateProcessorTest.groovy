package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.YearRange
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.series.entity.Series
import spock.lang.Specification

import java.time.LocalDate

class SeriesTemplateProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'ABBREVIATION'
	private static final Page PAGE = new Page(id: 1L)
	private static final String GUID = 'GUID'
	private static final Integer START_YEAR = 1995
	private static final Integer END_YEAR = 2000
	private static final LocalDate START_DATE = LocalDate.of(1996, 1, 1)
	private static final LocalDate END_DATE = LocalDate.of(1999, 2, 2)

	private GuidGenerator guidGeneratorMock

	private SeriesTemplateProcessor seriesTemplateProcessor

	def setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		seriesTemplateProcessor = new SeriesTemplateProcessor(guidGeneratorMock)
	}

	def "SeriesTemplate is mapped to Series"() {
		given:
		SeriesTemplate seriesTemplate = new SeriesTemplate(
				title: TITLE,
				page: PAGE,
				abbreviation: ABBREVIATION,
				productionYearRange: new YearRange(startYear: START_YEAR, endYear: END_YEAR),
				originalRunDateRange: new DateRange(startDate: START_DATE, endDate: END_DATE))

		when:
		Series series = seriesTemplateProcessor.process(seriesTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(PAGE, Series) >> GUID
		series.guid == GUID
		series.title == TITLE
		series.page == PAGE
		series.abbreviation == ABBREVIATION
		series.productionStartYear == START_YEAR
		series.productionEndYear == END_YEAR
		series.originalRunStartDate == START_DATE
		series.originalRunEndDate == END_DATE
	}

}
