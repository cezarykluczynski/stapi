package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SeasonNumberOfEpisodesFixedValueProvider implements FixedValueProvider<String, Integer> {

	private static final Map<String, Integer> SEASON_NUMBER_OF_EPISODES_MAP = ImmutableMap.<String, Integer>builder()
			.put("ST Season 1", 4)
			.put("ST Season 2", 6)
			.put("The Ready Room Season 1", 13)
			.put("The Ready Room Season 2", 11)
			.put("The Ready Room Lower Decks Specials", 7)
			.put("The Ready Room Season 3", 13)
			.put("The Ready Room Prodigy Specials", 2)
			.put("The Ready Room Season 4", 29)
			.put("The Ready Room Season 5", 10)
			.build();

	@Override
	public FixedValueHolder<Integer> getSearchedValue(String key) {
		return FixedValueHolder.of(SEASON_NUMBER_OF_EPISODES_MAP.containsKey(key), SEASON_NUMBER_OF_EPISODES_MAP.get(key));
	}

}
