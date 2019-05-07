package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeasonNumberOfEpisodesFixedValueProvider implements FixedValueProvider<String, Integer> {

	private static final Map<String, Integer> TITLES_TO_NUMBER_OF_EPISODES = Maps.newHashMap();

	static {
		TITLES_TO_NUMBER_OF_EPISODES.put("DIS Season 1", 15);
		TITLES_TO_NUMBER_OF_EPISODES.put("DIS Season 2", 14);
		TITLES_TO_NUMBER_OF_EPISODES.put("DS9 Season 1", 19);
		TITLES_TO_NUMBER_OF_EPISODES.put("DS9 Season 2", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("DS9 Season 3", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("DS9 Season 4", 25);
		TITLES_TO_NUMBER_OF_EPISODES.put("DS9 Season 5", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("DS9 Season 6", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("DS9 Season 7", 25);
		TITLES_TO_NUMBER_OF_EPISODES.put("ENT Season 1", 25);
		TITLES_TO_NUMBER_OF_EPISODES.put("ENT Season 2", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("ENT Season 3", 24);
		TITLES_TO_NUMBER_OF_EPISODES.put("ENT Season 4", 22);
		TITLES_TO_NUMBER_OF_EPISODES.put("TAS Season 1", 16);
		TITLES_TO_NUMBER_OF_EPISODES.put("TAS Season 2", 6);
		TITLES_TO_NUMBER_OF_EPISODES.put("TNG Season 1", 25);
		TITLES_TO_NUMBER_OF_EPISODES.put("TNG Season 2", 22);
		TITLES_TO_NUMBER_OF_EPISODES.put("TNG Season 3", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("TNG Season 4", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("TNG Season 5", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("TNG Season 6", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("TNG Season 7", 25);
		TITLES_TO_NUMBER_OF_EPISODES.put("TOS Season 1", 30);
		TITLES_TO_NUMBER_OF_EPISODES.put("TOS Season 2", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("TOS Season 3", 24);
		TITLES_TO_NUMBER_OF_EPISODES.put("VOY Season 1", 15);
		TITLES_TO_NUMBER_OF_EPISODES.put("VOY Season 2", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("VOY Season 3", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("VOY Season 4", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("VOY Season 5", 25);
		TITLES_TO_NUMBER_OF_EPISODES.put("VOY Season 6", 26);
		TITLES_TO_NUMBER_OF_EPISODES.put("VOY Season 7", 24);
	}

	@Override
	public FixedValueHolder<Integer> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_NUMBER_OF_EPISODES.containsKey(key), TITLES_TO_NUMBER_OF_EPISODES.get(key));
	}

}
