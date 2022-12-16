package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalPortType;

public class Animal {

	private AnimalPortType animalPortType; // not final for the sake of testing

	public Animal(AnimalPortType animalPortType) {
		this.animalPortType = animalPortType;
	}

	@Deprecated
	public AnimalFullResponse get(AnimalFullRequest request) {
		return animalPortType.getAnimalFull(request);
	}

	@Deprecated
	public AnimalBaseResponse search(AnimalBaseRequest request) {
		return animalPortType.getAnimalBase(request);
	}

}
