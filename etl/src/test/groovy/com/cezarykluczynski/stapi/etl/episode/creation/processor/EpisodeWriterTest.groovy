package com.cezarykluczynski.stapi.etl.episode.creation.processor

import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class EpisodeWriterTest extends Specification {

	private EpisodeRepository episodeRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private EpisodeWriter episodeWriterMock

	void setup() {
		episodeRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		episodeWriterMock = new EpisodeWriter(episodeRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Episode episode = new Episode()
		List<Episode> episodeList = Lists.newArrayList(episode)

		when:
		episodeWriterMock.write(episodeList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Episode) >> { args ->
			assert args[0][0] == episode
			episodeList
		}
		1 * episodeRepositoryMock.save(episodeList)
		0 * _
	}

}
