package com.cezarykluczynski.stapi.etl.template.common.dto.datetime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MonthYearCandidate {

	private final String month;

	private final String year;

}
