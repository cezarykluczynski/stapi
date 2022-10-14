package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.LiteratureApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFullResponse;

@SuppressWarnings("ParameterNumber")
public class Literature {

	private final LiteratureApi literatureApi;

	private final String apiKey;

	public Literature(LiteratureApi literatureApi, String apiKey) {
		this.literatureApi = literatureApi;
		this.apiKey = apiKey;
	}

	public LiteratureFullResponse get(String uid) throws ApiException {
		return literatureApi.v1RestLiteratureGet(uid, apiKey);
	}

	public LiteratureBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Boolean earthlyOrigin,
			Boolean shakespeareanWork, Boolean report, Boolean scientificLiterature, Boolean technicalManual, Boolean religiousLiterature)
			throws ApiException {
		return literatureApi.v1RestLiteratureSearchPost(pageNumber, pageSize, sort, apiKey, title, earthlyOrigin, shakespeareanWork, report,
				scientificLiterature, technicalManual, religiousLiterature);
	}

}
