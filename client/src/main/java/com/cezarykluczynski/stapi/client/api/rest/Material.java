package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.MaterialSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.MaterialApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFullResponse;

@SuppressWarnings("ParameterNumber")
public class Material {

	private final MaterialApi materialApi;

	public Material(MaterialApi materialApi) {
		this.materialApi = materialApi;
	}

	public MaterialFullResponse get(String uid) throws ApiException {
		return materialApi.v1RestMaterialGet(uid, null);
	}

	@Deprecated
	public MaterialBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean chemicalCompound,
			Boolean biochemicalCompound, Boolean drug, Boolean poisonousSubstance, Boolean explosive, Boolean gemstone, Boolean alloyOrComposite,
			Boolean fuel, Boolean mineral, Boolean preciousMaterial) throws ApiException {
		return materialApi.v1RestMaterialSearchPost(pageNumber, pageSize, sort, null, name, chemicalCompound, biochemicalCompound, drug,
				poisonousSubstance, explosive, gemstone, alloyOrComposite, fuel, mineral, preciousMaterial);
	}

	public MaterialBaseResponse search(MaterialSearchCriteria materialSearchCriteria) throws ApiException {
		return materialApi.v1RestMaterialSearchPost(materialSearchCriteria.getPageNumber(), materialSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(materialSearchCriteria.getSort()), null, materialSearchCriteria.getName(),
				materialSearchCriteria.getChemicalCompound(), materialSearchCriteria.getBiochemicalCompound(), materialSearchCriteria.getDrug(),
				materialSearchCriteria.getPoisonousSubstance(), materialSearchCriteria.getExplosive(), materialSearchCriteria.getGemstone(),
				materialSearchCriteria.getAlloyOrComposite(), materialSearchCriteria.getFuel(), materialSearchCriteria.getMineral(),
				materialSearchCriteria.getPreciousMaterial());
	}

}
