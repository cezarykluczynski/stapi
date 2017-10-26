package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.processor.ToEpisodeTemplateProcessor;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class EpisodeProcessor extends CompositeItemProcessor<PageHeader, Episode> {

	public EpisodeProcessor(PageHeaderProcessor pageHeaderProcessor,
			ToEpisodeTemplateProcessor toEpisodeTemplateProcessor, ToEpisodeEntityProcessor toEpisodeEntityProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, toEpisodeTemplateProcessor, toEpisodeEntityProcessor));
	}

}
