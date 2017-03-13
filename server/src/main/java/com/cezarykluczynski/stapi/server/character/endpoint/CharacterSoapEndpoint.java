package com.cezarykluczynski.stapi.server.character.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterResponse;
import com.cezarykluczynski.stapi.server.character.reader.CharacterSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class CharacterSoapEndpoint implements CharacterPortType {

	private CharacterSoapReader seriesSoapReader;

	public CharacterSoapEndpoint(CharacterSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public CharacterResponse getCharacters(@WebParam(partName = "request", name = "CharacterRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/character") CharacterRequest request) {
		return seriesSoapReader.readBase(request);
	}

}
