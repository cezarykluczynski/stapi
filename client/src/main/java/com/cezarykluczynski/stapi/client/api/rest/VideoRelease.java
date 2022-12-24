package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.VideoReleaseSearchCriteria;
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

	@Deprecated
	public VideoReleaseBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer yearFrom, Integer yearTo,
			Integer runTimeFrom, Integer runTimeTo) throws ApiException {
		return videoReleaseApi.v1RestVideoReleaseSearchPost(pageNumber, pageSize, sort, null, title, yearFrom, yearTo, runTimeFrom, runTimeTo);
	}

	public VideoReleaseBaseResponse search(VideoReleaseSearchCriteria videoReleaseSearchCriteria) throws ApiException {
		return videoReleaseApi.v1RestVideoReleaseSearchPost(videoReleaseSearchCriteria.getPageNumber(), videoReleaseSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(videoReleaseSearchCriteria.getSort()), null, videoReleaseSearchCriteria.getTitle(),
				videoReleaseSearchCriteria.getYearFrom(), videoReleaseSearchCriteria.getYearTo(), videoReleaseSearchCriteria.getRunTimeFrom(),
				videoReleaseSearchCriteria.getRunTimeTo());
	}

}
