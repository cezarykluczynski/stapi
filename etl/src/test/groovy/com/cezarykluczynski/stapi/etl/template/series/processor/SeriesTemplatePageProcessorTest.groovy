package com.cezarykluczynski.stapi.etl.template.series.processor

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToDateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToYearRangeProcessor
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'abbreviation'
	private static final Long PAGE_ID = 11L
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private PartToYearRangeProcessor partToYearRangeProcessorMock

	private PartToDateRangeProcessor partToDateRangeProcessorMock

	private PageBindingService pageBindingServiceMock

	private SeriesTemplatePageProcessor seriesTemplatePageProcessor

	def setup() {
		partToYearRangeProcessorMock = Mock(PartToYearRangeProcessor)
		partToDateRangeProcessorMock = Mock(PartToDateRangeProcessor)
		pageBindingServiceMock = Mock(PageBindingService)
		seriesTemplatePageProcessor = new SeriesTemplatePageProcessor(partToYearRangeProcessorMock,
				partToDateRangeProcessorMock, pageBindingServiceMock)
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
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE,
				templates: Lists.newArrayList(
						new Template(title: TemplateName.SIDEBAR_SERIES, parts: Lists.newArrayList(
								new Template.Part(key: SeriesTemplatePageProcessor.ABBR, value: ABBREVIATION),
								yearRangePart,
								dateRangePart
						)
				)
		))
		PageEntity pageEntity = new PageEntity()

		when:
		SeriesTemplate seriesTemplate = seriesTemplatePageProcessor.process(page)

		then: 'year range and date range parsing is delegated'
		1 * partToYearRangeProcessorMock.process(yearRangePart) >> yearRange
		1 * partToDateRangeProcessorMock.process(dateRangePart) >> dateRange
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity

		then: 'all values are parsed'
		seriesTemplate.title == TITLE
		seriesTemplate.page == pageEntity
		seriesTemplate.abbreviation == ABBREVIATION
		seriesTemplate.productionYearRange == yearRange
		seriesTemplate.originalRunDateRange == dateRange
	}


}
