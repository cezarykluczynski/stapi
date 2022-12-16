package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardDeckApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse;

@SuppressWarnings("ParameterNumber")
public class TradingCardDeck {

	private final TradingCardDeckApi tradingCardDeckApi;

	public TradingCardDeck(TradingCardDeckApi tradingCardDeckApi) {
		this.tradingCardDeckApi = tradingCardDeckApi;
	}

	public TradingCardDeckFullResponse get(String uid) throws ApiException {
		return tradingCardDeckApi.v1RestTradingCardDeckGet(uid, null);
	}

	public TradingCardDeckBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String tradingCardSetUid)
			throws ApiException {
		return tradingCardDeckApi.v1RestTradingCardDeckSearchPost(pageNumber, pageSize, sort, null, name, tradingCardSetUid);
	}

}
