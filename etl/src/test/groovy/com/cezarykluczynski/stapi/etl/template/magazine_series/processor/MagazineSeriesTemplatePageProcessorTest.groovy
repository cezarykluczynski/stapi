package com.cezarykluczynski.stapi.etl.template.magazine_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.magazine.creation.service.MagazineCandidatePageGatheringService
import com.cezarykluczynski.stapi.etl.magazine_series.creation.service.MagazineSeriesDetector
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearRange
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.magazine_series.service.MagazineSeriesPageFilter
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.cache.PageCacheStorage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class MagazineSeriesTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_MAGAZINE = 'TITLE (magazine)'
	private static final Integer ISSUES_INTEGER = 12
	private static final Integer MONTH_FROM = 6
	private static final Integer YEAR_FROM = 1979
	private static final Integer MONTH_TO = 11
	private static final Integer YEAR_TO = 1981

	private TemplateFinder templateFinderMock

	private MagazineSeriesDetector magazineSeriesDetectorMock

	private MagazineCandidatePageGatheringService magazineCandidatePageGatheringServiceMock

	private PageCacheStorage pageCacheStorageMock

	private PageBindingService pageBindingServiceMock

	private MagazineSeriesTemplatePartsEnrichingProcessor magazineSeriesTemplatePartsEnrichingProcessorMock

	private MagazineSeriesPageFilter magazineSeriesPageFilterMock

	private MagazineSeriesPublicationDatesFixedValueProvider magazineSeriesPublicationDatesFixedValueProviderMock

	private MagazineSeriesNumberOfIssuesFixedValueProvider magazineSeriesNumberOfIssuesFixedValueProviderMock

	private MagazineSeriesTemplatePageProcessor magazineSeriesTemplatePageProcessor

	void setup() {
		templateFinderMock = Mock()
		magazineSeriesDetectorMock = Mock()
		magazineCandidatePageGatheringServiceMock = Mock()
		pageCacheStorageMock = Mock()
		pageBindingServiceMock = Mock()
		magazineSeriesTemplatePartsEnrichingProcessorMock = Mock()
		magazineSeriesPageFilterMock = Mock()
		magazineSeriesPublicationDatesFixedValueProviderMock = Mock()
		magazineSeriesNumberOfIssuesFixedValueProviderMock = Mock()
		magazineSeriesTemplatePageProcessor = new MagazineSeriesTemplatePageProcessor(templateFinderMock, magazineSeriesDetectorMock,
				magazineCandidatePageGatheringServiceMock, pageCacheStorageMock, pageBindingServiceMock,
				magazineSeriesTemplatePartsEnrichingProcessorMock, magazineSeriesPageFilterMock,
				magazineSeriesPublicationDatesFixedValueProviderMock, magazineSeriesNumberOfIssuesFixedValueProviderMock)
	}

	void "returns null when page should be filtered out"() {
		given:
		Page page = new Page()

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * magazineSeriesPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		magazineSeriesTemplate == null
	}

	@SuppressWarnings('BracesForMethod')
	void """returns null when sidebar magazine series template is not found, and MagazineSeriesDetector does not detect magazine series,
			and sidebar magazine template is found, and adds page to MagazineCandidatePageGatheringService, and to PageCacheStorage"""() {
		given:
		Page page = new Page(title: TITLE)
		Template sidebarMagazine = Mock()

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * magazineSeriesPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE) >> Optional.of(sidebarMagazine)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_REFERENCE_BOOK) >> Optional.empty()
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> false
		1 * magazineCandidatePageGatheringServiceMock.addCandidate(page)
		1 * pageCacheStorageMock.put(page)
		0 * _
		magazineSeriesTemplate == null
	}

	@SuppressWarnings('BracesForMethod')
	void """returns null when sidebar magazine series template is not found, and MagazineSeriesDetector does not detect magazine series,
			and sidebar reference book template is found, and adds page to MagazineCandidatePageGatheringService, and to PageCacheStorage"""() {
		given:
		Page page = new Page(title: TITLE)
		Template sidebarReferenceBook = Mock()

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * magazineSeriesPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_REFERENCE_BOOK) >> Optional.of(sidebarReferenceBook)
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> false
		1 * magazineCandidatePageGatheringServiceMock.addCandidate(page)
		1 * pageCacheStorageMock.put(page)
		0 * _
		magazineSeriesTemplate == null
	}

	void "does not return null when no templates are found, but MagazineSeriesDetector does detect magazine series"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * magazineSeriesPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_REFERENCE_BOOK) >> Optional.empty()
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> true
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		0 * _
		magazineSeriesTemplate != null
	}

	void "clears title when it contains '(magazine)'"() {
		given:
		Page page = new Page(title: TITLE_MAGAZINE)
		ModelPage modelPage = new ModelPage()
		Template sidebarMagazineTemplate = Mock()
		List<Template.Part> templatePartList = Mock()

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * magazineSeriesPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.of(sidebarMagazineTemplate)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_REFERENCE_BOOK) >> Optional.empty()
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * sidebarMagazineTemplate.parts >> templatePartList
		1 * magazineSeriesTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			assert enrichablePair.input == templatePartList
			assert enrichablePair.output != null
		}
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		0 * _
		magazineSeriesTemplate.title == TITLE
	}

	void "adds publication dates from MagazineSeriesPublicationDatesFixedValueProvider"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()
		MonthYearRange monthYearRange = MonthYearRange.of(MonthYear.of(MONTH_FROM, YEAR_FROM), MonthYear.of(MONTH_TO, YEAR_TO))

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * magazineSeriesPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_REFERENCE_BOOK) >> Optional.empty()
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> true
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(monthYearRange)
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.notFound()
		0 * _
		magazineSeriesTemplate.publishedMonthFrom == MONTH_FROM
		magazineSeriesTemplate.publishedYearFrom == YEAR_FROM
		magazineSeriesTemplate.publishedMonthTo == MONTH_TO
		magazineSeriesTemplate.publishedYearTo == YEAR_TO
	}

	void "adds number of issues from MagazineSeriesNumberOfIssuesFixedValueProvider"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * magazineSeriesPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_REFERENCE_BOOK) >> Optional.empty()
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> true
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * magazineSeriesPublicationDatesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		1 * magazineSeriesNumberOfIssuesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(ISSUES_INTEGER)
		0 * _
		magazineSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

}
