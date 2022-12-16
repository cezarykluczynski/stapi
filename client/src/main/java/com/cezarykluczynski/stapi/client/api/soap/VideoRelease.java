package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleasePortType;

public class VideoRelease {

	private final VideoReleasePortType videoReleasePortType;

	public VideoRelease(VideoReleasePortType videoReleasePortType) {
		this.videoReleasePortType = videoReleasePortType;
	}

	@Deprecated
	public VideoReleaseFullResponse get(VideoReleaseFullRequest request) {
		return videoReleasePortType.getVideoReleaseFull(request);
	}

	@Deprecated
	public VideoReleaseBaseResponse search(VideoReleaseBaseRequest request) {
		return videoReleasePortType.getVideoReleaseBase(request);
	}

}

