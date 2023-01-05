package com.cezarykluczynski.stapi.etl.configuration.job

import org.springframework.batch.core.repository.JobRepository
import spock.lang.Specification

class JobBuilderFactoryTest extends Specification {

	private static final String JOB_NAME = 'JOB_NAME'

	private JobRepository jobRepositoryMock

	private JobBuilderFactory jobBuilderFactory

	void setup() {
		jobRepositoryMock = Mock()
		jobBuilderFactory = new JobBuilderFactory(jobRepositoryMock)
	}

	void "creates JobBuilder"() {
		when:
		org.springframework.batch.core.job.builder.JobBuilder jobBuilder = jobBuilderFactory.get(JOB_NAME)

		then:
		jobBuilder.name == JOB_NAME
	}

}
