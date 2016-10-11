package com.cezarykluczynski.stapi.etl.template.series.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.PartToDateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.PartToYearRangeProcessor
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import com.cezarykluczynski.stapi.util.constants.TemplateNames
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class PageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'abbreviation'

	private PartToYearRangeProcessor partToYearRangeProcessorMock

	private PartToDateRangeProcessor partToDateRangeProcessorMock

	private PageProcessor pageProcessor

	def setup() {
		partToYearRangeProcessorMock = Mock(PartToYearRangeProcessor)
		partToDateRangeProcessorMock = Mock(PartToDateRangeProcessor)
		pageProcessor = new PageProcessor(partToYearRangeProcessorMock, partToDateRangeProcessorMock)
	}

	def "missing template results in null SeriesTemplate"() {
		given:
		Page page = new Page()

		when:
		SeriesTemplate seriesTemplate = pageProcessor.process(page)

		then:
		seriesTemplate == null
	}

	def "valid template is parsed"() {
		given:
		Template.Part yearRangePart = new Template.Part(key: PageProcessor.DATES)
		Template.Part dateRangePart = new Template.Part(key: PageProcessor.RUN)
		YearRange yearRange = Mock(YearRange)
		DateRange dateRange = Mock(DateRange)

		Page page = new Page(
				title: TITLE,
				templates: Lists.newArrayList(
						new Template(title: TemplateNames.SIDEBAR_SERIES, parts: Lists.newArrayList(
								new Template.Part(key: PageProcessor.ABBR, value: ABBREVIATION),
								yearRangePart,
								dateRangePart
						)
				)
		))

		when:
		SeriesTemplate seriesTemplate = pageProcessor.process(page)

		then: 'year range and date range parsing is delegated'
		1 * partToYearRangeProcessorMock.process(yearRangePart) >> yearRange
		1 * partToDateRangeProcessorMock.process(dateRangePart) >> dateRange

		then: 'all values are parsed'
		seriesTemplate.title == TITLE
		seriesTemplate.abbreviation == ABBREVIATION
		seriesTemplate.productionYearRange == yearRange
		seriesTemplate.originalRunDateRange == dateRange
	}


}