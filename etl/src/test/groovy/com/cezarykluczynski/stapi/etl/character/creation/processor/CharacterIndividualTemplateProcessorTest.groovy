package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.etl.common.mapper.GenderMapper
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender as EtlGender
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender as ModelGender
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
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
	private static final Performer PERFORMER_1 = new Performer(id: 11L)
	private static final Performer PERFORMER_2 = new Performer(id: 12L)

	private GuidGenerator guidGeneratorMock

	private GenderMapper genderMapperMock

	private CharacterIndividualTemplateProcessor characterIndividualTemplateProcessor

	void setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		genderMapperMock = Mock(GenderMapper)
		characterIndividualTemplateProcessor = new CharacterIndividualTemplateProcessor(guidGeneratorMock, genderMapperMock)
	}

	void "should return null when template is product of redirect"() {
		given:
		IndividualTemplate individualTemplate = new IndividualTemplate(
				productOfRedirect: true
		)

		when:
		Character character = characterIndividualTemplateProcessor.process(individualTemplate)

		then:
		character == null
	}

	void "converts IndividualTemplate to Character"() {
		given:
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
				mirror: MIRROR,
				alternateReality: ALTERNATE_REALITY,
				performers: Sets.newHashSet(PERFORMER_1, PERFORMER_2)
		)
		when:
		Character character = characterIndividualTemplateProcessor.process(individualTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(PAGE, Character) >> GUID
		1 * genderMapperMock.fromEtlToModel(ETL_GENDER) >> MODEL_GENDER
		character.page == PAGE
		character.guid == GUID
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
		character.mirror == MIRROR
		character.alternateReality == ALTERNATE_REALITY
		character.performers.size() == 2
		character.performers.contains PERFORMER_1
		character.performers.contains PERFORMER_2
		PERFORMER_1.characters.contains character
		PERFORMER_2.characters.contains character
	}

	void "when mirror and alternate universe flags are null, false is put into Character"() {
		given:
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		Character character = characterIndividualTemplateProcessor.process(individualTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(null, Character) >> null
		1 * genderMapperMock.fromEtlToModel(null) >> null
		!character.mirror
		!character.alternateReality
	}

}
