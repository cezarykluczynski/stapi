package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.ComicCollectionSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.ComicCollectionApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class ComicCollection {

	private final ComicCollectionApi comicCollectionApi;

	public ComicCollection(ComicCollectionApi comicCollectionApi) {
		this.comicCollectionApi = comicCollectionApi;
	}

	@Deprecated
	public ComicCollectionFullResponse get(String uid) throws ApiException {
		return comicCollectionApi.v1RestComicCollectionGet(uid);
	}

	public ComicCollectionV2FullResponse getV2(String uid) throws ApiException {
		return comicCollectionApi.v2RestComicCollectionGet(uid);
	}

	@Deprecated
	public ComicCollectionBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer publishedYearFrom,
			Integer publishedYearTo, Integer numberOfPagesFrom, Integer numberOfPagesTo, Float stardateFrom, Float stardateTo, Integer yearFrom,
			Integer yearTo, Boolean photonovel) throws ApiException {
		return comicCollectionApi.v1RestComicCollectionSearchPost(pageNumber, pageSize, sort, title, publishedYearFrom, publishedYearTo,
				numberOfPagesFrom, numberOfPagesTo, stardateFrom, stardateTo, yearFrom, yearTo, photonovel);
	}

	public ComicCollectionBaseResponse search(ComicCollectionSearchCriteria comicCollectionSearchCriteria) throws ApiException {
		return comicCollectionApi.v1RestComicCollectionSearchPost(comicCollectionSearchCriteria.getPageNumber(),
				comicCollectionSearchCriteria.getPageSize(), StapiRestSortSerializer.serialize(comicCollectionSearchCriteria.getSort()),
				comicCollectionSearchCriteria.getTitle(), comicCollectionSearchCriteria.getPublishedYearFrom(),
				comicCollectionSearchCriteria.getPublishedYearTo(), comicCollectionSearchCriteria.getNumberOfPagesFrom(),
				comicCollectionSearchCriteria.getNumberOfPagesTo(), comicCollectionSearchCriteria.getStardateFrom(),
				comicCollectionSearchCriteria.getStardateTo(), comicCollectionSearchCriteria.getYearFrom(),
				comicCollectionSearchCriteria.getYearTo(), comicCollectionSearchCriteria.getPhotonovel());
	}

}
