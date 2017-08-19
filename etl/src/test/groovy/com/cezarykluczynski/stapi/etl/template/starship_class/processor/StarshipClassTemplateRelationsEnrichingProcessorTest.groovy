package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class StarshipClassTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String TYPE = 'TYPE'

	private StarshipClassSpacecraftTypeProcessor starshipClassSpacecraftTypeProcessorMock

	private StarshipClassTemplateRelationsEnrichingProcessor starshipClassTemplateRelationsEnrichingProcessor

	void setup() {
		starshipClassSpacecraftTypeProcessorMock = Mock()
		starshipClassTemplateRelationsEnrichingProcessor = new StarshipClassTemplateRelationsEnrichingProcessor(
				starshipClassSpacecraftTypeProcessorMock)
	}

	void "when active part is found, StarshipClassActivityPeriodProcessor is used to process it"() {
		given:
		Template sidebarStarshipClassTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipClassTemplateParameter.TYPE,
				value: TYPE)))
		StarshipClassTemplate starshipClassTemplate = new StarshipClassTemplate()
		SpacecraftType spacecraftType = Mock()

		when:
		starshipClassTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipClassTemplate, starshipClassTemplate))

		then:
		1 * starshipClassSpacecraftTypeProcessorMock.process(TYPE) >> spacecraftType
		0 * _
		starshipClassTemplate.spacecraftType == spacecraftType
	}

}
