package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.util.constant.SeriesAbbreviation;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeriesAbbreviationFixedValueProvider implements FixedValueProvider<String, String> {

	private static final Map<String, String> TITLE_TO_ABBREVIATION_MAP = Maps.newHashMap();

	static {
		TITLE_TO_ABBREVIATION_MAP.put("Star Trek: Voyager", SeriesAbbreviation.VOY);
		TITLE_TO_ABBREVIATION_MAP.put("Star Trek: Discovery", SeriesAbbreviation.DIS);
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_ABBREVIATION_MAP.containsKey(key), TITLE_TO_ABBREVIATION_MAP.get(key));
	}

}
