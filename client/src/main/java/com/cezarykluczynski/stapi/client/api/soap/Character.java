package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;

public class Character {

	private final CharacterPortType characterPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Character(CharacterPortType characterPortType, ApiKeySupplier apiKeySupplier) {
		this.characterPortType = characterPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public CharacterFullResponse get(CharacterFullRequest request) {
		apiKeySupplier.supply(request);
		return characterPortType.getCharacterFull(request);
	}

	public CharacterBaseResponse search(CharacterBaseRequest request) {
		apiKeySupplier.supply(request);
		return characterPortType.getCharacterBase(request);
	}

}
