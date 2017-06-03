package com.cezarykluczynski.stapi.etl.configuration.job;

import org.springframework.batch.core.Job;
import org.springframework.beans.factory.FactoryBean;

public class JobFactoryBean implements FactoryBean<Job> {

	private final Job job;

	JobFactoryBean(Job job) {
		this.job = job;
	}

	@Override
	public Job getObject() throws Exception {
		return job;
	}

	@Override
	public Class<?> getObjectType() {
		return job == null ? null : Job.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
