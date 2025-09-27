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
			.put("AT Season 1", 14)
			.put("ST Season 1", 4)
			.put("ST Season 2", 6)
			.put("TRR Season 1", 13)
			.put("TRR Season 2", 11)
			.put("TRR Season LD", 10)
			.put("TRR Season 3", 13)
			.put("TRR Season PRO", 2)
			.put("TRR Season 4", 29)
			.put("TRR Season 5", 20)
			.put("TRR Season 6", 9)
			.put("TRR Season SNW", 1)
			.build();

	@Override
	public FixedValueHolder<Integer> getSearchedValue(String key) {
		return FixedValueHolder.of(SEASON_NUMBER_OF_EPISODES_MAP.containsKey(key), SEASON_NUMBER_OF_EPISODES_MAP.get(key));
	}

}
