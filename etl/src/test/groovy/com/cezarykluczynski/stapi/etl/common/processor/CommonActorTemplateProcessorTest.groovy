package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.etl.common.mapper.GenderMapper
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.model.performer.entity.Performer

class CommonActorTemplateProcessorTest extends AbstractRealWorldActorTemplateProcessorTest {

	private GenderMapper genderMapperMock

	private CommonActorTemplateProcessor commonActorTemplateProcessor

	void setup() {
		genderMapperMock = Mock()
		commonActorTemplateProcessor = new CommonActorTemplateProcessor(genderMapperMock)
	}

	void "converts ActorTemplate to Performer"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate(
				name: NAME,
				page: PAGE,
				birthName: BIRTH_NAME,
				placeOfBirth: PLACE_OF_BIRTH,
				placeOfDeath: PLACE_OF_DEATH,
				gender: ETL_GENDER,
				lifeRange: new DateRange(
						startDate: DATE_OF_BIRTH,
						endDate: DATE_OF_DEATH))
		Performer realWorldPerson = new Performer()

		when:
		commonActorTemplateProcessor.processCommonFields(realWorldPerson, actorTemplate)

		then:
		1 * genderMapperMock.fromEtlToModel(ETL_GENDER) >> MODEL_GENDER
		realWorldPerson.name == NAME
		realWorldPerson.page == PAGE
		realWorldPerson.birthName == BIRTH_NAME
		realWorldPerson.placeOfBirth == PLACE_OF_BIRTH
		realWorldPerson.placeOfDeath == PLACE_OF_DEATH
		realWorldPerson.gender == MODEL_GENDER
		realWorldPerson.dateOfBirth == DATE_OF_BIRTH
		realWorldPerson.dateOfDeath == DATE_OF_DEATH
	}

	void "converts all fields to nulls"() {
		ActorTemplate actorTemplate = new ActorTemplate()
		Performer realWorldPerson = new Performer()

		when:
		commonActorTemplateProcessor.processCommonFields(realWorldPerson, actorTemplate)

		then:
		1 * genderMapperMock.fromEtlToModel(null) >> null
		realWorldPerson.name == null
		realWorldPerson.page == null
		realWorldPerson.birthName == null
		realWorldPerson.placeOfBirth == null
		realWorldPerson.placeOfDeath == null
		realWorldPerson.gender == null
		realWorldPerson.dateOfBirth == null
		realWorldPerson.dateOfDeath == null
	}

}
