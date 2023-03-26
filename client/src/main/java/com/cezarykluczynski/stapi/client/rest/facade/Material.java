package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.MaterialApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.MaterialFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.MaterialSearchCriteria;

public class Material {

	private final MaterialApi materialApi;

	public Material(MaterialApi materialApi) {
		this.materialApi = materialApi;
	}

	public MaterialFullResponse get(String uid) throws ApiException {
		return materialApi.v1GetMaterial(uid);
	}

	public MaterialBaseResponse search(MaterialSearchCriteria materialSearchCriteria) throws ApiException {
		return materialApi.v1SearchMaterials(materialSearchCriteria.getPageNumber(), materialSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(materialSearchCriteria.getSort()), materialSearchCriteria.getName(),
				materialSearchCriteria.getChemicalCompound(), materialSearchCriteria.getBiochemicalCompound(), materialSearchCriteria.getDrug(),
				materialSearchCriteria.getPoisonousSubstance(), materialSearchCriteria.getExplosive(), materialSearchCriteria.getGemstone(),
				materialSearchCriteria.getAlloyOrComposite(), materialSearchCriteria.getFuel(), materialSearchCriteria.getMineral(),
				materialSearchCriteria.getPreciousMaterial());
	}

}
