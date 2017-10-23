package com.cezarykluczynski.stapi.etl.staff.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class StaffProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ActorTemplatePageProcessor actorTemplatePageProcessorMock

	private StaffActorTemplateProcessor staffActorTemplateProcessorMock

	private StaffProcessor staffProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		actorTemplatePageProcessorMock = Mock()
		staffActorTemplateProcessorMock = Mock()
		staffProcessor = new StaffProcessor(pageHeaderProcessorMock, actorTemplatePageProcessorMock, staffActorTemplateProcessorMock)
	}

	void "converts PageHeader to Staff"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		ActorTemplate actorTemplate = new ActorTemplate()
		Staff staff = new Staff()

		when:
		Staff staffOutput = staffProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * actorTemplatePageProcessorMock.process(page) >> actorTemplate

		and:
		1 * staffActorTemplateProcessorMock.process(actorTemplate) >> staff

		then: 'last processor output is returned'
		staffOutput == staff
	}

}
