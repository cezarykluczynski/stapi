package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange;
import com.cezarykluczynski.stapi.etl.util.constant.SeriesAbbreviation;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class SeriesRunDateFixedValueProvider implements FixedValueProvider<String, DateRange> {

	private static final Map<String, DateRange> ABBREVIATION_TO_DATE_RANGE_MAP = Maps.newHashMap();

	static {
		ABBREVIATION_TO_DATE_RANGE_MAP.put(SeriesAbbreviation.AT, DateRange.of(LocalDate.of(2017, 9, 24), LocalDate.of(2018, 2, 11)));
		ABBREVIATION_TO_DATE_RANGE_MAP.put(SeriesAbbreviation.TRR, DateRange.of(LocalDate.of(2019, 1, 25), null));
	}

	@Override
	public FixedValueHolder<DateRange> getSearchedValue(String key) {
		return FixedValueHolder.of(ABBREVIATION_TO_DATE_RANGE_MAP.containsKey(key), ABBREVIATION_TO_DATE_RANGE_MAP.get(key));
	}

}
