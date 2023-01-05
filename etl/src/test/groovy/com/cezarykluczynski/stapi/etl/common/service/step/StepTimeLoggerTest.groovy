package com.cezarykluczynski.stapi.etl.common.service.step

import com.cezarykluczynski.stapi.etl.common.dto.Range
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

import java.time.LocalDateTime

class StepTimeLoggerTest extends Specification {

	private static final String STEP_NAME_1 = 'STEP_NAME_1'
	private static final String STEP_NAME_2 = 'STEP_NAME_2'
	private static final LocalDateTime START_TIME_1 = LocalDateTime.of(2017, 9, 29, 20, 24, 54)
	private static final LocalDateTime START_TIME_2 = LocalDateTime.of(2017, 9, 29, 21, 24, 54)
	private static final LocalDateTime END_TIME_1 = LocalDateTime.of(2017, 9, 29, 20, 37, 32)
	private static final LocalDateTime END_TIME_2 = LocalDateTime.of(2017, 9, 29, 21, 37, 32)

	private StepTimeFormatter stepTimeFormatterMock

	private StepTimeLogger stepTimeLogger

	void setup() {
		stepTimeFormatterMock = Mock()
		stepTimeLogger = new StepTimeLogger(stepTimeFormatterMock)
	}

	void "steps times are passed to formatter on step end"() {
		given:
		StepExecution stepExecution1 = Mock()
		StepExecution stepExecution2 = Mock()

		when:
		stepTimeLogger.stepStarted(stepExecution1)

		then:
		1 * stepExecution1.startTime >> START_TIME_1
		1 * stepExecution1.stepName >> STEP_NAME_1
		0 * _

		when:
		stepTimeLogger.stepStarted(stepExecution2)

		then:
		1 * stepExecution2.startTime >> START_TIME_2
		1 * stepExecution2.stepName >> STEP_NAME_2
		0 * _

		when:
		stepTimeLogger.stepEnded(stepExecution1)

		then:
		1 * stepExecution1.lastUpdated >> END_TIME_1
		1 * stepExecution1.stepName >> STEP_NAME_1
		1 * stepTimeFormatterMock.format(_ as Map) >> { Map<String, Range<LocalDateTime>> map ->
			assert map.size() == 1
			assert map[STEP_NAME_1] == Range.of(START_TIME_1, END_TIME_1)
		}
		0 * _

		when:
		stepTimeLogger.stepEnded(stepExecution2)

		then:
		1 * stepExecution2.lastUpdated >> END_TIME_2
		1 * stepExecution2.stepName >> STEP_NAME_2
		1 * stepTimeFormatterMock.format(_ as Map) >> { Map<String, Range<LocalDateTime>> map ->
			assert map.size() == 2
			assert map[STEP_NAME_1] == Range.of(START_TIME_1, END_TIME_1)
			assert map[STEP_NAME_2] == Range.of(START_TIME_2, END_TIME_2)
		}
		0 * _
	}

}
