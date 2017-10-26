package com.cezarykluczynski.stapi.server.trading_card.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardPortType;
import com.cezarykluczynski.stapi.server.trading_card.reader.TradingCardSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class TradingCardSoapEndpoint implements TradingCardPortType {

	public static final String ADDRESS = "/v1/soap/tradingCard";

	private final TradingCardSoapReader tradingCardSoapReader;

	public TradingCardSoapEndpoint(TradingCardSoapReader tradingCardSoapReader) {
		this.tradingCardSoapReader = tradingCardSoapReader;
	}

	@Override
	public TradingCardBaseResponse getTradingCardBase(@WebParam(partName = "request", name = "TradingCardBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/tradingCard") TradingCardBaseRequest request) {
		return tradingCardSoapReader.readBase(request);
	}

	@Override
	public TradingCardFullResponse getTradingCardFull(@WebParam(partName = "request", name = "TradingCardFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/tradingCard") TradingCardFullRequest request) {
		return tradingCardSoapReader.readFull(request);
	}

}
