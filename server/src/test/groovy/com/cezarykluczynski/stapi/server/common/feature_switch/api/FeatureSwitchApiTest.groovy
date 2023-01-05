package com.cezarykluczynski.stapi.server.common.feature_switch.api

import com.cezarykluczynski.stapi.server.common.feature_switch.dto.FeatureSwitchType
import com.cezarykluczynski.stapi.server.common.feature_switch.dto.FeatureSwitchesDTO
import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable
import com.cezarykluczynski.stapi.util.constant.SpringProfile
import org.springframework.core.env.Environment
import spock.lang.Specification

class FeatureSwitchApiTest extends Specification {

	private Environment environmentMock

	private FeatureSwitchApi featureSwitchApi

	void setup() {
		environmentMock = Mock()
		featureSwitchApi = new FeatureSwitchApi(environmentMock)
	}

	void "gets all feature switches"() {
		when:
		FeatureSwitchesDTO featureSwitchesDTO = featureSwitchApi.all

		then:
		1 * environmentMock.activeProfiles >> [SpringProfile.DOCKER]
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_TOS_AND_PP, 'false') >> 'true'
		0 * _
		featureSwitchesDTO.featureSwitches.size() == 2
		featureSwitchesDTO.featureSwitches[0].type == FeatureSwitchType.DOCKER
		featureSwitchesDTO.featureSwitches[0].enabled
		featureSwitchesDTO.featureSwitches[1].type == FeatureSwitchType.TOS_AND_PP
		featureSwitchesDTO.featureSwitches[1].enabled
	}

	void "(profile) tells when feature switch is enabled"() {
		when:
		boolean isEnabled = featureSwitchApi.isEnabled(FeatureSwitchType.DOCKER)

		then:
		1 * environmentMock.activeProfiles >> [SpringProfile.DOCKER]
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_TOS_AND_PP, 'false') >> 'false'
		0 * _
		isEnabled
	}

	void "(env) tells when feature switch is enabled"() {
		when:
		boolean isEnabled = featureSwitchApi.isEnabled(FeatureSwitchType.TOS_AND_PP)

		then:
		1 * environmentMock.activeProfiles >> [SpringProfile.DOCKER]
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_TOS_AND_PP, 'false') >> 'true'
		0 * _
		isEnabled
	}

	void "(profile) tells when feature switch is disabled"() {
		when:
		boolean isEnabled = featureSwitchApi.isEnabled(FeatureSwitchType.DOCKER)

		then:
		1 * environmentMock.activeProfiles >> []
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_TOS_AND_PP, 'false') >> 'false'
		0 * _
		!isEnabled
	}

	void "(env) tells when feature switch is disabled"() {
		when:
		boolean isEnabled = featureSwitchApi.isEnabled(FeatureSwitchType.TOS_AND_PP)

		then:
		1 * environmentMock.activeProfiles >> [SpringProfile.DOCKER]
		1 * environmentMock.getProperty(EnvironmentVariable.STAPI_TOS_AND_PP, 'false') >> 'false'
		0 * _
		!isEnabled
	}

}
