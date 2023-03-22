package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.FoodSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.FoodApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.FoodFullResponse;

@SuppressWarnings("ParameterNumber")
public class Food {

	private final FoodApi foodApi;

	public Food(FoodApi foodApi) {
		this.foodApi = foodApi;
	}

	public FoodFullResponse get(String uid) throws ApiException {
		return foodApi.v1GetFood(uid);
	}

	@Deprecated
	public FoodBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean earthlyOrigin, Boolean dessert,
			Boolean fruit, Boolean herbOrSpice, Boolean sauce, Boolean soup, Boolean beverage, Boolean alcoholicBeverage, Boolean juice, Boolean tea)
			throws ApiException {
		return foodApi.v1SearchFoods(pageNumber, pageSize, sort, name, earthlyOrigin, dessert, fruit, herbOrSpice, sauce, soup,
				beverage, alcoholicBeverage, juice, tea);
	}

	public FoodBaseResponse search(FoodSearchCriteria foodSearchCriteria) throws ApiException {
		return foodApi.v1SearchFoods(foodSearchCriteria.getPageNumber(), foodSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(foodSearchCriteria.getSort()), foodSearchCriteria.getName(),
				foodSearchCriteria.getEarthlyOrigin(), foodSearchCriteria.getDessert(), foodSearchCriteria.getFruit(),
				foodSearchCriteria.getHerbOrSpice(), foodSearchCriteria.getSauce(), foodSearchCriteria.getSoup(), foodSearchCriteria.getBeverage(),
				foodSearchCriteria.getAlcoholicBeverage(), foodSearchCriteria.getJuice(), foodSearchCriteria.getTea());
	}

}
