package com.cezarykluczynski.stapi.etl.astronomicalObject.link.configuration

import com.cezarykluczynski.stapi.etl.astronomicalObject.link.processor.AstronomicalObjectLinkReader
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.model.astronomicalObject.repository.AstronomicalObjectRepository
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class AstronomicalObjectLinkConfigurationTest extends Specification {

	private static final Integer COMMIT_INTERVAL = 1

	private ApplicationContext applicationContextMock

	private StepCompletenessDecider stepCompletenessDeciderMock

	private AstronomicalObjectRepository astronomicalObjectRepositoryMock

	private StepsProperties stepsPropertiesMock

	private AstronomicalObjectLinkConfiguration astronomicalObjectLinkConfiguration

	void setup() {
		applicationContextMock = Mock()
		stepCompletenessDeciderMock = Mock()
		astronomicalObjectRepositoryMock = Mock()
		stepsPropertiesMock = Mock()
		astronomicalObjectLinkConfiguration = new AstronomicalObjectLinkConfiguration(
				applicationContext: applicationContextMock,
				stepCompletenessDecider: stepCompletenessDeciderMock,
				astronomicalObjectRepository: astronomicalObjectRepositoryMock,
				stepsProperties: stepsPropertiesMock)
	}

	void "AstronomicalObjectLinkReader is created"() {
		given:
		StepProperties linkAstronomicalObjectsStepProperties = Mock()

		when:
		AstronomicalObjectLinkReader astronomicalObjectLinkReader = astronomicalObjectLinkConfiguration.astronomicalObjectLinkReader()

		then:
		1 * stepsPropertiesMock.linkAstronomicalObjects >> linkAstronomicalObjectsStepProperties
		1 * linkAstronomicalObjectsStepProperties.commitInterval >> COMMIT_INTERVAL
		0 * _
		astronomicalObjectLinkReader != null
	}

}
