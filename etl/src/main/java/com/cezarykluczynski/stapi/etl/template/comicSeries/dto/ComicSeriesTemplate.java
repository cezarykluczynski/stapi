package com.cezarykluczynski.stapi.etl.template.comicSeries.dto;

import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class ComicSeriesTemplate {

	private Page page;

	private String title;

	private ComicSeries series;

	private Integer publishedYearFrom;

	private Integer publishedMonthFrom;

	private Integer publishedDayFrom;

	private Integer publishedYearTo;

	private Integer publishedMonthTo;

	private Integer publishedDayTo;

	private Integer numberOfIssues;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	private Boolean photonovelSeries;

	private Set<Company> publishers = Sets.newHashSet();

	private boolean productOfRedirect;

}
