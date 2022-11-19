package com.cezarykluczynski.stapi.etl.template.common.dto.datetime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class MonthYearRange {

	private MonthYear from;

	private MonthYear to;

	public static MonthYearRange startYear(Integer startYear) {
		return MonthYearRange.of(MonthYear.of(null, startYear), MonthYear.of(null, null));
	}

	public static MonthYearRange years(Integer startYear, Integer endYear) {
		return MonthYearRange.of(MonthYear.of(null, startYear), MonthYear.of(null, endYear));
	}

	public static MonthYearRange start(Integer startMonth, Integer startYear) {
		return MonthYearRange.of(MonthYear.of(startMonth, startYear), MonthYear.of(null, null));
	}

	public static MonthYearRange range(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) {
		return MonthYearRange.of(MonthYear.of(startMonth, startYear), MonthYear.of(endMonth, endYear));
	}

}
