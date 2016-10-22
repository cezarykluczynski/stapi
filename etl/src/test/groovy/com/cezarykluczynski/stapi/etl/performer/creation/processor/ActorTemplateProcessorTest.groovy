package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import org.apache.commons.lang.math.RandomUtils
import spock.lang.Specification

import java.time.LocalDate

class ActorTemplateProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String BIRTH_NAME = 'BIRTH_NAME'
	private static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	private static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'
	private static final Gender GENDER = Gender.F
	private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1980, 1, 2)
	private static final LocalDate DATE_OF_DEATH = LocalDate.of(2020, 3, 4)
	private static final boolean ANIMAL_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean DIS_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean DS9_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean ENT_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean FILM_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean STAND_IN_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean STUNT_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean TAS_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean TNG_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean TOS_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean VIDEO_GAME_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean VOICE_PERFORMER = RandomUtils.nextBoolean()
	private static final boolean VOY_PERFORMER = RandomUtils.nextBoolean()

	private ActorTemplateProcessor actorTemplateProcessor

	def setup() {
		actorTemplateProcessor = new ActorTemplateProcessor()
	}

	def "converts ActorTemplate to Performer"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate(
				name: NAME,
				birthName: BIRTH_NAME,
				placeOfBirth: PLACE_OF_BIRTH,
				placeOfDeath: PLACE_OF_DEATH,
				gender: GENDER,
				lifeRange: new DateRange(
						startDate: DATE_OF_BIRTH,
						endDate: DATE_OF_DEATH
				),
				animalPerformer: ANIMAL_PERFORMER,
				disPerformer: DIS_PERFORMER,
				ds9Performer: DS9_PERFORMER,
				entPerformer: ENT_PERFORMER,
				filmPerformer: FILM_PERFORMER,
				standInPerformer: STAND_IN_PERFORMER,
				stuntPerformer: STUNT_PERFORMER,
				tasPerformer: TAS_PERFORMER,
				tngPerformer: TNG_PERFORMER,
				tosPerformer: TOS_PERFORMER,
				videoGamePerformer: VIDEO_GAME_PERFORMER,
				voicePerformer: VOICE_PERFORMER,
				voyPerformer: VOY_PERFORMER
		)

		when:
		Performer performer = actorTemplateProcessor.process(actorTemplate)

		then:
		performer.name == NAME
		performer.birthName == BIRTH_NAME
		performer.placeOfBirth == PLACE_OF_BIRTH
		performer.placeOfDeath == PLACE_OF_DEATH
		performer.gender == GENDER.name()
		performer.animalPerformer == ANIMAL_PERFORMER
		performer.disPerformer == DIS_PERFORMER
		performer.ds9Performer == DS9_PERFORMER
		performer.entPerformer == ENT_PERFORMER
		performer.filmPerformer == FILM_PERFORMER
		performer.standInPerformer == STAND_IN_PERFORMER
		performer.stuntPerformer == STUNT_PERFORMER
		performer.tasPerformer == TAS_PERFORMER
		performer.tngPerformer == TNG_PERFORMER
		performer.tosPerformer == TOS_PERFORMER
		performer.videoGamePerformer == VIDEO_GAME_PERFORMER
		performer.voicePerformer == VOICE_PERFORMER
		performer.voyPerformer == VOY_PERFORMER
	}

	def "ActorTemplate with only nulls and false values is converted to Performer with only nulls and false values"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()

		when:
		Performer performer = actorTemplateProcessor.process(actorTemplate)

		then:
		performer.name == null
		performer.birthName == null
		performer.placeOfBirth == null
		performer.placeOfDeath == null
		performer.gender == null
		performer.dateOfBirth == null
		performer.dateOfDeath == null
		!performer.animalPerformer
		!performer.disPerformer
		!performer.ds9Performer
		!performer.entPerformer
		!performer.filmPerformer
		!performer.standInPerformer
		!performer.stuntPerformer
		!performer.tasPerformer
		!performer.tngPerformer
		!performer.tosPerformer
		!performer.videoGamePerformer
		!performer.voicePerformer
		!performer.voyPerformer
	}

}
