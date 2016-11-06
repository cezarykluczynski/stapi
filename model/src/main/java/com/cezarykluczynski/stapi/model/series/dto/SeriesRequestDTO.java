package com.cezarykluczynski.stapi.model.series.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SeriesRequestDTO {

	private String title;

	private String abbreviation;

	private Integer productionStartYearFrom;

	private Integer productionStartYearTo;

	private Integer productionEndYearFrom;

	private Integer productionEndYearTo;

	private LocalDate originalRunStartFrom;

	private LocalDate originalRunStartTo;

	private LocalDate originalRunEndFrom;

	private LocalDate originalRunEndTo;

}
