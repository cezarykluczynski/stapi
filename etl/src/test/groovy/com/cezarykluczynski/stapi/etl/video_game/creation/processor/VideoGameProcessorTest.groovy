package com.cezarykluczynski.stapi.etl.video_game.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate
import com.cezarykluczynski.stapi.etl.template.video_game.processor.VideoGameTemplatePageProcessor
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class VideoGameProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private VideoGameTemplatePageProcessor videoGameTemplatePageProcessorMock

	private VideoGameTemplateProcessor videoGameTemplateProcessorMock

	private VideoGameProcessor videoGameProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		videoGameTemplatePageProcessorMock = Mock()
		videoGameTemplateProcessorMock = Mock()
		videoGameProcessor = new VideoGameProcessor(pageHeaderProcessorMock, videoGameTemplatePageProcessorMock, videoGameTemplateProcessorMock)
	}

	void "converts PageHeader to VideoGame"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()
		VideoGame videoGame = new VideoGame()

		when:
		VideoGame videoGameOutput = videoGameProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * videoGameTemplatePageProcessorMock.process(page) >> videoGameTemplate

		and:
		1 * videoGameTemplateProcessorMock.process(videoGameTemplate) >> videoGame

		then: 'last processor output is returned'
		videoGameOutput == videoGame
	}

}
