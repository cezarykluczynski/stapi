package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.CompanyApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse;

@SuppressWarnings("ParameterNumber")
public class Company {

	private final CompanyApi companyApi;

	private final String apiKey;

	public Company(CompanyApi companyApi, String apiKey) {
		this.companyApi = companyApi;
		this.apiKey = apiKey;
	}

	public CompanyFullResponse get(String uid) throws ApiException {
		return companyApi.companyGet(uid, apiKey);
	}

	public CompanyBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean broadcaster,
			Boolean collectibleCompany, Boolean conglomerate, Boolean digitalVisualEffectsCompany, Boolean distributor, Boolean gameCompany,
			Boolean filmEquipmentCompany, Boolean makeUpEffectsStudio, Boolean mattePaintingCompany, Boolean modelAndMiniatureEffectsCompany,
			Boolean postProductionCompany, Boolean productionCompany, Boolean propCompany, Boolean recordLabel, Boolean specialEffectsCompany,
			Boolean tvAndFilmProductionCompany, Boolean videoGameCompany) throws ApiException {
		return companyApi.companySearchPost(pageNumber, pageSize, sort, apiKey, name, broadcaster, collectibleCompany, conglomerate,
				digitalVisualEffectsCompany, distributor, gameCompany, filmEquipmentCompany, makeUpEffectsStudio, mattePaintingCompany,
				modelAndMiniatureEffectsCompany, postProductionCompany, productionCompany, propCompany, recordLabel, specialEffectsCompany,
				tvAndFilmProductionCompany, videoGameCompany);
	}

}
