package com.cezarykluczynski.stapi.etl.magazineSeries.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.magazineSeries.dto.MagazineSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.magazineSeries.processor.MagazineSeriesTemplatePageProcessor
import com.cezarykluczynski.stapi.model.magazineSeries.entity.MagazineSeries
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class MagazineSeriesProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private MagazineSeriesTemplatePageProcessor magazineSeriesTemplatePageProcessorMock

	private MagazineSeriesTemplateProcessor magazineSeriesTemplateProcessorMock

	private MagazineSeriesProcessor magazineSeriesProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		magazineSeriesTemplatePageProcessorMock = Mock()
		magazineSeriesTemplateProcessorMock = Mock()
		magazineSeriesProcessor = new MagazineSeriesProcessor(pageHeaderProcessorMock, magazineSeriesTemplatePageProcessorMock,
				magazineSeriesTemplateProcessorMock)
	}

	void "converts PageHeader to Comics"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate()
		MagazineSeries magazineSeries = new MagazineSeries()

		when:
		MagazineSeries magazineSeriesOutput = magazineSeriesProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page
		1 * magazineSeriesTemplatePageProcessorMock.process(page) >> magazineSeriesTemplate
		1 * magazineSeriesTemplateProcessorMock.process(magazineSeriesTemplate) >> magazineSeries

		then: 'last processor output is returned'
		magazineSeriesOutput == magazineSeries
	}

}
