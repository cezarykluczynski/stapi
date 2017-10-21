package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.google.common.collect.Maps
import org.springframework.batch.core.job.builder.JobBuilderException
import spock.lang.Specification

class StepConfigurationValidatorTest extends Specification {

	private StepToStepPropertiesProvider stepToStepPropertiesProviderMock

	private StepConfigurationValidator stepConfigurationValidator

	void setup() {
		stepToStepPropertiesProviderMock = Mock()
		stepConfigurationValidator = new StepConfigurationValidator(stepToStepPropertiesProviderMock)
	}

	void "throws exception when there are null steps"() {
		given:
		Map<String, StepProperties> stepPropertiesMap = createStepPropertiesMap()
		stepPropertiesMap.put(StepName.CREATE_VIDEO_RELEASES, null)

		when:
		stepConfigurationValidator.validate()

		then:
		1 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap
		thrown(NullPointerException)
	}

	void "throws exception when there are not enough steps"() {
		given:
		Map<String, StepProperties> stepPropertiesMap = createStepPropertiesMap()
		stepPropertiesMap.remove(StepName.CREATE_VIDEO_RELEASES)

		when:
		stepConfigurationValidator.validate()

		then:
		2 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'com.cezarykluczynski.stapi.util.exception.StapiRuntimeException: ' +
				'Number of configured steps is 43, but 42 steps found'
	}

	void "throws exception when two steps has the same order"() {
		given:
		Map<String, StepProperties> stepPropertiesMap = createStepPropertiesMap()
		StepProperties createEpisodesStepProperties = Mock()
		createEpisodesStepProperties.order >> 1
		stepPropertiesMap.put(StepName.CREATE_EPISODES, createEpisodesStepProperties)

		when:
		stepConfigurationValidator.validate()

		then:
		2 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'com.cezarykluczynski.stapi.util.exception.StapiRuntimeException: Step CREATE_EPISODES has order 1, ' +
				'but this order was already given to step CREATE_COMPANIES'
	}

	void "correctly configured steps passed validation"() {
		given:
		Map<String, StepProperties> stepPropertiesMap = createStepPropertiesMap()

		when:
		stepConfigurationValidator.validate()

		then:
		2 * stepToStepPropertiesProviderMock.provide() >> stepPropertiesMap
		notThrown(Exception)
	}

