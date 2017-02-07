package com.cezarykluczynski.stapi.etl.template.common.dto.datetime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class DateRange {

	private LocalDate startDate;

	private LocalDate endDate;

}
