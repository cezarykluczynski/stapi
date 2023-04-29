package com.cezarykluczynski.stapi.model.util

import spock.lang.Specification
import spock.lang.Unroll

class DbUtilsTest extends Specification {

	DbUtils dbUtils

	void setup() {
		dbUtils = new DbUtils()
	}

	@Unroll('when #jdbcUrl is passed, #port is returned')
	void "extracts port from JDBC url"() {
		expect:
		dbUtils.extractPortFromJdbcUrl(jdbcUrl) == port

		where:
		jdbcUrl                                  | port
		'jdbc:postgresql://localhost:5432/stapi' | 5432
		'jdbc:postgresql://localhost/stapi'      | -1
		'http://stapi.co/'                       | -1
	}

}
