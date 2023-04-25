package com.cezarykluczynski.stapi.etl.mediawiki.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = MediaWikiSourcesProperties.PREFIX)
public class MediaWikiSourcesProperties {

	public static final String PREFIX = "source.mediawiki";

	private MediaWikiSourceProperties memoryAlphaEn;

	private MediaWikiSourceProperties memoryBetaEn;

	private MediaWikiSourceProperties technicalHelper;

}
