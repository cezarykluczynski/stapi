package com.cezarykluczynski.stapi.etl.common.listener;

import com.cezarykluczynski.stapi.etl.common.service.step.ChunkLogger;
import com.cezarykluczynski.stapi.etl.common.service.step.StepLogger;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommonStepExecutionListener implements StepExecutionListener, ChunkListener {

	private final List<StepLogger> stepLoggerList;
	private final List<ChunkLogger> chunkLoggerList;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public CommonStepExecutionListener(List<StepLogger> stepLoggerList, List<ChunkLogger> chunkLoggerList) {
		this.stepLoggerList = stepLoggerList;
		this.chunkLoggerList = chunkLoggerList;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		stepLoggerList.forEach(stepLogger -> stepLogger.stepStarted(stepExecution));
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		stepLoggerList.forEach(stepLogger -> stepLogger.stepEnded(stepExecution));
		return null;
	}

	@Override
	public void afterChunk(ChunkContext context) {
		chunkLoggerList.forEach(chunkLogger -> chunkLogger.afterChunk(context));
	}

}
