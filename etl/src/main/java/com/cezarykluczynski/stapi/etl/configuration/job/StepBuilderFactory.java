package com.cezarykluczynski.stapi.etl.configuration.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StepBuilderFactory {

	private final JobRepository jobRepository;

	StepBuilder get(String stepName) {
		return new StepBuilder(stepName, jobRepository);
	}

}
