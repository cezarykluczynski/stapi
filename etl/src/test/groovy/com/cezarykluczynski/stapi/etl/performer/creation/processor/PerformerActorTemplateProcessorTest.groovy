package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.common.processor.AbstractRealWorldActorTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.common.processor.CommonActorTemplateProcessor
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.performer.entity.Performer

class PerformerActorTemplateProcessorTest extends AbstractRealWorldActorTemplateProcessorTest {

	private GuidGenerator guidGeneratorMock

	private CommonActorTemplateProcessor commonActorTemplateProcessorMock

	private PerformerActorTemplateProcessor performerActorTemplateProcessor

	void setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		commonActorTemplateProcessorMock = Mock(CommonActorTemplateProcessor)
		performerActorTemplateProcessor = new PerformerActorTemplateProcessor(guidGeneratorMock, commonActorTemplateProcessorMock)
	}

	void "converts ActorTemplate to Performer"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate(
				page: PAGE,
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
		Performer performer = performerActorTemplateProcessor.process(actorTemplate)

		then:
		1 * commonActorTemplateProcessorMock.processCommonFields(_ as RealWorldPerson, actorTemplate)
		1 * guidGeneratorMock.generateFromPage(PAGE, Performer) >> GUID
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

	void "ActorTemplate with only nulls and false values is converted to Performer with only nulls and false values"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()

		when:
		Performer performer = performerActorTemplateProcessor.process(actorTemplate)

		then:
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
