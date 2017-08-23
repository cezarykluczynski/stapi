package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StarshipClassTemplateNameCorrectionFixedValueProvider implements FixedValueProvider<String, String> {

	private static final Map<String, String> MAPPINGS = Maps.newHashMap();

	static {
		MAPPINGS.put("Federation attack fighter", "Attack fighter");
		MAPPINGS.put("Federation science vessel", "Science vessel");
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(MAPPINGS.containsKey(key), MAPPINGS.get(key));
	}

}
