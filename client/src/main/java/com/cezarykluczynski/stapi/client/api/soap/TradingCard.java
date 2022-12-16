package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardPortType;

public class TradingCard {

	private final TradingCardPortType tradingCardPortType;

	public TradingCard(TradingCardPortType tradingCardPortType) {
		this.tradingCardPortType = tradingCardPortType;
	}

	@Deprecated
	public TradingCardFullResponse get(TradingCardFullRequest request) {
		return tradingCardPortType.getTradingCardFull(request);
	}

	@Deprecated
	public TradingCardBaseResponse search(TradingCardBaseRequest request) {
		return tradingCardPortType.getTradingCardBase(request);
	}

}

