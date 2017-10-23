package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class PerformerProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ActorTemplatePageProcessor actorTemplatePageProcessorMock

	private PerformerActorTemplateProcessor actorTemplateProcessorMock

	private PerformerProcessor performerProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		actorTemplatePageProcessorMock = Mock()
		actorTemplateProcessorMock = Mock()
		performerProcessor = new PerformerProcessor(pageHeaderProcessorMock, actorTemplatePageProcessorMock,
				actorTemplateProcessorMock)
	}

	void "converts PageHeader to Performer"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		ActorTemplate actorTemplate = new ActorTemplate()
		Performer performer = new Performer()

		when:
		Performer performerOutput = performerProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * actorTemplatePageProcessorMock.process(page) >> actorTemplate

		and:
		1 * actorTemplateProcessorMock.process(actorTemplate) >> performer

		then: 'last processor output is returned'
		performerOutput == performer
	}

}
