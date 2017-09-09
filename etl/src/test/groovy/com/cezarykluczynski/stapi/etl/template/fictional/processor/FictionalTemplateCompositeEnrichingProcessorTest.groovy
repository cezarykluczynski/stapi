package com.cezarykluczynski.stapi.etl.template.fictional.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.fictional.dto.FictionalTemplateParameter
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class FictionalTemplateCompositeEnrichingProcessorTest extends Specification {

	private static final Gender GENDER = Gender.F
	private static final String SPECIES = 'SPECIES'

	private PartToGenderProcessor partToGenderProcessorMock

	private CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessorMock

	private CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessorMock

	private FictionalTemplateCompositeEnrichingProcessor fictionalTemplateCompositeEnrichingProcessor

	void setup() {
		partToGenderProcessorMock = Mock()
		characterSpeciesWikitextProcessorMock = Mock()
		characterTemplateActorLinkingEnrichingProcessorMock = Mock()
		fictionalTemplateCompositeEnrichingProcessor = new FictionalTemplateCompositeEnrichingProcessor(partToGenderProcessorMock,
				characterSpeciesWikitextProcessorMock, characterTemplateActorLinkingEnrichingProcessorMock)
	}

	void "sets fictionalCharacter flag"() {
		given:
		Template sidebarHologramTemplate = new Template()
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		characterTemplate.fictionalCharacter
	}

	void "when gender part is found, PartToGenderProcessor is used to process it"() {
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.GENDER)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * partToGenderProcessorMock.process(templatePart) >> GENDER
		0 * _
		characterTemplate.gender == GENDER
	}

	void "when species part is found, CharacterSpeciesWikitextProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.SPECIES, value: SPECIES)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate()
		CharacterSpecies characterSpecies1 = Mock()
		CharacterSpecies characterSpecies2 = Mock()

		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

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
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.ACTOR)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * characterTemplateActorLinkingEnrichingProcessorMock.enrich(_ as EnrichablePair<Template.Part, CharacterTemplate>) >> {
			EnrichablePair<Template.Part, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output == characterTemplate
		}
		0 * _
	}

}
