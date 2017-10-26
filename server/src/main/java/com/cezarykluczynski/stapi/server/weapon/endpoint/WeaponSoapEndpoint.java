package com.cezarykluczynski.stapi.server.weapon.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponPortType;
import com.cezarykluczynski.stapi.server.weapon.reader.WeaponSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class WeaponSoapEndpoint implements WeaponPortType {

	public static final String ADDRESS = "/v1/soap/weapon";

	private final WeaponSoapReader seriesSoapReader;

	public WeaponSoapEndpoint(WeaponSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public WeaponBaseResponse getWeaponBase(@WebParam(partName = "request", name = "WeaponBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/weapon") WeaponBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public WeaponFullResponse getWeaponFull(@WebParam(partName = "request", name = "WeaponFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/weapon") WeaponFullRequest request) {
		return seriesSoapReader.readFull(request);
	}

}
