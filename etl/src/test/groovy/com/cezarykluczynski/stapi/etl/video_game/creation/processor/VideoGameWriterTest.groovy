package com.cezarykluczynski.stapi.etl.video_game.creation.processor

import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class VideoGameWriterTest extends Specification {

	private VideoGameRepository videoGameRepositoryMock

	private VideoGameWriter videoGameWriter

	void setup() {
		videoGameRepositoryMock = Mock()
		videoGameWriter = new VideoGameWriter(videoGameRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		VideoGame videoGame = new VideoGame()
		List<VideoGame> videoGameList = Lists.newArrayList(videoGame)

		when:
		videoGameWriter.write(new Chunk(videoGameList))

		then:
		1 * videoGameRepositoryMock.saveAll(videoGameList)
		0 * _
	}

}
