package com.cezarykluczynski.stapi.model.common.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateRangeDTO {

	private LocalDate startDate;

	private LocalDate endDate;

}
