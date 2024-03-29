package com.cezarykluczynski.stapi.etl.episode.creation.processor

import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class EpisodeWriterTest extends Specification {

	private EpisodeRepository episodeRepositoryMock

	private EpisodeWriter episodeWriterMock

	void setup() {
		episodeRepositoryMock = Mock()
		episodeWriterMock = new EpisodeWriter(episodeRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Episode episode = new Episode()
		List<Episode> episodeList = Lists.newArrayList(episode)

		when:
		episodeWriterMock.write(new Chunk(episodeList))

		then:
		1 * episodeRepositoryMock.saveAll(episodeList)
		0 * _
	}

}
