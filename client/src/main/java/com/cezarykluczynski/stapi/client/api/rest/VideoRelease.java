package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.VideoReleaseApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2SearchCriteria;

public class VideoRelease {

	private final VideoReleaseApi videoReleaseApi;

	public VideoRelease(VideoReleaseApi videoReleaseApi) {
		this.videoReleaseApi = videoReleaseApi;
	}

	public VideoReleaseV2FullResponse getV2(String uid) throws ApiException {
		return videoReleaseApi.v2GetVideoRelease(uid);
	}

	public VideoReleaseV2BaseResponse searchV2(VideoReleaseV2SearchCriteria videoReleaseSearchCriteria) throws ApiException {
		return videoReleaseApi.v2SearchVideoReleases(videoReleaseSearchCriteria.getPageNumber(), videoReleaseSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(videoReleaseSearchCriteria.getSort()), videoReleaseSearchCriteria.getTitle(),
				videoReleaseSearchCriteria.getYearFrom(), videoReleaseSearchCriteria.getYearTo(), videoReleaseSearchCriteria.getRunTimeFrom(),
				videoReleaseSearchCriteria.getRunTimeTo(), videoReleaseSearchCriteria.getDocumentary(),
				videoReleaseSearchCriteria.getSpecialFeatures());
	}

}
