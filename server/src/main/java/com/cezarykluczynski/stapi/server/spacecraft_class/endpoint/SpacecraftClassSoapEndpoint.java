package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassPortType;
import com.cezarykluczynski.stapi.server.spacecraft_class.reader.SpacecraftClassSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class SpacecraftClassSoapEndpoint implements SpacecraftClassPortType {

	public static final String ADDRESS = "/v1/soap/spacecraftClass";

	private final SpacecraftClassSoapReader spacecraftClassSoapReader;

	public SpacecraftClassSoapEndpoint(SpacecraftClassSoapReader spacecraftClassSoapReader) {
		this.spacecraftClassSoapReader = spacecraftClassSoapReader;
	}

	@Override
	public SpacecraftClassBaseResponse getSpacecraftClassBase(@WebParam(partName = "request", name = "SpacecraftClassBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/spacecraftClass") SpacecraftClassBaseRequest request) {
		return spacecraftClassSoapReader.readBase(request);
	}

	@Override
	public SpacecraftClassFullResponse getSpacecraftClassFull(@WebParam(partName = "request", name = "SpacecraftClassFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/spacecraftClass") SpacecraftClassFullRequest request) {
		return spacecraftClassSoapReader.readFull(request);
	}

}
