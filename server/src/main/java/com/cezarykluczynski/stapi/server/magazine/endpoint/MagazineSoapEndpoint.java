package com.cezarykluczynski.stapi.server.magazine.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazinePortType;
import com.cezarykluczynski.stapi.server.magazine.reader.MagazineSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class MagazineSoapEndpoint implements MagazinePortType {

	public static final String ADDRESS = "/v1/soap/magazine";

	private final MagazineSoapReader magazineSoapReader;

	public MagazineSoapEndpoint(MagazineSoapReader magazineSoapReader) {
		this.magazineSoapReader = magazineSoapReader;
	}

	@Override
	public MagazineBaseResponse getMagazineBase(@WebParam(partName = "request", name = "MagazineBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/magazine") MagazineBaseRequest request) {
		return magazineSoapReader.readBase(request);
	}

	@Override
	public MagazineFullResponse getMagazineFull(@WebParam(partName = "request", name = "MagazineFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/magazine") MagazineFullRequest request) {
		return magazineSoapReader.readFull(request);
	}

}
