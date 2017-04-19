package com.cezarykluczynski.stapi.etl.template.common.dto;

import lombok.Data;

@Data
public class PublishableSeriesTemplate {

	private Integer publishedYearFrom;

	private Integer publishedMonthFrom;

	private Integer publishedDayFrom;

	private Integer publishedYearTo;

	private Integer publishedMonthTo;

	private Integer publishedDayTo;

}
