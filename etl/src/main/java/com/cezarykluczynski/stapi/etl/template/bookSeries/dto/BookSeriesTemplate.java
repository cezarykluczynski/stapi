package com.cezarykluczynski.stapi.etl.template.bookSeries.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.PublishableSeriesTemplate;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookSeriesTemplate extends PublishableSeriesTemplate {

	private Page page;

	private String title;

	private Integer numberOfBooks;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	private Set<Company> publishers = Sets.newHashSet();

}
