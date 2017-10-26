package com.cezarykluczynski.stapi.server.video_game.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGamePortType;
import com.cezarykluczynski.stapi.server.video_game.reader.VideoGameSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class VideoGameSoapEndpoint implements VideoGamePortType {

	public static final String ADDRESS = "/v1/soap/videoGame";

	private final VideoGameSoapReader videoGameSoapReader;

	public VideoGameSoapEndpoint(VideoGameSoapReader videoGameSoapReader) {
		this.videoGameSoapReader = videoGameSoapReader;
	}

	@Override
	public VideoGameBaseResponse getVideoGameBase(@WebParam(partName = "request", name = "VideoGameBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/videoGame") VideoGameBaseRequest request) {
		return videoGameSoapReader.readBase(request);
	}

	@Override
	public VideoGameFullResponse getVideoGameFull(@WebParam(partName = "request", name = "VideoGameFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/videoGame") VideoGameFullRequest request) {
		return videoGameSoapReader.readFull(request);
	}

}
