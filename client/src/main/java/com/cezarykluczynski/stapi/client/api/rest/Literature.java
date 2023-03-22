package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.LiteratureSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.LiteratureApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.LiteratureBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.LiteratureFullResponse;

@SuppressWarnings("ParameterNumber")
public class Literature {

	private final LiteratureApi literatureApi;

	public Literature(LiteratureApi literatureApi) {
		this.literatureApi = literatureApi;
	}

	public LiteratureFullResponse get(String uid) throws ApiException {
		return literatureApi.v1GetLiterature(uid);
	}

	@Deprecated
	public LiteratureBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Boolean earthlyOrigin,
			Boolean shakespeareanWork, Boolean report, Boolean scientificLiterature, Boolean technicalManual, Boolean religiousLiterature)
			throws ApiException {
		return literatureApi.v1SearchLiterature(pageNumber, pageSize, sort, title, earthlyOrigin, shakespeareanWork, report,
				scientificLiterature, technicalManual, religiousLiterature);
	}

	public LiteratureBaseResponse search(LiteratureSearchCriteria literatureSearchCriteria) throws ApiException {
		return literatureApi.v1SearchLiterature(literatureSearchCriteria.getPageNumber(), literatureSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(literatureSearchCriteria.getSort()), literatureSearchCriteria.getTitle(),
				literatureSearchCriteria.getEarthlyOrigin(), literatureSearchCriteria.getShakespeareanWork(), literatureSearchCriteria.getReport(),
				literatureSearchCriteria.getScientificLiterature(), literatureSearchCriteria.getTechnicalManual(),
				literatureSearchCriteria.getReligiousLiterature());
	}

}
