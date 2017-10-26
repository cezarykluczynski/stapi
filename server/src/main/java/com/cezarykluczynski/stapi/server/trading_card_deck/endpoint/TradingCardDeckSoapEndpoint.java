package com.cezarykluczynski.stapi.server.trading_card_deck.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckPortType;
import com.cezarykluczynski.stapi.server.trading_card_deck.reader.TradingCardDeckSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class TradingCardDeckSoapEndpoint implements TradingCardDeckPortType {

	public static final String ADDRESS = "/v1/soap/tradingCardDeck";

	private final TradingCardDeckSoapReader tradingCardDeckSoapReader;

	public TradingCardDeckSoapEndpoint(TradingCardDeckSoapReader tradingCardDeckSoapReader) {
		this.tradingCardDeckSoapReader = tradingCardDeckSoapReader;
	}

	@Override
	public TradingCardDeckBaseResponse getTradingCardDeckBase(@WebParam(partName = "request", name = "TradingCardDeckBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/tradingCardDeck") TradingCardDeckBaseRequest request) {
		return tradingCardDeckSoapReader.readBase(request);
	}

	@Override
	public TradingCardDeckFullResponse getTradingCardDeckFull(@WebParam(partName = "request", name = "TradingCardDeckFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/tradingCardDeck") TradingCardDeckFullRequest request) {
		return tradingCardDeckSoapReader.readFull(request);
	}

}
