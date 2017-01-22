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
		jobBuilderException.message == 'java.lang.RuntimeException: Number of configured steps is 7, but 0 steps found'
	}

	void "throws exception when two steps has the same order"() {
		given:
		StepProperties seriesStepProperties = Mock(StepProperties)
		seriesStepProperties.order >> 1
		stepsPropertiesMock.createSeries >> seriesStepProperties
		StepProperties performersStepProperties = Mock(StepProperties)
		seriesStepProperties.order >> 2
		stepsPropertiesMock.createPerformers >> performersStepProperties
		StepProperties staffStepProperties = Mock(StepProperties)
		staffStepProperties.order >> 3
		stepsPropertiesMock.createStaff >> staffStepProperties
		StepProperties astronomicalObjectsStepProperties  = Mock(StepProperties)
		astronomicalObjectsStepProperties .order >> 4
		stepsPropertiesMock.createAstronomicalObjects >> astronomicalObjectsStepProperties
		StepProperties charactersStepProperties = Mock(StepProperties)
		charactersStepProperties.order >> 5
		stepsPropertiesMock.createCharacters >> charactersStepProperties
		StepProperties episodesStepProperties = Mock(StepProperties)
		episodesStepProperties.order >> 1
		stepsPropertiesMock.createEpisodes >> episodesStepProperties
		StepProperties moviesStepProperties = Mock(StepProperties)
		moviesStepProperties.order >> 7
		stepsPropertiesMock.createMovies >> moviesStepProperties

		when:
		stepConfigurationValidator.validate()

		then:
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'java.lang.RuntimeException: Step CREATE_EPISODES has order 1, ' +
				'but this order was already given to step CREATE_SERIES'
	}

	void "correctly configured steps passed validation"() {
		given:
		StepProperties seriesStepProperties = Mock(StepProperties)
		seriesStepProperties.order >> 1
		stepsPropertiesMock.createSeries >> seriesStepProperties
		StepProperties performersStepProperties = Mock(StepProperties)
		seriesStepProperties.order >> 2
		stepsPropertiesMock.createPerformers >> performersStepProperties
		StepProperties staffStepProperties = Mock(StepProperties)
		staffStepProperties.order >> 3
		stepsPropertiesMock.createStaff >> staffStepProperties
		StepProperties astronomicalObjectsStepProperties  = Mock(StepProperties)
		astronomicalObjectsStepProperties .order >> 4
		stepsPropertiesMock.createAstronomicalObjects >> astronomicalObjectsStepProperties
		StepProperties charactersStepProperties = Mock(StepProperties)
		charactersStepProperties.order >> 5
		stepsPropertiesMock.createCharacters >> charactersStepProperties
		StepProperties episodesStepProperties = Mock(StepProperties)
		episodesStepProperties.order >> 6
		stepsPropertiesMock.createEpisodes >> episodesStepProperties
		StepProperties moviesStepProperties = Mock(StepProperties)
		moviesStepProperties.order >> 7
		stepsPropertiesMock.createMovies >> moviesStepProperties

		when:
		stepConfigurationValidator.validate()

		then:
		notThrown(JobBuilderException)
	}

}
