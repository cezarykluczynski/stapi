package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties
import org.springframework.batch.core.job.builder.JobBuilderException
import spock.lang.Specification

class StepConfigurationValidatorTest extends Specification {

	private StepsProperties stepsPropertiesMock

	private StepConfigurationValidator stepConfigurationValidator

	void setup() {
		stepsPropertiesMock = Mock()
		stepConfigurationValidator = new StepConfigurationValidator(stepsPropertiesMock)
	}

	void "throws exception when there are null steps"() {
		given:
		stepsPropertiesMock.createCharacters >> null

		when:
		stepConfigurationValidator.validate()

		then:
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'com.cezarykluczynski.stapi.util.exception.StapiRuntimeException: ' +
				'Number of configured steps is 27, but 0 steps found'
	}

	void "throws exception when two steps has the same order"() {
		given:
		StepProperties createCompaniesStepProperties = Mock()
		createCompaniesStepProperties.order >> 1
		stepsPropertiesMock.createCompanies >> createCompaniesStepProperties
		StepProperties createSeriesStepProperties = Mock()
		createSeriesStepProperties.order >> 2
		stepsPropertiesMock.createSeries >> createSeriesStepProperties
		StepProperties seasonsStepProperties = Mock()
		seasonsStepProperties.order >> 3
		stepsPropertiesMock.createSeasons >> seasonsStepProperties
		StepProperties performersStepProperties = Mock()
		performersStepProperties.order >> 4
		stepsPropertiesMock.createPerformers >> performersStepProperties
		StepProperties createStaffStepProperties = Mock()
		createStaffStepProperties.order >> 5
		stepsPropertiesMock.createStaff >> createStaffStepProperties
		StepProperties createAstronomicalObjectsStepProperties = Mock()
		createAstronomicalObjectsStepProperties.order >> 6
		stepsPropertiesMock.createAstronomicalObjects >> createAstronomicalObjectsStepProperties
		StepProperties createSpeciesStepProperties = Mock()
		createSpeciesStepProperties.order >> 7
		stepsPropertiesMock.createSpecies >> createSpeciesStepProperties
		StepProperties createCharactersStepProperties = Mock()
		createCharactersStepProperties.order >> 8
		stepsPropertiesMock.createCharacters >> createCharactersStepProperties
		StepProperties createEpisodesStepProperties = Mock()
		createEpisodesStepProperties.order >> 1
		stepsPropertiesMock.createEpisodes >> createEpisodesStepProperties
		StepProperties createMoviesStepProperties = Mock()
		createMoviesStepProperties.order >> 10
		stepsPropertiesMock.createMovies >> createMoviesStepProperties
		StepProperties linkAstronomicalObjectsStepProperties = Mock()
		linkAstronomicalObjectsStepProperties.order >> 11
		stepsPropertiesMock.linkAstronomicalObjects >> linkAstronomicalObjectsStepProperties
		StepProperties createComicSeriesStepProperties = Mock()
		createComicSeriesStepProperties.order >> 12
		stepsPropertiesMock.createComicSeries >> createComicSeriesStepProperties
		StepProperties linkComicSeriesStepProperties = Mock()
		linkComicSeriesStepProperties.order >> 13
		stepsPropertiesMock.linkComicSeries >> linkComicSeriesStepProperties
		StepProperties createComicsStepProperties = Mock()
		createComicsStepProperties.order >> 14
		stepsPropertiesMock.createComics >> createComicsStepProperties
		StepProperties createComicStripsStepProperties = Mock()
		createComicStripsStepProperties.order >> 15
		stepsPropertiesMock.createComicStrips >> createComicStripsStepProperties
		StepProperties createComicCollectionsStepProperties = Mock()
		createComicCollectionsStepProperties.order >> 16
		stepsPropertiesMock.createComicCollections >> createComicCollectionsStepProperties
		StepProperties createOrganizationsStepProperties = Mock()
		createOrganizationsStepProperties.order >> 17
		stepsPropertiesMock.createOrganizations >> createOrganizationsStepProperties
		StepProperties createFoodsStepProperties = Mock()
		createFoodsStepProperties.order >> 18
		stepsPropertiesMock.createFoods >> createFoodsStepProperties
		StepProperties createLocationsStepProperties = Mock()
		createLocationsStepProperties.order >> 19
		stepsPropertiesMock.createLocations >> createLocationsStepProperties
		StepProperties createBookSeriesStepProperties = Mock()
		createBookSeriesStepProperties.order >> 20
		stepsPropertiesMock.createBookSeries >> createBookSeriesStepProperties
		StepProperties linkBookSeriesStepProperties = Mock()
		linkBookSeriesStepProperties.order >> 21
		stepsPropertiesMock.linkBookSeries >> linkBookSeriesStepProperties
		StepProperties createBooksStepProperties = Mock()
		createBooksStepProperties.order >> 22
		stepsPropertiesMock.createBooks >> createBooksStepProperties
		StepProperties createBookCollectionsStepProperties = Mock()
		createBookCollectionsStepProperties.order >> 23
		stepsPropertiesMock.createBookCollections >> createBookCollectionsStepProperties
		StepProperties createMagazinesStepProperties = Mock()
		createMagazinesStepProperties.order >> 24
		stepsPropertiesMock.createMagazines >> createMagazinesStepProperties
		StepProperties createMagazineSeriesStepProperties = Mock()
		createMagazineSeriesStepProperties.order >> 25
		stepsPropertiesMock.createMagazineSeries >> createMagazineSeriesStepProperties
		StepProperties createLiteratureStepProperties = Mock()
		createLiteratureStepProperties.order >> 26
		stepsPropertiesMock.createLiterature >> createLiteratureStepProperties
		StepProperties createVideoReleasesStepProperties = Mock()
		createVideoReleasesStepProperties.order >> 27
		stepsPropertiesMock.createVideoReleases >> createVideoReleasesStepProperties

		when:
		stepConfigurationValidator.validate()

		then:
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'com.cezarykluczynski.stapi.util.exception.StapiRuntimeException: Step CREATE_EPISODES has order 1, ' +
				'but this order was already given to step CREATE_COMPANIES'
	}

