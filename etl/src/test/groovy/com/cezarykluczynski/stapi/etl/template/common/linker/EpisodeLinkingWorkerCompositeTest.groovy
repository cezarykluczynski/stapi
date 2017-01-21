package com.cezarykluczynski.stapi.etl.template.common.linker

import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class EpisodeLinkingWorkerCompositeTest extends Specification {

	private EpisodePerformancesLinkingWorker episodePerformancesLinkingWorkerMock

	private EpisodeStaffLinkingWorker episodeStaffLinkingWorkerMock

	private EpisodeLinkingWorkerComposite episodeLinkingWorkerComposite

	void setup() {
		episodePerformancesLinkingWorkerMock = Mock(EpisodePerformancesLinkingWorker)
		episodeStaffLinkingWorkerMock = Mock(EpisodeStaffLinkingWorker)
		episodeLinkingWorkerComposite = new EpisodeLinkingWorkerComposite(episodePerformancesLinkingWorkerMock, episodeStaffLinkingWorkerMock)
	}

	void "passes arguments to dependencies"() {
		given:
		Page page = Mock(Page)
		Episode episode = Mock(Episode)

		when:
		episodeLinkingWorkerComposite.link(page, episode)

		then:
		1 * episodePerformancesLinkingWorkerMock.link(page, episode)
		1 * episodeStaffLinkingWorkerMock.link(page, episode)
		0 * _
	}

}
