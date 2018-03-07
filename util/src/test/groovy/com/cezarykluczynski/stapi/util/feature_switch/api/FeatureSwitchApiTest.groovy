package com.cezarykluczynski.stapi.util.feature_switch.api

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.cezarykluczynski.stapi.util.feature_switch.configuration.FeatureSwitchProperties
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchesDTO
import com.cezarykluczynski.stapi.util.feature_switch.service.FeatureSwitchTypePropertiesMapper
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.ImmutableMap
import spock.lang.Specification

class FeatureSwitchApiTest extends Specification {

	private static final String KEY = 'ADMIN_PANEL'
	private static final FeatureSwitchType ENUM_VALUE = FeatureSwitchType.ADMIN_PANEL
	private static final boolean FEATURE_SWITCH_VALUE = RandomUtil.nextBoolean()

	private FeatureSwitchProperties featureSwitchPropertiesMock

	private FeatureSwitchTypePropertiesMapper featureSwitchTypePropertiesMapperMock

	private FeatureSwitchApi featureSwitchApi

	void setup() {
		featureSwitchPropertiesMock = Mock()
		featureSwitchTypePropertiesMapperMock = Mock()
		featureSwitchApi = new FeatureSwitchApi(featureSwitchPropertiesMock, featureSwitchTypePropertiesMapperMock)
	}

	void "gets all feature switches"() {
		when:
		FeatureSwitchesDTO featureSwitchesDTO = featureSwitchApi.all

		then:
		1 * featureSwitchPropertiesMock.featureSwitch >> ImmutableMap.of(KEY, FEATURE_SWITCH_VALUE)
		1 * featureSwitchTypePropertiesMapperMock.map(KEY) >> ENUM_VALUE
		0 * _
		featureSwitchesDTO.featureSwitches.size() == 1
		featureSwitchesDTO.featureSwitches[0].type == ENUM_VALUE
		featureSwitchesDTO.featureSwitches[0].enabled == FEATURE_SWITCH_VALUE
	}

	void "tells when feature switch is enabled"() {
		when:
		boolean isEnabled = featureSwitchApi.isEnabled(ENUM_VALUE)

		then:
		1 * featureSwitchPropertiesMock.featureSwitch >> ImmutableMap.of(KEY, true)
		1 * featureSwitchTypePropertiesMapperMock.map(KEY) >> ENUM_VALUE
		0 * _
		isEnabled
	}

	void "tells when feature switch is disabled"() {
		when:
		boolean isEnabled = featureSwitchApi.isEnabled(ENUM_VALUE)

		then:
		1 * featureSwitchPropertiesMock.featureSwitch >> ImmutableMap.of(KEY, false)
		1 * featureSwitchTypePropertiesMapperMock.map(KEY) >> ENUM_VALUE
		0 * _
		!isEnabled
	}

	void "throws exception when feature switch is not found"() {
		when:
		featureSwitchApi.isEnabled(ENUM_VALUE)

		then:
		1 * featureSwitchPropertiesMock.featureSwitch >> ImmutableMap.of()
		0 * _
		thrown(StapiRuntimeException)
	}

}
