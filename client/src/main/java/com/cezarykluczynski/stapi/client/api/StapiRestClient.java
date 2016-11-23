package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.rest.api.CharacterApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.PerformerApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.SeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.StaffApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiClient;
import lombok.Getter;

public class StapiRestClient extends AbstractStapiClient implements StapiClient {

	private String apiUrl;

	private ApiClient apiClient;

	@Getter
	private SeriesApi seriesApi;

	@Getter
	private PerformerApi performerApi;

	@Getter
	private StaffApi staffApi;

	@Getter
	private CharacterApi characterApi;

	public StapiRestClient() {
		seriesApi = new SeriesApi();
		performerApi = new PerformerApi();
		staffApi = new StaffApi();
		characterApi = new CharacterApi();
	}

	public StapiRestClient(String apiUrl) {
		this.apiUrl = apiUrl;
		createApiClient();
		seriesApi = new SeriesApi(apiClient);
		performerApi = new PerformerApi(apiClient);
		staffApi = new StaffApi(apiClient);
		characterApi = new CharacterApi(apiClient);
	}

	private void createApiClient() {
		apiClient = new ApiClient();
		apiClient.setBasePath(changeBaseUrl(apiUrl, apiClient.getBasePath()));
		apiClient.setConnectTimeout(10000);
	}

}
