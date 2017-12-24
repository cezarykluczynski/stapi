package com.cezarykluczynski.stapi.util.feature_switch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class FeatureSwitchDTO {

	private FeatureSwitchType type;

	private boolean enabled;

}
