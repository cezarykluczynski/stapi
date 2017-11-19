package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.VideoGameApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse;

import java.time.LocalDate;

public class VideoGame {

	private final VideoGameApi videoGameApi;

	private final String apiKey;

	public VideoGame(VideoGameApi videoGameApi, String apiKey) {
		this.videoGameApi = videoGameApi;
		this.apiKey = apiKey;
	}

	public VideoGameFullResponse get(String uid) throws ApiException {
		return videoGameApi.videoGameGet(uid, apiKey);
	}

	public VideoGameBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, LocalDate releaseDateFrom,
			LocalDate releaseDateTo) throws ApiException {
		return videoGameApi.videoGameSearchPost(pageNumber, pageSize, sort, apiKey, title, releaseDateFrom, releaseDateTo);
	}

}
