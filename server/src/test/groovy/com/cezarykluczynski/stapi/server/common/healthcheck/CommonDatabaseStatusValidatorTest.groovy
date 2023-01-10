package com.cezarykluczynski.stapi.server.common.healthcheck

import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification

import java.sql.SQLTimeoutException

class CommonDatabaseStatusValidatorTest extends Specification {

	SeriesRepository seriesRepositoryMock

	CommonDatabaseStatusValidator commonDatabaseStatusValidator

	void setup() {
		seriesRepositoryMock = Mock()
		commonDatabaseStatusValidator = new CommonDatabaseStatusValidator(seriesRepositoryMock)
	}

	void "does nothing when repository does not throw exception"() {
		when:
		commonDatabaseStatusValidator.validateDatabaseAccess()

		then:
		1 * seriesRepositoryMock.count() >> 11
		0 * _
		notThrown(StapiRuntimeException)
	}

	void "rethrows exception when repository throws exception"() {
		when:
		commonDatabaseStatusValidator.validateDatabaseAccess()

		then:
		1 * seriesRepositoryMock.count() >> {
			throw new SQLTimeoutException('', null)
		}
		0 * _
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message.contains('DB is down')
	}

}
