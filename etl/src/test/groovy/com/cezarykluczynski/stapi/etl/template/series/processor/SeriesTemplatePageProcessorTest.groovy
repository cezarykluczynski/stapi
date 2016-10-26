package com.cezarykluczynski.stapi.etl.template.series.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToDateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToYearRangeProcessor
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import com.cezarykluczynski.stapi.util.constant.TemplateNames
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'abbreviation'

	private PartToYearRangeProcessor partToYearRangeProcessorMock

	private PartToDateRangeProcessor partToDateRangeProcessorMock

	private SeriesTemplatePageProcessor seriesTemplatePageProcessor

	def setup() {
		partToYearRangeProcessorMock = Mock(PartToYearRangeProcessor)
		partToDateRangeProcessorMock = Mock(PartToDateRangeProcessor)
		seriesTemplatePageProcessor = new SeriesTemplatePageProcessor(partToYearRangeProcessorMock, partToDateRangeProcessorMock)
	}

	def "missing template results in null SeriesTemplate"() {
		given:
		Page page = new Page()

		when:
		SeriesTemplate seriesTemplate = seriesTemplatePageProcessor.process(page)

		then:
		seriesTemplate == null
	}

	def "valid template is parsed"() {
		given:
		Template.Part yearRangePart = new Template.Part(key: SeriesTemplatePageProcessor.DATES)
		Template.Part dateRangePart = new Template.Part(key: SeriesTemplatePageProcessor.RUN)
		YearRange yearRange = Mock(YearRange)
		DateRange dateRange = Mock(DateRange)

		Page page = new Page(
				title: TITLE,
				templates: Lists.newArrayList(
						new Template(title: TemplateNames.SIDEBAR_SERIES, parts: Lists.newArrayList(
								new Template.Part(key: SeriesTemplatePageProcessor.ABBR, value: ABBREVIATION),
								yearRangePart,
								dateRangePart
						)
				)
		))

		when:
		SeriesTemplate seriesTemplate = seriesTemplatePageProcessor.process(page)

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
