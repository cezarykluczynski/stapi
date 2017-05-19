package com.cezarykluczynski.stapi.model.series.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SeriesRequestDTO {

	private String uid;

	private String title;

	private String abbreviation;

	private Integer productionStartYearFrom;

	private Integer productionStartYearTo;

	private Integer productionEndYearFrom;

	private Integer productionEndYearTo;

	private LocalDate originalRunStartDateFrom;

	private LocalDate originalRunStartDateTo;

	private LocalDate originalRunEndDateFrom;

	private LocalDate originalRunEndDateTo;

	private RequestSortDTO sort;

}
