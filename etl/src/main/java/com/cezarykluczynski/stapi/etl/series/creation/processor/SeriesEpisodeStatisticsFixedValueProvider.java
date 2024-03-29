package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.series.creation.dto.SeriesEpisodeStatisticsDTO;
import com.cezarykluczynski.stapi.etl.util.constant.SeriesAbbreviation;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeriesEpisodeStatisticsFixedValueProvider implements FixedValueProvider<String, SeriesEpisodeStatisticsDTO> {

	private static final Map<String, SeriesEpisodeStatisticsDTO> ABBREVIATION_TO_STATISTICS_MAP = Maps.newHashMap();

	static {
		ABBREVIATION_TO_STATISTICS_MAP.put(SeriesAbbreviation.TOS, SeriesEpisodeStatisticsDTO.of(3, 79, 0));
		ABBREVIATION_TO_STATISTICS_MAP.put(SeriesAbbreviation.TNG, SeriesEpisodeStatisticsDTO.of(7, 176, 2));
		ABBREVIATION_TO_STATISTICS_MAP.put(SeriesAbbreviation.DS9, SeriesEpisodeStatisticsDTO.of(7, 173, 3));
		ABBREVIATION_TO_STATISTICS_MAP.put(SeriesAbbreviation.VOY, SeriesEpisodeStatisticsDTO.of(7, 168, 4));
		ABBREVIATION_TO_STATISTICS_MAP.put(SeriesAbbreviation.ENT, SeriesEpisodeStatisticsDTO.of(4, 97, 1));
		ABBREVIATION_TO_STATISTICS_MAP.put(SeriesAbbreviation.PRO, SeriesEpisodeStatisticsDTO.of(2, 19, 0));
		ABBREVIATION_TO_STATISTICS_MAP.put(SeriesAbbreviation.AT, SeriesEpisodeStatisticsDTO.of(1, 14, 0));
		ABBREVIATION_TO_STATISTICS_MAP.put(SeriesAbbreviation.TRR, SeriesEpisodeStatisticsDTO.of(4, 86, 0));
	}

	@Override
	public FixedValueHolder<SeriesEpisodeStatisticsDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(ABBREVIATION_TO_STATISTICS_MAP.containsKey(key), ABBREVIATION_TO_STATISTICS_MAP.get(key));
	}

}
