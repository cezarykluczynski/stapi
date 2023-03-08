package com.cezarykluczynski.stapi.model.spacecraft.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"spacecraftClass", "owner", "operator", "affiliation", "spacecraftTypes"})
@EqualsAndHashCode(callSuper = true, exclude = {"spacecraftClass", "owner", "operator", "affiliation", "spacecraftTypes"})
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = SpacecraftRepository.class, singularName = "spacecraft",
		pluralName = "spacecrafts", restApiVersion = "v2")
public class Spacecraft extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spacecraft_sequence_generator")
	@SequenceGenerator(name = "spacecraft_sequence_generator", sequenceName = "spacecraft_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String registry;

	private String status;

	private String dateStatus;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "spacecraft_class_id")
	private SpacecraftClass spacecraftClass;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	private Organization owner;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "operator_id")
	private Organization operator;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "affiliation_id")
	private Organization affiliation;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "spacecrafts_spacecraft_types",
			joinColumns = @JoinColumn(name = "spacecraft_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "spacecraft_type_id", nullable = false, updatable = false))
	private Set<SpacecraftType> spacecraftTypes = Sets.newHashSet();

}
