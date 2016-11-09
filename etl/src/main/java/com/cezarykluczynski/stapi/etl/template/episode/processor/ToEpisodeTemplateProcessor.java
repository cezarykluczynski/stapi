package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ToEpisodeTemplateProcessor implements ItemProcessor<Page, EpisodeTemplate> {

	@Override
	public EpisodeTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
