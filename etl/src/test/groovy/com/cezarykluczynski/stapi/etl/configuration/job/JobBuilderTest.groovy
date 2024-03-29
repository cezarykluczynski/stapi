package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.common.backup.AfterLatestStepJobExecutionListener
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider
import com.cezarykluczynski.stapi.etl.configuration.job.service.JobCompletenessDecider
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder as SpringBatchJobBuilder
import org.springframework.batch.core.job.flow.FlowJob
import org.springframework.batch.core.job.flow.support.SimpleFlow
import org.springframework.batch.core.job.flow.support.state.SplitState
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.ApplicationContext
import org.springframework.core.task.SimpleAsyncTaskExecutor
import spock.lang.Specification

class JobBuilderTest extends Specification {

	private ApplicationContext applicationContextMock

	private JobBuilderFactory jobBuilderFactoryMock

	private StepConfigurationValidator stepConfigurationValidatorMock

	private JobCompletenessDecider jobCompletenessDeciderMock

	private StepToStepPropertiesProvider stepToStepPropertiesProviderMock

	private StepCompletenessDecider stepCompletenessDeciderMock

	private Map<String, StepProperties> stepPropertiesMap

	private StepProperties stepProperties

	private JobBuilder jobBuilder

	private Step createCompaniesStep

	private Step createSeriesStep

	private Step createSeasonsStep

	private Step createPerformersStep

	private Step createStaffStep

	private Step createAstronomicalObjectsStep

	private Step linkAstronomicalObjectsStep

	private Step createSpeciesStep

	private Step createOrganizationsStep

	private Step createTitlesStep

	private Step createCharactersStep

	private Step linkCharactersStep

	private Step createEpisodesStep

	private Step createMoviesStep

	private Step createComicSeriesStep

	private Step linkComicSeriesStep

	private Step createComicsStep

	private Step createComicStripsStep

	private Step createComicCollectionsStep

	private Step createFoodsStep

	private Step createLocationsStep

	private Step createBookSeriesStep

	private Step createBooksStep

	private Step linkBookSeriesStep

	private Step createBookCollectionsStep

	private Step createMagazinesStep

	private Step createMagazineSeriesStep

	private Step createLiteratureStep

	private Step createVideoReleasesStep

	private Step createTradingCardsStep

	private Step createVideoGamesStep

	private Step createSoundtracksStep

	private Step createWeaponsStep

	private Step createSpacecraftClassesStep

	private Step createSpacecraftTypesStep

	private Step createSpacecraftsStep

	private Step createMaterialsStep

	private Step createConflictsStep

	private Step createAnimalsStep

	private Step createElementsStep

	private Step createMedicalConditionsStep

	private Step createTechnologyStep

	private Step createOccupationsStep

	private JobRepository jobRepository

	private SpringBatchJobBuilder springBatchJobBuilder

	private AfterLatestStepJobExecutionListener afterLatestStepJobExecutionListenerMock

