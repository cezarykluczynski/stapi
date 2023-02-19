package com.cezarykluczynski.stapi.etl.common.service.step;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChunkProgressLogger implements ChunkLogger, StepLogger {

	private final Map<String, StepProgress> progresses = Maps.newLinkedHashMap();

	private final StepToReadProcessorCountProvider stepToReadProcessorCountProvider;

	private final ChunkProgressLogMessageFormatter chunkProgressLogMessageFormatter;

	@Override
	public void afterChunk(ChunkContext context) {
		final StepExecution stepExecution = context.getStepContext().getStepExecution();
		String stepName = stepExecution.getStepName();
		StepProgress stepProgress = progresses.get(stepName);
		if (stepProgress == null) {
			log.error("No entry for step {}, skipping progress logging.", stepName);
			return;
		}

		LocalDateTime currentLastUpdated = stepExecution.getLastUpdated();
		if (currentLastUpdated == null) {
			currentLastUpdated = stepExecution.getStartTime();
		}
		if (currentLastUpdated == null) {
			log.error("No last updated nor start time can be obtained from StepExecution, skipping progress logging.");
			return;
		}
		long totalItems = stepProgress.getTotalItems();
		if (totalItems == 0) {
			log.error("Total items reported as 0 for step {}, this could mean a step depends on an empty repository or MA has no data.", stepName);
			return;
		}
		long currentProcessedItems = stepExecution.getWriteCount() + stepExecution.getFilterCount();
		long percentage = 100 * currentProcessedItems / totalItems;
		long passedMinutes = Duration.between(stepProgress.getStartTime(), currentLastUpdated).get(ChronoUnit.SECONDS) / 60;
		long estimatedRemainingMinutes = (int) Math.ceil((100d - percentage) * passedMinutes / percentage);
		LocalDateTime estimatedEndTime = currentLastUpdated.plusMinutes(estimatedRemainingMinutes);
		LocalDateTime previousLastLogged = stepProgress.getLastLogged();
		ChunkProgressContext chunkProgressContext = new ChunkProgressContext();
		chunkProgressContext.setStepName(stepName);
		chunkProgressContext.setCurrentLastUpdated(currentLastUpdated);
		chunkProgressContext.setPreviousLastLogged(previousLastLogged);
		chunkProgressContext.setEstimatedEndTime(estimatedEndTime);
		chunkProgressContext.setTotalItems(totalItems);
		chunkProgressContext.setCurrentProcessedItems(currentProcessedItems);
		chunkProgressContext.setPercentage(percentage);
		chunkProgressContext.setEstimatedRemainingMinutes(estimatedRemainingMinutes);
		final String formattedChunkProgress = chunkProgressLogMessageFormatter.format(chunkProgressContext);
		if (formattedChunkProgress != null) {
			log.info(formattedChunkProgress);
			stepProgress.setLastLogged(currentLastUpdated);
		}
		stepProgress.setProcessedItems(currentProcessedItems);
		stepProgress.setEstimatedEndTime(estimatedEndTime);
	}

	@Override
	public void stepStarted(StepExecution stepExecution) {
		final String stepName = stepExecution.getStepName();
		progresses.computeIfAbsent(stepName, key -> {
			StepProgress stepProgress = new StepProgress();
			final LocalDateTime startTime = stepExecution.getStartTime();
			stepProgress.setTotalItems(stepToReadProcessorCountProvider.getReadCountForStep(stepName));
			stepProgress.setProcessedItems(stepExecution.getWriteCount() + stepExecution.getFilterCount());
			stepProgress.setStartTime(startTime);
			stepProgress.setLastLogged(startTime);
			return stepProgress;
		});
	}

	@Override
	public void stepEnded(StepExecution stepExecution) {
		// do nothing
	}

	@Data
	static class ChunkProgressContext {

		private String stepName;

		private LocalDateTime currentLastUpdated;

		private LocalDateTime previousLastLogged;

		private LocalDateTime estimatedEndTime;

		private long totalItems;

		private long currentProcessedItems;

		private long percentage;

		private long estimatedRemainingMinutes;

	}

	@Data
	static class StepProgress {

		private long totalItems;

		private long processedItems;

		private LocalDateTime startTime;

		private LocalDateTime lastLogged;

		private LocalDateTime estimatedEndTime;

	}

}
