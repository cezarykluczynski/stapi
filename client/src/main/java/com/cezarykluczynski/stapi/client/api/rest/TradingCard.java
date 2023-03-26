package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSearchCriteria;

public class TradingCard {

	private final TradingCardApi tradingCardApi;

	public TradingCard(TradingCardApi tradingCardApi) {
		this.tradingCardApi = tradingCardApi;
	}

	public TradingCardFullResponse get(String uid) throws ApiException {
		return tradingCardApi.v1GetTradingCard(uid);
	}

	public TradingCardBaseResponse search(TradingCardSearchCriteria tradingCardSearchCriteria) throws ApiException {
		return tradingCardApi.v1SearchTradingCards(tradingCardSearchCriteria.getPageNumber(), tradingCardSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(tradingCardSearchCriteria.getSort()), tradingCardSearchCriteria.getName(),
				tradingCardSearchCriteria.getTradingCardDeckUid(), tradingCardSearchCriteria.getTradingCardSetUid());
	}

}
