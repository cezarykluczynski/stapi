package com.cezarykluczynski.stapi.server.common.dataversion;

import com.cezarykluczynski.stapi.etl.configuration.job.service.AllStepExecutionsProvider;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO;
import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Service
@SuppressFBWarnings("STCAL_INVOKE_ON_STATIC_DATE_FORMAT_INSTANCE") // false positive
public class CommonDataVersionProvider {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

	private final Environment environment;

	private final ApplicationContext applicationContext;

	public CommonDataVersionProvider(Environment environment, ApplicationContext applicationContext) {
		this.environment = environment;
		this.applicationContext = applicationContext;
	}

	public DataVersionDTO provide() {
		final String stapiDataVersion = environment.getProperty(EnvironmentVariable.STAPI_DATA_VERSION);
		if (stapiDataVersion != null) {
			return new DataVersionDTO(stapiDataVersion);
		}

		AllStepExecutionsProvider allStepExecutionsProvider;

		try {
			// no etl profile == no AllStepExecutionsProvider bean
			allStepExecutionsProvider = applicationContext.getBean(AllStepExecutionsProvider.class);
		} catch (NoSuchBeanDefinitionException e) {
			return new DataVersionDTO(null);
		}

		return new DataVersionDTO(allStepExecutionsProvider.provide(JobName.JOB_CREATE).stream()
				.filter(stepExecution -> BatchStatus.COMPLETED.equals(stepExecution.getStatus()))
				.filter(stepExecution -> stepExecution.getEndTime() != null)
				.max(Comparator.comparing(StepExecution::getEndTime))
				.map(stepExecution -> stepExecution.getEndTime().format(DATE_TIME_FORMATTER))
				.orElse(null));
	}

}
