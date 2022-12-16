package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.ElementApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Element {

	private final ElementApi elementApi;

	public Element(ElementApi elementApi) {
		this.elementApi = elementApi;
	}

	@Deprecated
	public ElementFullResponse get(String uid) throws ApiException {
		return elementApi.v1RestElementGet(uid, null);
	}

	public ElementV2FullResponse getV2(String uid) throws ApiException {
		return elementApi.v2RestElementGet(uid);
	}

	@Deprecated
	public ElementBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String symbol, Boolean transuranium,
			Boolean gammaSeries, Boolean hypersonicSeries, Boolean megaSeries, Boolean omegaSeries, Boolean transonicSeries, Boolean worldSeries)
			throws ApiException {
		return elementApi.v1RestElementSearchPost(pageNumber, pageSize, sort, null, name, symbol, transuranium, gammaSeries, hypersonicSeries,
				megaSeries, omegaSeries, transonicSeries, worldSeries);
	}

	public ElementV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String symbol, Boolean transuranic,
			Boolean gammaSeries, Boolean hypersonicSeries, Boolean megaSeries, Boolean omegaSeries, Boolean transonicSeries, Boolean worldSeries)
			throws ApiException {
		return elementApi.v2RestElementSearchPost(pageNumber, pageSize, sort, name, symbol, transuranic, gammaSeries, hypersonicSeries,
				megaSeries, omegaSeries, transonicSeries, worldSeries);
	}

}
