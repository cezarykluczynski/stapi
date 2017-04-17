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
		jobBuilderException.message == 'java.lang.RuntimeException: Number of configured steps is 19, but 0 steps found'
	}

	void "throws exception when two steps has the same order"() {
		given:
		StepProperties createCompaniesStepProperties = Mock(StepProperties)
		createCompaniesStepProperties.order >> 1
		stepsPropertiesMock.createCompanies >> createCompaniesStepProperties
		StepProperties createSeriesStepProperties = Mock(StepProperties)
		createSeriesStepProperties.order >> 2
		stepsPropertiesMock.createSeries >> createSeriesStepProperties
		StepProperties performersStepProperties = Mock(StepProperties)
		performersStepProperties.order >> 3
		stepsPropertiesMock.createPerformers >> performersStepProperties
		StepProperties createStaffStepProperties = Mock(StepProperties)
		createStaffStepProperties.order >> 4
		stepsPropertiesMock.createStaff >> createStaffStepProperties
		StepProperties createAstronomicalObjectsStepProperties = Mock(StepProperties)
		createAstronomicalObjectsStepProperties.order >> 5
		stepsPropertiesMock.createAstronomicalObjects >> createAstronomicalObjectsStepProperties
		StepProperties createSpeciesStepProperties = Mock(StepProperties)
		createSpeciesStepProperties.order >> 6
		stepsPropertiesMock.createSpecies >> createSpeciesStepProperties
		StepProperties createCharactersStepProperties = Mock(StepProperties)
		createCharactersStepProperties.order >> 7
		stepsPropertiesMock.createCharacters >> createCharactersStepProperties
		StepProperties createEpisodesStepProperties = Mock(StepProperties)
		createEpisodesStepProperties.order >> 1
		stepsPropertiesMock.createEpisodes >> createEpisodesStepProperties
		StepProperties createMoviesStepProperties = Mock(StepProperties)
		createMoviesStepProperties.order >> 9
		stepsPropertiesMock.createMovies >> createMoviesStepProperties
		StepProperties linkAstronomicalObjectsStepProperties = Mock(StepProperties)
		linkAstronomicalObjectsStepProperties.order >> 10
		stepsPropertiesMock.linkAstronomicalObjects >> linkAstronomicalObjectsStepProperties
		StepProperties createComicSeriesStepProperties = Mock(StepProperties)
		createComicSeriesStepProperties.order >> 11
		stepsPropertiesMock.createComicSeries >> createComicSeriesStepProperties
		StepProperties linkComicSeriesStepProperties = Mock(StepProperties)
		linkComicSeriesStepProperties.order >> 12
		stepsPropertiesMock.linkComicSeries >> linkComicSeriesStepProperties
		StepProperties createComicsStepProperties = Mock(StepProperties)
		createComicsStepProperties.order >> 13
		stepsPropertiesMock.createComics >> createComicsStepProperties
		StepProperties createComicStripsStepProperties = Mock(StepProperties)
		createComicStripsStepProperties.order >> 14
		stepsPropertiesMock.createComicStrips >> createComicStripsStepProperties
		StepProperties createComicCollectionsStepProperties = Mock(StepProperties)
		createComicCollectionsStepProperties.order >> 15
		stepsPropertiesMock.createComicCollections >> createComicCollectionsStepProperties
		StepProperties createOrganizationsStepProperties = Mock(StepProperties)
		createOrganizationsStepProperties.order >> 16
		stepsPropertiesMock.createOrganizations >> createOrganizationsStepProperties
		StepProperties createFoodsStepProperties = Mock(StepProperties)
		createFoodsStepProperties.order >> 17
		stepsPropertiesMock.createFoods >> createFoodsStepProperties
		StepProperties createLocationsStepProperties = Mock(StepProperties)
		createLocationsStepProperties.order >> 18
		stepsPropertiesMock.createLocations >> createLocationsStepProperties
		StepProperties createBooksStepProperties = Mock(StepProperties)
		createBooksStepProperties.order >> 19
		stepsPropertiesMock.createBooks >> createBooksStepProperties

		when:
		stepConfigurationValidator.validate()

		then:
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'java.lang.RuntimeException: Step CREATE_EPISODES has order 1, ' +
				'but this order was already given to step CREATE_COMPANIES'
	}

	void "correctly configured steps passed validation"() {
		given:
		StepProperties createCompaniesStepProperties = Mock(StepProperties)
		createCompaniesStepProperties.order >> 1
		stepsPropertiesMock.createCompanies >> createCompaniesStepProperties
		StepProperties createSeriesStepProperties = Mock(StepProperties)
		createSeriesStepProperties.order >> 2
		stepsPropertiesMock.createSeries >> createSeriesStepProperties
		StepProperties performersStepProperties = Mock(StepProperties)
		performersStepProperties.order >> 3
		stepsPropertiesMock.createPerformers >> performersStepProperties
		StepProperties createStaffStepProperties = Mock(StepProperties)
		createStaffStepProperties.order >> 4
		stepsPropertiesMock.createStaff >> createStaffStepProperties
		StepProperties createAstronomicalObjectsStepProperties = Mock(StepProperties)
		createAstronomicalObjectsStepProperties.order >> 5
		stepsPropertiesMock.createAstronomicalObjects >> createAstronomicalObjectsStepProperties
		StepProperties createSpeciesStepProperties = Mock(StepProperties)
		createSpeciesStepProperties.order >> 6
		stepsPropertiesMock.createSpecies >> createSpeciesStepProperties
		StepProperties createCharactersStepProperties = Mock(StepProperties)
		createCharactersStepProperties.order >> 7
		stepsPropertiesMock.createCharacters >> createCharactersStepProperties
		StepProperties createEpisodesStepProperties = Mock(StepProperties)
		createEpisodesStepProperties.order >> 8
		stepsPropertiesMock.createEpisodes >> createEpisodesStepProperties
		StepProperties createMoviesStepProperties = Mock(StepProperties)
		createMoviesStepProperties.order >> 9
		stepsPropertiesMock.createMovies >> createMoviesStepProperties
		StepProperties linkAstronomicalObjectsStepProperties = Mock(StepProperties)
		linkAstronomicalObjectsStepProperties.order >> 10
		stepsPropertiesMock.linkAstronomicalObjects >> linkAstronomicalObjectsStepProperties
		StepProperties createComicSeriesStepProperties = Mock(StepProperties)
		createComicSeriesStepProperties.order >> 11
		stepsPropertiesMock.createComicSeries >> createComicSeriesStepProperties
		StepProperties linkComicSeriesStepProperties = Mock(StepProperties)
		linkComicSeriesStepProperties.order >> 12
		stepsPropertiesMock.linkComicSeries >> linkComicSeriesStepProperties
		StepProperties createComicsStepProperties = Mock(StepProperties)
		createComicsStepProperties.order >> 13
		stepsPropertiesMock.createComics >> createComicsStepProperties
		StepProperties createComicStripsStepProperties = Mock(StepProperties)
		createComicStripsStepProperties.order >> 14
		stepsPropertiesMock.createComicStrips >> createComicStripsStepProperties
		StepProperties createComicCollectionsStepProperties = Mock(StepProperties)
		createComicCollectionsStepProperties.order >> 15
		stepsPropertiesMock.createComicCollections >> createComicCollectionsStepProperties
		StepProperties createOrganizationsStepProperties = Mock(StepProperties)
		createOrganizationsStepProperties.order >> 16
		stepsPropertiesMock.createOrganizations >> createOrganizationsStepProperties
		StepProperties createFoodsStepProperties = Mock(StepProperties)
		createFoodsStepProperties.order >> 17
		stepsPropertiesMock.createFoods >> createFoodsStepProperties
		StepProperties createLocationsStepProperties = Mock(StepProperties)
		createLocationsStepProperties.order >> 18
		stepsPropertiesMock.createLocations >> createLocationsStepProperties
		StepProperties createBooksStepProperties = Mock(StepProperties)
		createBooksStepProperties.order >> 19
		stepsPropertiesMock.createBooks >> createBooksStepProperties

		when:
		stepConfigurationValidator.validate()

		then:
		notThrown(JobBuilderException)
	}

}
