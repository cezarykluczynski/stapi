package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class VideoTemplateRelationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		Template sidebarVideoTemplate = enrichablePair.getInput();
		VideoTemplate videoTemplate = enrichablePair.getOutput();

		for (Template.Part part : sidebarVideoTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case VideoTemplateParameter.RATING:
				case VideoTemplateParameter.LANGUAGE:
				case VideoTemplateParameter.SUBTITLES:
				case VideoTemplateParameter.DUBBED:
				case VideoTemplateParameter.REFERENCE:
					// TODO
					break;
				default:
					break;
			}
		}
	}

}
