package com.cezarykluczynski.stapi.model.series.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class SeriesDTO {

	private String title;

	private String abbreviation;

//	 TODO
//	private CompanyDTO productionCompany;

//	 TODO
//	private CompanyDTO originalBroadcaster;

	private Integer productionStartYear;

	private Integer productionEndYear;

	private LocalDate originalRunStartDate;

	private LocalDate originalRunEndDate;

}
