package com.cezarykluczynski.stapi.model.species.entity;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"homeworld", "quadrant", "characters"})
@EqualsAndHashCode(callSuper = true, exclude = {"homeworld", "quadrant", "characters"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = SpeciesRepository.class, singularName = "species", pluralName = "species")
public class Species extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "species_sequence_generator")
	@SequenceGenerator(name = "species_sequence_generator", sequenceName = "species_sequence", allocationSize = 1)
	private Long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "homeworld_id")
	private AstronomicalObject homeworld;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "quadrant_id")
	private AstronomicalObject quadrant;

	private Boolean extinctSpecies;

	private Boolean warpCapableSpecies;

	private Boolean extraGalacticSpecies;

	private Boolean humanoidSpecies;

	private Boolean reptilianSpecies;

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
