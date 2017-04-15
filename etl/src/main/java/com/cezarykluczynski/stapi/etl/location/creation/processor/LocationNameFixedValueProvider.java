package com.cezarykluczynski.stapi.etl.location.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LocationNameFixedValueProvider implements FixedValueProvider<String, String> {

	private static final String SONY = "Sony";

	private static final Map<String, String> TITLE_TO_TITLE_MAP = Maps.newHashMap();

	static {
		TITLE_TO_TITLE_MAP.put("Canon (company)", "Canon");
		TITLE_TO_TITLE_MAP.put("Crown (manufacturer)", "Crown");
		TITLE_TO_TITLE_MAP.put("Martini (brand)", "Martini");
		TITLE_TO_TITLE_MAP.put("McCoy (surfboard)", "McCoy");
		TITLE_TO_TITLE_MAP.put("Quark's (mirror)", "Quark's");
		TITLE_TO_TITLE_MAP.put("Sony (mirror)", SONY);
		TITLE_TO_TITLE_MAP.put("Universal Studios (studio)", "Universal Studios");
		TITLE_TO_TITLE_MAP.put("Fusion (night club)", "Fusion");
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
		TITLE_TO_TITLE_MAP.put("Sony (manufacturer)", SONY);
		TITLE_TO_TITLE_MAP.put("Canteen (establishment)", "Canteen");
		TITLE_TO_TITLE_MAP.put("Crenshaw (city)", "Crenshaw");
		TITLE_TO_TITLE_MAP.put("Bluff (geography)", "Bluff");
		TITLE_TO_TITLE_MAP.put("Sha Ka Ree (mythology)", "Sha Ka Ree");
		TITLE_TO_TITLE_MAP.put("Workshop (location)", "Workshop");
		TITLE_TO_TITLE_MAP.put("Delaware (state)", "Delaware");
		TITLE_TO_TITLE_MAP.put("Starfleet Academy (Earth)", "Starfleet Academy");
		TITLE_TO_TITLE_MAP.put("Meridian (planet)", "Meridian");
		TITLE_TO_TITLE_MAP.put("Pound (location)", "Pound");
	}

	@Override
	public FixedValueHolder<String> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_TITLE_MAP.containsKey(key), TITLE_TO_TITLE_MAP.get(key));
	}

}
