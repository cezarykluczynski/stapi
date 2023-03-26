package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.VideoGameApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.VideoGameFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.VideoGameSearchCriteria;

public class VideoGame {

	private final VideoGameApi videoGameApi;

	public VideoGame(VideoGameApi videoGameApi) {
		this.videoGameApi = videoGameApi;
	}

	public VideoGameFullResponse get(String uid) throws ApiException {
		return videoGameApi.v1GetVideoGame(uid);
	}

	public VideoGameBaseResponse search(VideoGameSearchCriteria videoGameSearchCriteria) throws ApiException {
		return videoGameApi.v1SearchVideoGames(videoGameSearchCriteria.getPageNumber(), videoGameSearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(videoGameSearchCriteria.getSort()), videoGameSearchCriteria.getTitle(),
				videoGameSearchCriteria.getReleaseDateFrom(), videoGameSearchCriteria.getReleaseDateTo());
	}

}
