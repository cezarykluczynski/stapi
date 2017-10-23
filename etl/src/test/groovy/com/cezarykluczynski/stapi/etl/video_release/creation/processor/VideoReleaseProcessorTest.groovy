package com.cezarykluczynski.stapi.etl.video_release.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.template.video.processor.VideoTemplatePageProcessor
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class VideoReleaseProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private VideoTemplatePageProcessor videoTemplatePageProcessorMock

	private VideoReleaseTemplateProcessor videoReleaseTemplateProcessorMock

	private VideoReleaseProcessor videoReleaseProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		videoTemplatePageProcessorMock = Mock()
		videoReleaseTemplateProcessorMock = Mock()
		videoReleaseProcessor = new VideoReleaseProcessor(pageHeaderProcessorMock, videoTemplatePageProcessorMock, videoReleaseTemplateProcessorMock)
	}

	void "converts PageHeader to VideoRelease"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		VideoTemplate videoTemplate = new VideoTemplate()
		VideoRelease videoRelease = new VideoRelease()

		when:
		VideoRelease videoReleaseOutput = videoReleaseProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * videoTemplatePageProcessorMock.process(page) >> videoTemplate

		and:
		1 * videoReleaseTemplateProcessorMock.process(videoTemplate) >> videoRelease

		then: 'last processor output is returned'
		videoReleaseOutput == videoRelease
	}

}
