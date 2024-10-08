package com.cezarykluczynski.stapi.model.performer.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
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

import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"episodesPerformances", "episodesStuntPerformances", "episodesStandInPerformances", "moviesPerformances",
		"moviesStuntPerformances", "moviesStandInPerformances", "characters", "externalLinks"})
@EqualsAndHashCode(callSuper = true, exclude = {"episodesPerformances", "episodesStuntPerformances", "episodesStandInPerformances",
		"moviesPerformances", "moviesStuntPerformances", "moviesStandInPerformances", "characters", "externalLinks"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = PerformerRepository.class, singularName = "performer",
		pluralName = "performers", restApiVersion = "v2")
public class Performer extends RealWorldPerson implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "performer_sequence_generator")
	@SequenceGenerator(name = "performer_sequence_generator", sequenceName = "performer_sequence", allocationSize = 1)
	private Long id;

	private boolean animalPerformer;

	private boolean audiobookPerformer;

	private boolean cutPerformer;

	private boolean disPerformer;

	@Column(name = "ds9_performer")
	private boolean ds9Performer;

	private boolean entPerformer;

	private boolean filmPerformer;

	private boolean ldPerformer;

	private boolean picPerformer;

	private boolean proPerformer;

	private boolean puppeteer;

	private boolean snwPerformer;

	private boolean standInPerformer;

	private boolean stPerformer;

	private boolean stuntPerformer;

	private boolean tasPerformer;

	private boolean tngPerformer;

	private boolean tosPerformer;

	private boolean videoGamePerformer;

	private boolean voicePerformer;

	private boolean voyPerformer;

	@ManyToMany(mappedBy = "performers", targetEntity = Episode.class)
	private Set<Episode> episodesPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "stuntPerformers", targetEntity = Episode.class)
	private Set<Episode> episodesStuntPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "standInPerformers", targetEntity = Episode.class)
	private Set<Episode> episodesStandInPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "performers", targetEntity = Movie.class)
	private Set<Movie> moviesPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "stuntPerformers", targetEntity = Movie.class)
	private Set<Movie> moviesStuntPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "standInPerformers", targetEntity = Movie.class)
	private Set<Movie> moviesStandInPerformances = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "performers_characters",
			joinColumns = @JoinColumn(name = "performer_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	private Set<Character> characters = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "performers_external_links",
			joinColumns = @JoinColumn(name = "performer_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "external_link_id", nullable = false, updatable = false))
	private Set<ExternalLink> externalLinks = Sets.newHashSet();

}
