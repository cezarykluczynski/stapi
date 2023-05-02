package com.cezarykluczynski.stapi.etl.common.listener

import com.cezarykluczynski.stapi.etl.common.service.step.ChunkLogger
import com.cezarykluczynski.stapi.etl.common.service.step.StepLogger
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.scope.context.ChunkContext
import spock.lang.Specification

class CommonStepExecutionListenerTest extends Specification {

	private StepLogger stepLogger1

	private StepLogger stepLogger2

	private ChunkLogger chunkLogger1

	private ChunkLogger chunkLogger2

	private List<StepLogger> stepLoggerList

	private List<ChunkLogger> chunkLoggerList

	private StepExecution stepExecutionMock

	private ChunkContext chunkContextMock

	private CommonStepExecutionListener commonStepExecutionListener

	void setup() {
		stepLogger1 = Mock()
		stepLogger2 = Mock()
		chunkLogger1 = Mock()
		chunkLogger2 = Mock()
		stepLoggerList = [stepLogger1, stepLogger2]
		chunkLoggerList = [chunkLogger1, chunkLogger2]
		stepExecutionMock = Mock()
		chunkContextMock = Mock()
		commonStepExecutionListener = new CommonStepExecutionListener(stepLoggerList, chunkLoggerList)
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

	void "logs and does a backup after step"() {
		when: 'after step callback is called'
		commonStepExecutionListener.afterStep(stepExecutionMock)

		then: 'first logger is interacted with'
		1 * stepLogger1.stepEnded(stepExecutionMock)

		then: 'second logger is interacted with'
		1 * stepLogger2.stepEnded(stepExecutionMock)

		then: 'no other interactions are expected'
		0 * _
	}

	void "logs after chunk"() {
		when: 'after chunk callback is called'
		commonStepExecutionListener.afterChunk(chunkContextMock)

		then: 'first logger is interacted with'
		1 * chunkLogger1.afterChunk(chunkContextMock)

		then: 'second logger is interacted with'
		1 * chunkLogger2.afterChunk(chunkContextMock)

		then: 'no other interactions are expected'
		0 * _
	}

}
