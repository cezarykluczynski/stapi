package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BlikiUserDecoratorBeanMapProvider {

	private static final Map<String, MediaWikiSource> STRING_ENUM_MAP = Maps.newHashMap();

	@Getter
	private Map<MediaWikiSource, UserDecorator> userEnumMap = Maps.newHashMap();

	static {
		STRING_ENUM_MAP.put(BlikiConnectorConfiguration.MEMORY_ALPHA_EN_USER_DECORATOR, MediaWikiSource.MEMORY_ALPHA_EN);
		STRING_ENUM_MAP.put(BlikiConnectorConfiguration.MEMORY_BETA_EN_USER_DECORATOR, MediaWikiSource.MEMORY_BETA_EN);
		STRING_ENUM_MAP.put(BlikiConnectorConfiguration.TECHNICAL_HELPER_USER_DECORATOR, MediaWikiSource.TECHNICAL_HELPER);
	}

	public BlikiUserDecoratorBeanMapProvider(Map<String, UserDecorator> stringUserDecoratorMap) {
		translateStringMappingsToEnumMappings(stringUserDecoratorMap);
	}

	private void translateStringMappingsToEnumMappings(Map<String, UserDecorator> stringUserDecoratorMap) {
		stringUserDecoratorMap.entrySet().forEach(stringUserEntry -> {
			MediaWikiSource mediaWikiSourceKey = STRING_ENUM_MAP.get(stringUserEntry.getKey());
			userEnumMap.put(mediaWikiSourceKey, stringUserEntry.getValue());
		});
	}



}
