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

	private Step createSpeciesStep

	private Step createCharactersStep

	private Step createEpisodesStep

	private Step createMoviesStep

	private Step linkAstronomicalObjectsStep

	private Step createComicSeriesStep

	private Step linkComicSeriesStep

	private Step createComicsStep

	private Step createComicStripsStep

	private Step createComicCollectionsStep

	private Step createOrganizationsStep

	private Step createFoodsStep

	private Step createLocationsStep

	private Step createBooksStep

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
		createSpeciesStep = Mock(Step)
		createCharactersStep = Mock(Step)
		createEpisodesStep = Mock(Step)
		createMoviesStep = Mock(Step)
		linkAstronomicalObjectsStep = Mock(Step)
		createComicSeriesStep = Mock(Step)
		linkComicSeriesStep = Mock(Step)
		createComicsStep = Mock(Step)
		createComicStripsStep = Mock(Step)
		createComicCollectionsStep = Mock(Step)
		createOrganizationsStep = Mock(Step)
		createFoodsStep = Mock(Step)
		createLocationsStep = Mock(Step)
		createBooksStep = Mock(Step)
		jobRepository = Mock(JobRepository)
		springBatchJobBuilder = new SpringBatchJobBuilder(JobName.JOB_CREATE)
		springBatchJobBuilder.repository(jobRepository)
		jobBuilder = new JobBuilder(applicationContextMock, jobBuilderFactoryMock, stepConfigurationValidatorMock, jobCompletenessDeciderMock,
				stepToStepPropertiesProviderMock)
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
		1 * createCompaniesStep.name >> StepName.CREATE_COMPANIES

		then: 'CREATE_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SERIES, Step) >> createSeriesStep
		1 * createSeriesStep.name >> StepName.CREATE_SERIES

		then: 'CREATE_PERFORMERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_PERFORMERS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_PERFORMERS, Step) >> createPerformersStep
		1 * createPerformersStep.name >> StepName.CREATE_PERFORMERS

		then: 'CREATE_STAFF step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_STAFF) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_STAFF, Step) >> createStaffStep
		1 * createStaffStep.name >> StepName.CREATE_STAFF

		then: 'CREATE_ASTRONOMICAL_OBJECTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ASTRONOMICAL_OBJECTS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ASTRONOMICAL_OBJECTS, Step) >> createAstronomicalObjectsStep
		1 * createAstronomicalObjectsStep.name >> StepName.CREATE_ASTRONOMICAL_OBJECTS

		then: 'CREATE_SPECIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SPECIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SPECIES, Step) >> createSpeciesStep
		1 * createSpeciesStep.name >> StepName.CREATE_SPECIES

		then: 'CREATE_CHARACTERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_CHARACTERS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_CHARACTERS, Step) >> createCharactersStep
		1 * createCharactersStep.name >> StepName.CREATE_CHARACTERS

		then: 'CREATE_EPISODES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_EPISODES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_EPISODES, Step) >> createEpisodesStep
		1 * createEpisodesStep.name >> StepName.CREATE_EPISODES

		then: 'CREATE_MOVIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MOVIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MOVIES, Step) >> createMoviesStep
		1 * createMoviesStep.name >> StepName.CREATE_MOVIES

		then: 'LINK_ASTRONOMICAL_OBJECTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_ASTRONOMICAL_OBJECTS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.LINK_ASTRONOMICAL_OBJECTS, Step) >> linkAstronomicalObjectsStep
		1 * linkAstronomicalObjectsStep.name >> StepName.LINK_ASTRONOMICAL_OBJECTS

		then: 'CREATE_COMIC_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMIC_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMIC_SERIES, Step) >> createComicSeriesStep
		1 * createComicSeriesStep.name >> StepName.CREATE_COMIC_SERIES

		then: 'LINK_COMIC_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_COMIC_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.LINK_COMIC_SERIES, Step) >> linkComicSeriesStep
		1 * linkComicSeriesStep.name >> StepName.LINK_COMIC_SERIES

		then: 'CREATE_COMICS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMICS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMICS, Step) >> createComicsStep
		1 * createComicsStep.name >> StepName.CREATE_COMICS

		then: 'CREATE_COMIC_STRIPS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMIC_STRIPS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMIC_STRIPS, Step) >> createComicStripsStep
		1 * createComicStripsStep.name >> StepName.CREATE_COMIC_STRIPS

		then: 'CREATE_COMIC_COLLECTIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMIC_COLLECTIONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMIC_COLLECTIONS, Step) >> createComicCollectionsStep
		1 * createComicCollectionsStep.name >> StepName.CREATE_COMIC_COLLECTIONS

		then: 'CREATE_ORGANIZATIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ORGANIZATIONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ORGANIZATIONS, Step) >> createOrganizationsStep
		1 * createOrganizationsStep.name >> StepName.CREATE_ORGANIZATIONS

		then: 'CREATE_FOODS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_FOODS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_FOODS, Step) >> createFoodsStep
		1 * createFoodsStep.name >> StepName.CREATE_FOODS

		then: 'CREATE_LOCATIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_LOCATIONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_LOCATIONS, Step) >> createLocationsStep
		1 * createLocationsStep.name >> StepName.CREATE_LOCATIONS

		then: 'CREATE_BOOKS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_BOOKS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_BOOKS, Step) >> createBooksStep
		1 * createBooksStep.name >> StepName.CREATE_BOOKS

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

	void "Job is not build when no step is enabled"() {
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

		then: 'all steps are disabled'
		19 * stepPropertiesMap.get(_) >> stepProperties
		19 * stepProperties.isEnabled() >> false

		then: 'no other interactions are expected'
		0 * _

		then: 'null is being returned'
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
		1 * createCompaniesStep.name >> StepName.CREATE_COMPANIES

		then: 'CREATE_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SERIES, Step) >> createSeriesStep
		1 * createSeriesStep.name >> StepName.CREATE_SERIES

		then: 'other steps are skipped'
		17 * stepPropertiesMap.get(_) >> stepProperties
		17 * stepProperties.isEnabled() >> false

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
