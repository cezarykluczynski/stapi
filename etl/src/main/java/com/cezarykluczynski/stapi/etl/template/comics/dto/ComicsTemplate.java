package com.cezarykluczynski.stapi.etl.template.comics.dto;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class ComicsTemplate {

	private String title;

	private Page page;

	private Integer publishedYear;

	private Integer publishedMonth;

	private Integer publishedDay;

	private Integer coverYear;

	private Integer coverMonth;

	private Integer coverDay;

	private Integer numberOfPages;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private boolean photonovel;

	private boolean productOfRedirect;

	private Set<ComicSeries> comicSeries = Sets.newHashSet();

	private Set<Staff> writers = Sets.newHashSet();

	private Set<Staff> artists = Sets.newHashSet();

	private Set<Staff> editors = Sets.newHashSet();

	private Set<Staff> staff = Sets.newHashSet();

	private Set<Company> publishers = Sets.newHashSet();

	private Set<Character> characters = Sets.newHashSet();

}