	void setup() {
		applicationContextMock = Mock()
		jobBuilderFactoryMock = Mock()
		stepConfigurationValidatorMock = Mock()
		jobCompletenessDeciderMock = Mock()
		stepToStepPropertiesProviderMock = Mock()
		stepCompletenessDeciderMock = Mock()
		stepPropertiesMap = Mock()
		stepProperties = Mock()
		createCompaniesStep = Mock()
		createSeriesStep = Mock()
		createSeasonsStep = Mock()
		createPerformersStep = Mock()
		createStaffStep = Mock()
		createAstronomicalObjectsStep = Mock()
		linkAstronomicalObjectsStep = Mock()
		createSpeciesStep = Mock()
		createOrganizationsStep = Mock()
		createTitlesStep = Mock()
		createCharactersStep = Mock()
		linkCharactersStep = Mock()
		createEpisodesStep = Mock()
		createMoviesStep = Mock()
		createComicSeriesStep = Mock()
		linkComicSeriesStep = Mock()
		createComicsStep = Mock()
		createComicStripsStep = Mock()
		createComicCollectionsStep = Mock()
		createFoodsStep = Mock()
		createLocationsStep = Mock()
		createBookSeriesStep = Mock()
		createBooksStep = Mock()
		linkBookSeriesStep = Mock()
		createBookCollectionsStep = Mock()
		jobRepository = Mock()
		createMagazinesStep = Mock()
		createMagazineSeriesStep = Mock()
		createLiteratureStep = Mock()
		createVideoReleasesStep = Mock()
		createTradingCardsStep = Mock()
		createVideoGamesStep = Mock()
		createSoundtracksStep = Mock()
		createWeaponsStep = Mock()
		createSpacecraftTypesStep = Mock()
		createSpacecraftClassesStep = Mock()
		createSpacecraftsStep = Mock()
		createMaterialsStep = Mock()
		createConflictsStep = Mock()
		createAnimalsStep = Mock()
		createElementsStep = Mock()
		createMedicalConditionsStep = Mock()
		createTechnologyStep = Mock()
		createOccupationsStep = Mock()
		springBatchJobBuilder = new SpringBatchJobBuilder(JobName.JOB_CREATE)
		springBatchJobBuilder.repository(jobRepository)
		afterLatestStepJobExecutionListenerMock = Mock()
		jobBuilder = new JobBuilder(applicationContextMock, jobBuilderFactoryMock, stepConfigurationValidatorMock, jobCompletenessDeciderMock,
				stepToStepPropertiesProviderMock, stepCompletenessDeciderMock)
	}

	void "Job is built with first available step"() {
		given:
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = Mock()

		when:
		FlowJob job = (FlowJob) jobBuilder.buildNext()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether job is completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> false

		then: 'step properties are provided'
		1 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap

		then: 'CREATE_COMPANIES step is skipped because it is disabled'
		1 * stepPropertiesMap.get(StepName.CREATE_COMPANIES) >> stepProperties
		1 * stepProperties.enabled >> false

		then: 'CREATE_SERIES step is skipped because it is complete'
		1 * stepPropertiesMap.get(StepName.CREATE_SERIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SERIES) >> true

		then: 'CREATE_SEASONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SEASONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SEASONS) >> false
		1 * applicationContextMock.getBean(StepName.CREATE_SEASONS, Step) >> createSeasonsStep

		then: 'jobCreate builder is retrieved'
		1 * jobBuilderFactoryMock.get(JobName.JOB_CREATE) >> springBatchJobBuilder

		then: 'Task executor is retrieved from application context'
		1 * applicationContextMock.getBean(SimpleAsyncTaskExecutor) >> simpleAsyncTaskExecutor

		then: 'AfterLatestStepJobExecutionListener is retrieved from application context'
		1 * applicationContextMock.getBean(AfterLatestStepJobExecutionListener) >> afterLatestStepJobExecutionListenerMock

		then: 'no other interactions are expected'
		0 * _

		then: 'job is being returned'
		job.name == JobName.JOB_CREATE
		job.jobRepository == jobRepository
		((SplitState) ((SimpleFlow) job.flow).startState).flows.size() == 1
		((SplitState) ((SimpleFlow) job.flow).startState).flows[0].name == 'create1'
	}

	void "Job is not built when job is completed"() {
		when:
		FlowJob job = (FlowJob) jobBuilder.buildNext()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether all steps in job are completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> true

		then: 'no other interactions are expected'
		0 * _

		then: 'job is null'
		job == null
	}

	void "Job is not build when no steps are enabled"() {
		when:
		FlowJob job = (FlowJob) jobBuilder.buildNext()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether job is completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> false

		then: 'step properties are provided'
		1 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap

		then: 'all steps are disabled'
		StepConfigurationValidator.NUMBER_OF_STEPS * stepPropertiesMap.get(_) >> stepProperties
		StepConfigurationValidator.NUMBER_OF_STEPS * stepProperties.enabled >> false

		then: 'no other interactions are expected'
		0 * _

		then: 'null is being returned'
		job == null
	}

}
