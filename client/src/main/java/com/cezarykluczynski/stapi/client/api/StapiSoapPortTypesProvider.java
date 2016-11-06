package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.soap.*;
import lombok.Getter;

import javax.xml.ws.BindingProvider;
import java.util.Map;

public class StapiSoapPortTypesProvider extends AbstractStapiClient implements StapiClient {

	private String apiUrl;

	@Getter
	private SeriesPortType seriesPortType;

	@Getter
	private PerformerPortType performerPortType;

	@Getter
	private StaffPortType staffPortType;

	public StapiSoapPortTypesProvider() {
		seriesPortType = new SeriesService().getSeriesPortType();
		performerPortType = new PerformerService().getPerformerPortType();
		staffPortType = new StaffService().getStaffPortType();
	}

	public StapiSoapPortTypesProvider(String apiUrl) {
		this.apiUrl = apiUrl;
		seriesPortType = (SeriesPortType) changeUrl(new SeriesService().getSeriesPortType());
		performerPortType = (PerformerPortType) changeUrl(new PerformerService().getPerformerPortType());
		staffPortType = (StaffPortType) changeUrl(new StaffService().getStaffPortType());
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
