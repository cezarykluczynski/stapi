package com.cezarykluczynski.stapi.etl.template.magazine_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYear;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearRange;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MagazineSeriesPublicationDatesFixedValueProvider implements FixedValueProvider<String, MonthYearRange> {

	private static final Map<String, MonthYearRange> TITLES_TO_PUBLICATION_DATES = Maps.newHashMap();

	static {
		TITLES_TO_PUBLICATION_DATES.put("Star Trek Magazine", MonthYearRange.of(MonthYear.of(null, 1995), MonthYear.of(null, null)));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek Fact Files", MonthYearRange.of(MonthYear.of(null, 1997), MonthYear.of(null, 2008)));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Collector's Edition", MonthYearRange.of(MonthYear.of(null, 2004), MonthYear.of(null, 2009)));
		TITLES_TO_PUBLICATION_DATES.put("Inside Star Trek", MonthYearRange.of(MonthYear.of(null, 1968), MonthYear.of(null, 1979)));
		TITLES_TO_PUBLICATION_DATES.put("Cinefex", MonthYearRange.of(MonthYear.of(3, 1980), MonthYear.of(null, null)));
		TITLES_TO_PUBLICATION_DATES.put("Cinefantastique", MonthYearRange.of(MonthYear.of(null, 1970), MonthYear.of(null, 2006)));
		TITLES_TO_PUBLICATION_DATES.put("Fantastic Films", MonthYearRange.of(MonthYear.of(4, 1978), MonthYear.of(10, 1985)));
		TITLES_TO_PUBLICATION_DATES.put("T-Negative", MonthYearRange.of(MonthYear.of(null, 1969), MonthYear.of(null, 1979)));
		TITLES_TO_PUBLICATION_DATES.put("Star Trek: The Next Generation - The Official Poster Magazine", MonthYearRange.of(MonthYear.of(5, 1991),
				MonthYear.of(12, 1995)));
		TITLES_TO_PUBLICATION_DATES.put("The Electric Company Magazine", MonthYearRange.of(MonthYear.of(null, 1972), MonthYear.of(null, 1987)));
		TITLES_TO_PUBLICATION_DATES.put("Stardate", MonthYearRange.of(MonthYear.of(11, 1984), MonthYear.of(2, 1988)));
		TITLES_TO_PUBLICATION_DATES.put("The Official Star Trek The Next Generation: Build the USS Enterprise NCC-1701-D",
				MonthYearRange.of(MonthYear.of(6, 2011), MonthYear.of(8, 2011)));
	}

	@Override
	public FixedValueHolder<MonthYearRange> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLES_TO_PUBLICATION_DATES.containsKey(key), TITLES_TO_PUBLICATION_DATES.get(key));
	}

}
