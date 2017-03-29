package com.cezarykluczynski.stapi.etl.organization.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrganizationNameFixedValueProvider implements FixedValueProvider<String, String> {

	private static final Map<String, String> TITLE_TO_TITLE_MAP = Maps.newHashMap();

	static {
		// TODO
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_TITLE_MAP.containsKey(key), TITLE_TO_TITLE_MAP.get(key));
	}
}
