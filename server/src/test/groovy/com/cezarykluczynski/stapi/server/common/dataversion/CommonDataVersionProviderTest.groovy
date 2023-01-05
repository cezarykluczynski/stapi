package com.cezarykluczynski.stapi.server.common.dataversion

import com.cezarykluczynski.stapi.etl.configuration.job.service.AllStepExecutionsProvider
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.server.common.dto.DataVersionDTO
import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.StepExecution
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneOffset

class CommonDataVersionProviderTest extends Specification {

	private static final String DATA_VERSION = 'DATA_VERSION'

	Environment environmentMock

	ApplicationContext applicationContextMock

	CommonDataVersionProvider commonDataVersionProvider

	void setup() {
		environmentMock = Mock()
		applicationContextMock = Mock()
		commonDataVersionProvider = new CommonDataVersionProvider(environmentMock, applicationContextMock)
	}

	void "provides value when there is env variables set"() {
		when:
		DataVersionDTO dataVersionDTO = commonDataVersionProvider.provide()

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_DATA_VERSION) >> DATA_VERSION
		0 * _
		dataVersionDTO.dataVersion == DATA_VERSION
	}

	void "provides value when there is no env variables set, and no AllStepExecutionsProvider bean is present"() {
		when:
		DataVersionDTO dataVersionDTO = commonDataVersionProvider.provide()

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_DATA_VERSION) >> null
		1 * applicationContextMock.getBean(AllStepExecutionsProvider) >> {
			throw new NoSuchBeanDefinitionException('')
		}
		0 * _
		dataVersionDTO.dataVersion == null
	}

	void "provides value when there is no env variables set, but AllStepExecutionsProvider bean is present"() {
		given:
		AllStepExecutionsProvider allStepExecutionsProvider = Mock()
		StepExecution stepExecutionEarlier = new StepExecution('1', null)
		StepExecution stepExecutionLater = new StepExecution('2', null)
		stepExecutionEarlier.setStatus(BatchStatus.COMPLETED)
		stepExecutionLater.setStatus(BatchStatus.COMPLETED)
		stepExecutionEarlier.setEndTime(LocalDateTime.ofEpochSecond(1550000000L, 0, ZoneOffset.UTC))
		stepExecutionLater.setEndTime(LocalDateTime.ofEpochSecond(1650000000L, 0, ZoneOffset.UTC))

		when:
		DataVersionDTO dataVersionDTO = commonDataVersionProvider.provide()

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_DATA_VERSION) >> null
		1 * applicationContextMock.getBean(AllStepExecutionsProvider) >> allStepExecutionsProvider
		1 * allStepExecutionsProvider.provide(JobName.JOB_CREATE) >> [stepExecutionLater, stepExecutionEarlier]
		0 * _
		dataVersionDTO.dataVersion == '2022-04'
	}

	void "provides value when there is no env variables set, and no steps from AllStepExecutionsProvider bean present"() {
		given:
		AllStepExecutionsProvider allStepExecutionsProvider = Mock()

		when:
		DataVersionDTO dataVersionDTO = commonDataVersionProvider.provide()

		then:
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_DATA_VERSION) >> null
		1 * applicationContextMock.getBean(AllStepExecutionsProvider) >> allStepExecutionsProvider
		1 * allStepExecutionsProvider.provide(JobName.JOB_CREATE) >> []
		0 * _
		dataVersionDTO.dataVersion == null
	}

}
