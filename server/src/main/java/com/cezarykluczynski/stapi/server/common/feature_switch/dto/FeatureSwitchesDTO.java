package com.cezarykluczynski.stapi.server.common.feature_switch.dto;

import java.util.List;

public final class FeatureSwitchesDTO {

	private final List<FeatureSwitchDTO> featureSwitches;

	private FeatureSwitchesDTO(List<FeatureSwitchDTO> featureSwitches) {
		this.featureSwitches = featureSwitches;
	}

	public static FeatureSwitchesDTO of(List<FeatureSwitchDTO> featureSwitches) {
		return new FeatureSwitchesDTO(featureSwitches);
	}

	public List<FeatureSwitchDTO> getFeatureSwitches() {
		return List.copyOf(featureSwitches);
	}

}
