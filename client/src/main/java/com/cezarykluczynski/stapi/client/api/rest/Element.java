package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.ElementV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.ElementApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ElementFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.ElementV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ElementV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Element {

	private final ElementApi elementApi;

	public Element(ElementApi elementApi) {
		this.elementApi = elementApi;
	}

	@Deprecated
	public ElementFullResponse get(String uid) throws ApiException {
		return elementApi.v1GetElement(uid);
	}

	public ElementV2FullResponse getV2(String uid) throws ApiException {
		return elementApi.v2GetElement(uid);
	}

	@Deprecated
	public ElementBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String symbol, Boolean transuranium,
			Boolean gammaSeries, Boolean hypersonicSeries, Boolean megaSeries, Boolean omegaSeries, Boolean transonicSeries, Boolean worldSeries)
			throws ApiException {
		return elementApi.v1SearchElements(pageNumber, pageSize, sort, name, symbol, transuranium, gammaSeries, hypersonicSeries,
				megaSeries, omegaSeries, transonicSeries, worldSeries);
	}

	@Deprecated
	public ElementV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String symbol, Boolean transuranic,
			Boolean gammaSeries, Boolean hypersonicSeries, Boolean megaSeries, Boolean omegaSeries, Boolean transonicSeries, Boolean worldSeries)
			throws ApiException {
		return elementApi.v2SearchElements(pageNumber, pageSize, sort, name, symbol, transuranic, gammaSeries, hypersonicSeries,
				megaSeries, omegaSeries, transonicSeries, worldSeries);
	}

	public ElementV2BaseResponse searchV2(ElementV2SearchCriteria elementV2SearchCriteria) throws ApiException {
		return elementApi.v2SearchElements(elementV2SearchCriteria.getPageNumber(), elementV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(elementV2SearchCriteria.getSort()), elementV2SearchCriteria.getName(),
				elementV2SearchCriteria.getSymbol(), elementV2SearchCriteria.getTransuranic(), elementV2SearchCriteria.getGammaSeries(),
				elementV2SearchCriteria.getHypersonicSeries(), elementV2SearchCriteria.getMegaSeries(), elementV2SearchCriteria.getOmegaSeries(),
				elementV2SearchCriteria.getTransonicSeries(), elementV2SearchCriteria.getWorldSeries());
	}

}
