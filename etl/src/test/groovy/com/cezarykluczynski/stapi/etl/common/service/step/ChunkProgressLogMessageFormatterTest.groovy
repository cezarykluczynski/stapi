package com.cezarykluczynski.stapi.etl.common.service.step

import spock.lang.Specification

import java.time.LocalDateTime

class ChunkProgressLogMessageFormatterTest extends Specification {

	private static final String STEP_NAME = 'STEP_NAME'

	ChunkProgressLogMessageFormatter chunkProgressLogMessageFormatter

	void setup() {
		chunkProgressLogMessageFormatter = new ChunkProgressLogMessageFormatter()
	}

	void "formats log massage"() {
		given:
		ChunkProgressLogger.ChunkProgressContext chunkProgressContext = new ChunkProgressLogger.ChunkProgressContext(
				stepName: STEP_NAME,
				previousLastLogged: LocalDateTime.of(2023, 2, 19, 8, 29, 0),
				currentLastUpdated: LocalDateTime.of(2023, 2, 19, 8, 36, 0),
				estimatedEndTime: LocalDateTime.of(2023, 2, 19, 8, 48, 0),
				totalItems: 120,
				currentProcessedItems: 30,
				percentage: 25,
				estimatedRemainingMinutes: 12)

		when:
		String formattedLogMessaged = chunkProgressLogMessageFormatter.format(chunkProgressContext)

		then:
		formattedLogMessaged == 'Step STEP_NAME is at 30/120 (25%), estimated end time is 08:48 (in 12 minutes).'
	}

	void "returns null for when no message should be formatted"() {
		given:
		ChunkProgressLogger.ChunkProgressContext chunkProgressContext = new ChunkProgressLogger.ChunkProgressContext(
				previousLastLogged: LocalDateTime.of(2023, 2, 19, 8, 29, 0),
				currentLastUpdated: LocalDateTime.of(2023, 2, 19, 8, 34, 0)
		)

		expect:
		chunkProgressLogMessageFormatter.format(chunkProgressContext) == null
	}

}
