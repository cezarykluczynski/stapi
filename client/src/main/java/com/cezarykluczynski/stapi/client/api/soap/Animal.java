package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalPortType;

public class Animal {

	private AnimalPortType animalPortType; // not final for the sake of testing

	private final ApiKeySupplier apiKeySupplier;

	public Animal(AnimalPortType animalPortType, ApiKeySupplier apiKeySupplier) {
		this.animalPortType = animalPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public AnimalFullResponse get(AnimalFullRequest request) {
		apiKeySupplier.supply(request);
		return animalPortType.getAnimalFull(request);
	}

	public AnimalBaseResponse search(AnimalBaseRequest request) {
		apiKeySupplier.supply(request);
		return animalPortType.getAnimalBase(request);
	}

}
