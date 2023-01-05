package com.cezarykluczynski.stapi.server.common.feature_switch.dto;

public final class FeatureSwitchDTO {

	private final FeatureSwitchType type;

	private final boolean enabled;

	private FeatureSwitchDTO(FeatureSwitchType type, boolean enabled) {
		this.type = type;
		this.enabled = enabled;
	}

	public static FeatureSwitchDTO of(FeatureSwitchType type) {
		return new FeatureSwitchDTO(type, false);
	}

	public static FeatureSwitchDTO of(FeatureSwitchType type, boolean enabled) {
		return new FeatureSwitchDTO(type, enabled);
	}

	public FeatureSwitchType getType() {
		return type;
	}

	public boolean isEnabled() {
		return enabled;
	}

}
