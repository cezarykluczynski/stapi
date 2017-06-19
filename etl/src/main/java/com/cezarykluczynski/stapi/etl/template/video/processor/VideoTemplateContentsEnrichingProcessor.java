package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class VideoTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template, VideoTemplate> enrichablePair) throws Exception {
		// TODO
	}

}
