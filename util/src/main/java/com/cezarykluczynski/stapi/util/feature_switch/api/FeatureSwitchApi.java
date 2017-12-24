package com.cezarykluczynski.stapi.util.feature_switch.api;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.cezarykluczynski.stapi.util.feature_switch.configuration.FeatureSwitchProperties;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchDTO;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchType;
import com.cezarykluczynski.stapi.util.feature_switch.dto.FeatureSwitchesDTO;
import com.cezarykluczynski.stapi.util.feature_switch.service.FeatureSwitchTypePropertiesMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeatureSwitchApi {

	private final FeatureSwitchProperties featureSwitchProperties;

	private final FeatureSwitchTypePropertiesMapper featureSwitchTypePropertiesMapper;

	public FeatureSwitchApi(FeatureSwitchProperties featureSwitchProperties, FeatureSwitchTypePropertiesMapper featureSwitchTypePropertiesMapper) {
		this.featureSwitchProperties = featureSwitchProperties;
		this.featureSwitchTypePropertiesMapper = featureSwitchTypePropertiesMapper;
	}

	public FeatureSwitchesDTO getAll() {
		return FeatureSwitchesDTO.of(featureSwitchProperties.getFeatureSwitch().entrySet()
				.stream().map(this::map)
				.collect(Collectors.toList()));
	}

	private FeatureSwitchDTO map(Map.Entry<String, Boolean> stringBooleanEntry) {
		return FeatureSwitchDTO.of(featureSwitchTypePropertiesMapper.map(stringBooleanEntry.getKey()), stringBooleanEntry.getValue());
	}

	public boolean isEnabled(FeatureSwitchType featureSwitchType) {
		return featureSwitchProperties.getFeatureSwitch().entrySet()
				.stream()
				.map(this::map)
				.filter(featureSwitchDTO -> featureSwitchType.equals(featureSwitchDTO.getType()))
				.map(FeatureSwitchDTO::isEnabled)
				.findFirst()
				.orElseThrow(() -> new StapiRuntimeException(String.format("No mapping found for feature switch %s", featureSwitchType)));
	}

}
