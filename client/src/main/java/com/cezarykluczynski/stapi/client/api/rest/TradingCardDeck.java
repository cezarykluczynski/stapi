package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardDeckApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse;

@SuppressWarnings("ParameterNumber")
public class TradingCardDeck {

	private final TradingCardDeckApi tradingCardDeckApi;

	private final String apiKey;

	public TradingCardDeck(TradingCardDeckApi tradingCardDeckApi, String apiKey) {
		this.tradingCardDeckApi = tradingCardDeckApi;
		this.apiKey = apiKey;
	}

	public TradingCardDeckFullResponse get(String uid) throws ApiException {
		return tradingCardDeckApi.v1RestTradingCardDeckGet(uid, apiKey);
	}

	public TradingCardDeckBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String tradingCardSetUid)
			throws ApiException {
		return tradingCardDeckApi.v1RestTradingCardDeckSearchPost(pageNumber, pageSize, sort, apiKey, name, tradingCardSetUid);
	}

}
