package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.magazine_series.processor.MagazineSeriesTemplatePageProcessor
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
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

	void "converts PageHeader to MagazineSeries"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate()
		MagazineSeries magazineSeries = new MagazineSeries()

		when:
		MagazineSeries magazineSeriesOutput = magazineSeriesProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * magazineSeriesTemplatePageProcessorMock.process(page) >> magazineSeriesTemplate

		and:
		1 * magazineSeriesTemplateProcessorMock.process(magazineSeriesTemplate) >> magazineSeries

		then: 'last processor output is returned'
		magazineSeriesOutput == magazineSeries
	}

}
