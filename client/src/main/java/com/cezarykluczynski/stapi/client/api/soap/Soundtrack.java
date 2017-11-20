package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackPortType;

public class Soundtrack {

	private final SoundtrackPortType soundtrackPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Soundtrack(SoundtrackPortType soundtrackPortType, ApiKeySupplier apiKeySupplier) {
		this.soundtrackPortType = soundtrackPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public SoundtrackFullResponse get(SoundtrackFullRequest request) {
		apiKeySupplier.supply(request);
		return soundtrackPortType.getSoundtrackFull(request);
	}

	public SoundtrackBaseResponse search(SoundtrackBaseRequest request) {
		apiKeySupplier.supply(request);
		return soundtrackPortType.getSoundtrackBase(request);
	}

}
