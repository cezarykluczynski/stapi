package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

@Service
public class BlikiUserDecoratorBeanMapProvider {

	@Getter
	private Map<MediaWikiSource, UserDecorator> userEnumMap = Maps.newHashMap();

	private static final Map<String, MediaWikiSource> stringEnumMap = Maps.newHashMap();

	static {
		stringEnumMap.put(BlikiConnectorConfiguration.MEMORY_ALPHA_EN_USER_DECORATOR, MediaWikiSource.MEMORY_ALPHA_EN);
		stringEnumMap.put(BlikiConnectorConfiguration.MEMORY_BETA_EN_USER_DECORATOR, MediaWikiSource.MEMORY_BETA_EN);
	}

	@Inject
	public BlikiUserDecoratorBeanMapProvider(Map<String, UserDecorator> stringUserDecoratorMap) {
		translateStringMappingsToEnumMappings(stringUserDecoratorMap);
	}

	private void translateStringMappingsToEnumMappings(Map<String, UserDecorator> stringUserDecoratorMap) {
		stringUserDecoratorMap.entrySet().forEach(stringUserEntry -> {
			MediaWikiSource mediaWikiSourceKey = stringEnumMap.get(stringUserEntry.getKey());
			userEnumMap.put(mediaWikiSourceKey, stringUserEntry.getValue());
		});
	}



}
