package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.PageName
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification
import com.cezarykluczynski.stapi.etl.common.dto.Range

class ComicSeriesTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Long PAGE_ID = 11L
	private static final MediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private ComicSeriesTemplatePartsEnrichingProcessor comicSeriesTemplatePartsEnrichingProcessorMock

	private ComicSeriesPublishedDateFixedValueProvider comicSeriesPublishedDateFixedValueProviderMock

	private ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock

	private ComicSeriesTemplatePageProcessor comicSeriesTemplatePageProcessor

	void setup() {
		pageBindingServiceMock = Mock(PageBindingService)
		templateFinderMock = Mock(TemplateFinder)
		comicSeriesTemplatePartsEnrichingProcessorMock = Mock(ComicSeriesTemplatePartsEnrichingProcessor)
		comicSeriesPublishedDateFixedValueProviderMock = Mock(ComicSeriesPublishedDateFixedValueProvider)
		comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock = Mock(ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor)
		comicSeriesTemplatePageProcessor = new ComicSeriesTemplatePageProcessor(pageBindingServiceMock, templateFinderMock,
				comicSeriesTemplatePartsEnrichingProcessorMock, comicSeriesPublishedDateFixedValueProviderMock,
				comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock)
	}

	void "returns null when page is 'DC Comics TNG timeline'"() {
		given:
		Page page = new Page(
				title: PageName.DC_COMICS_TNG_TIMELINE,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList())

		when:
		ComicSeriesTemplate comicSeriesTemplate = comicSeriesTemplatePageProcessor.process(page)

		then:
		comicSeriesTemplate == null
	}

	void "missing template results ComicSeriesTemplate with only the name and page"() {
		given:
		Page page = new Page(
				title: TITLE,
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		ModelPage modelPage = new ModelPage()

		when:
		ComicSeriesTemplate comicSeriesTemplate = comicSeriesTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_SERIES) >> Optional.empty()
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		0 * _
		comicSeriesTemplate.title == TITLE
		comicSeriesTemplate.page == modelPage
		ReflectionTestUtils.getNumberOfNotNullFields(comicSeriesTemplate) == 4
	}

	void "when fixed value for published dates is found, it is passed to enriching processor"() {
		given:
		Page page = new Page(
				title: TITLE,
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		ModelPage modelPage = new ModelPage()
		Range<DayMonthYear> dayMonthYearRange = Mock(Range)

		when:
		ComicSeriesTemplate comicSeriesTemplate = comicSeriesTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_SERIES) >> Optional.empty()
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(dayMonthYearRange)
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input == dayMonthYearRange
			assert enrichablePair.output != null
		}
		0 * _
		comicSeriesTemplate.title == TITLE
		comicSeriesTemplate.page == modelPage
		ReflectionTestUtils.getNumberOfNotNullFields(comicSeriesTemplate) == 4
	}

	void "sets productOfRedirect flag to true"() {
		given:
		Page page = new Page(
				title: TITLE,
				redirectPath: Lists.newArrayList(Mock(PageHeader)))
		ModelPage modelPage = new ModelPage()

		when:
		ComicSeriesTemplate comicSeriesTemplate = comicSeriesTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_SERIES) >> Optional.empty()
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		comicSeriesTemplate.productOfRedirect
	}

	void "sets productOfRedirect flag to false"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		ComicSeriesTemplate comicSeriesTemplate = comicSeriesTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_SERIES) >> Optional.empty()
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		!comicSeriesTemplate.productOfRedirect
	}

	void "when sidebar comic series is found, enriching processor is called"() {
		given:
		List<Template.Part> templatePartList = Lists.newArrayList(Mock(Template.Part))
		Page page = new Page(title: TITLE)
		Template sidebarComicSeriesTemplate = new Template(parts: templatePartList)

		when:
		comicSeriesTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page)
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC_SERIES) >> Optional.of(sidebarComicSeriesTemplate)
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		1 * comicSeriesTemplatePartsEnrichingProcessorMock.enrich(_) >> { EnrichablePair<List<Template.Part>, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input == templatePartList
			assert enrichablePair.output != null
		}
		0 * _
	}

}
