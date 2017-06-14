package com.cezarykluczynski.stapi.etl.template.book_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookSeriesTemplateNumberOfBooksFixedValueProvider implements FixedValueProvider<String, Integer> {

	private static final Map<String, Integer> TITLES_TO_NUMBER_OF_ISSUES = Maps.newHashMap();

	static {
		TITLES_TO_NUMBER_OF_ISSUES.put("Which Way Books", 2);
		TITLES_TO_NUMBER_OF_ISSUES.put("Star Trek: Starfleet Corps of Engineers", 85);
	}

	@Override
	public FixedValueHolder<Integer> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_NUMBER_OF_ISSUES.containsKey(key), TITLES_TO_NUMBER_OF_ISSUES.get(key));
	}

}
