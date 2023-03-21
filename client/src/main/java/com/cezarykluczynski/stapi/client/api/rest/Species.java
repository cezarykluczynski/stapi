package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.SpeciesV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.SpeciesApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Species {

	private final SpeciesApi speciesApi;

	public Species(SpeciesApi speciesApi) {
		this.speciesApi = speciesApi;
	}

	@Deprecated
	public SpeciesFullResponse get(String uid) throws ApiException {
		return speciesApi.v1Get(uid);
	}

	public SpeciesV2FullResponse getV2(String uid) throws ApiException {
		return speciesApi.v2Get(uid);
	}

	@Deprecated
	public SpeciesBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean extinctSpecies,
			Boolean warpCapableSpecies, Boolean extraGalacticSpecies, Boolean humanoidSpecies, Boolean reptilianSpecies, Boolean nonCorporealSpecies,
			Boolean shapeshiftingSpecies, Boolean spaceborneSpecies, Boolean telepathicSpecies, Boolean transDimensionalSpecies,
			Boolean unnamedSpecies, Boolean alternateReality) throws ApiException {
		return speciesApi.v1Search(pageNumber, pageSize, sort, name, extinctSpecies, warpCapableSpecies, extraGalacticSpecies,
				humanoidSpecies, reptilianSpecies, nonCorporealSpecies, shapeshiftingSpecies, spaceborneSpecies, telepathicSpecies,
				transDimensionalSpecies, unnamedSpecies, alternateReality);
	}

	@Deprecated
	public SpeciesV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean extinctSpecies,
			Boolean warpCapableSpecies, Boolean extraGalacticSpecies, Boolean humanoidSpecies, Boolean reptilianSpecies, Boolean avianSpecies,
			Boolean nonCorporealSpecies, Boolean shapeshiftingSpecies, Boolean spaceborneSpecies, Boolean telepathicSpecies,
			Boolean transDimensionalSpecies, Boolean unnamedSpecies, Boolean alternateReality) throws ApiException {
		return speciesApi.v2Search(pageNumber, pageSize, sort, name, extinctSpecies, warpCapableSpecies, extraGalacticSpecies,
				humanoidSpecies, reptilianSpecies, avianSpecies, nonCorporealSpecies, shapeshiftingSpecies, spaceborneSpecies, telepathicSpecies,
				transDimensionalSpecies, unnamedSpecies, alternateReality);
	}

	public SpeciesV2BaseResponse searchV2(SpeciesV2SearchCriteria speciesV2SearchCriteria) throws ApiException {
		return speciesApi.v2Search(speciesV2SearchCriteria.getPageNumber(), speciesV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(speciesV2SearchCriteria.getSort()), speciesV2SearchCriteria.getName(),
				speciesV2SearchCriteria.getExtinctSpecies(), speciesV2SearchCriteria.getWarpCapableSpecies(),
				speciesV2SearchCriteria.getExtraGalacticSpecies(), speciesV2SearchCriteria.getHumanoidSpecies(),
				speciesV2SearchCriteria.getReptilianSpecies(), speciesV2SearchCriteria.getAvianSpecies(),
				speciesV2SearchCriteria.getNonCorporealSpecies(), speciesV2SearchCriteria.getShapeshiftingSpecies(),
				speciesV2SearchCriteria.getSpaceborneSpecies(), speciesV2SearchCriteria.getTelepathicSpecies(),
				speciesV2SearchCriteria.getTransDimensionalSpecies(), speciesV2SearchCriteria.getUnnamedSpecies(),
				speciesV2SearchCriteria.getAlternateReality());
	}

}
