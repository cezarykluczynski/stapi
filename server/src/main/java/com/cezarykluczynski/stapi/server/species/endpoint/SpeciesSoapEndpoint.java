package com.cezarykluczynski.stapi.server.species.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesResponse;
import com.cezarykluczynski.stapi.server.species.reader.SpeciesSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class SpeciesSoapEndpoint implements SpeciesPortType {

	private SpeciesSoapReader seriesSoapReader;

	public SpeciesSoapEndpoint(SpeciesSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	public SpeciesResponse getSpecies(@WebParam(partName = "request", name = "SpeciesRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/species") SpeciesRequest request) {
		return seriesSoapReader.read(request);
	}

}
