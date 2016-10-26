package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = BlikiConnectorProperties.PREFIX)
public class BlikiConnectorProperties {

	public static final String PREFIX = "bliki";

	private String sourceUrl;

}
