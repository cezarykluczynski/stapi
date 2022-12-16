package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackPortType;

public class Soundtrack {

	private final SoundtrackPortType soundtrackPortType;

	public Soundtrack(SoundtrackPortType soundtrackPortType) {
		this.soundtrackPortType = soundtrackPortType;
	}

	@Deprecated
	public SoundtrackFullResponse get(SoundtrackFullRequest request) {
		return soundtrackPortType.getSoundtrackFull(request);
	}

	@Deprecated
	public SoundtrackBaseResponse search(SoundtrackBaseRequest request) {
		return soundtrackPortType.getSoundtrackBase(request);
	}

}
