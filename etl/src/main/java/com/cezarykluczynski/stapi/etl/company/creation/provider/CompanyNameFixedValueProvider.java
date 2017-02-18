package com.cezarykluczynski.stapi.etl.company.creation.provider;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompanyNameFixedValueProvider implements FixedValueProvider<String, String> {

	private static final Map<String, String> TITLE_TO_TITLE_MAP = Maps.newHashMap();

	static {
		TITLE_TO_TITLE_MAP.put("Voyager (publisher)", "Voyager");
		TITLE_TO_TITLE_MAP.put("Space (channel)", "Space");
		TITLE_TO_TITLE_MAP.put("Fabbri Publishing (US)", "Fabbri Publishing");
		TITLE_TO_TITLE_MAP.put("United States Postal Service (real world)", "United States Postal Service");
		TITLE_TO_TITLE_MAP.put("Pioneer (company)", "Pioneer");
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_TITLE_MAP.containsKey(key), TITLE_TO_TITLE_MAP.get(key));
	}
}
