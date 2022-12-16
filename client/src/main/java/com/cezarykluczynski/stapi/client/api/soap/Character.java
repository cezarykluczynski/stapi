package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;

public class Character {

	private final CharacterPortType characterPortType;

	public Character(CharacterPortType characterPortType) {
		this.characterPortType = characterPortType;
	}

	@Deprecated
	public CharacterFullResponse get(CharacterFullRequest request) {
		return characterPortType.getCharacterFull(request);
	}

	@Deprecated
	public CharacterBaseResponse search(CharacterBaseRequest request) {
		return characterPortType.getCharacterBase(request);
	}

}
