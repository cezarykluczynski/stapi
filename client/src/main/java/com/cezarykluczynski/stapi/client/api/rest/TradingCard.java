package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.TradingCardSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardFullResponse;

@SuppressWarnings("ParameterNumber")
public class TradingCard {

	private final TradingCardApi tradingCardApi;

	public TradingCard(TradingCardApi tradingCardApi) {
		this.tradingCardApi = tradingCardApi;
	}

	public TradingCardFullResponse get(String uid) throws ApiException {
		return tradingCardApi.v1RestTradingCardGet(uid);
	}

	@Deprecated
	public TradingCardBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String tradingCardDeckUid,
			String tradingCardSetUid) throws ApiException {
		return tradingCardApi.v1RestTradingCardSearchPost(pageNumber, pageSize, sort, name, tradingCardDeckUid, tradingCardSetUid);
	}

	public TradingCardBaseResponse search(TradingCardSearchCriteria tradingCardSearchCriteria) throws ApiException {
		return tradingCardApi.v1RestTradingCardSearchPost(tradingCardSearchCriteria.getPageNumber(), tradingCardSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(tradingCardSearchCriteria.getSort()), tradingCardSearchCriteria.getName(),
				tradingCardSearchCriteria.getTradingCardDeckUid(), tradingCardSearchCriteria.getTradingCardSetUid());
	}

}
