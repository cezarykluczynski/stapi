package com.cezarykluczynski.stapi.etl.template.book_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class BookSeriesTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'
	private static final Long PAGE_ID = 11L
	private static final Boolean E_BOOK_SERIES = RandomUtil.nextBoolean()
	private static final MediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private BookSeriesTemplatePartsEnrichingProcessor bookSeriesTemplatePartsEnrichingProcessorMock

	private BookSeriesTemplateFixedValuesEnrichingProcessor bookSeriesTemplateFixedValuesEnrichingProcessorMock

	private BookSeriesTemplateEBookSeriesProcessor bookSeriesTemplateEBookSeriesProcessorMock

	private BookSeriesTemplatePageProcessor bookSeriesTemplatePageProcessor

	void setup() {
		pageBindingServiceMock = Mock()
		templateFinderMock = Mock()
		bookSeriesTemplatePartsEnrichingProcessorMock = Mock()
		bookSeriesTemplateFixedValuesEnrichingProcessorMock = Mock()
		bookSeriesTemplateEBookSeriesProcessorMock = Mock()
		bookSeriesTemplatePageProcessor = new BookSeriesTemplatePageProcessor(pageBindingServiceMock, templateFinderMock,
				bookSeriesTemplatePartsEnrichingProcessorMock, bookSeriesTemplateFixedValuesEnrichingProcessorMock,
				bookSeriesTemplateEBookSeriesProcessorMock)
	}

	void "missing template results BookSeriesTemplate with only the title, source, and eBookSeries flag"() {
		given:
		Page page = new Page(
				title: TITLE,
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		ModelPage modelPage = new ModelPage()

		when:
		BookSeriesTemplate bookSeriesTemplate = bookSeriesTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * bookSeriesTemplateEBookSeriesProcessorMock.process(page) >> E_BOOK_SERIES
		1 * bookSeriesTemplateFixedValuesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<BookSeriesTemplate, BookSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == enrichablePair.output
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_NOVEL_SERIES) >> Optional.empty()
		0 * _
		bookSeriesTemplate.title == TITLE
		bookSeriesTemplate.page == modelPage
		bookSeriesTemplate.EBookSeries == E_BOOK_SERIES
		ReflectionTestUtils.getNumberOfNotNullFields(bookSeriesTemplate) == 4
	}

	void "clears title when it contains brackets"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)

		when:
		BookSeriesTemplate bookSeriesTemplate = bookSeriesTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(*_) >> Optional.empty()
		bookSeriesTemplate.title == TITLE
	}

	void "returns null when page is an effect of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		BookSeriesTemplate bookSeriesTemplate = bookSeriesTemplatePageProcessor.process(page)

		then:
		bookSeriesTemplate == null
	}

	void "when sidebar book series is found, enriching processor is called"() {
		given:
		Template.Part templatePart = Mock()
		List<Template.Part> templatePartList = Lists.newArrayList(templatePart)
		Page page = new Page(title: TITLE)
		Template sidebarBookSeriesTemplate = new Template(parts: templatePartList)

		when:
		bookSeriesTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page)
		1 * bookSeriesTemplateEBookSeriesProcessorMock.process(page)
		1 * bookSeriesTemplateFixedValuesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<BookSeriesTemplate, BookSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == enrichablePair.output
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_NOVEL_SERIES) >> Optional.of(sidebarBookSeriesTemplate)
		1 * bookSeriesTemplatePartsEnrichingProcessorMock.enrich(_) >> { EnrichablePair<List<Template.Part>, BookSeriesTemplate> enrichablePair ->
			assert enrichablePair.input == templatePartList
			assert enrichablePair.output != null
		}
		0 * _
	}

}
