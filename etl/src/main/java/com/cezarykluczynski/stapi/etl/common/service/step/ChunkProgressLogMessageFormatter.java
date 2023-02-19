package com.cezarykluczynski.stapi.etl.common.service.step;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ChunkProgressLogMessageFormatter {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	public String format(ChunkProgressLogger.ChunkProgressContext chunkProgressContext) {
		if (chunkProgressContext.getPreviousLastLogged().plusMinutes(5).isBefore(chunkProgressContext.getCurrentLastUpdated())) {
			return String.format("Step %s is at %d/%d (%d%s), estimated end time is %s (in %d minutes).",
					chunkProgressContext.getStepName(), chunkProgressContext.getCurrentProcessedItems(), chunkProgressContext.getTotalItems(),
					chunkProgressContext.getPercentage(), "%", DATE_TIME_FORMATTER.format(chunkProgressContext.getEstimatedEndTime()),
					chunkProgressContext.getEstimatedRemainingMinutes());
		}
		return null;
	}

}
