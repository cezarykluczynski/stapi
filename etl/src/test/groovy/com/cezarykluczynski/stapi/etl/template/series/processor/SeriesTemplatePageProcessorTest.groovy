package com.cezarykluczynski.stapi.etl.template.series.processor

import com.cezarykluczynski.stapi.etl.common.service.CategoryFinder
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesRunDateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfEpisodesProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToDateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToYearRangeProcessor
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplateParameter
import com.cezarykluczynski.stapi.etl.template.series.service.SeriesPageFilter
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String ABBREVIATION = 'abbreviation'
	private static final Long PAGE_ID = 11L
	private static final String RAW_SEASONS = '3+'
	private static final Integer SEASONS_COUNT = 3
	private static final Integer EPISODES_COUNT = 27
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private SeriesPageFilter seriesPageFilterMock

	private PartToYearRangeProcessor partToYearRangeProcessorMock

	private PartToDateRangeProcessor partToDateRangeProcessorMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private SeriesTemplateCompanyProcessor seriesTemplateCompanyProcessorMock

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private NumberOfEpisodesProcessor numberOfEpisodesProcessorMock

	private SeriesRunDateEnrichingProcessor seriesRunDateEnrichingProcessorMock

	private CategoryFinder categoryFinderMock

	private SeriesTemplatePageProcessor seriesTemplatePageProcessor

	void setup() {
		seriesPageFilterMock = Mock()
		partToYearRangeProcessorMock = Mock()
		partToDateRangeProcessorMock = Mock()
		pageBindingServiceMock = Mock()
		templateFinderMock = Mock()
		seriesTemplateCompanyProcessorMock = Mock()
		numberOfPartsProcessorMock = Mock()
		numberOfEpisodesProcessorMock = Mock()
		seriesRunDateEnrichingProcessorMock = Mock()
		categoryFinderMock = Mock()
		seriesTemplatePageProcessor = new SeriesTemplatePageProcessor(seriesPageFilterMock, partToYearRangeProcessorMock,
				partToDateRangeProcessorMock, pageBindingServiceMock, templateFinderMock, seriesTemplateCompanyProcessorMock,
				numberOfPartsProcessorMock, numberOfEpisodesProcessorMock, seriesRunDateEnrichingProcessorMock, categoryFinderMock)
	}

	void "returns null when page should be filtered out"() {
		Page page = Mock()

		when:
		SeriesTemplate seriesTemplate = seriesTemplatePageProcessor.process(page)

		then:
		1 * seriesPageFilterMock.shouldBeFilteredOut(page) >> true
		seriesTemplate == null
	}

	void "missing template results in null SeriesTemplate"() {
		given:
		Page page = new Page()

		when:
		SeriesTemplate seriesTemplate = seriesTemplatePageProcessor.process(page)

		then:
		1 * seriesPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_SERIES) >> Optional.empty()
		seriesTemplate == null
	}

	void "valid template is parsed"() {
		given:
		Template.Part yearRangePart = new Template.Part(key: SeriesTemplateParameter.DATES)
		Template.Part dateRangePart = new Template.Part(key: SeriesTemplateParameter.RUN)
		Company productionCompany = Mock()
		Company originalBroadcaster = Mock()
		Template.Part productionCompanyPart = new Template.Part(key: SeriesTemplateParameter.STUDIO)
		Template.Part originalBroadcasterPart = new Template.Part(key: SeriesTemplateParameter.NETWORK)
		Template.Part seasonsCountPart = new Template.Part(key: SeriesTemplateParameter.SEASONS, value: RAW_SEASONS)
		Template.Part episodesCountPart = new Template.Part(key: SeriesTemplateParameter.EPISODES)
		YearRange yearRange = Mock()
		DateRange dateRange = Mock()

		Template template = new Template(title: TemplateTitle.SIDEBAR_SERIES, parts: Lists.newArrayList(
				new Template.Part(key: SeriesTemplateParameter.ABBR, value: ABBREVIATION),
				yearRangePart,
				dateRangePart,
				productionCompanyPart,
				originalBroadcasterPart,
				seasonsCountPart,
				episodesCountPart
		))
		Page page = new Page(
				title: TITLE,
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE,
				templates: Lists.newArrayList(template)
		)
		PageEntity pageEntity = new PageEntity()

		when:
		SeriesTemplate seriesTemplate = seriesTemplatePageProcessor.process(page)

		then: 'page is not supposed to be filtered out'
		1 * seriesPageFilterMock.shouldBeFilteredOut(page) >> false

		then: 'template is passed through TemplateFinder'
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_SERIES) >> Optional.of(template)

		then: 'year range and date range parsing is delegated'
		1 * partToYearRangeProcessorMock.process(yearRangePart) >> yearRange
		1 * partToDateRangeProcessorMock.process(dateRangePart) >> dateRange
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity

		then: 'company parsing is delegated'
		1 * seriesTemplateCompanyProcessorMock.process(productionCompanyPart) >> productionCompany
		1 * seriesTemplateCompanyProcessorMock.process(originalBroadcasterPart) >> originalBroadcaster

		then: 'seasons count is extracted'
		1 * numberOfPartsProcessorMock.process(RAW_SEASONS) >> SEASONS_COUNT

		then: 'episodes count is extracted'
		1 * numberOfEpisodesProcessorMock.process(episodesCountPart) >> EPISODES_COUNT

		then: 'original run date is set by enriching processor'
		1 * seriesRunDateEnrichingProcessorMock.enrich(_)

		then: 'companion series flag is decided'
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.STAR_TREK_COMPANION_SERIES]) >> true

		then: 'all values are parsed'
		seriesTemplate.title == TITLE
		seriesTemplate.page == pageEntity
		seriesTemplate.abbreviation == ABBREVIATION
		seriesTemplate.productionYearRange == yearRange
		seriesTemplate.originalRunDateRange == dateRange
		seriesTemplate.productionCompany == productionCompany
		seriesTemplate.originalBroadcaster == originalBroadcaster
		seriesTemplate.seasonsCount == SEASONS_COUNT
		seriesTemplate.episodesCount == EPISODES_COUNT
		seriesTemplate.companionSeries
	}

}
