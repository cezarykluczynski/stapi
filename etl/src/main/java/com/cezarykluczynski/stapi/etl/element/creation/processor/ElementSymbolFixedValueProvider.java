package com.cezarykluczynski.stapi.etl.element.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ElementSymbolFixedValueProvider implements FixedValueProvider<String, String> {

	private static final Map<String, String> TITLE_TO_SYMBOL_MAP = Maps.newHashMap();

	static {
		TITLE_TO_SYMBOL_MAP.put("Hydrogen", "H");
		TITLE_TO_SYMBOL_MAP.put("Sodium", "Na");
		TITLE_TO_SYMBOL_MAP.put("Deuterium", "D");
		TITLE_TO_SYMBOL_MAP.put("Californium", "Cf");
		TITLE_TO_SYMBOL_MAP.put("Hawkeye", "Ef");
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_SYMBOL_MAP.containsKey(key), TITLE_TO_SYMBOL_MAP.get(key));
	}

}
