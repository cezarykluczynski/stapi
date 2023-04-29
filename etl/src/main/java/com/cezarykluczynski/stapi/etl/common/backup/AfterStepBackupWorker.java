package com.cezarykluczynski.stapi.etl.common.backup;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import org.springframework.batch.core.StepExecution;

public interface AfterStepBackupWorker {

	boolean supports(String driveClassName);

	void backupStep(StepExecution stepExecution, StepProperties stepProperties);

}
