package com.cezarykluczynski.stapi.etl.video_release.creation.processor

import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class VideoReleaseWriterTest extends Specification {

	private VideoReleaseRepository videoReleaseRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private VideoReleaseWriter videoReleaseWriter

	void setup() {
		videoReleaseRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		videoReleaseWriter = new VideoReleaseWriter(videoReleaseRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		VideoRelease videoRelease = new VideoRelease()
		List<VideoRelease> videoReleaseList = Lists.newArrayList(videoRelease)

		when:
		videoReleaseWriter.write(videoReleaseList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, VideoRelease) >> { args ->
			assert args[0][0] == videoRelease
			videoReleaseList
		}
		1 * videoReleaseRepositoryMock.save(videoReleaseList)
		0 * _
	}

}
