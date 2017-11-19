package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardSetApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFullResponse;

@SuppressWarnings("ParameterNumber")
public class TradingCardSet {

	private final TradingCardSetApi tradingCardSetApi;

	private final String apiKey;

	public TradingCardSet(TradingCardSetApi tradingCardSetApi, String apiKey) {
		this.tradingCardSetApi = tradingCardSetApi;
		this.apiKey = apiKey;
	}

	public TradingCardSetFullResponse get(String uid) throws ApiException {
		return tradingCardSetApi.tradingCardSetGet(uid, apiKey);
	}

	public TradingCardSetBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Integer releaseYearFrom,
			Integer releaseYearTo, Integer cardsPerPackFrom, Integer cardsPerPackTo, Integer packsPerBoxFrom, Integer packsPerBoxTo,
			Integer boxesPerCaseFrom, Integer boxesPerCaseTo, Integer productionRunFrom, Integer productionRunTo, String productionRunUnit,
			Double cardWidthFrom, Double cardWidthTo, Double cardHeightFrom, Double cardHeightTo) throws ApiException {
		return tradingCardSetApi.tradingCardSetSearchPost(pageNumber, pageSize, sort, apiKey, name, releaseYearFrom, releaseYearTo, cardsPerPackFrom,
				cardsPerPackTo, packsPerBoxFrom, packsPerBoxTo, boxesPerCaseFrom, boxesPerCaseTo, productionRunFrom, productionRunTo,
				productionRunUnit, cardWidthFrom, cardWidthTo, cardHeightFrom, cardHeightTo);
	}

}
