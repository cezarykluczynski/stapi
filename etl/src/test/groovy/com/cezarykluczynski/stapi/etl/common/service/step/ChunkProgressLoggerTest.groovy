package com.cezarykluczynski.stapi.etl.common.service.step

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.scope.context.StepContext
import spock.lang.Specification

import java.time.LocalDateTime

class ChunkProgressLoggerTest extends Specification {

	private static final String STEP_NAME = 'STEP_NAME'
	private static final String LOG_MESSAGE = 'LOG_MESSAGE'
	private static final Integer TOTAL_ITEMS = 120

	StepToReadProcessorCountProvider stepToReadProcessorCountProviderMock

	ChunkProgressLogMessageFormatter chunkProgressLogMessageFormatterMock

	ChunkProgressLogger chunkProgressLogger

	void setup() {
		stepToReadProcessorCountProviderMock = Mock()
		chunkProgressLogMessageFormatterMock = Mock()
		chunkProgressLogger = new ChunkProgressLogger(stepToReadProcessorCountProviderMock, chunkProgressLogMessageFormatterMock)
	}

	void "starts step, gathers data for chunk, but does not log, then gather more data and logs"() {
		given:
		LocalDateTime startTime = LocalDateTime.of(2023, 2, 19, 9, 16, 0)
		LocalDateTime firstChunkTime = LocalDateTime.of(2023, 2, 19, 9, 20, 0)
		LocalDateTime secondChunkTime = LocalDateTime.of(2023, 2, 19, 9, 22, 0)
		LocalDateTime firstChunkEstimatedEndTime = LocalDateTime.of(2023, 2, 19, 9, 32, 0)
		LocalDateTime secondChunkEstimatedEndTime = LocalDateTime.of(2023, 2, 19, 9, 28, 0)
		StepExecution stepExecutionInitial = new StepExecution(STEP_NAME, new JobExecution(null, null, null))
		stepExecutionInitial.setStartTime(startTime)
		StepExecution stepExecutionFirstChunk = new StepExecution(STEP_NAME, new JobExecution(null, null, null))
		stepExecutionFirstChunk.setWriteCount(28)
		stepExecutionFirstChunk.setFilterCount(2)
		stepExecutionFirstChunk.setLastUpdated(firstChunkTime)
		StepExecution stepExecutionSecondChunk = new StepExecution(STEP_NAME, new JobExecution(null, null, null))
		stepExecutionSecondChunk.setWriteCount(57)
		stepExecutionSecondChunk.setFilterCount(3)
		stepExecutionSecondChunk.setStartTime(secondChunkTime)
		ChunkContext firstChunkContext = new ChunkContext(new StepContext(stepExecutionFirstChunk))
		ChunkContext secondChunkContext = new ChunkContext(new StepContext(stepExecutionSecondChunk))

		when:
		chunkProgressLogger.stepStarted(stepExecutionInitial)

		then:
		1 * stepToReadProcessorCountProviderMock.getReadCountForStep(STEP_NAME) >> TOTAL_ITEMS
		0 * _

		when:
		chunkProgressLogger.afterChunk(firstChunkContext)

		then:
		1 * chunkProgressLogMessageFormatterMock.format(_ as ChunkProgressLogger.ChunkProgressContext) >> {
				ChunkProgressLogger.ChunkProgressContext chunkProgressContext ->
			assert chunkProgressContext.stepName == STEP_NAME
			assert chunkProgressContext.currentLastUpdated == firstChunkTime
			assert chunkProgressContext.previousLastLogged == startTime
			assert chunkProgressContext.estimatedEndTime == firstChunkEstimatedEndTime
			assert chunkProgressContext.totalItems == TOTAL_ITEMS
			assert chunkProgressContext.currentProcessedItems == 30
			assert chunkProgressContext.percentage == 25
			assert chunkProgressContext.estimatedRemainingMinutes == 12
			null
		}
		0 * _
		chunkProgressLogger.progresses.get(STEP_NAME).lastLogged == startTime

		when:
		chunkProgressLogger.afterChunk(secondChunkContext)

		then:
		1 * chunkProgressLogMessageFormatterMock.format(_ as ChunkProgressLogger.ChunkProgressContext) >> {
			ChunkProgressLogger.ChunkProgressContext chunkProgressContext ->
				assert chunkProgressContext.stepName == STEP_NAME
				assert chunkProgressContext.currentLastUpdated == secondChunkTime
				assert chunkProgressContext.previousLastLogged == startTime
				assert chunkProgressContext.estimatedEndTime == secondChunkEstimatedEndTime
				assert chunkProgressContext.totalItems == TOTAL_ITEMS
				assert chunkProgressContext.currentProcessedItems == 60
				assert chunkProgressContext.percentage == 50
				assert chunkProgressContext.estimatedRemainingMinutes == 6
				LOG_MESSAGE
		}
		0 * _
		chunkProgressLogger.progresses.get(STEP_NAME).lastLogged == secondChunkTime
	}

}
