package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.SpeciesApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse;

@SuppressWarnings("ParameterNumber")
public class Species {

	private final SpeciesApi speciesApi;

	private final String apiKey;

	public Species(SpeciesApi speciesApi, String apiKey) {
		this.speciesApi = speciesApi;
		this.apiKey = apiKey;
	}

	public SpeciesFullResponse get(String uid) throws ApiException {
		return speciesApi.speciesGet(uid, apiKey);
	}

	public SpeciesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean extinctSpecies,
			Boolean warpCapableSpecies, Boolean extraGalacticSpecies, Boolean humanoidSpecies, Boolean reptilianSpecies, Boolean nonCorporealSpecies,
			Boolean shapeshiftingSpecies, Boolean spaceborneSpecies, Boolean telepathicSpecies, Boolean transDimensionalSpecies,
			Boolean unnamedSpecies, Boolean alternateReality) throws ApiException {
		return speciesApi.speciesSearchPost(pageNumber, pageSize, sort, apiKey, name, extinctSpecies, warpCapableSpecies, extraGalacticSpecies,
				humanoidSpecies, reptilianSpecies, nonCorporealSpecies, shapeshiftingSpecies, spaceborneSpecies, telepathicSpecies,
				transDimensionalSpecies, unnamedSpecies, alternateReality);
	}

}
