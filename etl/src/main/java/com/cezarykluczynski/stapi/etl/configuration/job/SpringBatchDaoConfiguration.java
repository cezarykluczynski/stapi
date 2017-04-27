package com.cezarykluczynski.stapi.etl.configuration.job;

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

import javax.inject.Inject;
import java.lang.reflect.Field;

@Configuration
@DependsOn("batchDatabaseInitializer")
public class SpringBatchDaoConfiguration {

	@Inject
	private JobRepository jobRepository;

	@Bean
	public JobInstanceDao jobInstanceDao() {
		return getFieldFromJobRepository("jobInstanceDao");
	}

	@Bean
	public JobExecutionDao jobExecutionDao() {
		return getFieldFromJobRepository("jobExecutionDao");
	}

	@Bean
	public StepExecutionDao stepExecutionDao() {
		return getFieldFromJobRepository("stepExecutionDao");
	}

	@Bean
	public ExecutionContextDao executionContextDao() {
		return getFieldFromJobRepository("ecDao");
	}

	@SuppressWarnings({"unchecked"})
	private <T> T getFieldFromJobRepository(String name) {
		SimpleJobRepository simpleJobRepository = getSimpleJobRepository();
		try {
			Field field = simpleJobRepository.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return (T) field.get(simpleJobRepository);
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
