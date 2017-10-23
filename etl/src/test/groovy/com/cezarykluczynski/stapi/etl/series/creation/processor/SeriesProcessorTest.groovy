package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import com.cezarykluczynski.stapi.etl.template.series.processor.SeriesTemplatePageProcessor
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class SeriesProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private SeriesTemplatePageProcessor pageProcessorMock

	private SeriesTemplateProcessor seriesTemplateProcessorMock

	private SeriesProcessor seriesProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		pageProcessorMock = Mock()
		seriesTemplateProcessorMock = Mock()
		seriesProcessor = new SeriesProcessor(pageHeaderProcessorMock, pageProcessorMock, seriesTemplateProcessorMock)
	}

	void "converts PageHeader to Series"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		SeriesTemplate seriesTemplate = new SeriesTemplate()
		Series series = new Series()

		when:
		Series seriesOutput = seriesProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * pageProcessorMock.process(page) >> seriesTemplate

		and:
		1 * seriesTemplateProcessorMock.process(seriesTemplate) >> series

		then: 'last processor output is returned'
		seriesOutput == series
	}

}
