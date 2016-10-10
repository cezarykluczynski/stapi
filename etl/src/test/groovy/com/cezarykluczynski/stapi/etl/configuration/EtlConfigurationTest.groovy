package com.cezarykluczynski.stapi.etl.configuration

import com.cezarykluczynski.stapi.etl.util.Steps
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.job.builder.FlowJobBuilder
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.builder.JobFlowBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class EtlConfigurationTest extends Specification {

	private JobBuilderFactory jobBuilderFactoryMock

	private ApplicationContext applicationContextMock

	private EtlConfiguration etlConfiguration

	def setup() {
		jobBuilderFactoryMock = Mock(JobBuilderFactory)
		applicationContextMock = Mock(ApplicationContext)
		etlConfiguration = new EtlConfiguration(
				jobBuilderFactory: jobBuilderFactoryMock,
				applicationContext: applicationContextMock)
	}

	def "Job is built"() {
		given:
		Step step1Mock = Mock(Step)
		JobBuilder jobBuilderMock = Mock(JobBuilder)
		JobFlowBuilder jobFlowBuilderMock = Mock(JobFlowBuilder)
		FlowJobBuilder flowJobBuilderMock = Mock(FlowJobBuilder)
		Job jobMock = Mock(Job)

		when:
		Job job = etlConfiguration.job()

		then: "job builder is retrieved"
		1 * jobBuilderFactoryMock.get("job") >> jobBuilderMock
		1 * jobBuilderMock.incrementer(_ as RunIdIncrementer) >> jobBuilderMock

		then: "first step is retrieved from application context, then set"
		1 * applicationContextMock.getBean(Steps.STEP_001_CREATE_SERIES, Step.class) >> step1Mock
		1 * jobBuilderMock.flow(step1Mock) >> jobFlowBuilderMock

		then: "job is built"
		1 * jobFlowBuilderMock._() >> flowJobBuilderMock
		1 * flowJobBuilderMock.build() >> jobMock

		then: "job is being returned"
		job == jobMock
	}

}
