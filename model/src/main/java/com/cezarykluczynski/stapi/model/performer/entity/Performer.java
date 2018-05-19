package com.cezarykluczynski.stapi.model.performer.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"episodesPerformances", "episodesStuntPerformances", "episodesStandInPerformances", "moviesPerformances",
		"moviesStuntPerformances", "moviesStandInPerformances", "characters"})
@EqualsAndHashCode(callSuper = true, exclude = {"episodesPerformances", "episodesStuntPerformances", "episodesStandInPerformances",
		"moviesPerformances", "moviesStuntPerformances", "moviesStandInPerformances", "characters"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = PerformerRepository.class, singularName = "performer",
		pluralName = "performers")
public class Performer extends RealWorldPerson implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "performer_sequence_generator")
	@SequenceGenerator(name = "performer_sequence_generator", sequenceName = "performer_sequence", allocationSize = 1)
	private Long id;

	private boolean animalPerformer;

	private boolean disPerformer;

	@Column(name = "ds9_performer")
	private boolean ds9Performer;

	private boolean entPerformer;

	private boolean filmPerformer;

	private boolean standInPerformer;

	private boolean stuntPerformer;

	private boolean tasPerformer;

	private boolean tngPerformer;

	private boolean tosPerformer;

	private boolean videoGamePerformer;

	private boolean voicePerformer;

	private boolean voyPerformer;

	@ManyToMany(mappedBy = "performers", targetEntity = Episode.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Episode> episodesPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "stuntPerformers", targetEntity = Episode.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Episode> episodesStuntPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "standInPerformers", targetEntity = Episode.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Episode> episodesStandInPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "performers", targetEntity = Movie.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Movie> moviesPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "stuntPerformers", targetEntity = Movie.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Movie> moviesStuntPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "standInPerformers", targetEntity = Movie.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Movie> moviesStandInPerformances = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "performers_characters",
			joinColumns = @JoinColumn(name = "performer_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Character> characters = Sets.newHashSet();

}
