package com.cezarykluczynski.stapi.model.spacecraft_class.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Data
@Entity

@ToString(callSuper = true, exclude = {"species", "owners", "operators", "affiliations", "spacecraftTypes", "armaments", "spacecrafts"})
@EqualsAndHashCode(callSuper = true, exclude = {"species", "owners", "operators", "affiliations", "spacecraftTypes", "armaments", "spacecrafts"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = SpacecraftClassRepository.class, singularName = "spacecraft class",
		pluralName = "spacecraft classes", restApiVersion = "v2")
public class SpacecraftClass extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spacecraft_class_sequence_generator")
	@SequenceGenerator(name = "spacecraft_class_sequence_generator", sequenceName = "spacecraft_class_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Integer numberOfDecks;

	private String crew;

	private Boolean warpCapable;

	private String activeFrom;

	private String activeTo;

	private Boolean mirror;

	private Boolean alternateReality;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "species_id")
	private Species species;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "spacecraft_classes_owners",
			joinColumns = @JoinColumn(name = "spacecraft_class_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "organization_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Organization> owners = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "spacecraft_classes_operators",
			joinColumns = @JoinColumn(name = "spacecraft_class_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "organization_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Organization> operators = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "spacecraft_classes_affiliations",
			joinColumns = @JoinColumn(name = "spacecraft_class_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "organization_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Organization> affiliations = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "spacecraft_classes_types",
			joinColumns = @JoinColumn(name = "spacecraft_class_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "spacecraft_type_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<SpacecraftType> spacecraftTypes = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "spacecraft_classes_weapons",
			joinColumns = @JoinColumn(name = "spacecraft_class_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "weapon_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Weapon> armaments = Sets.newHashSet();

	@OneToMany(mappedBy = "spacecraftClass", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Spacecraft> spacecrafts = Sets.newHashSet();

}
