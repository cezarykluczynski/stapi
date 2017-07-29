package com.cezarykluczynski.stapi.etl.video_game.creation.processor

import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class VideoGameWriterTest extends Specification {

	private VideoGameRepository videoGameRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private VideoGameWriter videoGameWriter

	void setup() {
		videoGameRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		videoGameWriter = new VideoGameWriter(videoGameRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		VideoGame videoGame = new VideoGame()
		List<VideoGame> videoGameList = Lists.newArrayList(videoGame)

		when:
		videoGameWriter.write(videoGameList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, VideoGame) >> { args ->
			assert args[0][0] == videoGame
			videoGameList
		}
		1 * videoGameRepositoryMock.save(videoGameList)
		0 * _
	}

}
