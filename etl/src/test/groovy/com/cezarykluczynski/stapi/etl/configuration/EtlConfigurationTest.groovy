package com.cezarykluczynski.stapi.etl.configuration

import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor
import spock.lang.Specification

class EtlConfigurationTest extends Specification {

	private EtlConfiguration etlConfiguration

	def setup() {
		etlConfiguration = new EtlConfiguration()
	}

	def "task exeturor is created"() {
		when:
		TaskExecutor taskExecutor = etlConfiguration.taskExecutor()

		then:
		taskExecutor instanceof SimpleAsyncTaskExecutor
	}

}
