package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.VideoGameSearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.VideoGameApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse;

import java.time.LocalDate;

public class VideoGame {

	private final VideoGameApi videoGameApi;

	public VideoGame(VideoGameApi videoGameApi) {
		this.videoGameApi = videoGameApi;
	}

	public VideoGameFullResponse get(String uid) throws ApiException {
		return videoGameApi.v1RestVideoGameGet(uid, null);
	}

	@Deprecated
	public VideoGameBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, LocalDate releaseDateFrom,
			LocalDate releaseDateTo) throws ApiException {
		return videoGameApi.v1RestVideoGameSearchPost(pageNumber, pageSize, sort, null, title, releaseDateFrom, releaseDateTo);
	}

	public VideoGameBaseResponse search(VideoGameSearchCriteria videoGameSearchCriteria) throws ApiException {
		return videoGameApi.v1RestVideoGameSearchPost(videoGameSearchCriteria.getPageNumber(), videoGameSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(videoGameSearchCriteria.getSort()), null, videoGameSearchCriteria.getTitle(),
				videoGameSearchCriteria.getReleaseDateFrom(), videoGameSearchCriteria.getReleaseDateTo());
	}

}
