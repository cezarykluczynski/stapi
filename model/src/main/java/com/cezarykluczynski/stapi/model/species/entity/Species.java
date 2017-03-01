package com.cezarykluczynski.stapi.model.species.entity;

import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"homeworld", "quadrant"})
@EqualsAndHashCode(callSuper = true, exclude = {"homeworld", "quadrant"})
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

}
