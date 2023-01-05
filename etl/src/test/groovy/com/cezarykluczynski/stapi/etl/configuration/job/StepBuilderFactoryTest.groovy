package com.cezarykluczynski.stapi.etl.configuration.job

import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import spock.lang.Specification

class StepBuilderFactoryTest extends Specification {

	private static final String STEP_NAME = 'STEP_NAME'

	private JobRepository jobRepositoryMock

	private StepBuilderFactory stepBuilderFactory

	void setup() {
		jobRepositoryMock = Mock()
		stepBuilderFactory = new StepBuilderFactory(jobRepositoryMock)
	}

	void "creates StepBuilder"() {
		when:
		StepBuilder stepBuilder = stepBuilderFactory.get(STEP_NAME)

		then:
		stepBuilder.name == STEP_NAME
	}

}
