package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.ElementApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ElementV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ElementV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.ElementV2SearchCriteria;

public class Element {

	private final ElementApi elementApi;

	public Element(ElementApi elementApi) {
		this.elementApi = elementApi;
	}

	public ElementV2FullResponse getV2(String uid) throws ApiException {
		return elementApi.v2GetElement(uid);
	}

	public ElementV2BaseResponse searchV2(ElementV2SearchCriteria elementV2SearchCriteria) throws ApiException {
		return elementApi.v2SearchElements(elementV2SearchCriteria.getPageNumber(), elementV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(elementV2SearchCriteria.getSort()), elementV2SearchCriteria.getName(),
				elementV2SearchCriteria.getSymbol(), elementV2SearchCriteria.getTransuranic(), elementV2SearchCriteria.getGammaSeries(),
				elementV2SearchCriteria.getHypersonicSeries(), elementV2SearchCriteria.getMegaSeries(), elementV2SearchCriteria.getOmegaSeries(),
				elementV2SearchCriteria.getTransonicSeries(), elementV2SearchCriteria.getWorldSeries());
	}

}
