package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class VideoTemplateTitleEnrichingProcessorTest extends Specification {

	VideoTemplateTitleEnrichingProcessor videoTemplateTitleEnrichingProcessor

	void setup() {
		videoTemplateTitleEnrichingProcessor = new VideoTemplateTitleEnrichingProcessor()
	}

	void "when title has no date, brackets are stripped"() {
		given:
		final Page page = new Page(title: 'Star Trek - The Motion Pictures (DVD)')
		final VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		videoTemplate.title == 'Star Trek - The Motion Pictures'
	}

	void "when title has date, brackets are kept"() {
		given:
		final Page page = new Page(title: 'Star Trek - The Motion Pictures DVD Collection (2001)')
		final VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		videoTemplate.title == page.title
	}

}
