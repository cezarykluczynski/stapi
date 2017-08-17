package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class StarshipClassTemplateContentsEnrichingProcessorTest extends Specification {

	private static final String DECKS_STRING = '42'
	private static final Integer DECKS_INTEGER = 42
	private static final String SPEED = 'SPEED'
	private static final Boolean WARP_CAPABLE = RandomUtil.nextBoolean()

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private StarshipClassWarpCapableProcessor starshipClassWarpCapableProcessorMock

	private StarshipClassTemplateContentsEnrichingProcessor starshipClassTemplateContentsEnrichingProcessor

	void setup() {
		numberOfPartsProcessorMock = Mock()
		starshipClassWarpCapableProcessorMock = Mock()
		starshipClassTemplateContentsEnrichingProcessor = new StarshipClassTemplateContentsEnrichingProcessor(numberOfPartsProcessorMock,
				starshipClassWarpCapableProcessorMock)
	}

	void "when decks part is found, NumberOfPartsProcessor is used to process it"() {
		given:
		Template sidebarStarshipClassTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.DECKS,
				value: DECKS_STRING)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipClassTemplate, starshipClassTemplate))

		then:
		1 * numberOfPartsProcessorMock.process(DECKS_STRING) >> DECKS_INTEGER
		0 * _
		starshipClassTemplate.numberOfDecks == DECKS_INTEGER
	}

	void "when speed part is found, StarshipClassWarpCapableProcessor is used to process it"() {
		given:
		Template sidebarStarshipClassTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.SPEED,
				value: SPEED)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()

		when:
		starshipClassTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipClassTemplate, starshipClassTemplate))

		then:
		1 * starshipClassWarpCapableProcessorMock.process(SPEED) >> WARP_CAPABLE
		0 * _
		starshipClassTemplate.warpCapable == WARP_CAPABLE
	}

}
