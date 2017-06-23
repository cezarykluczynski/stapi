package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.video_release.creation.service.VideoReleasePageFilter
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class VideoTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'

	private VideoReleasePageFilter videoReleasePageFilterMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private VideoTemplateCompositeEnrichingProcessor videoTemplateCompositeEnrichingProcessorMock

	private VideoTemplateFormatEnrichingProcessor videoTemplateFormatEnrichingProcessorMock

	private VideoTemplatePageProcessor videoTemplatePageProcessor

	void setup() {
		videoReleasePageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		templateFinderMock = Mock()
		videoTemplateCompositeEnrichingProcessorMock = Mock()
		videoTemplateFormatEnrichingProcessorMock = Mock()
		videoTemplatePageProcessor = new VideoTemplatePageProcessor(videoReleasePageFilterMock, pageBindingServiceMock, templateFinderMock,
				videoTemplateCompositeEnrichingProcessorMock, videoTemplateFormatEnrichingProcessorMock)
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
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_VIDEO) >> Optional.empty()
		1 * videoTemplateFormatEnrichingProcessorMock.enrich(_)
		0 * _
		videoTemplate.page == modelPage
		videoTemplate.title == TITLE
	}

	void "when sidebar video template is found, it is passed along with VideoTemplate to VideoTemplateCompositeEnrichingProcessor"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = Mock()
		Template sidebarVideoTemplate = Mock()

		when:
		VideoTemplate videoTemplate = videoTemplatePageProcessor.process(page)

		then:
		1 * videoReleasePageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_VIDEO) >> Optional.of(sidebarVideoTemplate)
		1 * videoTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<Template, VideoTemplate> enrichablePair ->
			assert enrichablePair.input == sidebarVideoTemplate
			assert enrichablePair.output != null
		}
		1 * videoTemplateFormatEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {  EnrichablePair<Page, VideoTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		0 * _
		videoTemplate.page == modelPage
		videoTemplate.title == TITLE
	}

}
