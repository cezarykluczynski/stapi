package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;
import lombok.Getter;

public class StapiSoapClient {

	private StapiSoapPortTypesProvider stapiSoapPortTypesProvider;

	@Getter
	private SeriesPortType seriesPortType;

	@Getter
	private PerformerPortType performerPortType;

	@Getter
	private StaffPortType staffPortType;

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
		this.performerPortType = stapiSoapPortTypesProvider.getPerformerPortType();
		this.staffPortType = stapiSoapPortTypesProvider.getStaffPortType();
	}

}
