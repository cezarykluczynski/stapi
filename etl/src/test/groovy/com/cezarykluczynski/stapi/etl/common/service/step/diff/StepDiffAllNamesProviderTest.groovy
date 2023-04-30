package com.cezarykluczynski.stapi.etl.common.service.step.diff

import com.cezarykluczynski.stapi.client.rest.StapiRestClient
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import spock.lang.Specification

class StepDiffAllNamesProviderTest extends Specification {

	StepDiffAllNamesProvider stepDiffAllNamesProvider

	void setup() {
		stepDiffAllNamesProvider = new StepDiffAllNamesProvider(new StepNameToApiProvider())
	}

	void "gets all series"() {
		given:
		StapiRestClient stapiRestClientMock = new StapiRestClient()

		when:
		List<String> allNames = stepDiffAllNamesProvider.get(StepName.CREATE_SERIES, stapiRestClientMock)

		then:
		allNames.size() >= 12
		allNames.size() <= 20
		allNames.contains 'Star Trek: The Next Generation'
		allNames.contains 'Star Trek: Picard'
	}

}
