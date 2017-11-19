package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ElementApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse;

@SuppressWarnings("ParameterNumber")
public class Element {

	private final ElementApi elementApi;

	private final String apiKey;

	public Element(ElementApi elementApi, String apiKey) {
		this.elementApi = elementApi;
		this.apiKey = apiKey;
	}

	public ElementFullResponse get(String uid) throws ApiException {
		return elementApi.elementGet(uid, apiKey);
	}

	public ElementBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String symbol, Boolean transuranium,
			Boolean gammaSeries, Boolean hypersonicSeries, Boolean megaSeries, Boolean omegaSeries, Boolean transonicSeries, Boolean worldSeries)
			throws ApiException {
		return elementApi.elementSearchPost(pageNumber, pageSize, sort, apiKey, name, symbol, transuranium, gammaSeries, hypersonicSeries, megaSeries,
				omegaSeries, transonicSeries, worldSeries);
	}

}
