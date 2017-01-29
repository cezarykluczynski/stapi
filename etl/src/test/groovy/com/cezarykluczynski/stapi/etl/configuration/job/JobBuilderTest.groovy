package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider
import com.cezarykluczynski.stapi.etl.configuration.job.service.JobCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.job.builder.JobBuilder as SpringBatchJobBuilder
import org.springframework.batch.core.job.flow.FlowJob
import org.springframework.batch.core.job.flow.support.SimpleFlow
import org.springframework.batch.core.job.flow.support.state.SplitState
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.ApplicationContext
import org.springframework.core.task.TaskExecutor
import spock.lang.Specification

class JobBuilderTest extends Specification {

	private ApplicationContext applicationContextMock

	private JobBuilderFactory jobBuilderFactoryMock

	private StepConfigurationValidator stepConfigurationValidatorMock

	private JobCompletenessDecider jobCompletenessDeciderMock

	private StepToStepPropertiesProvider stepToStepPropertiesProviderMock

	private Map<String, StepProperties> stepPropertiesMap

	private StepProperties stepProperties

	private JobBuilder jobBuilder

	private Step createCompaniesStep

	private Step createSeriesStep

	private Step createPerformersStep

	private Step createStaffStep

	private Step createAstronomicalObjectsStep

	private Step createCharactersStep

	private Step createEpisodesStep

	private Step createMoviesStep

	private Step linkAstronomicalObjectsStep

	private JobRepository jobRepository

	private SpringBatchJobBuilder springBatchJobBuilder

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		jobBuilderFactoryMock = Mock(JobBuilderFactory)
		stepConfigurationValidatorMock = Mock(StepConfigurationValidator)
		jobCompletenessDeciderMock = Mock(JobCompletenessDecider)
		stepToStepPropertiesProviderMock = Mock(StepToStepPropertiesProvider)
		stepPropertiesMap = Mock(Map)
		stepProperties = Mock(StepProperties)
		createCompaniesStep = Mock(Step)
		createSeriesStep = Mock(Step)
		createPerformersStep = Mock(Step)
		createStaffStep = Mock(Step)
		createAstronomicalObjectsStep = Mock(Step)
		createCharactersStep = Mock(Step)
		createEpisodesStep = Mock(Step)
		createMoviesStep = Mock(Step)
		linkAstronomicalObjectsStep = Mock(Step)
		jobRepository = Mock(JobRepository)
		springBatchJobBuilder = new SpringBatchJobBuilder(JobName.JOB_CREATE)
		springBatchJobBuilder.repository(jobRepository)
		jobBuilder = new JobBuilder(applicationContextMock, jobBuilderFactoryMock, stepConfigurationValidatorMock,
				jobCompletenessDeciderMock, stepToStepPropertiesProviderMock)
	}

	void "Job is built"() {
		given:
		TaskExecutor taskExecutor = Mock(TaskExecutor)

		when:
		FlowJob job = (FlowJob) jobBuilder.build()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether job is completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> false

		then: 'jobCreate builder is retrieved'
		1 * jobBuilderFactoryMock.get(JobName.JOB_CREATE) >> springBatchJobBuilder

		then: 'step properties are provided'
		1 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap

		then: 'CREATE_COMPANIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMPANIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMPANIES, Step) >> createCompaniesStep
		1 * createCompaniesStep.name >> ''

		then: 'CREATE_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SERIES, Step) >> createSeriesStep
		1 * createSeriesStep.name >> ''

		then: 'CREATE_PERFORMERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_PERFORMERS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_PERFORMERS, Step) >> createPerformersStep
		1 * createPerformersStep.name >> ''

		then: 'CREATE_STAFF step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_STAFF) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_STAFF, Step) >> createStaffStep
		1 * createStaffStep.name >> ''

		then: 'CREATE_ASTRONOMICAL_OBJECTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ASTRONOMICAL_OBJECTS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ASTRONOMICAL_OBJECTS, Step) >> createAstronomicalObjectsStep
		1 * createAstronomicalObjectsStep.name >> ''

		then: 'CREATE_CHARACTERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_CHARACTERS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_CHARACTERS, Step) >> createCharactersStep
		1 * createCharactersStep.name >> ''

		then: 'CREATE_EPISODES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_EPISODES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_EPISODES, Step) >> createEpisodesStep
		1 * createEpisodesStep.name >> ''

		then: 'CREATE_MOVIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MOVIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MOVIES, Step) >> createMoviesStep
		1 * createMoviesStep.name >> ''

		then: 'LINK_ASTRONOMICAL_OBJECTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_ASTRONOMICAL_OBJECTS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.LINK_ASTRONOMICAL_OBJECTS, Step) >> linkAstronomicalObjectsStep
		1 * linkAstronomicalObjectsStep.name >> ''

		then: 'Task executor is retrieved from application context'
		1 * applicationContextMock.getBean(TaskExecutor) >> taskExecutor

		then: 'no other interactions are expected'
		0 * _

		then: 'job is being returned'
		job.name == JobName.JOB_CREATE
		job.jobRepository == jobRepository
		((SplitState) ((SimpleFlow) job.flow).startState).flows.size() == 1
		((SplitState) ((SimpleFlow) job.flow).startState).flows[0].name == 'flow1'
	}

	void "Job is not built when job is completed"() {
		when:
		FlowJob job = (FlowJob) jobBuilder.build()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether all steps in job are completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> true

		then: 'no other interactions are expected'
		0 * _

		then: 'job is null'
		job == null
	}

	void "job is built with only two steps"() {
		given:
		TaskExecutor taskExecutor = Mock(TaskExecutor)

		when:
		FlowJob job = (FlowJob) jobBuilder.build()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether job is completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> false

		then: 'jobCreate builder is retrieved'
		1 * jobBuilderFactoryMock.get(JobName.JOB_CREATE) >> springBatchJobBuilder

		then: 'step properties are provided'
		1 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap

		then: 'CREATE_COMPANIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMPANIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMPANIES, Step) >> createCompaniesStep
		1 * createCompaniesStep.name >> ''

		then: 'CREATE_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SERIES, Step) >> createSeriesStep
		1 * createSeriesStep.name >> ''

		then: 'other steps are skipped'
		7 * stepPropertiesMap.get(_) >> stepProperties
		7 * stepProperties.isEnabled() >> false

		then: 'Task executor is retrieved from application context'
		1 * applicationContextMock.getBean(TaskExecutor) >> taskExecutor

		then: 'no other interactions are expected'
		0 * _

		then: 'job is being returned'
		job.name == JobName.JOB_CREATE
		job.jobRepository == jobRepository
		((SplitState) ((SimpleFlow) job.flow).startState).flows.size() == 1
		((SplitState) ((SimpleFlow) job.flow).startState).flows[0].name == 'flow1'
	}

}
