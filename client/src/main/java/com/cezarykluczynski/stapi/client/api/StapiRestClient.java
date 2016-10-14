package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.rest.api.SeriesApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiClient;
import lombok.Getter;

public class StapiRestClient extends AbstractStapiClient implements StapiClient {

	private String apiUrl;

	private ApiClient apiClient;

	@Getter
	private SeriesApi seriesApi;

	public StapiRestClient() {
		seriesApi = new SeriesApi();
	}

	public StapiRestClient(String apiUrl) {
		this.apiUrl = apiUrl;
		createApiClient();
		seriesApi = new SeriesApi(apiClient);
	}

	private void createApiClient() {
		apiClient = new ApiClient();
		apiClient.setBasePath(changeBaseUrl(apiUrl, apiClient.getBasePath()));
	}

}
