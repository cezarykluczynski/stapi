package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardPortType;

public class TradingCard {

	private final TradingCardPortType tradingCardPortType;

	private final ApiKeySupplier apiKeySupplier;

	public TradingCard(TradingCardPortType tradingCardPortType, ApiKeySupplier apiKeySupplier) {
		this.tradingCardPortType = tradingCardPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public TradingCardFullResponse get(TradingCardFullRequest request) {
		apiKeySupplier.supply(request);
		return tradingCardPortType.getTradingCardFull(request);
	}

	public TradingCardBaseResponse search(TradingCardBaseRequest request) {
		apiKeySupplier.supply(request);
		return tradingCardPortType.getTradingCardBase(request);
	}

}

