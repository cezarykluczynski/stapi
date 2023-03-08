package com.cezarykluczynski.stapi.model.conflict.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.conflict.repository.ConflictRepository;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
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
@ToString(callSuper = true, exclude = {"locations", "firstSideBelligerents", "secondSideBelligerents", "firstSideLocations", "secondSideLocations",
		"firstSideCommanders", "secondSideCommanders"})
@EqualsAndHashCode(callSuper = true, exclude = {"locations", "firstSideBelligerents", "secondSideBelligerents", "firstSideLocations",
		"secondSideLocations", "firstSideCommanders", "secondSideCommanders"})
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = ConflictRepository.class, singularName = "conflict",
		pluralName = "conflicts")
public class Conflict extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conflict_sequence_generator")
	@SequenceGenerator(name = "conflict_sequence_generator", sequenceName = "conflict_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean earthConflict;

	private Boolean federationWar;

	private Boolean klingonWar;

	private Boolean dominionWarBattle;

	private Boolean alternateReality;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "conflicts_locations",
			joinColumns = @JoinColumn(name = "conflict_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "location_id", nullable = false, updatable = false))
	private Set<Location> locations = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "conflicts_1side_belligerents",
			joinColumns = @JoinColumn(name = "conflict_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "organization_id", nullable = false, updatable = false))
	private Set<Organization> firstSideBelligerents = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "conflicts_2side_belligerents",
			joinColumns = @JoinColumn(name = "conflict_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "organization_id", nullable = false, updatable = false))
	private Set<Organization> secondSideBelligerents = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "conflicts_1side_locations",
			joinColumns = @JoinColumn(name = "conflict_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "location_id", nullable = false, updatable = false))
	private Set<Location> firstSideLocations = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "conflicts_2side_locations",
			joinColumns = @JoinColumn(name = "conflict_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "location_id", nullable = false, updatable = false))
	private Set<Location> secondSideLocations = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "conflicts_1side_commanders",
			joinColumns = @JoinColumn(name = "conflict_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	private Set<Character> firstSideCommanders = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "conflicts_2side_commanders",
			joinColumns = @JoinColumn(name = "conflict_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	private Set<Character> secondSideCommanders = Sets.newHashSet();

}
