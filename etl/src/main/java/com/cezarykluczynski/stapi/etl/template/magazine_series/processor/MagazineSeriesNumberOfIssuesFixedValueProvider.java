package com.cezarykluczynski.stapi.etl.template.magazine_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MagazineSeriesNumberOfIssuesFixedValueProvider implements FixedValueProvider<String, Integer> {

	private static final Map<String, Integer> TITLES_TO_PUBLICATION_DATES = Maps.newHashMap();

	static {
		TITLES_TO_PUBLICATION_DATES.put("Star Trek Magazine", 240);
		TITLES_TO_PUBLICATION_DATES.put("Star Trek Fact Files", 855);
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Official Fan Club of the UK Magazine", 13);
		TITLES_TO_PUBLICATION_DATES.put("Star Trek Giant Poster Book", 18);
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Next Generation Magazine (Marvel)", 25);
		TITLES_TO_PUBLICATION_DATES.put("Inside Star Trek", 30);
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Magazine", 52);
		TITLES_TO_PUBLICATION_DATES.put("Cinefantastique", 198);
	}

	@Override
	public FixedValueHolder<Integer> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_PUBLICATION_DATES.containsKey(key), TITLES_TO_PUBLICATION_DATES.get(key));
	}

}
