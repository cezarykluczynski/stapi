package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.DataVersionApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;

public class DataVersion {

	private final DataVersionApi dataVersionApi;

	public DataVersion(DataVersionApi dataVersionApi) {
		this.dataVersionApi = dataVersionApi;
	}

	public com.cezarykluczynski.stapi.client.v1.rest.model.DataVersion getDataVersion() throws ApiException {
		return dataVersionApi.v1RestCommonDataVersionGet();
	}

}
