package com.cezarykluczynski.stapi.model.common.etl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = EtlProperties.PREFIX)
public class EtlProperties {

	public static final String PREFIX = "etl";

	private Boolean enabled;

}
