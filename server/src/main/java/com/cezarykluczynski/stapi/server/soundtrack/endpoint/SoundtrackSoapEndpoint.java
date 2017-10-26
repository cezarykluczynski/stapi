package com.cezarykluczynski.stapi.server.soundtrack.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackPortType;
import com.cezarykluczynski.stapi.server.soundtrack.reader.SoundtrackSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class SoundtrackSoapEndpoint implements SoundtrackPortType {

	public static final String ADDRESS = "/v1/soap/soundtrack";

	private final SoundtrackSoapReader soundtrackSoapReader;

	public SoundtrackSoapEndpoint(SoundtrackSoapReader soundtrackSoapReader) {
		this.soundtrackSoapReader = soundtrackSoapReader;
	}

	@Override
	public SoundtrackBaseResponse getSoundtrackBase(@WebParam(partName = "request", name = "SoundtrackBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/soundtrack") SoundtrackBaseRequest request) {
		return soundtrackSoapReader.readBase(request);
	}

	@Override
	public SoundtrackFullResponse getSoundtrackFull(@WebParam(partName = "request", name = "SoundtrackFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/soundtrack") SoundtrackFullRequest request) {
		return soundtrackSoapReader.readFull(request);
	}

}
