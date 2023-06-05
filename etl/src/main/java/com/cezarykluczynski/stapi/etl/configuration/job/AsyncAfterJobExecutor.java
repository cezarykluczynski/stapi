package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.common.backup.BackupAfterStepExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncAfterJobExecutor {

	private final BackupAfterStepExecutor backupAfterStepExecutor;
	private final NextJobRunner nextJobRunner;

	@Async
	public void execute(StepExecution lastStepExecution) {
		backupAfterStepExecutor.execute(lastStepExecution);
		nextJobRunner.scheduleNext(lastStepExecution);
	}

}
