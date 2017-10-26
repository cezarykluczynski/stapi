package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class EpisodeLinkingWorkerComposite implements LinkingWorker<Page, Episode> {

	private final EpisodePerformancesLinkingWorker episodePerformancesLinkingWorker;

	private final EpisodeStaffLinkingWorker episodeStaffLinkingWorker;

	public EpisodeLinkingWorkerComposite(EpisodePerformancesLinkingWorker episodePerformancesLinkingWorker,
			EpisodeStaffLinkingWorker episodeStaffLinkingWorker) {
		this.episodePerformancesLinkingWorker = episodePerformancesLinkingWorker;
		this.episodeStaffLinkingWorker = episodeStaffLinkingWorker;
	}

	@Override
	public void link(Page source, Episode baseEntity) {
		episodePerformancesLinkingWorker.link(source, baseEntity);
		episodeStaffLinkingWorker.link(source, baseEntity);
	}
}
