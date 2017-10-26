package com.cezarykluczynski.stapi.server.food.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.FoodFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.FoodPortType;
import com.cezarykluczynski.stapi.server.food.reader.FoodSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class FoodSoapEndpoint implements FoodPortType {

	public static final String ADDRESS = "/v1/soap/food";

	private final FoodSoapReader seriesSoapReader;

	public FoodSoapEndpoint(FoodSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public FoodBaseResponse getFoodBase(@WebParam(partName = "request", name = "FoodBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/food") FoodBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public FoodFullResponse getFoodFull(@WebParam(partName = "request", name = "FoodFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/food") FoodFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
