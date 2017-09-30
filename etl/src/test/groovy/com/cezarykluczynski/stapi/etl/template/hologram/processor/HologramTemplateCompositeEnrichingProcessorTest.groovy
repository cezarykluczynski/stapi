package com.cezarykluczynski.stapi.etl.template.hologram.processor

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.DateStatusProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.StatusProcessor
import com.cezarykluczynski.stapi.etl.template.fictional.dto.FictionalTemplateParameter
import com.cezarykluczynski.stapi.etl.template.hologram.dto.HologramTemplateParameter
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class HologramTemplateCompositeEnrichingProcessorTest extends Specification {

	private static final Long PAGE_ID = 5L
	private static final String APPEARANCE = 'APPEARANCE'
	private static final String SPECIES = 'SPECIES'
	private static final String CREATOR = 'CREATOR'
	private static final String SPOUSE = 'SPOUSE'
	private static final String CHILDREN = 'CHILDREN'
	private static final String RELATIVE = 'RELATIVE'
	private static final String ACTIVATION_DATE_INPUT = 'ACTIVATION_DATE_INPUT'
	private static final String ACTIVATION_DATE_OUTPUT = 'ACTIVATION_DATE_OUTPUT'
	private static final String STATUS_INPUT = 'STATUS_INPUT'
	private static final String STATUS_OUTPUT = 'STATUS_OUTPUT'
	private static final String DATE_STATUS_INPUT = 'DATE_STATUS_INPUT'
	private static final String DATE_STATUS_OUTPUT = 'DATE_STATUS_OUTPUT'
	private static final String ACTOR = 'ACTOR'

	private CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessorMock

	private CharactersRelationsCache charactersRelationsCacheMock

	private HologramActivationDateProcessor hologramActivationDateProcessorMock

	private StatusProcessor statusProcessorMock

	private DateStatusProcessor dateStatusProcessorMock

	private CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessorMock

	private HologramTemplateCompositeEnrichingProcessor hologramTemplateCompositeEnrichingProcessor

	void setup() {
		characterSpeciesWikitextProcessorMock = Mock()
		charactersRelationsCacheMock = Mock()
		hologramActivationDateProcessorMock = Mock()
		statusProcessorMock = Mock()
		dateStatusProcessorMock = Mock()
		characterTemplateActorLinkingEnrichingProcessorMock = Mock()
		hologramTemplateCompositeEnrichingProcessor = new HologramTemplateCompositeEnrichingProcessor(characterSpeciesWikitextProcessorMock,
				charactersRelationsCacheMock, hologramActivationDateProcessorMock, statusProcessorMock, dateStatusProcessorMock,
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

	void "when creator part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: FictionalTemplateParameter.CREATOR, value: CREATOR)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_HOLOGRAM
			assert characterRelationsMap.keySet()[0].parameterName == HologramTemplateParameter.CREATOR
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
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_HOLOGRAM
			assert characterRelationsMap.keySet()[0].parameterName == HologramTemplateParameter.SPOUSE
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
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_HOLOGRAM
			assert characterRelationsMap.keySet()[0].parameterName == HologramTemplateParameter.CHILDREN
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
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_HOLOGRAM
			assert characterRelationsMap.keySet()[0].parameterName == HologramTemplateParameter.RELATIVE
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when activation date part is found, HologramActivationDateProcessor is used to process it"() {
		given:
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(
				new Template.Part(
						key: HologramTemplateParameter.ACTIVATION_DATE,
						value: ACTIVATION_DATE_INPUT)))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * hologramActivationDateProcessorMock.process(ACTIVATION_DATE_INPUT) >> ACTIVATION_DATE_OUTPUT
		0 * _
		characterTemplate.hologramActivationDate == ACTIVATION_DATE_OUTPUT
	}

	void "when status part is found, StatusProcessor is used to process it"() {
		given:
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(
				new Template.Part(
						key: HologramTemplateParameter.STATUS,
						value: STATUS_INPUT)))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * statusProcessorMock.process(STATUS_INPUT) >> STATUS_OUTPUT
		0 * _
		characterTemplate.hologramStatus == STATUS_OUTPUT
	}

	void "when date status part is found, DateStatusProcessor is used to process it"() {
		given:
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(
				new Template.Part(
						key: HologramTemplateParameter.DATE_STATUS,
						value: DATE_STATUS_INPUT)))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * dateStatusProcessorMock.process(DATE_STATUS_INPUT) >> DATE_STATUS_OUTPUT
		0 * _
		characterTemplate.hologramDateStatus == DATE_STATUS_OUTPUT
	}

	void "when actor key is found, CharacterTemplateActorLinkingEnrichingProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: HologramTemplateParameter.ACTOR, value: ACTOR)
		Template sidebarHologramTemplate = new Template(parts: Lists.newArrayList(templatePart))
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		hologramTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(sidebarHologramTemplate, characterTemplate))

		then:
		1 * characterTemplateActorLinkingEnrichingProcessorMock.enrich(_ as EnrichablePair<String, CharacterTemplate>) >> {
			EnrichablePair<Template.Part, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == ACTOR
				assert enrichablePair.output == characterTemplate
		}
		0 * _
	}

}
