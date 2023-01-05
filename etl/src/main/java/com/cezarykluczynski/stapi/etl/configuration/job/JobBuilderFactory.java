package com.cezarykluczynski.stapi.etl.configuration.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobBuilderFactory {

	private final JobRepository jobRepository;

	public org.springframework.batch.core.job.builder.JobBuilder get(String jobName) {
		return new org.springframework.batch.core.job.builder.JobBuilder(jobName, jobRepository);
	}

}
