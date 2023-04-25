package com.cezarykluczynski.stapi.etl.common.configuration

import com.squareup.okhttp.OkHttpClient
import spock.lang.Specification

class CommonConfigurationTest extends Specification {

	private CommonConfiguration commonConfiguration

	void setup() {
		commonConfiguration = new CommonConfiguration()
	}

	void "OkHttpClient is created"() {
		when:
		OkHttpClient okHttpClient = commonConfiguration.okHttpClient()

		then:
		okHttpClient != null
	}

}
