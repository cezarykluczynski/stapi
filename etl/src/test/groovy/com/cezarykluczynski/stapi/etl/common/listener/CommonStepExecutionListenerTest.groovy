package com.cezarykluczynski.stapi.etl.common.listener

import com.cezarykluczynski.stapi.etl.common.service.step.StepLogger
import com.google.common.collect.Lists
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class CommonStepExecutionListenerTest extends Specification {

	private StepLogger stepLogger1

	private StepLogger stepLogger2

	private List<StepLogger> stepLoggerList

	private StepExecution stepExecutionMock

	private CommonStepExecutionListener commonStepExecutionListener

	void setup() {
		stepLogger1 = Mock()
		stepLogger2 = Mock()
		stepLoggerList = Lists.newArrayList(stepLogger1, stepLogger2)
		stepExecutionMock = Mock()
		commonStepExecutionListener = new CommonStepExecutionListener(stepLoggerList)
	}

	void "logs before step"() {
		when: 'before step callback is called'
		commonStepExecutionListener.beforeStep(stepExecutionMock)

		then: 'first logger is interacted with'
		1 * stepLogger1.stepStarted(stepExecutionMock)

		then: 'second logger is interacted with'
		1 * stepLogger2.stepStarted(stepExecutionMock)

		then: 'no other interactions are expected'
		0 * _
	}

	void "logs after step"() {
		when: 'after step callback is called'
		commonStepExecutionListener.afterStep(stepExecutionMock)

		then: 'first logger is interacted with'
		1 * stepLogger1.stepEnded(stepExecutionMock)

		then: 'second logger is interacted with'
		1 * stepLogger2.stepEnded(stepExecutionMock)

		then: 'no other interactions are expected'
		0 * _
	}

}
