package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardSetApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetSearchCriteria;

public class TradingCardSet {

	private final TradingCardSetApi tradingCardSetApi;

	public TradingCardSet(TradingCardSetApi tradingCardSetApi) {
		this.tradingCardSetApi = tradingCardSetApi;
	}

	public TradingCardSetFullResponse get(String uid) throws ApiException {
		return tradingCardSetApi.v1GetTradingCardSet(uid);
	}

	public TradingCardSetBaseResponse search(TradingCardSetSearchCriteria tradingCardSetSearchCriteria) throws ApiException {
		return tradingCardSetApi.v1SearchTradingCardSets(tradingCardSetSearchCriteria.getPageNumber(),
				tradingCardSetSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(tradingCardSetSearchCriteria.getSort()),
				tradingCardSetSearchCriteria.getName(), tradingCardSetSearchCriteria.getReleaseYearFrom(),
				tradingCardSetSearchCriteria.getReleaseYearTo(), tradingCardSetSearchCriteria.getCardsPerPackFrom(),
				tradingCardSetSearchCriteria.getCardsPerPackTo(), tradingCardSetSearchCriteria.getPacksPerBoxFrom(),
				tradingCardSetSearchCriteria.getPacksPerBoxTo(), tradingCardSetSearchCriteria.getBoxesPerCaseFrom(),
				tradingCardSetSearchCriteria.getBoxesPerCaseTo(), tradingCardSetSearchCriteria.getProductionRunFrom(),
				tradingCardSetSearchCriteria.getProductionRunTo(), tradingCardSetSearchCriteria.getProductionRunUnit(),
				tradingCardSetSearchCriteria.getCardWidthFrom(), tradingCardSetSearchCriteria.getCardWidthTo(),
				tradingCardSetSearchCriteria.getCardHeightFrom(), tradingCardSetSearchCriteria.getCardHeightTo());
	}

}
