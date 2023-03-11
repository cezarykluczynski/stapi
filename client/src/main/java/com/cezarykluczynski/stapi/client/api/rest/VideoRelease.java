package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.VideoReleaseSearchCriteria;
import com.cezarykluczynski.stapi.client.api.dto.VideoReleaseV2SearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.VideoReleaseApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class VideoRelease {

	private final VideoReleaseApi videoReleaseApi;

	public VideoRelease(VideoReleaseApi videoReleaseApi) {
		this.videoReleaseApi = videoReleaseApi;
	}

	@Deprecated
	public VideoReleaseFullResponse get(String uid) throws ApiException {
		return videoReleaseApi.v1RestVideoReleaseGet(uid);
	}

	public VideoReleaseV2FullResponse getV2(String uid) throws ApiException {
		return videoReleaseApi.v2RestVideoReleaseGet(uid);
	}

	@Deprecated
	public VideoReleaseBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String title, Integer yearFrom, Integer yearTo,
			Integer runTimeFrom, Integer runTimeTo) throws ApiException {
		return videoReleaseApi.v1RestVideoReleaseSearchPost(pageNumber, pageSize, sort, title, yearFrom, yearTo, runTimeFrom, runTimeTo);
	}

	@Deprecated
	public VideoReleaseBaseResponse search(VideoReleaseSearchCriteria videoReleaseSearchCriteria) throws ApiException {
		return videoReleaseApi.v1RestVideoReleaseSearchPost(videoReleaseSearchCriteria.getPageNumber(), videoReleaseSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(videoReleaseSearchCriteria.getSort()), videoReleaseSearchCriteria.getTitle(),
				videoReleaseSearchCriteria.getYearFrom(), videoReleaseSearchCriteria.getYearTo(), videoReleaseSearchCriteria.getRunTimeFrom(),
				videoReleaseSearchCriteria.getRunTimeTo());
	}

	public VideoReleaseV2BaseResponse searchV2(VideoReleaseV2SearchCriteria videoReleaseSearchCriteria) throws ApiException {
		return videoReleaseApi.v2RestVideoReleaseSearchPost(videoReleaseSearchCriteria.getPageNumber(), videoReleaseSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(videoReleaseSearchCriteria.getSort()), videoReleaseSearchCriteria.getTitle(),
				videoReleaseSearchCriteria.getYearFrom(), videoReleaseSearchCriteria.getYearTo(), videoReleaseSearchCriteria.getRunTimeFrom(),
				videoReleaseSearchCriteria.getRunTimeTo(), videoReleaseSearchCriteria.getDocumentary(),
				videoReleaseSearchCriteria.getSpecialFeatures());
	}

}
