package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookPublishedDateFixedValueProvider implements FixedValueProvider<String, DayMonthYear> {

	private static final Map<String, DayMonthYear> TITLES_TO_PUBLISHED_RANGE = Maps.newHashMap();

	static {
		TITLES_TO_PUBLISHED_RANGE.put("Enterprise: Role Play Game In Star Trek", DayMonthYear.of(null, null, 1983));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek: Insurrection - Handbook of Production Information", DayMonthYear.of(null, null, 1998));
	}

	@Override
	public FixedValueHolder<DayMonthYear> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_PUBLISHED_RANGE.containsKey(key), TITLES_TO_PUBLISHED_RANGE.get(key));
	}

}
