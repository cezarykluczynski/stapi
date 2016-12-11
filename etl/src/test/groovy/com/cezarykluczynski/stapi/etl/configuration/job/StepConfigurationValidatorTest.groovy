package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties
import org.springframework.batch.core.job.builder.JobBuilderException
import spock.lang.Specification

class StepConfigurationValidatorTest extends Specification {

	private StepsProperties stepsPropertiesMock

	private StepConfigurationValidator stepConfigurationValidator

	def setup() {
		stepsPropertiesMock = Mock()
		stepConfigurationValidator = new StepConfigurationValidator(stepsPropertiesMock)
	}

	def "throws exception when there are null steps"() {
		given:
		stepsPropertiesMock.getCreateCharacters() >> null

		when:
		stepConfigurationValidator.validate()

		then:
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'java.lang.RuntimeException: Number of configured steps is 5, but 0 steps found'

	}

	def "throws exception when two steps has the same order"() {
		given:
		stepsPropertiesMock.getCreateSeries() >> Mock(StepProperties) {
			getOrder() >> 1
		}
		stepsPropertiesMock.getCreatePerformers() >> Mock(StepProperties) {
			getOrder() >> 2
		}
		stepsPropertiesMock.getCreateStaff() >> Mock(StepProperties) {
			getOrder() >> 3
		}
		stepsPropertiesMock.getCreateCharacters() >> Mock(StepProperties) {
			getOrder() >> 4
		}
		stepsPropertiesMock.getCreateEpisodes() >> Mock(StepProperties) {
			getOrder() >> 1
		}

		when:
		stepConfigurationValidator.validate()

		then:
		JobBuilderException jobBuilderException = thrown(JobBuilderException)
		jobBuilderException.message == 'java.lang.RuntimeException: Step CREATE_EPISODES has order 1, ' +
				'but this order was already given to step CREATE_SERIES'
	}

	def "correctly configured steps passed validation"() {
		given:
		stepsPropertiesMock.getCreateSeries() >> Mock(StepProperties) {
			getOrder() >> 1
		}
		stepsPropertiesMock.getCreatePerformers() >> Mock(StepProperties) {
			getOrder() >> 2
		}
		stepsPropertiesMock.getCreateStaff() >> Mock(StepProperties) {
			getOrder() >> 3
		}
		stepsPropertiesMock.getCreateCharacters() >> Mock(StepProperties) {
			getOrder() >> 4
		}
		stepsPropertiesMock.getCreateEpisodes() >> Mock(StepProperties) {
			getOrder() >> 5
		}

		when:
		stepConfigurationValidator.validate()

		then:
		notThrown(JobBuilderException)
	}

}
