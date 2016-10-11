package com.cezarykluczynski.stapi.etl.template.common.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateRange {

	private LocalDate startDate;

	private LocalDate endDate;

}
