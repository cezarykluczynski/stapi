package com.cezarykluczynski.stapi.util.feature_switch.service

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType
import spock.lang.Specification

class FeatureSwitchTypePropertiesMapperTest extends Specification {

	private FeatureSwitchTypePropertiesMapper featureSwitchTypePropertiesMapper

	void setup() {
		featureSwitchTypePropertiesMapper = new FeatureSwitchTypePropertiesMapper()
	}

	void "maps valid string to enum"() {
		expect:
		featureSwitchTypePropertiesMapper.map('adminPanel') == FeatureSwitchType.ADMIN_PANEL
		featureSwitchTypePropertiesMapper.map('userPanel') == FeatureSwitchType.USER_PANEL
	}

	void "throws exception when invalid value is mapped to enum"() {
		when:
		featureSwitchTypePropertiesMapper.map('unknown')

		then:
		thrown(StapiRuntimeException)
	}

}
