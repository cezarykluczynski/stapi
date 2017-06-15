package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MagazineTemplateNumberOfPagesFixedValueProvider implements FixedValueProvider<String, Integer> {

	private static final Map<String, Integer> TITLES_TO_NUMBER_OF_PAGES = Maps.newHashMap();

	static {
		TITLES_TO_NUMBER_OF_PAGES.put("Star Trek 30 Years", 168);
		TITLES_TO_NUMBER_OF_PAGES.put("Star Trek: The Lost Photographs", 100);
		TITLES_TO_NUMBER_OF_PAGES.put("Star Trek Monthly issue 100", 100);
		TITLES_TO_NUMBER_OF_PAGES.put("Star Trek 25th Anniversary Magazine", 97);
	}

	@Override
	public FixedValueHolder<Integer> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_NUMBER_OF_PAGES.containsKey(key), TITLES_TO_NUMBER_OF_PAGES.get(key));
	}

}
