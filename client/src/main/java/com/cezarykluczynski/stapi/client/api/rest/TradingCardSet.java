package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.TradingCardSetSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardSetApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ProductionRunUnit;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetFullResponse;

@SuppressWarnings("ParameterNumber")
public class TradingCardSet {

	private final TradingCardSetApi tradingCardSetApi;

	public TradingCardSet(TradingCardSetApi tradingCardSetApi) {
		this.tradingCardSetApi = tradingCardSetApi;
	}

	public TradingCardSetFullResponse get(String uid) throws ApiException {
		return tradingCardSetApi.v1GetTradingCardSet(uid);
	}

	@Deprecated
	public TradingCardSetBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Integer releaseYearFrom,
			Integer releaseYearTo, Integer cardsPerPackFrom, Integer cardsPerPackTo, Integer packsPerBoxFrom, Integer packsPerBoxTo,
			Integer boxesPerCaseFrom, Integer boxesPerCaseTo, Integer productionRunFrom, Integer productionRunTo, String productionRunUnit,
			Double cardWidthFrom, Double cardWidthTo, Double cardHeightFrom, Double cardHeightTo) throws ApiException {
		return tradingCardSetApi.v1SearchTradingCardSets(pageNumber, pageSize, sort, name, releaseYearFrom, releaseYearTo,
				cardsPerPackFrom, cardsPerPackTo, packsPerBoxFrom, packsPerBoxTo, boxesPerCaseFrom, boxesPerCaseTo, productionRunFrom,
				productionRunTo, productionRunUnit, cardWidthFrom, cardWidthTo, cardHeightFrom, cardHeightTo);
	}

	public TradingCardSetBaseResponse search(TradingCardSetSearchCriteria tradingCardSetSearchCriteria) throws ApiException {
		return tradingCardSetApi.v1SearchTradingCardSets(tradingCardSetSearchCriteria.getPageNumber(),
				tradingCardSetSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(tradingCardSetSearchCriteria.getSort()),
				tradingCardSetSearchCriteria.getName(), tradingCardSetSearchCriteria.getReleaseYearFrom(),
				tradingCardSetSearchCriteria.getReleaseYearTo(), tradingCardSetSearchCriteria.getCardsPerPackFrom(),
				tradingCardSetSearchCriteria.getCardsPerPackTo(), tradingCardSetSearchCriteria.getPacksPerBoxFrom(),
				tradingCardSetSearchCriteria.getPacksPerBoxTo(), tradingCardSetSearchCriteria.getBoxesPerCaseFrom(),
				tradingCardSetSearchCriteria.getBoxesPerCaseTo(), tradingCardSetSearchCriteria.getProductionRunFrom(),
				tradingCardSetSearchCriteria.getProductionRunTo(), nameIfPresent(tradingCardSetSearchCriteria.getProductionRunUnit()),
				tradingCardSetSearchCriteria.getCardWidthFrom(), tradingCardSetSearchCriteria.getCardWidthTo(),
				tradingCardSetSearchCriteria.getCardHeightFrom(), tradingCardSetSearchCriteria.getCardHeightTo());
	}

	private static String nameIfPresent(ProductionRunUnit productionRunUnit) {
		return productionRunUnit != null ? productionRunUnit.name() : null;
	}

}
