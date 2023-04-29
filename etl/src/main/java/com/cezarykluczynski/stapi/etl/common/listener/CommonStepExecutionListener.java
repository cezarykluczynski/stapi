package com.cezarykluczynski.stapi.etl.common.listener;

import com.cezarykluczynski.stapi.etl.common.backup.BackupAfterStepExecutor;
import com.cezarykluczynski.stapi.etl.common.service.step.ChunkLogger;
import com.cezarykluczynski.stapi.etl.common.service.step.StepLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonStepExecutionListener implements StepExecutionListener, ChunkListener {

	private final List<StepLogger> stepLoggerList;
	private final List<ChunkLogger> chunkLoggerList;
	private final BackupAfterStepExecutor backupAfterStepExecutor;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		stepLoggerList.forEach(stepLogger -> stepLogger.stepStarted(stepExecution));
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		stepLoggerList.forEach(stepLogger -> stepLogger.stepEnded(stepExecution));
		backupAfterStepExecutor.execute(stepExecution);
		return null;
	}

	@Override
	public void afterChunk(ChunkContext context) {
		chunkLoggerList.forEach(chunkLogger -> chunkLogger.afterChunk(context));
	}

}
