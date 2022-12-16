package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.FoodPortType;

public class Food {

	private final FoodPortType foodPortType;

	public Food(FoodPortType foodPortType) {
		this.foodPortType = foodPortType;
	}

	@Deprecated
	public FoodFullResponse get(FoodFullRequest request) {
		return foodPortType.getFoodFull(request);
	}

	@Deprecated
	public FoodBaseResponse search(FoodBaseRequest request) {
		return foodPortType.getFoodBase(request);
	}

}
