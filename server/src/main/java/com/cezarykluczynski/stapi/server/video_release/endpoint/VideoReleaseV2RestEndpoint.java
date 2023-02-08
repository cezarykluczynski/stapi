package com.cezarykluczynski.stapi.server.video_release.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2FullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseV2RestBeanParams;
import com.cezarykluczynski.stapi.server.video_release.reader.VideoReleaseV2RestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class VideoReleaseV2RestEndpoint {

	public static final String ADDRESS = "/v2/rest/videoRelease";

	private final VideoReleaseV2RestReader videoReleaseV2RestReader;

	public VideoReleaseV2RestEndpoint(VideoReleaseV2RestReader videoReleaseV2RestReader) {
		this.videoReleaseV2RestReader = videoReleaseV2RestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public VideoReleaseV2FullResponse getVideoRelease(@QueryParam("uid") String uid) {
		return videoReleaseV2RestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public VideoReleaseV2BaseResponse searchVideoRelease(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return videoReleaseV2RestReader.readBase(VideoReleaseV2RestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public VideoReleaseV2BaseResponse searchVideoRelease(@BeanParam VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams) {
		return videoReleaseV2RestReader.readBase(videoReleaseV2RestBeanParams);
	}

}
