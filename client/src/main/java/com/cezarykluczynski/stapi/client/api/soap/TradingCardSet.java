package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetPortType;

public class TradingCardSet {

	private final TradingCardSetPortType tradingCardSetPortType;

	private final ApiKeySupplier apiKeySupplier;

	public TradingCardSet(TradingCardSetPortType tradingCardSetPortType, ApiKeySupplier apiKeySupplier) {
		this.tradingCardSetPortType = tradingCardSetPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public TradingCardSetFullResponse get(TradingCardSetFullRequest request) {
		apiKeySupplier.supply(request);
		return tradingCardSetPortType.getTradingCardSetFull(request);
	}

	public TradingCardSetBaseResponse search(TradingCardSetBaseRequest request) {
		apiKeySupplier.supply(request);
		return tradingCardSetPortType.getTradingCardSetBase(request);
	}

}
