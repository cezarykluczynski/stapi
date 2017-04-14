package com.cezarykluczynski.stapi.etl.location.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LocationNameFixedValueProvider implements FixedValueProvider<String, String> {

	private static final Map<String, String> TITLE_TO_TITLE_MAP = Maps.newHashMap();

	static {
		TITLE_TO_TITLE_MAP.put("Fusion (night club)", "Fusion");
		TITLE_TO_TITLE_MAP.put("Quark's (mirror)", "Quark's");
		TITLE_TO_TITLE_MAP.put("Alliance (nation)", "Alliance");
		TITLE_TO_TITLE_MAP.put("Kes (government)", "Kes");
		TITLE_TO_TITLE_MAP.put("Vulcan (island)", "Vulcan");
		TITLE_TO_TITLE_MAP.put("Darmok (colony)", "Darmok");
		TITLE_TO_TITLE_MAP.put("Tanagra (island)", "Tanagra");
		TITLE_TO_TITLE_MAP.put("Beach (formation)", "Beach");
		TITLE_TO_TITLE_MAP.put("USS Enterprise (replica)", "USS Enterprise");
		TITLE_TO_TITLE_MAP.put("Vrax (location)", "Vrax");
		TITLE_TO_TITLE_MAP.put("Kir (city)", "Kir");
		TITLE_TO_TITLE_MAP.put("Regula I (alternate reality)", "Regula I");
		TITLE_TO_TITLE_MAP.put("T'Paal (city)", "T'Paal");
		TITLE_TO_TITLE_MAP.put("Tanis (city)", "Tanis");
		TITLE_TO_TITLE_MAP.put("Rixx (location)", "Rixx");
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_TITLE_MAP.containsKey(key), TITLE_TO_TITLE_MAP.get(key));
	}

}
