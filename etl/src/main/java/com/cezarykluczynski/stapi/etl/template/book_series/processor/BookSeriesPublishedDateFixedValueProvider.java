package com.cezarykluczynski.stapi.etl.template.book_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookSeriesPublishedDateFixedValueProvider implements FixedValueProvider<String, Range<DayMonthYear>> {

	private static final Map<String, Range<DayMonthYear>> TITLES_TO_PUBLISHED_RANGE = Maps.newHashMap();

	static {
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek: Prometheus", Range.of(DayMonthYear.of(null, 7, 2016), DayMonthYear.of(null, 9, 2016)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek: Section 31", Range.of(DayMonthYear.of(null, 5, 2001), DayMonthYear.of(null, 10, 2014)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek Logs", Range.of(DayMonthYear.of(null, 6, 1974), DayMonthYear.of(null, null, 2006)));
	}

	@Override
	public FixedValueHolder<Range<DayMonthYear>> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_PUBLISHED_RANGE.containsKey(key), TITLES_TO_PUBLISHED_RANGE.get(key));
	}

}
