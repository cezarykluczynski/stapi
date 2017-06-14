package com.cezarykluczynski.stapi.etl.template.publishable_series.dto;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class PublishableSeriesTemplate {

	private Page page;

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedMonthFrom;

	private Integer publishedDayFrom;

	private Integer publishedYearTo;

	private Integer publishedMonthTo;

	private Integer publishedDayTo;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	private Set<Company> publishers = Sets.newHashSet();

}
