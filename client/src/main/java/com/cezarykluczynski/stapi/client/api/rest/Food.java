package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.FoodApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.FoodFullResponse;

@SuppressWarnings("ParameterNumber")
public class Food {

	private final FoodApi foodApi;

	public Food(FoodApi foodApi) {
		this.foodApi = foodApi;
	}

	public FoodFullResponse get(String uid) throws ApiException {
		return foodApi.v1RestFoodGet(uid, null);
	}

	public FoodBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean earthlyOrigin, Boolean dessert,
			Boolean fruit, Boolean herbOrSpice, Boolean sauce, Boolean soup, Boolean beverage, Boolean alcoholicBeverage, Boolean juice, Boolean tea)
			throws ApiException {
		return foodApi.v1RestFoodSearchPost(pageNumber, pageSize, sort, null, name, earthlyOrigin, dessert, fruit, herbOrSpice, sauce, soup,
				beverage, alcoholicBeverage, juice, tea);
	}

}
