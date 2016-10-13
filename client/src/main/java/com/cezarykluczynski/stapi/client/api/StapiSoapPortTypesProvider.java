package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.soap.SeriesService;
import lombok.Getter;

import javax.xml.ws.BindingProvider;
import java.util.Map;

public class StapiSoapPortTypesProvider {

	private final String canonicalApiUrl = "http://stapi.co/";

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
		String newServiceUrl = ((String) requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY))
				.replace(canonicalApiUrl, apiUrl);
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newServiceUrl);
		return service;
	}

}
