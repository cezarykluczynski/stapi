package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.VideoReleaseApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse;

@SuppressWarnings("ParameterNumber")
public class VideoRelease {

	private final VideoReleaseApi videoReleaseApi;

	public VideoRelease(VideoReleaseApi videoReleaseApi) {
		this.videoReleaseApi = videoReleaseApi;
	}

	public VideoReleaseFullResponse get(String uid) throws ApiException {
		return videoReleaseApi.v1RestVideoReleaseGet(uid, null);
	}

	public VideoReleaseBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer yearFrom, Integer yearTo,
			Integer runTimeFrom, Integer runTimeTo) throws ApiException {
		return videoReleaseApi.v1RestVideoReleaseSearchPost(pageNumber, pageSize, sort, null, title, yearFrom, yearTo, runTimeFrom, runTimeTo);
	}

}
