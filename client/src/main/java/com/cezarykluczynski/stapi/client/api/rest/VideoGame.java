package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.VideoGameSearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.VideoGameApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.VideoGameFullResponse;

import java.time.LocalDate;

public class VideoGame {

	private final VideoGameApi videoGameApi;

	public VideoGame(VideoGameApi videoGameApi) {
		this.videoGameApi = videoGameApi;
	}

	public VideoGameFullResponse get(String uid) throws ApiException {
		return videoGameApi.v1Get(uid);
	}

	@Deprecated
	public VideoGameBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, LocalDate releaseDateFrom,
			LocalDate releaseDateTo) throws ApiException {
		return videoGameApi.v1Search(pageNumber, pageSize, sort, title, releaseDateFrom, releaseDateTo);
	}

	public VideoGameBaseResponse search(VideoGameSearchCriteria videoGameSearchCriteria) throws ApiException {
		return videoGameApi.v1Search(videoGameSearchCriteria.getPageNumber(), videoGameSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(videoGameSearchCriteria.getSort()), videoGameSearchCriteria.getTitle(),
				videoGameSearchCriteria.getReleaseDateFrom(), videoGameSearchCriteria.getReleaseDateTo());
	}

}
