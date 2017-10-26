package com.cezarykluczynski.stapi.server.video_release.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleasePortType;
import com.cezarykluczynski.stapi.server.video_release.reader.VideoReleaseSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class VideoReleaseSoapEndpoint implements VideoReleasePortType {

	public static final String ADDRESS = "/v1/soap/videoRelease";

	private final VideoReleaseSoapReader videoReleaseSoapReader;

	public VideoReleaseSoapEndpoint(VideoReleaseSoapReader videoReleaseSoapReader) {
		this.videoReleaseSoapReader = videoReleaseSoapReader;
	}

	@Override
	public VideoReleaseBaseResponse getVideoReleaseBase(@WebParam(partName = "request", name = "VideoReleaseBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/videoRelease") VideoReleaseBaseRequest request) {
		return videoReleaseSoapReader.readBase(request);
	}

	@Override
	public VideoReleaseFullResponse getVideoReleaseFull(@WebParam(partName = "request", name = "VideoReleaseFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/videoRelease") VideoReleaseFullRequest request) {
		return videoReleaseSoapReader.readFull(request);
	}

}
