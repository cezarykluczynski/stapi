package com.cezarykluczynski.stapi.model.video_release.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"series", "references", "ratings", "languages", "languagesSubtitles", "languagesDubbed"})
@EqualsAndHashCode(callSuper = true, exclude = {"series", "references", "ratings", "languages", "languagesSubtitles", "languagesDubbed"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = VideoReleaseRepository.class, singularName = "video release",
		pluralName = "video releases")
public class VideoRelease extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_release_sequence_generator")
	@SequenceGenerator(name = "video_release_sequence_generator", sequenceName = "video_release_sequence", allocationSize = 1)
	private Long id;

	private String title;

	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "series_id")
	private Series series;

	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "season_id")
	private Season season;

	@Enumerated(EnumType.STRING)
	private VideoReleaseFormat format;

	private Integer numberOfEpisodes;

	@Column(name = "number_of_fl_episodes")
	private Integer numberOfFeatureLengthEpisodes;

	private Integer numberOfDataCarriers;

	private Integer runTime;

	private Integer yearFrom;

	private Integer yearTo;

	private LocalDate regionFreeReleaseDate;

	@Column(name = "region_1a_release_date")
	private LocalDate region1AReleaseDate;

	@Column(name = "region_1_slimline_release_date")
	private LocalDate region1SlimlineReleaseDate;

	@Column(name = "region_2b_release_date")
	private LocalDate region2BReleaseDate;

	@Column(name = "region_2_slimline_release_date")
	private LocalDate region2SlimlineReleaseDate;

	@Column(name = "region_4_release_date")
	private LocalDate region4AReleaseDate;

	@Column(name = "region_4_slimline_release_date")
	private LocalDate region4SlimlineReleaseDate;

	private Boolean amazonDigitalRelease;

	private Boolean dailymotionDigitalRelease;

	private Boolean googlePlayDigitalRelease;

	private Boolean iTunesDigitalRelease;

	private Boolean ultraVioletDigitalRelease;

	private Boolean vimeoDigitalRelease;

	private Boolean vuduDigitalRelease;

	@Column(name = "xbox_smart_glass_digital")
	private Boolean xboxSmartGlassDigitalRelease;

	private Boolean youTubeDigitalRelease;

	private Boolean netflixDigitalRelease;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_releases_references",
			joinColumns = @JoinColumn(name = "video_release_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "reference_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Reference> references = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_releases_ratings",
			joinColumns = @JoinColumn(name = "video_release_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "content_rating_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<ContentRating> ratings = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_releases_languages",
			joinColumns = @JoinColumn(name = "video_release_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "content_language_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<ContentLanguage> languages = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_releases_languages_sub",
			joinColumns = @JoinColumn(name = "video_release_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "content_language_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<ContentLanguage> languagesSubtitles = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_releases_languages_dub",
			joinColumns = @JoinColumn(name = "video_release_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "content_language_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<ContentLanguage> languagesDubbed = Sets.newHashSet();

}
