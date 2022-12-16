package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetPortType;

public class TradingCardSet {

	private final TradingCardSetPortType tradingCardSetPortType;

	public TradingCardSet(TradingCardSetPortType tradingCardSetPortType) {
		this.tradingCardSetPortType = tradingCardSetPortType;
	}

	@Deprecated
	public TradingCardSetFullResponse get(TradingCardSetFullRequest request) {
		return tradingCardSetPortType.getTradingCardSetFull(request);
	}

	@Deprecated
	public TradingCardSetBaseResponse search(TradingCardSetBaseRequest request) {
		return tradingCardSetPortType.getTradingCardSetBase(request);
	}

}
