package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BlikiUserDecoratorFactory {

	private final Map<MediaWikiSource, String> sourceToUrlMap = Maps.newHashMap();

	public BlikiUserDecoratorFactory(MediaWikiSourcesProperties mediaWikiSourcesProperties) {
		sourceToUrlMap.put(MediaWikiSource.MEMORY_ALPHA_EN, mediaWikiSourcesProperties.getMemoryAlphaEn().getApiUrl());
		sourceToUrlMap.put(MediaWikiSource.MEMORY_BETA_EN, mediaWikiSourcesProperties.getMemoryBetaEn().getApiUrl());
		sourceToUrlMap.put(MediaWikiSource.TECHNICAL_HELPER, mediaWikiSourcesProperties.getTechnicalHelper().getApiUrl());
	}

	// HttpClient used by Connector has a problem with hanging indefinitely, however it happens after a long time,
	// so to mitigate that, a new UserDecorator is returned for every request.
	public UserDecorator createFor(MediaWikiSource mediaWikiSource) {
		return new UserDecorator("", "", sourceToUrlMap.get(mediaWikiSource));
	}

}
