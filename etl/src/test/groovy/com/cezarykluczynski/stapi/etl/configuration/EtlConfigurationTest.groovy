package com.cezarykluczynski.stapi.etl.configuration

import org.springframework.core.task.SimpleAsyncTaskExecutor
import spock.lang.Specification

class EtlConfigurationTest extends Specification {

	private EtlConfiguration etlConfiguration

	void setup() {
		etlConfiguration = new EtlConfiguration()
	}

	void "task executor is created"() {
		when:
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = etlConfiguration.simpleAsyncTaskExecutor()

		then:
		simpleAsyncTaskExecutor instanceof SimpleAsyncTaskExecutor
	}

}
