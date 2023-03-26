package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardDeckApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckSearchCriteria;

public class TradingCardDeck {

	private final TradingCardDeckApi tradingCardDeckApi;

	public TradingCardDeck(TradingCardDeckApi tradingCardDeckApi) {
		this.tradingCardDeckApi = tradingCardDeckApi;
	}

	public TradingCardDeckFullResponse get(String uid) throws ApiException {
		return tradingCardDeckApi.v1GetTradingCardDeck(uid);
	}

	public TradingCardDeckBaseResponse search(TradingCardDeckSearchCriteria tradingCardDeckSearchCriteria) throws ApiException {
		return tradingCardDeckApi.v1SearchTradingCardDecks(tradingCardDeckSearchCriteria.getPageNumber(),
				tradingCardDeckSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(tradingCardDeckSearchCriteria.getSort()),
				tradingCardDeckSearchCriteria.getName(), tradingCardDeckSearchCriteria.getTradingCardSetUid());
	}

}
