package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MovieTemplateStardateYearFixedValueProvider implements FixedValueProvider<String, StardateYearDTO> {

	private static final Map<String, StardateYearDTO> TITLE_TO_STARDATE_YEAR_MAP = Maps.newHashMap();

	static {
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek: First Contact", StardateYearDTO.of(50893.5F, 50893.5F, 2063, 2373));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek: The Motion Picture", StardateYearDTO.of(7410.2F, 7410.2F, 2263, 2263));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek", StardateYearDTO.of(2233.04F, 2258.42F, 2233, 2387));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek II: The Wrath of Khan", StardateYearDTO.of(8130.3F, 8130.3F, 2285, 2285));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek Generations", StardateYearDTO.of(48632.4F, 48632.4F, 2371, 2293));
	}

	@Override
	public FixedValueHolder<StardateYearDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_STARDATE_YEAR_MAP.containsKey(key), TITLE_TO_STARDATE_YEAR_MAP.get(key));
	}

}
