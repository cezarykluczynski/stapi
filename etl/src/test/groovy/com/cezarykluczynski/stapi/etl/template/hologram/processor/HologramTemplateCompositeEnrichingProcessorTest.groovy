package com.cezarykluczynski.stapi.etl.template.hologram.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.hologram.dto.HologramTemplateParameter
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class HologramTemplateCompositeEnrichingProcessorTest extends Specification {

	private static final String APPEARANCE = 'APPEARANCE'
	private static final String SPECIES = 'SPECIES'

	private CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessorMock

	private CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessorMock

	private HologramTemplateCompositeEnrichingProcessor hologramTemplateCompositeEnrichingProcessor

	void setup() {
		characterSpeciesWikitextProcessorMock = Mock()
		characterTemplateActorLinkingEnrichingProcessorMock = Mock()
		hologramTemplateCompositeEnrichingProcessor = new HologramTemplateCompositeEnrichingProcessor(characterSpeciesWikitextProcessorMock,
				characterTemplateActorLinkingEnrichingProcessorMock)
	}

	void "sets hologram flag"() {
		given:
		Template sidebarHologramTemplate = new Template()
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		characterTemplate.hologram
	}

	void "when appearance part is found, CharacterSpeciesWikitextProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: HologramTemplateParameter.APPEARANCE, value: APPEARANCE)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate()
		CharacterSpecies characterSpecies1 = Mock()
		CharacterSpecies characterSpecies2 = Mock()

		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * characterSpeciesWikitextProcessorMock.process(_ as Pair) >> { Pair<String, CharacterTemplate> pair ->
			assert pair.key == APPEARANCE
			assert pair.value == characterTemplate
			Sets.newHashSet(characterSpecies1, characterSpecies2)
		}
		0 * _
		characterTemplate.characterSpecies.contains characterSpecies1
		characterTemplate.characterSpecies.contains characterSpecies2
	}

	void "when species part is found, CharacterSpeciesWikitextProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: HologramTemplateParameter.SPECIES, value: SPECIES)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate()
		CharacterSpecies characterSpecies1 = Mock()
		CharacterSpecies characterSpecies2 = Mock()

		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * characterSpeciesWikitextProcessorMock.process(_ as Pair) >> { Pair<String, CharacterTemplate> pair ->
			assert pair.key == SPECIES
			assert pair.value == characterTemplate
			Sets.newHashSet(characterSpecies1, characterSpecies2)
		}
		0 * _
		characterTemplate.characterSpecies.contains characterSpecies1
		characterTemplate.characterSpecies.contains characterSpecies2
	}

	void "when actor key is found, CharacterTemplateActorLinkingEnrichingProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: HologramTemplateParameter.ACTOR)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * characterTemplateActorLinkingEnrichingProcessorMock.enrich(_ as EnrichablePair<Template.Part, CharacterTemplate>) >> {
			EnrichablePair<Template.Part, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output == characterTemplate
		}
		0 * _
	}

}
