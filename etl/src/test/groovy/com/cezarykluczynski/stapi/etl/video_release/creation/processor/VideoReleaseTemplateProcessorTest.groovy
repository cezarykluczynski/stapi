package com.cezarykluczynski.stapi.etl.video_release.creation.processor

import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.util.AbstractVideoReleaseTest

class VideoReleaseTemplateProcessorTest extends AbstractVideoReleaseTest {

	private UidGenerator uidGeneratorMock

	private VideoReleaseTemplateProcessor videoReleaseTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		videoReleaseTemplateProcessor = new VideoReleaseTemplateProcessor(uidGeneratorMock)
	}

	void "converts VideoTemplate to VideoRelease"() {
		given:
		VideoTemplate videoReleaseTemplate = new VideoTemplate(
				page: page,
				title: TITLE)

		when:
		VideoRelease videoRelease = videoReleaseTemplateProcessor.process(videoReleaseTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, VideoRelease) >> UID
		0 * _
		videoRelease.uid == UID
		videoRelease.page == page
		videoRelease.title == TITLE
	}

}
