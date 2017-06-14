package com.cezarykluczynski.stapi.etl.template.comics.dto;

import com.cezarykluczynski.stapi.etl.template.publishable.dto.PublishableTemplate;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComicsTemplate extends PublishableTemplate {

	private Integer numberOfPages;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private boolean photonovel;

	private boolean adaptation;

	private Set<ComicSeries> comicSeries = Sets.newHashSet();

	private Set<Staff> writers = Sets.newHashSet();

	private Set<Staff> artists = Sets.newHashSet();

	private Set<Staff> editors = Sets.newHashSet();

	private Set<Staff> staff = Sets.newHashSet();

	private Set<Company> publishers = Sets.newHashSet();

	private Set<Character> characters = Sets.newHashSet();

	private Set<Reference> references = Sets.newHashSet();

}
