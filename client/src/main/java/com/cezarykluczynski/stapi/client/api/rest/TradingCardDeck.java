package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.TradingCardDeckSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardDeckApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckFullResponse;

@SuppressWarnings("ParameterNumber")
public class TradingCardDeck {

	private final TradingCardDeckApi tradingCardDeckApi;

	public TradingCardDeck(TradingCardDeckApi tradingCardDeckApi) {
		this.tradingCardDeckApi = tradingCardDeckApi;
	}

	public TradingCardDeckFullResponse get(String uid) throws ApiException {
		return tradingCardDeckApi.v1GetTradingCardDeck(uid);
	}

	@Deprecated
	public TradingCardDeckBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String tradingCardSetUid)
			throws ApiException {
		return tradingCardDeckApi.v1SearchTradingCardDecks(pageNumber, pageSize, sort, name, tradingCardSetUid);
	}

	public TradingCardDeckBaseResponse search(TradingCardDeckSearchCriteria tradingCardDeckSearchCriteria) throws ApiException {
		return tradingCardDeckApi.v1SearchTradingCardDecks(tradingCardDeckSearchCriteria.getPageNumber(),
				tradingCardDeckSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(tradingCardDeckSearchCriteria.getSort()),
				tradingCardDeckSearchCriteria.getName(), tradingCardDeckSearchCriteria.getTradingCardSetUid());
	}

}
