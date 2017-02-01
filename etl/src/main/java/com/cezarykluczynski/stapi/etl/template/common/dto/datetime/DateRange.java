package com.cezarykluczynski.stapi.etl.template.common.dto.datetime;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateRange {

	private LocalDate startDate;

	private LocalDate endDate;

}
