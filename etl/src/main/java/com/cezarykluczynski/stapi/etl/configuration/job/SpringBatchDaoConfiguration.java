package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.util.tool.ReflectionUtil;
import jakarta.inject.Inject;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.batch.core.repository.dao.JobInstanceDao;
import org.springframework.batch.core.repository.dao.StepExecutionDao;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("batchDataSourceInitializer")
public class SpringBatchDaoConfiguration {

	@Inject
	private JobRepository jobRepository;

	@Bean
	public JobInstanceDao jobInstanceDao() {
		return getFieldFromJobRepository("jobInstanceDao", JobInstanceDao.class);
	}

	@Bean
	public JobExecutionDao jobExecutionDao() {
		return getFieldFromJobRepository("jobExecutionDao", JobExecutionDao.class);
	}

	@Bean
	public StepExecutionDao stepExecutionDao() {
		return getFieldFromJobRepository("stepExecutionDao", StepExecutionDao.class);
	}

	@Bean
	public ExecutionContextDao executionContextDao() {
		return getFieldFromJobRepository("ecDao", ExecutionContextDao.class);
	}

	private <T> T getFieldFromJobRepository(String name, Class<T> type) {
		SimpleJobRepository simpleJobRepository = getSimpleJobRepository();
		try {
			return ReflectionUtil.getFieldValue(simpleJobRepository.getClass(), simpleJobRepository, name, type);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new BeanInitializationException(String.format("Cannot extract field %s from JobRepository", name), e);
		}
	}

	private SimpleJobRepository getSimpleJobRepository() {
		try {
			return getTargetObject(jobRepository);
		} catch (Exception e) {
			throw new RuntimeException("Could not extract actual SimpleJobRepository from proxy");
		}
	}

	// source: http://stackoverflow.com/a/37946701/3807342
	@SuppressWarnings({"unchecked"})
	private <T> T getTargetObject(Object proxy) throws Exception {
		if (AopUtils.isJdkDynamicProxy(proxy)) {
			return (T) getTargetObject(((Advised) proxy).getTargetSource().getTarget());
		}
		return (T) proxy;
	}

}
