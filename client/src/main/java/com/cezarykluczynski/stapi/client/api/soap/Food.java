package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.FoodPortType;

public class Food {

	private final FoodPortType foodPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Food(FoodPortType foodPortType, ApiKeySupplier apiKeySupplier) {
		this.foodPortType = foodPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public FoodFullResponse get(FoodFullRequest request) {
		apiKeySupplier.supply(request);
		return foodPortType.getFoodFull(request);
	}

	public FoodBaseResponse search(FoodBaseRequest request) {
		apiKeySupplier.supply(request);
		return foodPortType.getFoodBase(request);
	}

}
