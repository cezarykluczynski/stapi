package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.ComicCollectionApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionV2FullResponse;

public class ComicCollection {

	private final ComicCollectionApi comicCollectionApi;

	public ComicCollection(ComicCollectionApi comicCollectionApi) {
		this.comicCollectionApi = comicCollectionApi;
	}

	public ComicCollectionV2FullResponse getV2(String uid) throws ApiException {
		return comicCollectionApi.v2GetComicCollection(uid);
	}

	public ComicCollectionBaseResponse search(ComicCollectionSearchCriteria comicCollectionSearchCriteria) throws ApiException {
		return comicCollectionApi.v1SearchComicCollections(comicCollectionSearchCriteria.getPageNumber(),
				comicCollectionSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(comicCollectionSearchCriteria.getSort()),
				comicCollectionSearchCriteria.getTitle(), comicCollectionSearchCriteria.getPublishedYearFrom(),
				comicCollectionSearchCriteria.getPublishedYearTo(), comicCollectionSearchCriteria.getNumberOfPagesFrom(),
				comicCollectionSearchCriteria.getNumberOfPagesTo(), comicCollectionSearchCriteria.getStardateFrom(),
				comicCollectionSearchCriteria.getStardateTo(), comicCollectionSearchCriteria.getYearFrom(),
				comicCollectionSearchCriteria.getYearTo(), comicCollectionSearchCriteria.getPhotonovel());
	}

}
