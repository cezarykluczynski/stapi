package com.cezarykluczynski.stapi.etl.template.fictional.processor

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.fictional.dto.FictionalTemplateParameter
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class FictionalTemplateCompositeEnrichingProcessorTest extends Specification {

	private static final Gender GENDER = Gender.F
	private static final Long PAGE_ID = 5L
	private static final String SPECIES = 'SPECIES'
	private static final String CREATOR = 'CREATOR'
	private static final String CHARACTER = 'CHARACTER'
	private static final String SPOUSE = 'SPOUSE'
	private static final String CHILDREN = 'CHILDREN'
	private static final String RELATIVE = 'RELATIVE'
	private static final String ACTOR = 'ACTOR'

	private PartToGenderProcessor partToGenderProcessorMock

	private CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessorMock

	private CharactersRelationsCache charactersRelationsCacheMock

	private CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessorMock

	private FictionalTemplateCompositeEnrichingProcessor fictionalTemplateCompositeEnrichingProcessor

	void setup() {
		partToGenderProcessorMock = Mock()
		characterSpeciesWikitextProcessorMock = Mock()
		charactersRelationsCacheMock = Mock()
		characterTemplateActorLinkingEnrichingProcessorMock = Mock()
		fictionalTemplateCompositeEnrichingProcessor = new FictionalTemplateCompositeEnrichingProcessor(partToGenderProcessorMock,
				characterSpeciesWikitextProcessorMock, charactersRelationsCacheMock, characterTemplateActorLinkingEnrichingProcessorMock)
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

	void "when creator part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.CREATOR, value: CREATOR)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_FICTIONAL
			assert characterRelationsMap.keySet()[0].parameterName == FictionalTemplateParameter.CREATOR
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when character part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.CHARACTER, value: CHARACTER)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_FICTIONAL
			assert characterRelationsMap.keySet()[0].parameterName == FictionalTemplateParameter.CHARACTER
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when spouse part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.SPOUSE, value: SPOUSE)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_FICTIONAL
			assert characterRelationsMap.keySet()[0].parameterName == FictionalTemplateParameter.SPOUSE
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when children part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.CHILDREN, value: CHILDREN)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_FICTIONAL
			assert characterRelationsMap.keySet()[0].parameterName == FictionalTemplateParameter.CHILDREN
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when relative part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.RELATIVE, value: RELATIVE)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_FICTIONAL
			assert characterRelationsMap.keySet()[0].parameterName == FictionalTemplateParameter.RELATIVE
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when actor key is found, CharacterTemplateActorLinkingEnrichingProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.ACTOR, value: ACTOR)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		fictionalTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * characterTemplateActorLinkingEnrichingProcessorMock.enrich(_ as EnrichablePair<String, CharacterTemplate>) >> {
			EnrichablePair<Template.Part, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == ACTOR
				assert enrichablePair.output == characterTemplate
		}
		0 * _
	}

}
