package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SpeciesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse;

@SuppressWarnings("ParameterNumber")
public class Species {

	private final SpeciesApi speciesApi;

	public Species(SpeciesApi speciesApi) {
		this.speciesApi = speciesApi;
	}

	public SpeciesFullResponse get(String uid) throws ApiException {
		return speciesApi.v1RestSpeciesGet(uid, null);
	}

	public SpeciesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean extinctSpecies,
			Boolean warpCapableSpecies, Boolean extraGalacticSpecies, Boolean humanoidSpecies, Boolean reptilianSpecies, Boolean nonCorporealSpecies,
			Boolean shapeshiftingSpecies, Boolean spaceborneSpecies, Boolean telepathicSpecies, Boolean transDimensionalSpecies,
			Boolean unnamedSpecies, Boolean alternateReality) throws ApiException {
		return speciesApi.v1RestSpeciesSearchPost(pageNumber, pageSize, sort, null, name, extinctSpecies, warpCapableSpecies, extraGalacticSpecies,
				humanoidSpecies, reptilianSpecies, nonCorporealSpecies, shapeshiftingSpecies, spaceborneSpecies, telepathicSpecies,
				transDimensionalSpecies, unnamedSpecies, alternateReality);
	}

}
