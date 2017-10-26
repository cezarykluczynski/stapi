package com.cezarykluczynski.stapi.server.spacecraft.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftPortType;
import com.cezarykluczynski.stapi.server.spacecraft.reader.SpacecraftSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class SpacecraftSoapEndpoint implements SpacecraftPortType {

	public static final String ADDRESS = "/v1/soap/spacecraft";

	private final SpacecraftSoapReader spacecraftSoapReader;

	public SpacecraftSoapEndpoint(SpacecraftSoapReader spacecraftSoapReader) {
		this.spacecraftSoapReader = spacecraftSoapReader;
	}

	@Override
	public SpacecraftBaseResponse getSpacecraftBase(@WebParam(partName = "request", name = "SpacecraftBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/spacecraft") SpacecraftBaseRequest request) {
		return spacecraftSoapReader.readBase(request);
	}

	@Override
	public SpacecraftFullResponse getSpacecraftFull(@WebParam(partName = "request", name = "SpacecraftFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/spacecraft") SpacecraftFullRequest request) {
		return spacecraftSoapReader.readFull(request);
	}

}
