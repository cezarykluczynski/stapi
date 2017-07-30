package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
class VideoGameTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoGameTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template, VideoGameTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		VideoGameTemplate videoGameTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case VideoGameTemplateParameter.TITLE:
				case VideoGameTemplateParameter.RELEASED:
				case VideoGameTemplateParameter.YEAR:
				case VideoGameTemplateParameter.STARDATE:
				case VideoGameTemplateParameter.REQUIREMENTS:
				default:
					// TODO
					break;
			}
		}
	}

}
