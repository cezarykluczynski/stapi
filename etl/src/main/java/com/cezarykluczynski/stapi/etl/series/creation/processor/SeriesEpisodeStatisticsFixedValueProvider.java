package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.series.creation.dto.SeriesEpisodeStatisticsDTO;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeriesEpisodeStatisticsFixedValueProvider implements FixedValueProvider<String, SeriesEpisodeStatisticsDTO> {

	private static final Map<String, SeriesEpisodeStatisticsDTO> ABBREVIATION_TO_STATISTICS_MAP = Maps.newHashMap();

	static {
		ABBREVIATION_TO_STATISTICS_MAP.put("TOS", SeriesEpisodeStatisticsDTO.of(3, 76, 0));
		ABBREVIATION_TO_STATISTICS_MAP.put("TAS", SeriesEpisodeStatisticsDTO.of(2, 22, 0));
		ABBREVIATION_TO_STATISTICS_MAP.put("TNG", SeriesEpisodeStatisticsDTO.of(7, 176, 2));
		ABBREVIATION_TO_STATISTICS_MAP.put("DS9", SeriesEpisodeStatisticsDTO.of(7, 173, 3));
		ABBREVIATION_TO_STATISTICS_MAP.put("VOY", SeriesEpisodeStatisticsDTO.of(7, 167, 4));
		ABBREVIATION_TO_STATISTICS_MAP.put("ENT", SeriesEpisodeStatisticsDTO.of(4, 97, 1));
		ABBREVIATION_TO_STATISTICS_MAP.put("DIS", SeriesEpisodeStatisticsDTO.of(1, 13, 0));
	}

	@Override
	public FixedValueHolder<SeriesEpisodeStatisticsDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(ABBREVIATION_TO_STATISTICS_MAP.containsKey(key), ABBREVIATION_TO_STATISTICS_MAP.get(key));
	}
}