	private Map<String, StepProperties> createStepPropertiesMap() {
		Map<String, StepProperties> stepPropertiesMap = Maps.newLinkedHashMap()

		StepProperties createCompaniesStepProperties = Mock()
		createCompaniesStepProperties.order >> 1
		stepPropertiesMap.put(StepName.CREATE_COMPANIES, createCompaniesStepProperties)
		StepProperties createSeriesStepProperties = Mock()
		createSeriesStepProperties.order >> 2
		stepPropertiesMap.put(StepName.CREATE_SERIES, createSeriesStepProperties)
		StepProperties createSeasonsStepProperties = Mock()
		createSeasonsStepProperties.order >> 3
		stepPropertiesMap.put(StepName.CREATE_SEASONS, createSeasonsStepProperties)
		StepProperties createPerformersStepProperties = Mock()
		createPerformersStepProperties.order >> 4
		stepPropertiesMap.put(StepName.CREATE_PERFORMERS, createPerformersStepProperties)
		StepProperties createStaffStepProperties = Mock()
		createStaffStepProperties.order >> 5
		stepPropertiesMap.put(StepName.CREATE_STAFF, createStaffStepProperties)
		StepProperties createAstronomicalObjectsStepProperties = Mock()
		createAstronomicalObjectsStepProperties.order >> 6
		stepPropertiesMap.put(StepName.CREATE_ASTRONOMICAL_OBJECTS, createAstronomicalObjectsStepProperties)
		StepProperties createSpeciesStepProperties = Mock()
		createSpeciesStepProperties.order >> 7
		stepPropertiesMap.put(StepName.CREATE_SPECIES, createSpeciesStepProperties)
		StepProperties createOrganizationsStepProperties = Mock()
		createOrganizationsStepProperties.order >> 8
		stepPropertiesMap.put(StepName.CREATE_ORGANIZATIONS, createOrganizationsStepProperties)
		StepProperties createTitlesStepProperties = Mock()
		createTitlesStepProperties.order >> 9
		stepPropertiesMap.put(StepName.CREATE_TITLES, createTitlesStepProperties)
		StepProperties createCharactersStepProperties = Mock()
		createCharactersStepProperties.order >> 10
		stepPropertiesMap.put(StepName.CREATE_CHARACTERS, createCharactersStepProperties)
		StepProperties linkCharactersStepProperties = Mock()
		linkCharactersStepProperties.order >> 11
		stepPropertiesMap.put(StepName.LINK_CHARACTERS, linkCharactersStepProperties)
		StepProperties createEpisodesStepProperties = Mock()
		createEpisodesStepProperties.order >> 12
		stepPropertiesMap.put(StepName.CREATE_EPISODES, createEpisodesStepProperties)
		StepProperties createMoviesStepProperties = Mock()
		createMoviesStepProperties.order >> 13
		stepPropertiesMap.put(StepName.CREATE_MOVIES, createMoviesStepProperties)
		StepProperties linkAstronomicalObjectsStepProperties = Mock()
		linkAstronomicalObjectsStepProperties.order >> 14
		stepPropertiesMap.put(StepName.LINK_ASTRONOMICAL_OBJECTS, linkAstronomicalObjectsStepProperties)
		StepProperties createComicSeriesStepProperties = Mock()
		createComicSeriesStepProperties.order >> 15
		stepPropertiesMap.put(StepName.CREATE_COMIC_SERIES, createComicSeriesStepProperties)
		StepProperties linkComicSeriesStepProperties = Mock()
		linkComicSeriesStepProperties.order >> 16
		stepPropertiesMap.put(StepName.LINK_COMIC_SERIES, linkComicSeriesStepProperties)
		StepProperties createComicsStepProperties = Mock()
		createComicsStepProperties.order >> 17
		stepPropertiesMap.put(StepName.CREATE_COMICS, createComicsStepProperties)
		StepProperties createComicStripsStepProperties = Mock()
		createComicStripsStepProperties.order >> 18
		stepPropertiesMap.put(StepName.CREATE_COMIC_STRIPS, createComicStripsStepProperties)
		StepProperties createComicCollectionsStepProperties = Mock()
		createComicCollectionsStepProperties.order >> 19
		stepPropertiesMap.put(StepName.CREATE_COMIC_COLLECTIONS, createComicCollectionsStepProperties)
		StepProperties createFoodsStepProperties = Mock()
		createFoodsStepProperties.order >> 20
		stepPropertiesMap.put(StepName.CREATE_FOODS, createFoodsStepProperties)
		StepProperties createLocationsStepProperties = Mock()
		createLocationsStepProperties.order >> 21
		stepPropertiesMap.put(StepName.CREATE_LOCATIONS, createLocationsStepProperties)
		StepProperties createBookSeriesStepProperties = Mock()
		createBookSeriesStepProperties.order >> 22
		stepPropertiesMap.put(StepName.CREATE_BOOK_SERIES, createBookSeriesStepProperties)
		StepProperties linkBookSeriesStepProperties = Mock()
		linkBookSeriesStepProperties.order >> 23
		stepPropertiesMap.put(StepName.LINK_BOOK_SERIES, linkBookSeriesStepProperties)
		StepProperties createBooksStepProperties = Mock()
		createBooksStepProperties.order >> 24
		stepPropertiesMap.put(StepName.CREATE_BOOKS, createBooksStepProperties)
		StepProperties createBookCollectionsStepProperties = Mock()
		createBookCollectionsStepProperties.order >> 25
		stepPropertiesMap.put(StepName.CREATE_BOOK_COLLECTIONS, createBookCollectionsStepProperties)
		StepProperties createMagazinesStepProperties = Mock()
		createMagazinesStepProperties.order >> 26
		stepPropertiesMap.put(StepName.CREATE_MAGAZINES, createMagazinesStepProperties)
		StepProperties createMagazineSeriesStepProperties = Mock()
		createMagazineSeriesStepProperties.order >> 27
		stepPropertiesMap.put(StepName.CREATE_MAGAZINE_SERIES, createMagazineSeriesStepProperties)
		StepProperties createLiteratureStepProperties = Mock()
		createLiteratureStepProperties.order >> 28
		stepPropertiesMap.put(StepName.CREATE_LITERATURE, createLiteratureStepProperties)
		StepProperties createVideoReleasesStepProperties = Mock()
		createVideoReleasesStepProperties.order >> 29
		stepPropertiesMap.put(StepName.CREATE_VIDEO_RELEASES, createVideoReleasesStepProperties)
		StepProperties createTradingCardsStepProperties = Mock()
		createTradingCardsStepProperties.order >> 30
		stepPropertiesMap.put(StepName.CREATE_TRADING_CARDS, createTradingCardsStepProperties)
		StepProperties createVideoGamesStepProperties = Mock()
		createVideoGamesStepProperties.order >> 31
		stepPropertiesMap.put(StepName.CREATE_VIDEO_GAMES, createVideoGamesStepProperties)
		StepProperties createSoundtracksStepProperties = Mock()
		createSoundtracksStepProperties.order >> 32
		stepPropertiesMap.put(StepName.CREATE_SOUNDTRACKS, createSoundtracksStepProperties)
		StepProperties createWeaponsStepProperties = Mock()
		createWeaponsStepProperties.order >> 33
		stepPropertiesMap.put(StepName.CREATE_WEAPONS, createWeaponsStepProperties)
		StepProperties createSpacecraftTypesStepProperties = Mock()
		createSpacecraftTypesStepProperties.order >> 34
		stepPropertiesMap.put(StepName.CREATE_SPACECRAFT_TYPES, createSpacecraftTypesStepProperties)
		StepProperties createSpacecraftClassesStepProperties = Mock()
		createSpacecraftClassesStepProperties.order >> 35
		stepPropertiesMap.put(StepName.CREATE_SPACECRAFT_CLASSES, createSpacecraftClassesStepProperties)
		StepProperties createSpacecraftsStepProperties = Mock()
		createSpacecraftsStepProperties.order >> 36
		stepPropertiesMap.put(StepName.CREATE_SPACECRAFTS, createSpacecraftsStepProperties)
		StepProperties createMaterialsStepProperties = Mock()
		createMaterialsStepProperties.order >> 37
		stepPropertiesMap.put(StepName.CREATE_MATERIALS, createMaterialsStepProperties)
		StepProperties createConflictsStepProperties = Mock()
		createConflictsStepProperties.order >> 38
		stepPropertiesMap.put(StepName.CREATE_CONFLICTS, createConflictsStepProperties)
		StepProperties createAnimalsStepProperties = Mock()
		createAnimalsStepProperties.order >> 39
		stepPropertiesMap.put(StepName.CREATE_ANIMALS, createAnimalsStepProperties)
		StepProperties createElementsStepProperties = Mock()
		createElementsStepProperties.order >> 40
		stepPropertiesMap.put(StepName.CREATE_ELEMENTS, createElementsStepProperties)
		StepProperties createMedicalConditionsStepProperties = Mock()
		createMedicalConditionsStepProperties.order >> 41
		stepPropertiesMap.put(StepName.CREATE_MEDICAL_CONDITIONS, createMedicalConditionsStepProperties)
		StepProperties createTechnologyProperties = Mock()
		createTechnologyProperties.order >> 42
		stepPropertiesMap.put(StepName.CREATE_TECHNOLOGY, createTechnologyProperties)
		StepProperties createOccupationsProperties = Mock()
		createOccupationsProperties.order >> 43
		stepPropertiesMap.put(StepName.CREATE_OCCUPATIONS, createOccupationsProperties)

		stepPropertiesMap
	}

}
