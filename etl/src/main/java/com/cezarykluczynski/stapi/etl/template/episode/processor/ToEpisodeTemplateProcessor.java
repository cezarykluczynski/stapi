package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.template.common.processor.linker.EpisodePerformancesLinkingWorker;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ToEpisodeTemplateProcessor implements ItemProcessor<Page, EpisodeTemplate> {

	private EpisodePerformancesLinkingWorker episodePerformancesLinkingWorker;

	@Inject
	public ToEpisodeTemplateProcessor(EpisodePerformancesLinkingWorker episodePerformancesLinkingWorker) {
		this.episodePerformancesLinkingWorker = episodePerformancesLinkingWorker;
	}

	@Override
	public EpisodeTemplate process(Page item) throws Exception {
		episodePerformancesLinkingWorker.link(item);

		// TODO
		return null;
	}

}
