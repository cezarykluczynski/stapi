package com.cezarykluczynski.stapi.etl.template.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class DayMonthYearCandidate {

	private String day;

	private String month;

	private String year;

}
