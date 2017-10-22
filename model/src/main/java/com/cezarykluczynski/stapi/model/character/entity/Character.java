package com.cezarykluczynski.stapi.model.character.entity;

import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"performers", "episodes", "movies", "characterSpecies", "characterRelations", "titles", "occupations",
		"organizations"})
@EqualsAndHashCode(callSuper = true, exclude = {"performers", "episodes", "movies", "characterSpecies", "characterRelations", "titles", "occupations",
		"organizations"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = CharacterRepository.class, singularName = "character",
		pluralName = "characters")
public class Character extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_sequence_generator")
	@SequenceGenerator(name = "character_sequence_generator", sequenceName = "character_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private Integer yearOfBirth;

	private Integer monthOfBirth;

	private Integer dayOfBirth;

	private String placeOfBirth;

	private Integer yearOfDeath;

	private Integer monthOfDeath;

	private Integer dayOfDeath;

	private String placeOfDeath;

	private Integer height;

	private Integer weight;

	private Boolean deceased;

	@Enumerated(EnumType.STRING)
	private BloodType bloodType;

	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;

	private String serialNumber;

	private String hologramActivationDate;

	private String hologramStatus;

	private String hologramDateStatus;

	private Boolean hologram;

	private Boolean fictionalCharacter;

	private Boolean mirror;

	private Boolean alternateReality;

	@ManyToMany(mappedBy = "characters", targetEntity = Performer.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Performer> performers = Sets.newHashSet();

	@ManyToMany(mappedBy = "characters", targetEntity = Episode.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Episode> episodes = Sets.newHashSet();

	@ManyToMany(mappedBy = "characters", targetEntity = Movie.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Movie> movies = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "characters_character_species",
			joinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_species_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<CharacterSpecies> characterSpecies = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "characters_character_relations",
			joinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_relation_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<CharacterRelation> characterRelations = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "characters_titles",
			joinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "title_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Title> titles = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "characters_occupations",
			joinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "occupation_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Occupation> occupations = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "characters_organizations",
			joinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "organization_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Organization> organizations = Sets.newHashSet();

}
