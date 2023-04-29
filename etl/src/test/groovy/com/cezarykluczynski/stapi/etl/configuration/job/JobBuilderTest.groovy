package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider
import com.cezarykluczynski.stapi.etl.configuration.job.service.JobCompletenessDecider
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

	void setup() {
		applicationContextMock = Mock()
		jobBuilderFactoryMock = Mock()
		stepConfigurationValidatorMock = Mock()
		jobCompletenessDeciderMock = Mock()
		stepToStepPropertiesProviderMock = Mock()
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
		jobBuilder = new JobBuilder(applicationContextMock, jobBuilderFactoryMock, stepConfigurationValidatorMock, jobCompletenessDeciderMock,
				stepToStepPropertiesProviderMock)
	}

	void "Job is built"() {
		given:
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = Mock()

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
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMPANIES, Step) >> createCompaniesStep

		then: 'CREATE_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SERIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SERIES, Step) >> createSeriesStep

		then: 'CREATE_SEASONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SEASONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SEASONS, Step) >> createSeasonsStep

		then: 'CREATE_PERFORMERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_PERFORMERS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_PERFORMERS, Step) >> createPerformersStep

		then: 'CREATE_STAFF step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_STAFF) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_STAFF, Step) >> createStaffStep

		then: 'CREATE_ASTRONOMICAL_OBJECTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ASTRONOMICAL_OBJECTS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ASTRONOMICAL_OBJECTS, Step) >> createAstronomicalObjectsStep

		then: 'LINK_ASTRONOMICAL_OBJECTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_ASTRONOMICAL_OBJECTS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.LINK_ASTRONOMICAL_OBJECTS, Step) >> linkAstronomicalObjectsStep

		then: 'CREATE_SPECIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SPECIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SPECIES, Step) >> createSpeciesStep

		then: 'CREATE_ORGANIZATIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ORGANIZATIONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ORGANIZATIONS, Step) >> createOrganizationsStep

		then: 'CREATE_TITLES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_TITLES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_TITLES, Step) >> createTitlesStep

		then: 'CREATE_OCCUPATIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_OCCUPATIONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_OCCUPATIONS, Step) >> createOccupationsStep

		then: 'CREATE_CHARACTERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_CHARACTERS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_CHARACTERS, Step) >> createCharactersStep

		then: 'LINK_CHARACTERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_CHARACTERS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.LINK_CHARACTERS, Step) >> linkCharactersStep

		then: 'CREATE_EPISODES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_EPISODES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_EPISODES, Step) >> createEpisodesStep

		then: 'CREATE_MOVIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MOVIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MOVIES, Step) >> createMoviesStep

		then: 'CREATE_COMIC_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMIC_SERIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMIC_SERIES, Step) >> createComicSeriesStep

		then: 'LINK_COMIC_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_COMIC_SERIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.LINK_COMIC_SERIES, Step) >> linkComicSeriesStep

		then: 'CREATE_COMICS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMICS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMICS, Step) >> createComicsStep

		then: 'CREATE_COMIC_STRIPS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMIC_STRIPS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMIC_STRIPS, Step) >> createComicStripsStep

		then: 'CREATE_COMIC_COLLECTIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_COMIC_COLLECTIONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMIC_COLLECTIONS, Step) >> createComicCollectionsStep

		then: 'CREATE_FOODS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_FOODS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_FOODS, Step) >> createFoodsStep

		then: 'CREATE_LOCATIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_LOCATIONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_LOCATIONS, Step) >> createLocationsStep

		then: 'CREATE_BOOK_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_BOOK_SERIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_BOOK_SERIES, Step) >> createBookSeriesStep

		then: 'LINK_BOOK_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_BOOK_SERIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.LINK_BOOK_SERIES, Step) >> linkBookSeriesStep

		then: 'CREATE_BOOKS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_BOOKS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_BOOKS, Step) >> createBooksStep

		then: 'CREATE_BOOK_COLLECTIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_BOOK_COLLECTIONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_BOOK_COLLECTIONS, Step) >> createBookCollectionsStep

		then: 'CREATE_MAGAZINE_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MAGAZINE_SERIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MAGAZINE_SERIES, Step) >> createMagazineSeriesStep

		then: 'CREATE_MAGAZINES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MAGAZINES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MAGAZINES, Step) >> createMagazinesStep

		then: 'CREATE_LITERATURE step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_LITERATURE) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_LITERATURE, Step) >> createLiteratureStep

		then: 'CREATE_VIDEO_RELEASES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_VIDEO_RELEASES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_VIDEO_RELEASES, Step) >> createVideoReleasesStep

		then: 'CREATE_TRADING_CARDS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_TRADING_CARDS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_TRADING_CARDS, Step) >> createTradingCardsStep

		then: 'CREATE_VIDEO_GAMES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_VIDEO_GAMES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_VIDEO_GAMES, Step) >> createVideoGamesStep

		then: 'CREATE_SOUNDTRACKS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SOUNDTRACKS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SOUNDTRACKS, Step) >> createSoundtracksStep

		then: 'CREATE_WEAPONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_WEAPONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_WEAPONS, Step) >> createWeaponsStep

		then: 'CREATE_TECHNOLOGY step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_TECHNOLOGY) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_TECHNOLOGY, Step) >> createTechnologyStep

		then: 'CREATE_SPACECRAFT_TYPES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SPACECRAFT_TYPES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SPACECRAFT_TYPES, Step) >> createSpacecraftTypesStep

		then: 'CREATE_SPACECRAFT_CLASSES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SPACECRAFT_CLASSES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SPACECRAFT_CLASSES, Step) >> createSpacecraftClassesStep

		then: 'CREATE_SPACECRAFTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SPACECRAFTS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SPACECRAFTS, Step) >> createSpacecraftsStep

		then: 'CREATE_MATERIALS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MATERIALS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MATERIALS, Step) >> createMaterialsStep

		then: 'CREATE_CONFLICTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_CONFLICTS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_CONFLICTS, Step) >> createConflictsStep

		then: 'CREATE_ANIMALS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ANIMALS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ANIMALS, Step) >> createAnimalsStep

		then: 'CREATE_ELEMENTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ELEMENTS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ELEMENTS, Step) >> createElementsStep

		then: 'CREATE_MEDICAL_CONDITIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MEDICAL_CONDITIONS) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MEDICAL_CONDITIONS, Step) >> createMedicalConditionsStep

		then: 'Task executor is retrieved from application context'
		1 * applicationContextMock.getBean(SimpleAsyncTaskExecutor) >> simpleAsyncTaskExecutor

		then: 'no other interactions are expected'
		0 * _

		then: 'job is being returned'
		job.name == JobName.JOB_CREATE
		job.jobRepository == jobRepository
		((SplitState) ((SimpleFlow) job.flow).startState).flows.size() == 1
		((SplitState) ((SimpleFlow) job.flow).startState).flows[0].name == 'create'
	}

	void "job is built with only two steps"() {
		given:
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = Mock()

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
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_COMPANIES, Step) >> createCompaniesStep

		then: 'CREATE_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SERIES) >> stepProperties
		1 * stepProperties.enabled >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SERIES, Step) >> createSeriesStep

		then: 'other steps are skipped'
		(StepConfigurationValidator.NUMBER_OF_STEPS - 2) * stepPropertiesMap.get(_) >> stepProperties
		(StepConfigurationValidator.NUMBER_OF_STEPS - 2) * stepProperties.enabled >> false

		then: 'Task executor is retrieved from application context'
		1 * applicationContextMock.getBean(SimpleAsyncTaskExecutor) >> simpleAsyncTaskExecutor

		then: 'no other interactions are expected'
		0 * _

		then: 'job is being returned'
		job.name == JobName.JOB_CREATE
		job.jobRepository == jobRepository
		((SplitState) ((SimpleFlow) job.flow).startState).flows.size() == 1
		((SplitState) ((SimpleFlow) job.flow).startState).flows[0].name == 'create'
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

	void "Job is not build when no steps are enabled"() {
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
		StepConfigurationValidator.NUMBER_OF_STEPS * stepPropertiesMap.get(_) >> stepProperties
		StepConfigurationValidator.NUMBER_OF_STEPS * stepProperties.enabled >> false

		then: 'no other interactions are expected'
		0 * _

		then: 'null is being returned'
		job == null
	}

}
