package com.cezarykluczynski.stapi.etl.material.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.material.entity.Material
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class MaterialProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private MaterialPageProcessor materialPageProcessorMock

	private MaterialProcessor materialProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		materialPageProcessorMock = Mock()
		materialProcessor = new MaterialProcessor(pageHeaderProcessorMock, materialPageProcessorMock)
	}

	void "converts PageHeader to Material"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		Material material = new Material()

		when:
		Material materialOutput = materialProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * materialPageProcessorMock.process(page) >> material

		then: 'last processor output is returned'
		materialOutput == material
	}

}
