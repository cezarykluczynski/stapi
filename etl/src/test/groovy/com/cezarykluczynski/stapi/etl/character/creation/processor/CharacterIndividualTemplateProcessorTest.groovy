package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.etl.common.mapper.GenderMapper
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender as EtlGender
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender as ModelGender
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.util.AbstractIndividualTest
import com.google.common.collect.Sets

class CharacterIndividualTemplateProcessorTest extends AbstractIndividualTest {

	private static final Page PAGE = new Page(id: 1L)
	private static final EtlGender ETL_GENDER = EtlGender.F
	private static final ModelGender MODEL_GENDER = ModelGender.F
	private static final BloodType BLOOD_TYPE = BloodType.B_NEGATIVE
	private static final MaritalStatus MARITAL_STATUS = MaritalStatus.MARRIED

	private UidGenerator uidGeneratorMock

	private GenderMapper genderMapperMock

	private CharacterIndividualTemplateProcessor characterIndividualTemplateProcessor

	void setup() {
		uidGeneratorMock = Mock()
		genderMapperMock = Mock()
		characterIndividualTemplateProcessor = new CharacterIndividualTemplateProcessor(uidGeneratorMock, genderMapperMock)
	}

	void "converts IndividualTemplate to Character"() {
		given:
		Performer performer1 = new Performer(id: 11L)
		Performer performer2 = new Performer(id: 12L)
		CharacterSpecies characterSpecies1 = new CharacterSpecies(id: 21L)
		CharacterSpecies characterSpecies2 = new CharacterSpecies(id: 22L)

		IndividualTemplate individualTemplate = new IndividualTemplate(
				page: PAGE,
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
				hologram: HOLOGRAM,
				fictionalCharacter: FICTIONAL_CHARACTER,
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY,
				performers: Sets.newHashSet(performer1, performer2),
				characterSpecies: Sets.newHashSet(characterSpecies1, characterSpecies2)
		)
		when:
		Character character = characterIndividualTemplateProcessor.process(individualTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(PAGE, Character) >> UID
		1 * genderMapperMock.fromEtlToModel(ETL_GENDER) >> MODEL_GENDER
		0 * _
		character.page == PAGE
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
	}

	void "when boolean flags are null, false is put into them"() {
		given:
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		Character character = characterIndividualTemplateProcessor.process(individualTemplate)

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
