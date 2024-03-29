package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import spock.lang.Specification

class MedicalConditionProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private MedicalConditionPageProcessor medicalConditionPageProcessorMock

	private MedicalConditionProcessor medicalConditionProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		medicalConditionPageProcessorMock = Mock()
		medicalConditionProcessor = new MedicalConditionProcessor(pageHeaderProcessorMock, medicalConditionPageProcessorMock)
	}

	void "converts PageHeader to MedicalCondition"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		MedicalCondition medicalCondition = new MedicalCondition()

		when:
		MedicalCondition medicalConditionOutput = medicalConditionProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * medicalConditionPageProcessorMock.process(page) >> medicalCondition

		then: 'last processor output is returned'
		medicalConditionOutput == medicalCondition
	}

}
