package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.SpeciesApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2SearchCriteria;

public class Species {

	private final SpeciesApi speciesApi;

	public Species(SpeciesApi speciesApi) {
		this.speciesApi = speciesApi;
	}

	public SpeciesV2FullResponse getV2(String uid) throws ApiException {
		return speciesApi.v2GetSpecies(uid);
	}

	public SpeciesV2BaseResponse searchV2(SpeciesV2SearchCriteria speciesV2SearchCriteria) throws ApiException {
		return speciesApi.v2SearchSpecies(speciesV2SearchCriteria.getPageNumber(), speciesV2SearchCriteria.getPageSize(),
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
