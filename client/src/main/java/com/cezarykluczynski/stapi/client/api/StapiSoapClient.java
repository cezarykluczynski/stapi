package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.soap.SeriesPortType;
import lombok.Getter;

public class StapiSoapClient {

	private StapiSoapPortTypesProvider stapiSoapPortTypesProvider;

	@Getter
	private SeriesPortType seriesPortType;

	public StapiSoapClient() {
		this.stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider();
		this.bindPortTypes();
	}

	public StapiSoapClient(String apiUrl) {
		this.stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider(apiUrl);
		this.bindPortTypes();
	}

	private void bindPortTypes() {
		this.seriesPortType = stapiSoapPortTypesProvider.getSeriesPortType();
	}


}
