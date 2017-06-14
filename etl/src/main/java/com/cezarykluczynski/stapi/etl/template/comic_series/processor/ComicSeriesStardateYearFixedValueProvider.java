package com.cezarykluczynski.stapi.etl.template.comic_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ComicSeriesStardateYearFixedValueProvider implements FixedValueProvider<String, StardateYearDTO> {

	private static final Map<String, StardateYearDTO> TITLES_TO_STARDATE_YEARS = Maps.newHashMap();

	static {
		TITLES_TO_STARDATE_YEARS.put("Star Trek: The Official Motion Picture Adaptation", StardateYearDTO.of(null, null, 2230, 2387));
		TITLES_TO_STARDATE_YEARS.put("Star Trek: The Modala Imperative", StardateYearDTO.of(3012F, 44398F, 2266, 2367));
		TITLES_TO_STARDATE_YEARS.put("Star Trek: The Next Generation (DC volume 1)", StardateYearDTO.of(null, null, 2364, 2364));
	}

	@Override
	public FixedValueHolder<StardateYearDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_STARDATE_YEARS.containsKey(key), TITLES_TO_STARDATE_YEARS.get(key));
	}
}
