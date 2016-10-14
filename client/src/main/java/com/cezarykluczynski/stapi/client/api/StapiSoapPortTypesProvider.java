package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.soap.SeriesService;
import lombok.Getter;

import javax.xml.ws.BindingProvider;
import java.util.Map;

public class StapiSoapPortTypesProvider extends AbstractStapiClient implements StapiClient {

	private String apiUrl;

	@Getter
	private SeriesPortType seriesPortType;

	public StapiSoapPortTypesProvider() {
		seriesPortType = new SeriesService().getSeriesPortType();
	}

	public StapiSoapPortTypesProvider(String apiUrl) {
		this.apiUrl = apiUrl;
		seriesPortType = ((SeriesPortType) changeUrl(new SeriesService().getSeriesPortType()));
	}

	private Object changeUrl(Object service) {
		BindingProvider bindingProvider = (BindingProvider) service;
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		String newServiceUrl = changeBaseUrl(apiUrl,
				(String) requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newServiceUrl);
		return service;
	}

}
