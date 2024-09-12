package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CommonActorTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class PerformerActorTemplateProcessor implements ItemProcessor<ActorTemplate, Performer> {

	private final UidGenerator uidGenerator;

	private final CommonActorTemplateProcessor commonActorTemplateProcessor;

	public PerformerActorTemplateProcessor(UidGenerator uidGenerator, CommonActorTemplateProcessor commonActorTemplateProcessor) {
		this.uidGenerator = uidGenerator;
		this.commonActorTemplateProcessor = commonActorTemplateProcessor;
	}

	@Override
	public Performer process(ActorTemplate item) throws Exception {
		Performer performer = new Performer();

		commonActorTemplateProcessor.processCommonFields(performer, item);
		performer.setUid(uidGenerator.generateFromPage(item.getPage(), Performer.class));
		performer.setAnimalPerformer(item.isAnimalPerformer());
		performer.setAudiobookPerformer(item.isAudiobookPerformer());
		performer.setCutPerformer(item.isCutPerformer());
		performer.setAnimalPerformer(item.isAnimalPerformer());
		performer.setDisPerformer(item.isDisPerformer());
		performer.setDs9Performer(item.isDs9Performer());
		performer.setEntPerformer(item.isEntPerformer());
		performer.setFilmPerformer(item.isFilmPerformer());
		performer.setLdPerformer(item.isLdPerformer());
		performer.setPicPerformer(item.isPicPerformer());
		performer.setProPerformer(item.isProPerformer());
		performer.setPuppeteer(item.isPuppeteer());
		performer.setSnwPerformer(item.isSnwPerformer());
		performer.setStandInPerformer(item.isStandInPerformer());
		performer.setStPerformer(item.isStPerformer());
		performer.setStuntPerformer(item.isStuntPerformer());
		performer.setTasPerformer(item.isTasPerformer());
		performer.setTngPerformer(item.isTngPerformer());
		performer.setTosPerformer(item.isTosPerformer());
		performer.setVideoGamePerformer(item.isVideoGamePerformer());
		performer.setVoicePerformer(item.isVoicePerformer());
		performer.setVoyPerformer(item.isVoyPerformer());
		performer.getExternalLinks().addAll(item.getExternalLinks());

		return performer;
	}
}
