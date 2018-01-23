package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.performer.creation.service.ActorPageFilter
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class ActorTemplatePageProcessorTest extends Specification {

	private ActorPageFilter actorPageFilterMock

	private ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessorMock

	private ActorTemplateListPageProcessor actorTemplateListPageProcessorMock

	private ActorTemplatePageProcessor actorTemplatePageProcessor

	void setup() {
		actorPageFilterMock = Mock()
		actorTemplateSinglePageProcessorMock = Mock()
		actorTemplateListPageProcessorMock = Mock()
		actorTemplatePageProcessor = new ActorTemplatePageProcessor(actorPageFilterMock, actorTemplateSinglePageProcessorMock,
				actorTemplateListPageProcessorMock)
	}

	void "returns null when ActorPageFilter return true"() {
		given:
		Page pageMock = Mock()

		when:
		ActorTemplate actorTemplate = actorTemplatePageProcessor.process(pageMock)

		then:
		1 * actorPageFilterMock.shouldBeFilteredOut(pageMock) >> true
		0 * _
		actorTemplate == null

	}

	void "gets ActorTemplate from ActorTemplateSinglePageProcessor when it is found"() {
		given:
		Page pageMock = Mock()
		ActorTemplate actorTemplate = Mock()

		when:
		ActorTemplate actorTemplateOutput = actorTemplatePageProcessor.process(pageMock)

		then:
		1 * actorPageFilterMock.shouldBeFilteredOut(pageMock) >> false
		1 * actorTemplateSinglePageProcessorMock.process(pageMock) >> actorTemplate
		0 * _
		actorTemplateOutput == actorTemplate
	}

	void "gets ActorTemplate from ActorTemplateListPageProcessor when ActorTemplateSinglePageProcessor returns null"() {
		given:
		Page pageMock = Mock()
		ActorTemplate actorTemplate = Mock()

		when:
		ActorTemplate actorTemplateOutput = actorTemplatePageProcessor.process(pageMock)

		then:
		1 * actorPageFilterMock.shouldBeFilteredOut(pageMock) >> false
		1 * actorTemplateSinglePageProcessorMock.process(pageMock) >> null
		1 * actorTemplateListPageProcessorMock.process(pageMock) >> actorTemplate
		0 * _
		actorTemplateOutput == actorTemplate
	}

}
