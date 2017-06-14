package com.cezarykluczynski.stapi.etl.template.comic_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ComicSeriesTemplateNumberOfIssuesFixedValueProvider implements FixedValueProvider<String, Integer> {

	private static final Map<String, Integer> TITLES_TO_NUMBER_OF_ISSUES = Maps.newHashMap();

	static {
		TITLES_TO_NUMBER_OF_ISSUES.put("War and Madness", 5);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Original Series (IDW)", 67);
		TITLES_TO_NUMBER_OF_ISSUES.put("McDonald's", 5);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek Annual (DC)", 9);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek Annual (Western)", 12);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Next Generation (IDW)", 49);
		TITLES_TO_NUMBER_OF_ISSUES.put("The Worst of Both Worlds!", 4);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: Deep Space Nine (WildStorm)", 8);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Modala Imperative", 8);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek Comic", 3);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Next Generation - Mirror Broken", 0);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek Photostories", 2);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek Fotonovels", 12);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: The Next Generation - Aliens: Acceptable Losses", 0);
	}

	@Override
	public FixedValueHolder<Integer> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_NUMBER_OF_ISSUES.containsKey(key), TITLES_TO_NUMBER_OF_ISSUES.get(key));
	}

}
