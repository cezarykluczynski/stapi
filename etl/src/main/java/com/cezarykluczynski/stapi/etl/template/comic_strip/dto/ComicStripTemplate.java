package com.cezarykluczynski.stapi.etl.template.comic_strip.dto;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

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

	private Integer yearFrom;

	private Integer yearTo;

	private Set<ComicSeries> comicSeries = Sets.newHashSet();

	private Set<Staff> writers = Sets.newHashSet();

	private Set<Staff> artists = Sets.newHashSet();

	private Set<Character> characters = Sets.newHashSet();

}
