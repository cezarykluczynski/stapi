package com.cezarykluczynski.stapi.etl.comic_series.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.comic_series.processor.ComicSeriesTemplatePageProcessor
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class ComicSeriesProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ComicSeriesTemplatePageProcessor comicSeriesTemplatePageProcessorMock

	private ComicSeriesTemplateProcessor comicSeriesTemplateProcessorMock

	private ComicSeriesProcessor comicSeriesProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		comicSeriesTemplatePageProcessorMock = Mock()
		comicSeriesTemplateProcessorMock = Mock()
		comicSeriesProcessor = new ComicSeriesProcessor(pageHeaderProcessorMock, comicSeriesTemplatePageProcessorMock,
				comicSeriesTemplateProcessorMock)
	}

	void "converts PageHeader to ComicSeries"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()
		ComicSeries comicSeries = new ComicSeries()

		when:
		ComicSeries comicSeriesOutput = comicSeriesProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * comicSeriesTemplatePageProcessorMock.process(page) >> comicSeriesTemplate

		and:
		1 * comicSeriesTemplateProcessorMock.process(comicSeriesTemplate) >> comicSeries

		then: 'last processor output is returned'
		comicSeriesOutput == comicSeries
	}

}
