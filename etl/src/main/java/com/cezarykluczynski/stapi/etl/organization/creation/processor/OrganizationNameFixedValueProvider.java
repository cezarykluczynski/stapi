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
		TITLE_TO_TITLE_MAP.put("Canon (company)", "Canon");
		TITLE_TO_TITLE_MAP.put("Crown (manufacturer)", "Crown");
		TITLE_TO_TITLE_MAP.put("Martini (brand)", "Martini");
		TITLE_TO_TITLE_MAP.put("McCoy (surfboard)", "McCoy");
		TITLE_TO_TITLE_MAP.put("Military Assault Command Operations (mirror)", "Military Assault Command Operations");
		TITLE_TO_TITLE_MAP.put("Mountain (mirror)", "Mountain");
		TITLE_TO_TITLE_MAP.put("Quark's (mirror)", "Quark's");
		TITLE_TO_TITLE_MAP.put("Sony (mirror)", "Sony");
		TITLE_TO_TITLE_MAP.put("Starfleet Command (mirror)", "Starfleet Command");
		TITLE_TO_TITLE_MAP.put("Starfleet (mirror)", "Starfleet");
		TITLE_TO_TITLE_MAP.put("Universal Studios (studio)", "Universal Studios");
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_TITLE_MAP.containsKey(key), TITLE_TO_TITLE_MAP.get(key));
	}

}
