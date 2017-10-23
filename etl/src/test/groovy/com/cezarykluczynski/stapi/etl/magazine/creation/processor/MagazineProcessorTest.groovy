package com.cezarykluczynski.stapi.etl.magazine.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate
import com.cezarykluczynski.stapi.etl.template.magazine.processor.MagazineTemplatePageProcessor
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class MagazineProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private MagazineTemplatePageProcessor magazineTemplatePageProcessorMock

	private MagazineTemplateProcessor magazineTemplateProcessorMock

	private MagazineProcessor magazineProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		magazineTemplatePageProcessorMock = Mock()
		magazineTemplateProcessorMock = Mock()
		magazineProcessor = new MagazineProcessor(pageHeaderProcessorMock, magazineTemplatePageProcessorMock, magazineTemplateProcessorMock)
	}

	void "converts PageHeader to Magazine"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		MagazineTemplate magazineTemplate = new MagazineTemplate()
		Magazine magazine = new Magazine()

		when:
		Magazine magazineOutput = magazineProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * magazineTemplatePageProcessorMock.process(page) >> magazineTemplate

		and:
		1 * magazineTemplateProcessorMock.process(magazineTemplate) >> magazine

		then: 'last processor output is returned'
		magazineOutput == magazine
	}

}
