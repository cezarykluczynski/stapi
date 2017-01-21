package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class ActorTemplatePageProcessorTest extends Specification {

	private ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessorMock

	private ActorTemplateListPageProcessor actorTemplateListPageProcessorMock

	private ActorTemplatePageProcessor actorTemplatePageProcessor

	private Page pageMock

	void setup() {
		actorTemplateSinglePageProcessorMock = Mock(ActorTemplateSinglePageProcessor)
		actorTemplateListPageProcessorMock = Mock(ActorTemplateListPageProcessor)
		actorTemplatePageProcessor = new ActorTemplatePageProcessor(actorTemplateSinglePageProcessorMock,
				actorTemplateListPageProcessorMock)
		pageMock = Mock(Page)
	}

	void "gets ActorTemplate from ActorTemplateSinglePageProcessor when it is found"() {
		given:
		ActorTemplate actorTemplate = Mock(ActorTemplate)

		when:
		ActorTemplate actorTemplateOutput = actorTemplatePageProcessor.process(pageMock)

		then:
		1 * actorTemplateSinglePageProcessorMock.process(pageMock) >> actorTemplate
		0 * actorTemplateListPageProcessorMock._
		actorTemplateOutput == actorTemplate
	}

	void "gets ActorTemplate from ActorTemplateListPageProcessor when ActorTemplateSinglePageProcessor returns null"() {
		given:
		ActorTemplate actorTemplate = Mock(ActorTemplate)

		when:
		ActorTemplate actorTemplateOutput = actorTemplatePageProcessor.process(pageMock)

		then:
		1 * actorTemplateSinglePageProcessorMock.process(pageMock) >> null
		1 * actorTemplateListPageProcessorMock.process(pageMock) >> actorTemplate
		actorTemplateOutput == actorTemplate
	}

}
