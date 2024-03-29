package com.cezarykluczynski.stapi.model.video_game.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.genre.entity.Genre;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.platform.entity.Platform;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository;
import com.google.common.collect.Sets;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"publishers", "developers", "platforms", "genres", "ratings", "references"})
@EqualsAndHashCode(callSuper = true, exclude = {"publishers", "developers", "platforms", "genres", "ratings", "references"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = VideoGameRepository.class, singularName = "video game",
		pluralName = "video games")
public class VideoGame extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_game_sequence_generator")
	@SequenceGenerator(name = "video_game_sequence_generator", sequenceName = "video_game_sequence", allocationSize = 1)
	private Long id;

	private String title;

	private LocalDate releaseDate;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private String systemRequirements;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_games_publishers",
			joinColumns = @JoinColumn(name = "video_game_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> publishers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_games_developers",
			joinColumns = @JoinColumn(name = "video_game_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> developers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_games_platforms",
			joinColumns = @JoinColumn(name = "video_game_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "platform_id", nullable = false, updatable = false))
	private Set<Platform> platforms = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_games_genres",
			joinColumns = @JoinColumn(name = "video_game_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "genre_id", nullable = false, updatable = false))
	private Set<Genre> genres = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_games_ratings",
			joinColumns = @JoinColumn(name = "video_game_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "content_rating_id", nullable = false, updatable = false))
	private Set<ContentRating> ratings = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "video_games_references",
			joinColumns = @JoinColumn(name = "video_game_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "reference_id", nullable = false, updatable = false))
	private Set<Reference> references = Sets.newHashSet();

}
