package com.cezarykluczynski.stapi.etl.wordpress.service;

import com.cezarykluczynski.stapi.etl.wordpress.api.enums.WordPressSource;
import com.cezarykluczynski.stapi.etl.wordpress.configuration.WordPressSourceProperties;
import com.cezarykluczynski.stapi.etl.wordpress.configuration.WordPressSourcesProperties;
import com.google.common.collect.Maps;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WordPressSourceConfigurationProvider {

	private static final Map<WordPressSource, WordPressSourceProperties> MAP = Maps.newHashMap();

	private final WordPressSourcesProperties wordPressSourcesProperties;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
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
