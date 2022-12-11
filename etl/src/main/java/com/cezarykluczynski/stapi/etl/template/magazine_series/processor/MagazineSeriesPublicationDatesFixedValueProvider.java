package com.cezarykluczynski.stapi.etl.template.magazine_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearRange;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@SuppressWarnings("LineLength")
public class MagazineSeriesPublicationDatesFixedValueProvider implements FixedValueProvider<String, MonthYearRange> {

	private static final Map<String, MonthYearRange> TITLES_TO_PUBLICATION_DATES = Maps.newHashMap();

	static {
		TITLES_TO_PUBLICATION_DATES.put("Star Trek Magazine", MonthYearRange.startYear(1995));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek Fact Files", MonthYearRange.years(1997, 2008));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Collector's Edition", MonthYearRange.years(2004, 2009));
		TITLES_TO_PUBLICATION_DATES.put("Inside Star Trek", MonthYearRange.years(1968, 1979));
		TITLES_TO_PUBLICATION_DATES.put("Cinefex", MonthYearRange.start(3, 1980));
		TITLES_TO_PUBLICATION_DATES.put("Cinefantastique", MonthYearRange.years(1970, 2006));
		TITLES_TO_PUBLICATION_DATES.put("Fantastic Films", MonthYearRange.range(4, 1978, 10, 1985));
		TITLES_TO_PUBLICATION_DATES.put("T-Negative", MonthYearRange.years(1969, 1979));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Next Generation - The Official Poster Magazine", MonthYearRange.range(5, 1991, 12, 1995));
		TITLES_TO_PUBLICATION_DATES.put("The Electric Company Magazine", MonthYearRange.years(1972, 1987));
		TITLES_TO_PUBLICATION_DATES.put("Stardate", MonthYearRange.range(11, 1984, 2, 1988));
		TITLES_TO_PUBLICATION_DATES.put("The Official Star Trek The Next Generation: Build the USS Enterprise NCC-1701-D", MonthYearRange.range(6, 2011, 8, 2011));
		TITLES_TO_PUBLICATION_DATES.put("Files Magazine", MonthYearRange.years(1985, 1988));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek Generations - The Official Poster Magazine", MonthYearRange.years(1995, 1995));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Official Starships Collection", MonthYearRange.range(8, 2013, 6, 2022));
	}

	@Override
	public FixedValueHolder<MonthYearRange> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_PUBLICATION_DATES.containsKey(key), TITLES_TO_PUBLICATION_DATES.get(key));
	}

}
