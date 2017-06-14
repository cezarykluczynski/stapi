package com.cezarykluczynski.stapi.etl.template.species.dto;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;

@Data
public class SpeciesTemplate {

	private Page page;

	private String name;

	private AstronomicalObject homeworld;

	private AstronomicalObject quadrant;

	private boolean extinctSpecies;

	private boolean warpCapableSpecies;

	private boolean extraGalacticSpecies;

	private boolean humanoidSpecies;

	private boolean reptilianSpecies;

	private boolean nonCorporealSpecies;

	private boolean shapeshiftingSpecies;

	private boolean spaceborneSpecies;

	private boolean telepathicSpecies;

	private boolean transDimensionalSpecies;

	private boolean unnamedSpecies;

	private boolean alternateReality;

}
