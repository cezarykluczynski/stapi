package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import spock.lang.Specification

import java.time.LocalDate

class ActorTemplateProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String BIRTH_NAME = 'BIRTH_NAME'
	private static final Gender GENDER = Gender.F
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1980, 1, 2)
	private static final LocalDate DATE_OF_DEATH = LocalDate.of(2020, 3, 4)

	private ActorTemplateProcessor actorTemplateProcessor

	def setup() {
		actorTemplateProcessor = new ActorTemplateProcessor()
	}

	def "converts ActorTemplate to Performer"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate(
				name: NAME,
				birthName: BIRTH_NAME,
				gender: GENDER,
				lifeRange: new DateRange(
						startDate: DATE_OF_BIRTH,
						endDate: DATE_OF_DEATH
				))

		when:
		Performer performer = actorTemplateProcessor.process(actorTemplate)

		then:
		performer.name == NAME
		performer.birthName == BIRTH_NAME
		performer.gender == GENDER.name()
	}

	def "ActorTemplate with only nulls is converter to Performer with only nulls"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()

		when:
		Performer performer = actorTemplateProcessor.process(actorTemplate)

		then:
		performer.name == null
		performer.birthName == null
		performer.gender == null
		performer.dateOfBirth == null
		performer.dateOfDeath == null
	}

}
