package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.rest.api.DataVersionApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;

public class DataVersion {

	private final DataVersionApi dataVersionApi;

	public DataVersion(DataVersionApi dataVersionApi) {
		this.dataVersionApi = dataVersionApi;
	}

	public com.cezarykluczynski.stapi.client.rest.model.DataVersion getDataVersion() throws ApiException {
		return dataVersionApi.v1Get();
	}

}
