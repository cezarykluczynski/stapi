package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompanyAliasFixedValueProvider implements FixedValueProvider<String, String> {

	private static final Map<String, String> ALIASES = Maps.newHashMap();

	static {
		final String skyBoxInternational = "SkyBox International";
		ALIASES.put("CBS Studios Inc.", "CBS Studios");
		ALIASES.put("SkyBox", skyBoxInternational);
		ALIASES.put("Playmates", "Playmates Toys");
		ALIASES.put("Impel", skyBoxInternational);
		ALIASES.put("Kellogg’s", "Kellogg Company");
		ALIASES.put("CIC", "CIC Video");
		ALIASES.put("Hamilton Gifts", "The Hamilton Collection");
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(ALIASES.containsKey(key), ALIASES.get(key));
	}

}
