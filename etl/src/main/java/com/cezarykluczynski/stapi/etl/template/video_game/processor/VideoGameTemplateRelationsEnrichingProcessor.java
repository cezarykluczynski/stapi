package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
class VideoGameTemplateRelationsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, VideoGameTemplate>> {

	@Override
	public void enrich(EnrichablePair<Template, VideoGameTemplate> enrichablePair) throws Exception {
		// TODO
	}

}
