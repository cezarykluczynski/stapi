package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.etl.common.mapper.GenderMapper
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender as EtlGender
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender as ModelGender
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.util.AbstractIndividualTest
import com.google.common.collect.Sets

class CharacterTemplateProcessorTest extends AbstractIndividualTest {

	private static final EtlGender ETL_GENDER = EtlGender.F
	private static final ModelGender MODEL_GENDER = ModelGender.F
	private static final BloodType BLOOD_TYPE = BloodType.B_NEGATIVE
	private static final MaritalStatus MARITAL_STATUS = MaritalStatus.MARRIED

	private UidGenerator uidGeneratorMock

	private GenderMapper genderMapperMock

	private CharacterTemplateProcessor characterTemplateProcessor

	void setup() {
		uidGeneratorMock = Mock()
		genderMapperMock = Mock()
		characterTemplateProcessor = new CharacterTemplateProcessor(uidGeneratorMock, genderMapperMock)
	}

	void "converts CharacterTemplate to Character"() {
		given:
		Page page = Mock()
		Performer performer1 = new Performer(id: 1L)
		Performer performer2 = new Performer(id: 2L)
		CharacterSpecies characterSpecies1 = Mock()
		CharacterSpecies characterSpecies2 = Mock()
		CharacterRelation characterRelation1 = Mock()
		CharacterRelation characterRelation2 = Mock()
		Title title1 = Mock()
		Title title2 = Mock()
		Occupation occupation1 = Mock()
		Occupation occupation2 = Mock()
		Organization organization1 = Mock()
		Organization organization2 = Mock()

		CharacterTemplate characterTemplate = new CharacterTemplate(
				page: page,
				name: NAME,
				gender: ETL_GENDER,
				yearOfBirth: YEAR_OF_BIRTH,
				monthOfBirth: MONTH_OF_BIRTH,
				dayOfBirth: DAY_OF_BIRTH,
				placeOfBirth: PLACE_OF_BIRTH,
				yearOfDeath: YEAR_OF_DEATH,
				monthOfDeath: MONTH_OF_DEATH,
				dayOfDeath: DAY_OF_DEATH,
				placeOfDeath: PLACE_OF_DEATH,
				height: HEIGHT,
				weight: WEIGHT,
				deceased: DECEASED,
				bloodType: BLOOD_TYPE,
				maritalStatus: MARITAL_STATUS,
				serialNumber: SERIAL_NUMBER,
				hologramActivationDate: HOLOGRAM_ACTIVATION_DATE,
				hologramStatus: HOLOGRAM_STATUS,
				hologramDateStatus: HOLOGRAM_DATE_STATUS,
				hologram: HOLOGRAM,
				fictionalCharacter: FICTIONAL_CHARACTER,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY,
				performers: Sets.newHashSet(performer1, performer2),
				characterSpecies: Sets.newHashSet(characterSpecies1, characterSpecies2),
				characterRelations: Sets.newHashSet(characterRelation1, characterRelation2),
				titles: Sets.newHashSet(title1, title2),
				occupations: Sets.newHashSet(occupation1, occupation2),
				organizations: Sets.newHashSet(organization1, organization2))

		when:
		Character character = characterTemplateProcessor.process(characterTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Character) >> UID
		1 * genderMapperMock.fromEtlToModel(ETL_GENDER) >> MODEL_GENDER
		0 * _
		character.page == page
		character.uid == UID
		character.name == NAME
		character.gender == MODEL_GENDER
		character.yearOfBirth == YEAR_OF_BIRTH
		character.monthOfBirth == MONTH_OF_BIRTH
		character.dayOfBirth == DAY_OF_BIRTH
		character.placeOfBirth == PLACE_OF_BIRTH
		character.yearOfDeath == YEAR_OF_DEATH
		character.monthOfDeath == MONTH_OF_DEATH
		character.dayOfDeath == DAY_OF_DEATH
		character.placeOfDeath == PLACE_OF_DEATH
		character.height == HEIGHT
		character.weight == WEIGHT
		character.deceased == DECEASED
		character.bloodType == BLOOD_TYPE
		character.maritalStatus == MARITAL_STATUS
		character.serialNumber == SERIAL_NUMBER
		character.hologramActivationDate == HOLOGRAM_ACTIVATION_DATE
		character.hologramStatus == HOLOGRAM_STATUS
		character.hologramDateStatus == HOLOGRAM_DATE_STATUS
		character.hologram == HOLOGRAM
		character.fictionalCharacter == FICTIONAL_CHARACTER
		character.mirror == MIRROR
		character.alternateReality == ALTERNATE_REALITY
		character.performers.size() == 2
		character.performers.contains performer1
		character.performers.contains performer2
		performer1.characters.contains character
		performer2.characters.contains character
		character.characterSpecies.contains characterSpecies1
		character.characterSpecies.contains characterSpecies2
		character.characterRelations.contains characterRelation1
		character.characterRelations.contains characterRelation2
		character.titles.contains title1
		character.titles.contains title2
		character.occupations.contains occupation1
		character.occupations.contains occupation2
		character.organizations.contains organization1
		character.organizations.contains organization2
	}

	void "when boolean flags are null, false is put into them"() {
		given:
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		Character character = characterTemplateProcessor.process(characterTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(null, Character) >> null
		1 * genderMapperMock.fromEtlToModel(null) >> null
		0 * _
		!character.hologram
		!character.fictionalCharacter
		!character.mirror
		!character.alternateReality
	}

}
