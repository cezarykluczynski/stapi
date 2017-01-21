package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.common.mapper.GenderMapper;
import com.cezarykluczynski.stapi.etl.common.processor.AbstractActorTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PerformerActorTemplateProcessor extends AbstractActorTemplateProcessor implements ItemProcessor<ActorTemplate, Performer> {

	private GuidGenerator guidGenerator;

	@Inject
	public PerformerActorTemplateProcessor(GuidGenerator guidGenerator, GenderMapper genderMapper) {
		super(genderMapper);
		this.guidGenerator = guidGenerator;
	}

	@Override
	public Performer process(ActorTemplate item) throws Exception {
		Performer performer = new Performer();

		processCommonFields(performer, item);
		performer.setGuid(guidGenerator.generateFromPage(item.getPage(), Performer.class));
		performer.setAnimalPerformer(item.isAnimalPerformer());
		performer.setDisPerformer(item.isDisPerformer());
		performer.setDs9Performer(item.isDs9Performer());
		performer.setEntPerformer(item.isEntPerformer());
		performer.setFilmPerformer(item.isFilmPerformer());
		performer.setStandInPerformer(item.isStandInPerformer());
		performer.setStuntPerformer(item.isStuntPerformer());
		performer.setTasPerformer(item.isTasPerformer());
		performer.setTngPerformer(item.isTngPerformer());
		performer.setTosPerformer(item.isTosPerformer());
		performer.setVideoGamePerformer(item.isVideoGamePerformer());
		performer.setVoicePerformer(item.isVoicePerformer());
		performer.setVoyPerformer(item.isVoyPerformer());

		return performer;
	}
}
