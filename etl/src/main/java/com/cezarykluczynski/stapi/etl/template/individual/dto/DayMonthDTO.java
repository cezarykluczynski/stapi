package com.cezarykluczynski.stapi.etl.template.individual.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Month;

@Data
@AllArgsConstructor(staticName = "of")
public class DayMonthDTO {

	private final Integer day;

	private final Month month;

}
