package com.cezarykluczynski.stapi.util.feature_switch.service;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

@Service
public class FeatureSwitchTypePropertiesMapper {

	public FeatureSwitchType map(String featureSwitchName) {
		Preconditions.checkNotNull(featureSwitchName, "Feature switch name cannot be null");
		try {
			return FeatureSwitchType.valueOf(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, featureSwitchName));
		} catch (IllegalArgumentException e) {
			throw new StapiRuntimeException(e);
		}
	}

}
