package com.cezarykluczynski.stapi.sources.wordpress.service;

import com.cezarykluczynski.stapi.sources.wordpress.api.enums.WordPressSource;
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourceProperties;
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourcesProperties;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WordPressSourceConfigurationProvider {

	private static final Map<WordPressSource, WordPressSourceProperties> MAP = Maps.newHashMap();

	private final WordPressSourcesProperties wordPressSourcesProperties;

	public WordPressSourceConfigurationProvider(WordPressSourcesProperties wordPressSourcesProperties) {
		this.wordPressSourcesProperties = wordPressSourcesProperties;
	}

	public synchronized WordPressSourceProperties provideFor(WordPressSource wordPressSource) {
		if (MAP.isEmpty()) {
			MAP.put(WordPressSource.STAR_TREK_CARDS, wordPressSourcesProperties.getStarTrekCards());
		}

		return wordPressSource == null ? null : MAP.get(wordPressSource);
	}

}
