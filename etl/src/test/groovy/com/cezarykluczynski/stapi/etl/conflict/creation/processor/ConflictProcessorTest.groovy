package com.cezarykluczynski.stapi.etl.conflict.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.etl.template.military_conflict.processor.MilitaryConflictTemplatePageProcessor
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class ConflictProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	MilitaryConflictTemplatePageProcessor militaryConflictTemplatePageProcessorMock

	MilitaryConflictTemplateProcessor militaryConflictTemplateProcessorMock

	private ConflictProcessor conflictProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		militaryConflictTemplatePageProcessorMock = Mock()
		militaryConflictTemplateProcessorMock = Mock()
		conflictProcessor = new ConflictProcessor(pageHeaderProcessorMock, militaryConflictTemplatePageProcessorMock,
				militaryConflictTemplateProcessorMock)
	}

	void "converts PageHeader to Conflict"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()
		Conflict conflict = new Conflict()

		when:
		Conflict outputConflict = conflictProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * militaryConflictTemplatePageProcessorMock.process(page) >> militaryConflictTemplate

		and:
		1 * militaryConflictTemplateProcessorMock.process(militaryConflictTemplate) >> conflict

		then: 'last processor output is returned'
		outputConflict == conflict
	}

}
