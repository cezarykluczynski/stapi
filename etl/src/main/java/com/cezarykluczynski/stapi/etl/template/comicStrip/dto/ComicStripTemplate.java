package com.cezarykluczynski.stapi.etl.template.comicStrip.dto;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;

@Data
public class ComicStripTemplate {

	private Page page;

	private String title;

	private String periodical;

	private Integer publishedYearFrom;

	private Integer publishedMonthFrom;

	private Integer publishedDayFrom;

	private Integer publishedYearTo;

	private Integer publishedMonthTo;

	private Integer publishedDayTo;

	private Integer numberOfPages;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private boolean productOfRedirect;

}