	void "correctly configured steps passed validation"() {
		given:
		StepProperties createCompaniesStepProperties = Mock()
		createCompaniesStepProperties.order >> 1
		stepsPropertiesMock.createCompanies >> createCompaniesStepProperties
		StepProperties createSeriesStepProperties = Mock()
		createSeriesStepProperties.order >> 2
		stepsPropertiesMock.createSeries >> createSeriesStepProperties
		StepProperties seasonsStepProperties = Mock()
		seasonsStepProperties.order >> 3
		stepsPropertiesMock.createSeasons >> seasonsStepProperties
		StepProperties performersStepProperties = Mock()
		performersStepProperties.order >> 4
		stepsPropertiesMock.createPerformers >> performersStepProperties
		StepProperties createStaffStepProperties = Mock()
		createStaffStepProperties.order >> 5
		stepsPropertiesMock.createStaff >> createStaffStepProperties
		StepProperties createAstronomicalObjectsStepProperties = Mock()
		createAstronomicalObjectsStepProperties.order >> 6
		stepsPropertiesMock.createAstronomicalObjects >> createAstronomicalObjectsStepProperties
		StepProperties createSpeciesStepProperties = Mock()
		createSpeciesStepProperties.order >> 7
		stepsPropertiesMock.createSpecies >> createSpeciesStepProperties
		StepProperties createCharactersStepProperties = Mock()
		createCharactersStepProperties.order >> 8
		stepsPropertiesMock.createCharacters >> createCharactersStepProperties
		StepProperties createEpisodesStepProperties = Mock()
		createEpisodesStepProperties.order >> 9
		stepsPropertiesMock.createEpisodes >> createEpisodesStepProperties
		StepProperties createMoviesStepProperties = Mock()
		createMoviesStepProperties.order >> 10
		stepsPropertiesMock.createMovies >> createMoviesStepProperties
		StepProperties linkAstronomicalObjectsStepProperties = Mock()
		linkAstronomicalObjectsStepProperties.order >> 11
		stepsPropertiesMock.linkAstronomicalObjects >> linkAstronomicalObjectsStepProperties
		StepProperties createComicSeriesStepProperties = Mock()
		createComicSeriesStepProperties.order >> 12
		stepsPropertiesMock.createComicSeries >> createComicSeriesStepProperties
		StepProperties linkComicSeriesStepProperties = Mock()
		linkComicSeriesStepProperties.order >> 13
		stepsPropertiesMock.linkComicSeries >> linkComicSeriesStepProperties
		StepProperties createComicsStepProperties = Mock()
		createComicsStepProperties.order >> 14
		stepsPropertiesMock.createComics >> createComicsStepProperties
		StepProperties createComicStripsStepProperties = Mock()
		createComicStripsStepProperties.order >> 15
		stepsPropertiesMock.createComicStrips >> createComicStripsStepProperties
		StepProperties createComicCollectionsStepProperties = Mock()
		createComicCollectionsStepProperties.order >> 16
		stepsPropertiesMock.createComicCollections >> createComicCollectionsStepProperties
		StepProperties createOrganizationsStepProperties = Mock()
		createOrganizationsStepProperties.order >> 17
		stepsPropertiesMock.createOrganizations >> createOrganizationsStepProperties
		StepProperties createFoodsStepProperties = Mock()
		createFoodsStepProperties.order >> 18
		stepsPropertiesMock.createFoods >> createFoodsStepProperties
		StepProperties createLocationsStepProperties = Mock()
		createLocationsStepProperties.order >> 19
		stepsPropertiesMock.createLocations >> createLocationsStepProperties
		StepProperties createBookSeriesStepProperties = Mock()
		createBookSeriesStepProperties.order >> 20
		stepsPropertiesMock.createBookSeries >> createBookSeriesStepProperties
		StepProperties linkBookSeriesStepProperties = Mock()
		linkBookSeriesStepProperties.order >> 21
		stepsPropertiesMock.linkBookSeries >> linkBookSeriesStepProperties
		StepProperties createBooksStepProperties = Mock()
		createBooksStepProperties.order >> 22
		stepsPropertiesMock.createBooks >> createBooksStepProperties
		StepProperties createBookCollectionsStepProperties = Mock()
		createBookCollectionsStepProperties.order >> 23
		stepsPropertiesMock.createBookCollections >> createBookCollectionsStepProperties
		StepProperties createMagazinesStepProperties = Mock()
		createMagazinesStepProperties.order >> 24
		stepsPropertiesMock.createMagazines >> createMagazinesStepProperties
		StepProperties createMagazineSeriesStepProperties = Mock()
		createMagazineSeriesStepProperties.order >> 25
		stepsPropertiesMock.createMagazineSeries >> createMagazineSeriesStepProperties
		StepProperties createLiteratureStepProperties = Mock()
		createLiteratureStepProperties.order >> 26
		stepsPropertiesMock.createLiterature >> createLiteratureStepProperties
		StepProperties createVideoReleasesStepProperties = Mock()
		createVideoReleasesStepProperties.order >> 27
		stepsPropertiesMock.createVideoReleases >> createVideoReleasesStepProperties

		when:
		stepConfigurationValidator.validate()

		then:
		notThrown(JobBuilderException)
	}

}
