package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class SpacecraftTypeProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private SpacecraftTypePageProcessor starshipClassTemplateProcessorMock

	private SpacecraftTypeProcessor spacecraftTypeProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		starshipClassTemplateProcessorMock = Mock()
		spacecraftTypeProcessor = new SpacecraftTypeProcessor(pageHeaderProcessorMock, starshipClassTemplateProcessorMock)
	}

	void "converts PageHeader to SpacecraftType"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		SpacecraftType spacecraftType = new SpacecraftType()

		when:
		SpacecraftType outputSpacecraftType = spacecraftTypeProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * starshipClassTemplateProcessorMock.process(page) >> spacecraftType

		then: 'last processor output is returned'
		outputSpacecraftType == spacecraftType
	}

}
