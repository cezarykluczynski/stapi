package com.cezarykluczynski.stapi.etl.template.video_game.dto;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.genre.entity.Genre;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.platform.entity.Platform;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.google.common.collect.Sets;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class VideoGameTemplate {

	private Page page;

	private String title;

	private LocalDate releaseDate;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private String systemRequirements;

	private Set<Company> publishers = Sets.newHashSet();

	private Set<Company> developers = Sets.newHashSet();

	private Set<Platform> platforms = Sets.newHashSet();

	private Set<Genre> genres = Sets.newHashSet();

	private Set<ContentRating> ratings = Sets.newHashSet();

	private Set<Reference> references = Sets.newHashSet();

}
