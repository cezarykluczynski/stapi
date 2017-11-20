package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleasePortType;

public class VideoRelease {

	private final VideoReleasePortType videoReleasePortType;

	private final ApiKeySupplier apiKeySupplier;

	public VideoRelease(VideoReleasePortType videoReleasePortType, ApiKeySupplier apiKeySupplier) {
		this.videoReleasePortType = videoReleasePortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public VideoReleaseFullResponse get(VideoReleaseFullRequest request) {
		apiKeySupplier.supply(request);
		return videoReleasePortType.getVideoReleaseFull(request);
	}

	public VideoReleaseBaseResponse search(VideoReleaseBaseRequest request) {
		apiKeySupplier.supply(request);
		return videoReleasePortType.getVideoReleaseBase(request);
	}

}

