package com.cezarykluczynski.stapi.util.feature_switch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class FeatureSwitchesDTO {

	private List<FeatureSwitchDTO> featureSwitches;

}
