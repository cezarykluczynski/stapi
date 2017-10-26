package com.cezarykluczynski.stapi.server.element.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ElementPortType;
import com.cezarykluczynski.stapi.server.element.reader.ElementSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class ElementSoapEndpoint implements ElementPortType {

	public static final String ADDRESS = "/v1/soap/element";

	private final ElementSoapReader seriesSoapReader;

	public ElementSoapEndpoint(ElementSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public ElementBaseResponse getElementBase(@WebParam(partName = "request", name = "ElementBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/element") ElementBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public ElementFullResponse getElementFull(@WebParam(partName = "request", name = "ElementFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/element") ElementFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
