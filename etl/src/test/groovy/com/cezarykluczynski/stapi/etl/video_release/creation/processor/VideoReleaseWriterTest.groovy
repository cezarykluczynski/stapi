package com.cezarykluczynski.stapi.etl.video_release.creation.processor

import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class VideoReleaseWriterTest extends Specification {

	private VideoReleaseRepository videoReleaseRepositoryMock

	private VideoReleaseWriter videoReleaseWriter

	void setup() {
		videoReleaseRepositoryMock = Mock()
		videoReleaseWriter = new VideoReleaseWriter(videoReleaseRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		VideoRelease videoRelease = new VideoRelease()
		List<VideoRelease> videoReleaseList = Lists.newArrayList(videoRelease)

		when:
		videoReleaseWriter.write(new Chunk(videoReleaseList))

		then:
		1 * videoReleaseRepositoryMock.saveAll(videoReleaseList)
		0 * _
	}

}
