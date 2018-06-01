package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationsMap
import com.cezarykluczynski.stapi.etl.character.common.service.CharactersRelationsCache
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateActorLinkingEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter
import com.cezarykluczynski.stapi.etl.template.individual.processor.species.CharacterSpeciesWikitextProcessor
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class IndividualTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String VALUE = 'VALUE'
	private static final Integer HEIGHT = 183
	private static final Integer WEIGHT = 77
	private static final Integer YEAR = 1970
	private static final Integer MONTH = 10
	private static final Integer DAY = 7
	private static final Long PAGE_ID = 5L
	private static final Gender GENDER = Gender.F
	private static final String FATHER = 'FATHER'
	private static final String MOTHER = 'MOTHER'
	private static final String OWNER = 'OWNER'
	private static final String SIBLING = 'SIBLING'
	private static final String RELATIVE = 'RELATIVE'
	private static final String SPOUSE = 'SPOUSE'
	private static final String CHILDREN = 'CHILDREN'
	private static final String SERIAL_NUMBER = 'SERIAL_NUMBER'
	private static final MaritalStatus MARITAL_STATUS = MaritalStatus.MARRIED
	private static final BloodType BLOOD_TYPE = BloodType.B_NEGATIVE

	private PartToGenderProcessor partToGenderProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private IndividualLifeBoundaryProcessor individualLifeBoundaryProcessorMock

	private CharacterTemplateActorLinkingEnrichingProcessor characterTemplateActorLinkingEnrichingProcessorMock

	private IndividualHeightProcessor individualHeightProcessorMock

	private IndividualWeightProcessor individualWeightProcessorMock

	private IndividualSerialNumberProcessor individualSerialNumberProcessorMock

	private IndividualBloodTypeProcessor individualBloodTypeProcessorMock

	private CharactersRelationsCache charactersRelationsCacheMock

	private MaritalStatusProcessor maritalStatusProcessorMock

	private CharacterSpeciesWikitextProcessor characterSpeciesWikitextProcessorMock

	private IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessor

	void setup() {
		partToGenderProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		individualLifeBoundaryProcessorMock = Mock()
		characterTemplateActorLinkingEnrichingProcessorMock = Mock()
		individualHeightProcessorMock = Mock()
		individualWeightProcessorMock = Mock()
		individualSerialNumberProcessorMock = Mock()
		individualBloodTypeProcessorMock = Mock()
		charactersRelationsCacheMock = Mock()
		maritalStatusProcessorMock = Mock()
		characterSpeciesWikitextProcessorMock = Mock()
		individualTemplatePartsEnrichingProcessor = new IndividualTemplatePartsEnrichingProcessor(partToGenderProcessorMock,
				wikitextToEntitiesProcessorMock, individualLifeBoundaryProcessorMock, characterTemplateActorLinkingEnrichingProcessorMock,
				individualHeightProcessorMock, individualWeightProcessorMock, individualSerialNumberProcessorMock, individualBloodTypeProcessorMock,
				charactersRelationsCacheMock, maritalStatusProcessorMock, characterSpeciesWikitextProcessorMock)
	}

	void "sets gender from PartToGenderProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.GENDER)
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * partToGenderProcessorMock.process(templatePart) >> GENDER
		0 * _
		characterTemplate.gender == GENDER
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 7
	}

	void "adds all Organizations from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.AFFILIATION, value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()
		Organization organization1 = Mock()
		Organization organization2 = Mock()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOrganizations(VALUE) >> Lists.newArrayList(organization1, organization2)
		0 * _
		characterTemplate.organizations.contains organization1
		characterTemplate.organizations.contains organization2
	}

	void "adds all Titles from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.RANK, value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()
		Title title1 = Mock()
		Title title2 = Mock()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findTitles(VALUE) >> Lists.newArrayList(title1, title2)
		0 * _
		characterTemplate.titles.contains title1
		characterTemplate.titles.contains title2
	}

	void "adds all Occupations from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.OCCUPATION, value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()
		Occupation occupation1 = Mock()
		Occupation occupation2 = Mock()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findOccupations(VALUE) >> Lists.newArrayList(occupation1, occupation2)
		0 * _
		characterTemplate.occupations.contains occupation1
		characterTemplate.occupations.contains occupation2
	}

	void "when actor key is found, CharacterTemplateActorLinkingEnrichingProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.ACTOR, value: VALUE)
		CharacterTemplate characterTemplateInActorLinkingProcessor = null
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * characterTemplateActorLinkingEnrichingProcessorMock.enrich(_ as EnrichablePair<String, CharacterTemplate>) >> {
			EnrichablePair<String, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == VALUE
				assert enrichablePair.output == characterTemplate
				characterTemplateInActorLinkingProcessor = enrichablePair.output
		}
		0 * _
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 6
		characterTemplateInActorLinkingProcessor == characterTemplate
	}

	void "sets height from IndividualHeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplateParameter.HEIGHT,
				value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * individualHeightProcessorMock.process(VALUE) >> HEIGHT
		0 * _
		characterTemplate.height == HEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 7
	}

	void "sets weight from IndividualWeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplateParameter.WEIGHT,
				value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * individualWeightProcessorMock.process(VALUE) >> WEIGHT
		0 * _
		characterTemplate.weight == WEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 7
	}

	void "sets serial number from IndividualSerialNumberProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplateParameter.SERIAL_NUMBER,
				value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * individualSerialNumberProcessorMock.process(VALUE) >> SERIAL_NUMBER
		0 * _
		characterTemplate.serialNumber == SERIAL_NUMBER
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 7
	}

	void "sets birth values from IndividualLifeBoundaryProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplateParameter.BORN,
				value: VALUE)
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO(
				year: YEAR,
				month: MONTH,
				day: DAY)
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * individualLifeBoundaryProcessorMock.process(VALUE) >> individualLifeBoundaryDTO
		0 * _
		characterTemplate.yearOfBirth == YEAR
		characterTemplate.monthOfBirth == MONTH
		characterTemplate.dayOfBirth == DAY
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 9
	}

	void "sets death values from IndividualLifeBoundaryProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplateParameter.DIED,
				value: VALUE)
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO(
				year: YEAR,
				month: MONTH,
				day: DAY)
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * individualLifeBoundaryProcessorMock.process(VALUE) >> individualLifeBoundaryDTO
		0 * _
		characterTemplate.yearOfDeath == YEAR
		characterTemplate.monthOfDeath == MONTH
		characterTemplate.dayOfDeath == DAY
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 9
	}

	void "when father part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.FATHER, value: FATHER)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_INDIVIDUAL
			assert characterRelationsMap.keySet()[0].parameterName == IndividualTemplateParameter.FATHER
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when mother part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.MOTHER, value: MOTHER)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_INDIVIDUAL
			assert characterRelationsMap.keySet()[0].parameterName == IndividualTemplateParameter.MOTHER
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when owner part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.OWNER, value: OWNER)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_INDIVIDUAL
			assert characterRelationsMap.keySet()[0].parameterName == IndividualTemplateParameter.OWNER
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when sibling part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.SIBLING, value: SIBLING)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_INDIVIDUAL
			assert characterRelationsMap.keySet()[0].parameterName == IndividualTemplateParameter.SIBLING
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when relative part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.RELATIVE, value: RELATIVE)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_INDIVIDUAL
			assert characterRelationsMap.keySet()[0].parameterName == IndividualTemplateParameter.RELATIVE
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when spouse part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.SPOUSE, value: SPOUSE)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_INDIVIDUAL
			assert characterRelationsMap.keySet()[0].parameterName == IndividualTemplateParameter.SPOUSE
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "when children part is found, it is put into CharactersRelationsCache"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.CHILDREN, value: CHILDREN)
		CharacterTemplate characterTemplate = new CharacterTemplate(page: new Page(pageId: PAGE_ID))
		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * charactersRelationsCacheMock.put(PAGE_ID, _ as CharacterRelationsMap) >> { Long pageId, CharacterRelationsMap characterRelationsMap ->
			assert characterRelationsMap.size() == 1
			assert characterRelationsMap.keySet()[0].sidebarTemplateTitle == TemplateTitle.SIDEBAR_INDIVIDUAL
			assert characterRelationsMap.keySet()[0].parameterName == IndividualTemplateParameter.CHILDREN
			assert characterRelationsMap.values()[0] == templatePart
		}
		0 * _
	}

	void "sets marital status from MaritalStatusProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplateParameter.MARITAL_STATUS,
				value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * maritalStatusProcessorMock.process(VALUE) >> MARITAL_STATUS
		0 * _
		characterTemplate.maritalStatus == MARITAL_STATUS
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 7
	}

	void "sets blood type from BloodTypeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: IndividualTemplateParameter.BLOOD_TYPE,
				value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * individualBloodTypeProcessorMock.process(VALUE) >> BLOOD_TYPE
		0 * _
		characterTemplate.bloodType == BLOOD_TYPE
		ReflectionTestUtils.getNumberOfNotNullFields(characterTemplate) == 7
	}

	void "adds all CharacterSpecies from CharacterSpeciesWikitextProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.SPECIES, value: VALUE)
		CharacterTemplate characterTemplate = new CharacterTemplate()
		CharacterSpecies characterSpecies1 = Mock()
		CharacterSpecies characterSpecies2 = Mock()

		when:
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), characterTemplate))

		then:
		1 * characterSpeciesWikitextProcessorMock.process(_ as Pair) >> { Pair<String, CharacterTemplate> pair ->
			assert pair.key == VALUE
			assert pair.value == characterTemplate
			Sets.newHashSet(characterSpecies1, characterSpecies2)
		}
		0 * _
		characterTemplate.characterSpecies.contains characterSpecies1
		characterTemplate.characterSpecies.contains characterSpecies2
	}

}
