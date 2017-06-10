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
		jobBuilderException.message == 'java.lang.RuntimeException: Number of configured steps is 24, but 0 steps found'
	}

	void "throws exception when two steps has the same order"() {
		given:
		StepProperties createCompaniesStepProperties = Mock()
		createCompaniesStepProperties.order >> 1
		stepsPropertiesMock.createCompanies >> createCompaniesStepProperties
		StepProperties createSeriesStepProperties = Mock()
		createSeriesStepProperties.order >> 2
		stepsPropertiesMock.createSeries >> createSeriesStepProperties
		StepProperties performersStepProperties = Mock()
		performersStepProperties.order >> 3
		stepsPropertiesMock.createPerformers >> performersStepProperties
		StepProperties createStaffStepProperties = Mock()
		createStaffStepProperties.order >> 4
		stepsPropertiesMock.createStaff >> createStaffStepProperties
		StepProperties createAstronomicalObjectsStepProperties = Mock()
		createAstronomicalObjectsStepProperties.order >> 5
		stepsPropertiesMock.createAstronomicalObjects >> createAstronomicalObjectsStepProperties
		StepProperties createSpeciesStepProperties = Mock()
		createSpeciesStepProperties.order >> 6
		stepsPropertiesMock.createSpecies >> createSpeciesStepProperties
		StepProperties createCharactersStepProperties = Mock()
		createCharactersStepProperties.order >> 7
		stepsPropertiesMock.createCharacters >> createCharactersStepProperties
		StepProperties createEpisodesStepProperties = Mock()
		createEpisodesStepProperties.order >> 1
		stepsPropertiesMock.createEpisodes >> createEpisodesStepProperties
		StepProperties createMoviesStepProperties = Mock()
		createMoviesStepProperties.order >> 9
		stepsPropertiesMock.createMovies >> createMoviesStepProperties
		StepProperties linkAstronomicalObjectsStepProperties = Mock()
		linkAstronomicalObjectsStepProperties.order >> 10
		stepsPropertiesMock.linkAstronomicalObjects >> linkAstronomicalObjectsStepProperties
		StepProperties createComicSeriesStepProperties = Mock()
		createComicSeriesStepProperties.order >> 11
		stepsPropertiesMock.createComicSeries >> createComicSeriesStepProperties
		StepProperties linkComicSeriesStepProperties = Mock()
		linkComicSeriesStepProperties.order >> 12
		stepsPropertiesMock.linkComicSeries >> linkComicSeriesStepProperties
		StepProperties createComicsStepProperties = Mock()
		createComicsStepProperties.order >> 13
		stepsPropertiesMock.createComics >> createComicsStepProperties
		StepProperties createComicStripsStepProperties = Mock()
		createComicStripsStepProperties.order >> 14
		stepsPropertiesMock.createComicStrips >> createComicStripsStepProperties
		StepProperties createComicCollectionsStepProperties = Mock()
		createComicCollectionsStepProperties.order >> 15
		stepsPropertiesMock.createComicCollections >> createComicCollectionsStepProperties
		StepProperties createOrganizationsStepProperties = Mock()
		createOrganizationsStepProperties.order >> 16
		stepsPropertiesMock.createOrganizations >> createOrganizationsStepProperties
		StepProperties createFoodsStepProperties = Mock()
		createFoodsStepProperties.order >> 17
		stepsPropertiesMock.createFoods >> createFoodsStepProperties
		StepProperties createLocationsStepProperties = Mock()
		createLocationsStepProperties.order >> 18
		stepsPropertiesMock.createLocations >> createLocationsStepProperties
		StepProperties createBookSeriesStepProperties = Mock()
		createBookSeriesStepProperties.order >> 19
		stepsPropertiesMock.createBookSeries >> createBookSeriesStepProperties
		StepProperties linkBookSeriesStepProperties = Mock()
		linkBookSeriesStepProperties.order >> 20
		stepsPropertiesMock.linkBookSeries >> linkBookSeriesStepProperties
		StepProperties createBooksStepProperties = Mock()
		createBooksStepProperties.order >> 21
		stepsPropertiesMock.createBooks >> createBooksStepProperties
		StepProperties createBookCollectionsStepProperties = Mock()
		createBookCollectionsStepProperties.order >> 22
		stepsPropertiesMock.createBookCollections >> createBookCollectionsStepProperties
		StepProperties createMagazinesStepProperties = Mock()
		createMagazinesStepProperties.order >> 23
		stepsPropertiesMock.createMagazines >> createMagazinesStepProperties
		StepProperties createMagazineSeriesStepProperties = Mock()
		createMagazineSeriesStepProperties.order >> 24
		stepsPropertiesMock.createMagazineSeries >> createMagazineSeriesStepProperties

		when:
		stepConfigurationValidator.validate()

		then:
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'java.lang.RuntimeException: Step CREATE_EPISODES has order 1, ' +
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
		StepProperties performersStepProperties = Mock()
		performersStepProperties.order >> 3
		stepsPropertiesMock.createPerformers >> performersStepProperties
		StepProperties createStaffStepProperties = Mock()
		createStaffStepProperties.order >> 4
		stepsPropertiesMock.createStaff >> createStaffStepProperties
		StepProperties createAstronomicalObjectsStepProperties = Mock()
		createAstronomicalObjectsStepProperties.order >> 5
		stepsPropertiesMock.createAstronomicalObjects >> createAstronomicalObjectsStepProperties
		StepProperties createSpeciesStepProperties = Mock()
		createSpeciesStepProperties.order >> 6
		stepsPropertiesMock.createSpecies >> createSpeciesStepProperties
		StepProperties createCharactersStepProperties = Mock()
		createCharactersStepProperties.order >> 7
		stepsPropertiesMock.createCharacters >> createCharactersStepProperties
		StepProperties createEpisodesStepProperties = Mock()
		createEpisodesStepProperties.order >> 8
		stepsPropertiesMock.createEpisodes >> createEpisodesStepProperties
		StepProperties createMoviesStepProperties = Mock()
		createMoviesStepProperties.order >> 9
		stepsPropertiesMock.createMovies >> createMoviesStepProperties
		StepProperties linkAstronomicalObjectsStepProperties = Mock()
		linkAstronomicalObjectsStepProperties.order >> 10
		stepsPropertiesMock.linkAstronomicalObjects >> linkAstronomicalObjectsStepProperties
		StepProperties createComicSeriesStepProperties = Mock()
		createComicSeriesStepProperties.order >> 11
		stepsPropertiesMock.createComicSeries >> createComicSeriesStepProperties
		StepProperties linkComicSeriesStepProperties = Mock()
		linkComicSeriesStepProperties.order >> 12
		stepsPropertiesMock.linkComicSeries >> linkComicSeriesStepProperties
		StepProperties createComicsStepProperties = Mock()
		createComicsStepProperties.order >> 13
		stepsPropertiesMock.createComics >> createComicsStepProperties
		StepProperties createComicStripsStepProperties = Mock()
		createComicStripsStepProperties.order >> 14
		stepsPropertiesMock.createComicStrips >> createComicStripsStepProperties
		StepProperties createComicCollectionsStepProperties = Mock()
		createComicCollectionsStepProperties.order >> 15
		stepsPropertiesMock.createComicCollections >> createComicCollectionsStepProperties
		StepProperties createOrganizationsStepProperties = Mock()
		createOrganizationsStepProperties.order >> 16
		stepsPropertiesMock.createOrganizations >> createOrganizationsStepProperties
		StepProperties createFoodsStepProperties = Mock()
		createFoodsStepProperties.order >> 17
		stepsPropertiesMock.createFoods >> createFoodsStepProperties
		StepProperties createLocationsStepProperties = Mock()
		createLocationsStepProperties.order >> 18
		stepsPropertiesMock.createLocations >> createLocationsStepProperties
		StepProperties createBookSeriesStepProperties = Mock()
		createBookSeriesStepProperties.order >> 19
		stepsPropertiesMock.createBookSeries >> createBookSeriesStepProperties
		StepProperties linkBookSeriesStepProperties = Mock()
		linkBookSeriesStepProperties.order >> 20
		stepsPropertiesMock.linkBookSeries >> linkBookSeriesStepProperties
		StepProperties createBooksStepProperties = Mock()
		createBooksStepProperties.order >> 21
		stepsPropertiesMock.createBooks >> createBooksStepProperties
		StepProperties createBookCollectionsStepProperties = Mock()
		createBookCollectionsStepProperties.order >> 22
		stepsPropertiesMock.createBookCollections >> createBookCollectionsStepProperties
		StepProperties createMagazinesStepProperties = Mock()
		createMagazinesStepProperties.order >> 23
		stepsPropertiesMock.createMagazines >> createMagazinesStepProperties
		StepProperties createMagazineSeriesStepProperties = Mock()
		createMagazineSeriesStepProperties.order >> 24
		stepsPropertiesMock.createMagazineSeries >> createMagazineSeriesStepProperties

		when:
		stepConfigurationValidator.validate()

		then:
		notThrown(JobBuilderException)
	}

}
