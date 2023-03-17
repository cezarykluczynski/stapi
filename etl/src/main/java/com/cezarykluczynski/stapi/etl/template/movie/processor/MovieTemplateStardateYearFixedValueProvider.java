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
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek: The Motion Picture", StardateYearDTO.of(7410.2F, 7410.2F, 2263, 2263));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek II: The Wrath of Khan", StardateYearDTO.of(8130.3F, 8130.3F, 2285, 2285));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek III: The Search for Spock", StardateYearDTO.of(8210.3F, 8210.3F, 2285, 2285));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek IV: The Voyage Home", StardateYearDTO.of(8390.0F, 8390.0F, 1986, 2286));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek V: The Final Frontier", StardateYearDTO.of(8454.1F, 8454.1F, 2287, 2287));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek VI: The Undiscovered Country", StardateYearDTO.of(9521.6F, 9521.6F, 2293, 2293));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek Generations", StardateYearDTO.of(48632.4F, 48632.4F, 2371, 2293));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek: First Contact", StardateYearDTO.of(50893.5F, 50893.5F, 2063, 2373));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek: Insurrection", StardateYearDTO.of(null, null, 2375, 2375));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek Nemesis", StardateYearDTO.of(56844.9F, 56844.9F, 2379, 2379));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek (film)", StardateYearDTO.of(2233.04F, 2258.42F, 2233, 2387));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek Into Darkness", StardateYearDTO.of(2259.55F, 2260.133F, 2259, 2260));
		TITLE_TO_STARDATE_YEAR_MAP.put("Star Trek Beyond", StardateYearDTO.of(2263.2F, 2263.2F, 2263, 2263));
	}

	@Override
	public FixedValueHolder<StardateYearDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_STARDATE_YEAR_MAP.containsKey(key), TITLE_TO_STARDATE_YEAR_MAP.get(key));
	}

}
