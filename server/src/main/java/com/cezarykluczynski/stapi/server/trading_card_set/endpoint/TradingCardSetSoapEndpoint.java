package com.cezarykluczynski.stapi.server.trading_card_set.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetPortType;
import com.cezarykluczynski.stapi.server.trading_card_set.reader.TradingCardSetSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class TradingCardSetSoapEndpoint implements TradingCardSetPortType {

	public static final String ADDRESS = "/v1/soap/tradingCardSet";

	private final TradingCardSetSoapReader tradingCardSetSoapReader;

	public TradingCardSetSoapEndpoint(TradingCardSetSoapReader tradingCardSetSoapReader) {
		this.tradingCardSetSoapReader = tradingCardSetSoapReader;
	}

	@Override
	public TradingCardSetBaseResponse getTradingCardSetBase(@WebParam(partName = "request", name = "TradingCardSetBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/tradingCardSet") TradingCardSetBaseRequest request) {
		return tradingCardSetSoapReader.readBase(request);
	}

	@Override
	public TradingCardSetFullResponse getTradingCardSetFull(@WebParam(partName = "request", name = "TradingCardSetFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/tradingCardSet") TradingCardSetFullRequest request) {
		return tradingCardSetSoapReader.readFull(request);
	}

}
