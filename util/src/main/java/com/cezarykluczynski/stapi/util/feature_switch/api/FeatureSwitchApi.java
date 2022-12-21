package com.cezarykluczynski.stapi.util.feature_switch.api;

import com.cezarykluczynski.stapi.util.constant.EnvironmentVariable;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchDTO;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchesDTO;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureSwitchApi {

	private final Environment environment;

	public FeatureSwitchesDTO getAll() {
		final List<FeatureSwitchDTO> featureSwitches = Lists.newArrayList();
		featureSwitches.add(FeatureSwitchDTO.of(FeatureSwitchType.DOCKER,
				Arrays.asList(environment.getActiveProfiles()).contains(SpringProfile.DOCKER)));
		featureSwitches.add(FeatureSwitchDTO.of(FeatureSwitchType.TOS_AND_PP,
				environment.getProperty(EnvironmentVariable.STAPI_TOS_AND_PP, "false").equals("true")));
		return FeatureSwitchesDTO.of(featureSwitches);
	}

	public boolean isEnabled(FeatureSwitchType featureSwitchType) {
		return getAll().getFeatureSwitches().stream()
				.filter(featureSwitchDTO -> featureSwitchType.equals(featureSwitchDTO.getType()))
				.map(FeatureSwitchDTO::isEnabled)
				.findFirst()
				.orElseThrow(() -> new StapiRuntimeException(String.format("No mapping found for feature switch %s", featureSwitchType)));
	}

}
