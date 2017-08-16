package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class StarshipClassTemplateCompositeEnrichingProcessorTest extends Specification {

	private StarshipClassTemplateContentsEnrichingProcessor starshipClassTemplateContentsEnrichingProcessorMock

	private StarshipClassTemplateRelationsEnrichingProcessor starshipClassTemplateRelationsEnrichingProcessorMock

	private StarshipClassTemplateCompositeEnrichingProcessor starshipClassTemplateCompositeEnrichingProcessor

	void setup() {
		starshipClassTemplateContentsEnrichingProcessorMock = Mock()
		starshipClassTemplateRelationsEnrichingProcessorMock = Mock()
		starshipClassTemplateCompositeEnrichingProcessor = new StarshipClassTemplateCompositeEnrichingProcessor(
				starshipClassTemplateContentsEnrichingProcessorMock, starshipClassTemplateRelationsEnrichingProcessorMock)
	}

	void "passed enrichable pair to all dependencies"() {
		given:
		Template template = Mock()
		StarshipClassTemplate starshipClassTemplate = Mock()
		EnrichablePair<Template, StarshipClassTemplate> enrichablePair = EnrichablePair.of(template, starshipClassTemplate)

		when:
		starshipClassTemplateCompositeEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * starshipClassTemplateContentsEnrichingProcessorMock.enrich(enrichablePair)
		1 * starshipClassTemplateRelationsEnrichingProcessorMock.enrich(enrichablePair)
		0 * _
	}

}
