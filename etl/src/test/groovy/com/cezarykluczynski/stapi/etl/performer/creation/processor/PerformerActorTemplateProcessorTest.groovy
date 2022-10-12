package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.common.processor.AbstractRealWorldActorTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.common.processor.CommonActorTemplateProcessor
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.performer.entity.Performer

class PerformerActorTemplateProcessorTest extends AbstractRealWorldActorTemplateProcessorTest {

	private UidGenerator uidGeneratorMock

	private CommonActorTemplateProcessor commonActorTemplateProcessorMock

	private PerformerActorTemplateProcessor performerActorTemplateProcessor

	void setup() {
		uidGeneratorMock = Mock()
		commonActorTemplateProcessorMock = Mock()
		performerActorTemplateProcessor = new PerformerActorTemplateProcessor(uidGeneratorMock, commonActorTemplateProcessorMock)
	}

	void "converts ActorTemplate to Performer"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate(
				page: PAGE,
				animalPerformer: ANIMAL_PERFORMER,
				audiobookPerformer: AUDIOBOOK_PERFORMER,
				cutPerformer: CUT_PERFORMER,
				disPerformer: DIS_PERFORMER,
				ds9Performer: DS9_PERFORMER,
				entPerformer: ENT_PERFORMER,
				filmPerformer: FILM_PERFORMER,
				ldPerformer: LD_PERFORMER,
				picPerformer: PIC_PERFORMER,
				proPerformer: PRO_PERFORMER,
				puppeteer: PUPPETEER,
				snwPerformer: SNW_PERFORMER,
				standInPerformer: STAND_IN_PERFORMER,
				stPerformer: ST_PERFORMER,
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
		1 * uidGeneratorMock.generateFromPage(PAGE, Performer) >> UID
		performer.animalPerformer == ANIMAL_PERFORMER
		performer.audiobookPerformer == AUDIOBOOK_PERFORMER
		performer.cutPerformer == CUT_PERFORMER
		performer.disPerformer == DIS_PERFORMER
		performer.ds9Performer == DS9_PERFORMER
		performer.entPerformer == ENT_PERFORMER
		performer.filmPerformer == FILM_PERFORMER
		performer.ldPerformer == LD_PERFORMER
		performer.picPerformer == PIC_PERFORMER
		performer.proPerformer == PRO_PERFORMER
		performer.puppeteer == PUPPETEER
		performer.snwPerformer == SNW_PERFORMER
		performer.standInPerformer == STAND_IN_PERFORMER
		performer.stPerformer == ST_PERFORMER
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
		!performer.audiobookPerformer
		!performer.cutPerformer
		!performer.disPerformer
		!performer.ds9Performer
		!performer.entPerformer
		!performer.filmPerformer
		!performer.ldPerformer
		!performer.picPerformer
		!performer.proPerformer
		!performer.puppeteer
		!performer.snwPerformer
		!performer.standInPerformer
		!performer.stPerformer
		!performer.stuntPerformer
		!performer.tasPerformer
		!performer.tngPerformer
		!performer.tosPerformer
		!performer.videoGamePerformer
		!performer.voicePerformer
		!performer.voyPerformer
	}

}
