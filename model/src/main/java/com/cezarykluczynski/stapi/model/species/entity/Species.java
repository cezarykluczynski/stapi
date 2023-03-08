package com.cezarykluczynski.stapi.model.species.entity;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
import com.google.common.collect.Sets;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"homeworld", "quadrant", "characters"})
@EqualsAndHashCode(callSuper = true, exclude = {"homeworld", "quadrant", "characters"})
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = SpeciesRepository.class, singularName = "species", pluralName = "species",
		restApiVersion = "v2")
public class Species extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "species_sequence_generator")
	@SequenceGenerator(name = "species_sequence_generator", sequenceName = "species_sequence", allocationSize = 1)
	private Long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "homeworld_id")
	private AstronomicalObject homeworld;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "quadrant_id")
	private AstronomicalObject quadrant;

	private Boolean extinctSpecies;

	private Boolean warpCapableSpecies;

	private Boolean extraGalacticSpecies;

	private Boolean humanoidSpecies;

	private Boolean reptilianSpecies;

	private Boolean avianSpecies;

	private Boolean nonCorporealSpecies;

	private Boolean shapeshiftingSpecies;

	private Boolean spaceborneSpecies;

	private Boolean telepathicSpecies;

	private Boolean transDimensionalSpecies;

	private Boolean unnamedSpecies;

	private Boolean alternateReality;

	@Transient
	private Set<Character> characters = Sets.newHashSet();

}
