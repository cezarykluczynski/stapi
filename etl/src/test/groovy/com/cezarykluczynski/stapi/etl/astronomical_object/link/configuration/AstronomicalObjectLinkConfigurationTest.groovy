package com.cezarykluczynski.stapi.etl.astronomical_object.link.configuration

import com.cezarykluczynski.stapi.etl.astronomical_object.link.processor.AstronomicalObjectLinkReader
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository
import spock.lang.Specification

class AstronomicalObjectLinkConfigurationTest extends Specification {

	private static final Integer COMMIT_INTERVAL = 1

	private AstronomicalObjectRepository astronomicalObjectRepositoryMock

	private StepsProperties stepsPropertiesMock

	private AstronomicalObjectLinkConfiguration astronomicalObjectLinkConfiguration

	void setup() {
		astronomicalObjectRepositoryMock = Mock()
		stepsPropertiesMock = Mock()
		astronomicalObjectLinkConfiguration = new AstronomicalObjectLinkConfiguration(
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
