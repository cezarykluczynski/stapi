package com.cezarykluczynski.stapi.server.occupation.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationPortType;
import com.cezarykluczynski.stapi.server.occupation.reader.OccupationSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class OccupationSoapEndpoint implements OccupationPortType {

	public static final String ADDRESS = "/v1/soap/occupation";

	private final OccupationSoapReader seriesSoapReader;

	public OccupationSoapEndpoint(OccupationSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public OccupationBaseResponse getOccupationBase(@WebParam(partName = "request", name = "OccupationBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/occupation") OccupationBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public OccupationFullResponse getOccupationFull(@WebParam(partName = "request", name = "OccupationFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/occupation") OccupationFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
