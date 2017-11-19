package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.VideoReleaseApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse;

@SuppressWarnings("ParameterNumber")
public class VideoRelease {

	private final VideoReleaseApi videoReleaseApi;

	private final String apiKey;

	public VideoRelease(VideoReleaseApi videoReleaseApi, String apiKey) {
		this.videoReleaseApi = videoReleaseApi;
		this.apiKey = apiKey;
	}

	public VideoReleaseFullResponse get(String uid) throws ApiException {
		return videoReleaseApi.videoReleaseGet(uid, apiKey);
	}

	public VideoReleaseBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer yearFrom, Integer yearTo,
			Integer runTimeFrom, Integer runTimeTo) throws ApiException {
		return videoReleaseApi.videoReleaseSearchPost(pageNumber, pageSize, sort, apiKey, title, yearFrom, yearTo, runTimeFrom, runTimeTo);
	}

}
