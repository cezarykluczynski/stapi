package com.cezarykluczynski.stapi.etl.template.common.dto.datetime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class DayMonthYearCandidate {

	private final String day;

	private final String month;

	private final String year;

}
