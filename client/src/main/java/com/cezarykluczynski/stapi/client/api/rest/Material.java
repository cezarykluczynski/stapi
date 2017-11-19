package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.MaterialApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFullResponse;

@SuppressWarnings("ParameterNumber")
public class Material {

	private final MaterialApi materialApi;

	private final String apiKey;

	public Material(MaterialApi materialApi, String apiKey) {
		this.materialApi = materialApi;
		this.apiKey = apiKey;
	}

	public MaterialFullResponse get(String uid) throws ApiException {
		return materialApi.materialGet(uid, apiKey);
	}

	public MaterialBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean chemicalCompound,
			Boolean biochemicalCompound, Boolean drug, Boolean poisonousSubstance, Boolean explosive, Boolean gemstone, Boolean alloyOrComposite,
			Boolean fuel, Boolean mineral, Boolean preciousMaterial) throws ApiException {
		return materialApi.materialSearchPost(pageNumber, pageSize, sort, apiKey, name, chemicalCompound, biochemicalCompound, drug,
				poisonousSubstance, explosive, gemstone, alloyOrComposite, fuel, mineral, preciousMaterial);
	}

}
