package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.template.starship_class.processor.StarshipClassTemplatePageProcessor
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class SpacecraftClassProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private StarshipClassTemplatePageProcessor starshipClassTemplatePageProcessorMock

	private StarshipClassTemplateProcessor starshipClassTemplateProcessorMock

	private SpacecraftClassProcessor spacecraftClassProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		starshipClassTemplatePageProcessorMock = Mock()
		starshipClassTemplateProcessorMock = Mock()
		spacecraftClassProcessor = new SpacecraftClassProcessor(pageHeaderProcessorMock, starshipClassTemplatePageProcessorMock,
				starshipClassTemplateProcessorMock)
	}

	void "converts PageHeader to SpacecraftClass"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		SpacecraftClass spacecraftClass = new SpacecraftClass()

		when:
		SpacecraftClass outputSpacecraftClass = spacecraftClassProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * starshipClassTemplatePageProcessorMock.process(page) >> starshipClassTemplate

		and:
		1 * starshipClassTemplateProcessorMock.process(starshipClassTemplate) >> spacecraftClass

		then: 'last processor output is returned'
		outputSpacecraftClass == spacecraftClass
	}

}
