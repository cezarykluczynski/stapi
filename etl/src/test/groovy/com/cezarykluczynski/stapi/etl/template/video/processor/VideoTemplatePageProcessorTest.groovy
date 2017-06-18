package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.video_release.creation.service.VideoReleasePageFilter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import spock.lang.Specification

class VideoTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'

	private VideoReleasePageFilter videoReleasePageFilterMock

	private PageBindingService pageBindingServiceMock

	private VideoTemplatePageProcessor videoTemplatePageProcessor

	void setup() {
		videoReleasePageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		videoTemplatePageProcessor = new VideoTemplatePageProcessor(videoReleasePageFilterMock, pageBindingServiceMock)
	}

	void "returns null when VideoReleasePageFilter returns true"() {
		given:
		Page page = Mock()

		when:
		VideoTemplate videoTemplate = videoTemplatePageProcessor.process(page)

		then:
		1 * videoReleasePageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		videoTemplate == null
	}

	void "clears title when it contains brackets"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = Mock()

		when:
		VideoTemplate videoTemplate = videoTemplatePageProcessor.process(page)

		then:
		1 * videoReleasePageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		0 * _
		videoTemplate.page == modelPage
		videoTemplate.title == TITLE
	}

}
