package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.service.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EpisodeTitleFixedValueProvider implements FixedValueProvider<String, String> {

	private static final Map<String, String> TITLE_TO_TITLE_MAP = Maps.newHashMap();

	static {
		TITLE_TO_TITLE_MAP.put("E┬▓", "E²");
		TITLE_TO_TITLE_MAP.put("M├ęnage ├á Troi", "Ménage à Troi");
		TITLE_TO_TITLE_MAP.put("Vis ├á Vis", "Vis à Vis");
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_TITLE_MAP.containsKey(key), TITLE_TO_TITLE_MAP.get(key));
	}
}
