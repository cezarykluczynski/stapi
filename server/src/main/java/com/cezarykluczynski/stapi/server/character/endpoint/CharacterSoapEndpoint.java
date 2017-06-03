package com.cezarykluczynski.stapi.server.character.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;
import com.cezarykluczynski.stapi.server.character.reader.CharacterSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class CharacterSoapEndpoint implements CharacterPortType {

	public static final String ADDRESS = "/v1/soap/character";

	private final CharacterSoapReader characterSoapReader;

	public CharacterSoapEndpoint(CharacterSoapReader characterSoapReader) {
		this.characterSoapReader = characterSoapReader;
	}

	@Override
	public CharacterBaseResponse getCharacterBase(@WebParam(partName = "request", name = "CharacterBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/character") CharacterBaseRequest request) {
		return characterSoapReader.readBase(request);
	}

	@Override
	public CharacterFullResponse getCharacterFull(@WebParam(partName = "request", name = "CharacterFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/character") CharacterFullRequest request) {
		return characterSoapReader.readFull(request);
	}

}
