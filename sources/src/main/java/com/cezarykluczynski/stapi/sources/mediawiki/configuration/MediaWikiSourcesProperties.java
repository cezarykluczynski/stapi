package com.cezarykluczynski.stapi.sources.mediawiki.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = MediaWikiSourcesProperties.PREFIX)
public class MediaWikiSourcesProperties {

	public static final String PREFIX = "source.mediaWiki";

	private MediaWikiSourceProperties memoryAlphaEn;

	private MediaWikiSourceProperties memoryBetaEn;

	private MediaWikiSourceProperties technicalHelper;

}
