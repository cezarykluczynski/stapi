package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ComicSeriesPublishedDateFixedValueProvider implements FixedValueProvider<String, Range<DayMonthYear>> {

	private static final Map<String, Range<DayMonthYear>> TITLES_TO_PUBLISHED_RANGE = Maps.newHashMap();

	static {
		TITLES_TO_PUBLISHED_RANGE.put("McDonald's", Range.of(DayMonthYear.of(null, null, 1979), DayMonthYear.of(null, 2, 1980)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek Annual (Western)", Range.of(DayMonthYear.of(null, null, 1969), DayMonthYear.of(null, null, 1986)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek Comic Strip (UK)", Range.of(DayMonthYear.of(null, null, 1969), DayMonthYear.of(null, null, 1979)));
		TITLES_TO_PUBLISHED_RANGE.put("WildStorm Comics", Range.of(DayMonthYear.of(null, null, 2000), DayMonthYear.of(null, null, 2001)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek: The Modala Imperative", Range.of(DayMonthYear.of(null, 8, 1992),
				DayMonthYear.of(null, 8, 1992)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek: Alien Spotlight", Range.of(DayMonthYear.of(null, 10, 2007), DayMonthYear.of(null, 12, 2009)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek Comic", Range.of(DayMonthYear.of(null, 5, 2009), DayMonthYear.of(null, 7, 2009)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek Monthly Comic", Range.of(DayMonthYear.of(null, null, 1992), DayMonthYear.of(null, null, 1992)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek Photostories", Range.of(DayMonthYear.of(null, 5, 1980), DayMonthYear.of(null, 10, 1982)));
		TITLES_TO_PUBLISHED_RANGE.put("Peter Pan Records", Range.of(DayMonthYear.of(null, null, 1975), DayMonthYear.of(null, null, 1979)));
		TITLES_TO_PUBLISHED_RANGE.put("Star Trek Fotonovels", Range.of(DayMonthYear.of(null, null, 1977), DayMonthYear.of(null, null, 1978)));
	}

	@Override
	public FixedValueHolder<Range<DayMonthYear>> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_PUBLISHED_RANGE.containsKey(key), TITLES_TO_PUBLISHED_RANGE.get(key));
	}

}
