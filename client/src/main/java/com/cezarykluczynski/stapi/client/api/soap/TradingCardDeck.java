package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckPortType;

public class TradingCardDeck {

	private final TradingCardDeckPortType tradingCardDeckPortType;

	private final ApiKeySupplier apiKeySupplier;

	public TradingCardDeck(TradingCardDeckPortType tradingCardDeckPortType, ApiKeySupplier apiKeySupplier) {
		this.tradingCardDeckPortType = tradingCardDeckPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public TradingCardDeckFullResponse get(TradingCardDeckFullRequest request) {
		apiKeySupplier.supply(request);
		return tradingCardDeckPortType.getTradingCardDeckFull(request);
	}

	public TradingCardDeckBaseResponse search(TradingCardDeckBaseRequest request) {
		apiKeySupplier.supply(request);
		return tradingCardDeckPortType.getTradingCardDeckBase(request);
	}

}

