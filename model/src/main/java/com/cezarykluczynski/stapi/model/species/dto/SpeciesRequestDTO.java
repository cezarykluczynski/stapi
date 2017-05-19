package com.cezarykluczynski.stapi.model.species.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;

@Data
public class SpeciesRequestDTO {

	private String uid;

	private String name;

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

	private RequestSortDTO sort;

}
