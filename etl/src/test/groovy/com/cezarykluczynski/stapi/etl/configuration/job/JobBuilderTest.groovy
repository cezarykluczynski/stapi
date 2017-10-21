package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider
import com.cezarykluczynski.stapi.etl.configuration.job.service.JobCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.model.common.etl.EtlProperties
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

	private EtlProperties etlPropertiesMock

	private Map<String, StepProperties> stepPropertiesMap

	private StepProperties stepProperties

	private JobBuilder jobBuilder

	private Step createCompaniesStep

	private Step createSeriesStep

	private Step createSeasonsStep

	private Step createPerformersStep

	private Step createStaffStep

	private Step createAstronomicalObjectsStep

	private Step createSpeciesStep

	private Step createOrganizationsStep

	private Step createTitlesStep

	private Step createCharactersStep

	private Step linkCharactersStep

	private Step createEpisodesStep

	private Step createMoviesStep

	private Step linkAstronomicalObjectsStep

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
		etlPropertiesMock = Mock()
		stepPropertiesMap = Mock()
		stepProperties = Mock()
		createCompaniesStep = Mock()
		createSeriesStep = Mock()
		createSeasonsStep = Mock()
		createPerformersStep = Mock()
		createStaffStep = Mock()
		createAstronomicalObjectsStep = Mock()
		createSpeciesStep = Mock()
		createOrganizationsStep = Mock()
		createTitlesStep = Mock()
		createCharactersStep = Mock()
		linkCharactersStep = Mock()
		createEpisodesStep = Mock()
		createMoviesStep = Mock()
		linkAstronomicalObjectsStep = Mock()
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
				stepToStepPropertiesProviderMock, etlPropertiesMock)
	}

	void "Job is built"() {
		given:
		TaskExecutor taskExecutor = Mock()

		when:
		FlowJob job = (FlowJob) jobBuilder.build()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether job is completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> false

		then: 'check is performed whether etl is enabled'
		1 * etlPropertiesMock.enabled >> true

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

		then: 'CREATE_SEASONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SEASONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SEASONS, Step) >> createSeasonsStep
		1 * createSeasonsStep.name >> StepName.CREATE_SEASONS

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

		then: 'CREATE_ORGANIZATIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ORGANIZATIONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ORGANIZATIONS, Step) >> createOrganizationsStep
		1 * createOrganizationsStep.name >> StepName.CREATE_ORGANIZATIONS

		then: 'CREATE_TITLES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_TITLES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_TITLES, Step) >> createTitlesStep
		1 * createTitlesStep.name >> StepName.CREATE_TITLES

		then: 'CREATE_CHARACTERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_CHARACTERS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_CHARACTERS, Step) >> createCharactersStep
		1 * createCharactersStep.name >> StepName.CREATE_CHARACTERS

		then: 'LINK_CHARACTERS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_CHARACTERS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.LINK_CHARACTERS, Step) >> linkCharactersStep
		1 * linkCharactersStep.name >> StepName.LINK_CHARACTERS

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

		then: 'CREATE_BOOK_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_BOOK_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_BOOK_SERIES, Step) >> createBookSeriesStep
		1 * createBookSeriesStep.name >> StepName.CREATE_BOOK_SERIES

		then: 'LINK_BOOK_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.LINK_BOOK_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.LINK_BOOK_SERIES, Step) >> linkBookSeriesStep
		1 * linkBookSeriesStep.name >> StepName.LINK_BOOK_SERIES

		then: 'CREATE_BOOKS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_BOOKS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_BOOKS, Step) >> createBooksStep
		1 * createBooksStep.name >> StepName.CREATE_BOOKS

		then: 'CREATE_BOOK_COLLECTIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_BOOK_COLLECTIONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_BOOK_COLLECTIONS, Step) >> createBookCollectionsStep
		1 * createBookCollectionsStep.name >> StepName.CREATE_BOOK_COLLECTIONS

		then: 'CREATE_MAGAZINE_SERIES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MAGAZINE_SERIES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MAGAZINE_SERIES, Step) >> createMagazineSeriesStep
		1 * createMagazineSeriesStep.name >> StepName.CREATE_MAGAZINE_SERIES

		then: 'CREATE_MAGAZINES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MAGAZINES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MAGAZINES, Step) >> createMagazinesStep
		1 * createMagazinesStep.name >> StepName.CREATE_MAGAZINES

		then: 'CREATE_LITERATURE step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_LITERATURE) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_LITERATURE, Step) >> createLiteratureStep
		1 * createLiteratureStep.name >> StepName.CREATE_LITERATURE

		then: 'CREATE_VIDEO_RELEASES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_VIDEO_RELEASES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_VIDEO_RELEASES, Step) >> createVideoReleasesStep
		1 * createVideoReleasesStep.name >> StepName.CREATE_VIDEO_RELEASES

		then: 'CREATE_TRADING_CARDS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_TRADING_CARDS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_TRADING_CARDS, Step) >> createTradingCardsStep
		1 * createTradingCardsStep.name >> StepName.CREATE_TRADING_CARDS

		then: 'CREATE_VIDEO_GAMES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_VIDEO_GAMES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_VIDEO_GAMES, Step) >> createVideoGamesStep
		1 * createVideoGamesStep.name >> StepName.CREATE_VIDEO_GAMES

		then: 'CREATE_SOUNDTRACKS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SOUNDTRACKS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SOUNDTRACKS, Step) >> createSoundtracksStep
		1 * createSoundtracksStep.name >> StepName.CREATE_SOUNDTRACKS

		then: 'CREATE_WEAPONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_WEAPONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_WEAPONS, Step) >> createWeaponsStep
		1 * createWeaponsStep.name >> StepName.CREATE_WEAPONS

		then: 'CREATE_SPACECRAFT_TYPES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SPACECRAFT_TYPES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SPACECRAFT_TYPES, Step) >> createSpacecraftTypesStep
		1 * createSpacecraftTypesStep.name >> StepName.CREATE_SPACECRAFT_TYPES

		then: 'CREATE_SPACECRAFT_CLASSES step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SPACECRAFT_CLASSES) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SPACECRAFT_CLASSES, Step) >> createSpacecraftClassesStep
		1 * createSpacecraftClassesStep.name >> StepName.CREATE_SPACECRAFT_CLASSES

		then: 'CREATE_SPACECRAFTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_SPACECRAFTS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_SPACECRAFTS, Step) >> createSpacecraftsStep
		1 * createSpacecraftsStep.name >> StepName.CREATE_SPACECRAFTS

		then: 'CREATE_MATERIALS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MATERIALS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MATERIALS, Step) >> createMaterialsStep
		1 * createMaterialsStep.name >> StepName.CREATE_MATERIALS

		then: 'CREATE_CONFLICTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_CONFLICTS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_CONFLICTS, Step) >> createConflictsStep
		1 * createConflictsStep.name >> StepName.CREATE_CONFLICTS

		then: 'CREATE_ANIMALS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ANIMALS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ANIMALS, Step) >> createAnimalsStep
		1 * createAnimalsStep.name >> StepName.CREATE_ANIMALS

		then: 'CREATE_ELEMENTS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_ELEMENTS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_ELEMENTS, Step) >> createElementsStep
		1 * createElementsStep.name >> StepName.CREATE_ELEMENTS

		then: 'CREATE_MEDICAL_CONDITIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_MEDICAL_CONDITIONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_MEDICAL_CONDITIONS, Step) >> createMedicalConditionsStep
		1 * createMedicalConditionsStep.name >> StepName.CREATE_MEDICAL_CONDITIONS

		then: 'CREATE_TECHNOLOGY step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_TECHNOLOGY) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_TECHNOLOGY, Step) >> createTechnologyStep
		1 * createTechnologyStep.name >> StepName.CREATE_TECHNOLOGY

		then: 'CREATE_OCCUPATIONS step is retrieved from application context'
		1 * stepPropertiesMap.get(StepName.CREATE_OCCUPATIONS) >> stepProperties
		1 * stepProperties.isEnabled() >> true
		1 * applicationContextMock.getBean(StepName.CREATE_OCCUPATIONS, Step) >> createOccupationsStep
		1 * createOccupationsStep.name >> StepName.CREATE_OCCUPATIONS

		then: 'Task executor is retrieved from application context'
		1 * applicationContextMock.getBean(TaskExecutor) >> taskExecutor

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
		TaskExecutor taskExecutor = Mock()

		when:
		FlowJob job = (FlowJob) jobBuilder.build()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether job is completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> false

		then: 'check is performed whether etl is enabled'
		1 * etlPropertiesMock.enabled >> true

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
		(StepConfigurationValidator.NUMBER_OF_STEPS - 2) * stepPropertiesMap.get(_) >> stepProperties
		(StepConfigurationValidator.NUMBER_OF_STEPS - 2) * stepProperties.isEnabled() >> false

		then: 'Task executor is retrieved from application context'
		1 * applicationContextMock.getBean(TaskExecutor) >> taskExecutor

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

		then: 'check is performed whether etl is enabled'
		1 * etlPropertiesMock.enabled >> true

		then: 'jobCreate builder is retrieved'
		1 * jobBuilderFactoryMock.get(JobName.JOB_CREATE) >> springBatchJobBuilder

		then: 'step properties are provided'
		1 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap

		then: 'all steps are disabled'
		StepConfigurationValidator.NUMBER_OF_STEPS * stepPropertiesMap.get(_) >> stepProperties
		StepConfigurationValidator.NUMBER_OF_STEPS * stepProperties.isEnabled() >> false

		then: 'no other interactions are expected'
		0 * _

		then: 'null is being returned'
		job == null
	}

	void "Job is not build when etl is not enabled"() {
		when:
		FlowJob job = (FlowJob) jobBuilder.build()

		then: 'validation is performed'
		1 * stepConfigurationValidatorMock.validate()

		then: 'check is performed whether job is completed'
		1 * jobCompletenessDeciderMock.isJobCompleted(JobName.JOB_CREATE) >> false

		then: 'check is performed whether etl is enabled'
		1 * etlPropertiesMock.enabled >> false

		then: 'no other interactions are expected'
		0 * _

		then: 'null is being returned'
		job == null
	}

}
