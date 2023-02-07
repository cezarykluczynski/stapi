package com.cezarykluczynski.stapi.etl.template.video.dto;

import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import com.google.common.collect.Sets;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class VideoTemplate {

	private String title;

	private Page page;

	private Set<Series> series = Sets.newHashSet();

	private Set<Season> seasons = Sets.newHashSet();

	private Set<Movie> movies = Sets.newHashSet();

	private VideoReleaseFormat format;

	private Integer numberOfEpisodes;

	private Integer numberOfFeatureLengthEpisodes;

	private Integer numberOfDataCarriers;

	private Integer runTime;

	private Integer yearFrom;

	private Integer yearTo;

	private LocalDate regionFreeReleaseDate;

	private LocalDate region1AReleaseDate;

	private LocalDate region1SlimlineReleaseDate;

	private LocalDate region2BReleaseDate;

	private LocalDate region2SlimlineReleaseDate;

	private LocalDate region4AReleaseDate;

	private LocalDate region4SlimlineReleaseDate;

	private Boolean amazonDigitalRelease;

	private Boolean dailymotionDigitalRelease; // TODO: remove? Not a single "yes" latety

	private Boolean googlePlayDigitalRelease;

	@SuppressWarnings("memberName")
	private Boolean iTunesDigitalRelease;

	private Boolean ultraVioletDigitalRelease;

	private Boolean vimeoDigitalRelease;

	private Boolean vuduDigitalRelease;

	private Boolean xboxSmartGlassDigitalRelease;

	private Boolean youTubeDigitalRelease;

	private Boolean netflixDigitalRelease;

	private Boolean documentary;

	private Boolean specialFeatures;

	private Set<Reference> references = Sets.newHashSet();

	private Set<ContentRating> ratings = Sets.newHashSet();

	private Set<ContentLanguage> languages = Sets.newHashSet();

	private Set<ContentLanguage> languagesSubtitles = Sets.newHashSet();

	private Set<ContentLanguage> languagesDubbed = Sets.newHashSet();

}
