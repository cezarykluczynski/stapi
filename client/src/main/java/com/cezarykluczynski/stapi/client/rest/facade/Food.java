package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.FoodApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.FoodFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.FoodSearchCriteria;

public class Food {

	private final FoodApi foodApi;

	public Food(FoodApi foodApi) {
		this.foodApi = foodApi;
	}

	public FoodFullResponse get(String uid) throws ApiException {
		return foodApi.v1GetFood(uid);
	}

	public FoodBaseResponse search(FoodSearchCriteria foodSearchCriteria) throws ApiException {
		return foodApi.v1SearchFoods(foodSearchCriteria.getPageNumber(), foodSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(foodSearchCriteria.getSort()), foodSearchCriteria.getName(),
				foodSearchCriteria.getEarthlyOrigin(), foodSearchCriteria.getDessert(), foodSearchCriteria.getFruit(),
				foodSearchCriteria.getHerbOrSpice(), foodSearchCriteria.getSauce(), foodSearchCriteria.getSoup(), foodSearchCriteria.getBeverage(),
				foodSearchCriteria.getAlcoholicBeverage(), foodSearchCriteria.getJuice(), foodSearchCriteria.getTea());
	}

}
