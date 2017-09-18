package com.cezarykluczynski.stapi.etl.title.creation.service

import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class TitleListStepExecutionListenerTest extends Specification {

	private TitleListCache titleListCacheMock

	private TitleListStepExecutionListener titleListStepExecutionListener

	void setup() {
		titleListCacheMock = Mock()
		titleListStepExecutionListener = new TitleListStepExecutionListener(titleListCacheMock)
	}

	void "does nothing before step"() {
		given:
		StepExecution stepExecution = Mock()

		when:
		titleListStepExecutionListener.beforeStep(stepExecution)

		then:
		0 * _
	}

	void "calls TitleListCache after step"() {
		given:
		StepExecution stepExecution = Mock()

		when:
		titleListStepExecutionListener.afterStep(stepExecution)

		then:
		1 * titleListCacheMock.produceTitlesFromListPages()
		0 * _
	}

}
