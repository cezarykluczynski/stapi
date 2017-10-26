package com.cezarykluczynski.stapi.server.material.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialPortType;
import com.cezarykluczynski.stapi.server.material.reader.MaterialSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class MaterialSoapEndpoint implements MaterialPortType {

	public static final String ADDRESS = "/v1/soap/material";

	private final MaterialSoapReader seriesSoapReader;

	public MaterialSoapEndpoint(MaterialSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public MaterialBaseResponse getMaterialBase(@WebParam(partName = "request", name = "MaterialBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/material") MaterialBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public MaterialFullResponse getMaterialFull(@WebParam(partName = "request", name = "MaterialFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/material") MaterialFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
