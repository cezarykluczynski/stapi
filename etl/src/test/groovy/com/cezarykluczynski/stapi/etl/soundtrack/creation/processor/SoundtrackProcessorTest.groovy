package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate
import com.cezarykluczynski.stapi.etl.template.soundtrack.processor.SoundtrackTemplatePageProcessor
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class SoundtrackProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private SoundtrackTemplatePageProcessor soundtrackTemplatePageProcessorMock

	private SoundtrackTemplateProcessor soundtrackTemplateProcessorMock

	private SoundtrackProcessor soundtrackProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		soundtrackTemplatePageProcessorMock = Mock()
		soundtrackTemplateProcessorMock = Mock()
		soundtrackProcessor = new SoundtrackProcessor(pageHeaderProcessorMock, soundtrackTemplatePageProcessorMock, soundtrackTemplateProcessorMock)
	}

	void "converts PageHeader to Soundtrack"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()
		Soundtrack soundtrack = new Soundtrack()

		when:
		Soundtrack soundtrackOutput = soundtrackProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * soundtrackTemplatePageProcessorMock.process(page) >> soundtrackTemplate

		and:
		1 * soundtrackTemplateProcessorMock.process(soundtrackTemplate) >> soundtrack

		then: 'last processor output is returned'
		soundtrackOutput == soundtrack
	}

}
