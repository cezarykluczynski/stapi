package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFullResponse;

@SuppressWarnings("ParameterNumber")
public class TradingCard {

	private final TradingCardApi tradingCardApi;

	private final String apiKey;

	public TradingCard(TradingCardApi tradingCardApi, String apiKey) {
		this.tradingCardApi = tradingCardApi;
		this.apiKey = apiKey;
	}

	public TradingCardFullResponse get(String uid) throws ApiException {
		return tradingCardApi.tradingCardGet(uid, apiKey);
	}

	public TradingCardBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String tradingCardDeckUid,
			String tradingCardSetUid) throws ApiException {
		return tradingCardApi.tradingCardSearchPost(pageNumber, pageSize, sort, apiKey, name, tradingCardDeckUid, tradingCardSetUid);
	}

}
