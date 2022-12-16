package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckPortType;

public class TradingCardDeck {

	private final TradingCardDeckPortType tradingCardDeckPortType;

	public TradingCardDeck(TradingCardDeckPortType tradingCardDeckPortType) {
		this.tradingCardDeckPortType = tradingCardDeckPortType;
	}

	@Deprecated
	public TradingCardDeckFullResponse get(TradingCardDeckFullRequest request) {
		return tradingCardDeckPortType.getTradingCardDeckFull(request);
	}

	@Deprecated
	public TradingCardDeckBaseResponse search(TradingCardDeckBaseRequest request) {
		return tradingCardDeckPortType.getTradingCardDeckBase(request);
	}

}

